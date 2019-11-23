package com.proshine.midware.clouddisk.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.proshine.base.common.constant.CommonConstant;
import com.proshine.base.common.utils.*;
import com.proshine.expo.base.Identities;
import com.proshine.expo.base.dto.Message;
import com.proshine.expo.gateway.security.dto.CustomUserDetails;
import com.proshine.expo.midware.clouddisk.CloudDiskExpo;
import com.proshine.expo.midware.clouddisk.entity.Attachment;
import com.proshine.expo.midware.clouddisk.entity.TotalClass;
import com.proshine.expo.midware.file.FileExpo;
import com.proshine.expo.terminal.terminal.entity.TbTerminal;
import com.proshine.midware.clouddisk.dao.AttachmentDao;
import com.proshine.midware.clouddisk.support.AttachmentSuffixConvert;
import com.proshine.midware.clouddisk.support.AttachmentType;
import com.proshine.midware.clouddisk.support.MultipartFileWrapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 文件服务接口实现
 *
 * @author 高孙琼
 */
@Log4j2
@Service
@com.alibaba.dubbo.config.annotation.Service
@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
public class CloudDiskHandler implements CloudDiskExpo {

    private static final ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
    /* 文件小于该值时不需要队列直接下载，否则加入队列 */
    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024;
    @Autowired
    private AttachmentDao attachmentDao;
    @Reference(group = "mongo")
    private FileExpo mongoHandler;

    @Override
    public Attachment insert(Attachment attachment, InputStream inputStream) {
        try {
            // 设置文件id
            attachment.setId(Identities.uuid2());

            // 设置创建者信息
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            attachment.setCstmId(userDetails.getCstmId());
            attachment.setCreatorId(userDetails.getUserId());
            attachment.setCreatorName(userDetails.getUsername());

            attachment.setCheckStatus("1");
            attachment.setCheckUserId(userDetails.getUserId());
            attachment.setCheckUserName(userDetails.getUsername());
            attachment.setCheckTime(new Date());

            // 检测附件md5码是否已经存在，如果不存在，则是新的文件，写入文件系统
            String fullPath = attachment.getPath();
            String filename = FilenameUtils.getName(fullPath);
            String path = FilenameUtils.getFullPath(fullPath).replaceAll("/$", "");
            if (!mongoHandler.exists(path, filename, new ObjectId(attachment.getMd5()))) {
                uploadMaterialToMongodb(attachment, path, filename, inputStream);
            }

            // 保存附件对象
            attachmentDao.insert(attachment);

            return attachment;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Attachment upload(String parentId, String libraryType, MultipartFile file) {
        try {

            // 构建附件对象
            Attachment attachment = new MultipartFileWrapper(file).toAttachment();
            // 设置文件id
            attachment.setId(Identities.uuid2());
            attachment.setParentId(parentId);
            attachment.setLibraryType(libraryType);

            // 设置创建者信息
            CustomUserDetails userDetails = getUserDetails();
            Set<GrantedAuthority> set = userDetails.getAuthorities();
            for (org.springframework.security.core.GrantedAuthority GrantedAuthority : set) {
                if (GrantedAuthority.getAuthority().equals(CommonConstant.ROLE_CHECK) || GrantedAuthority.getAuthority().equals(CommonConstant.ROLE_SUPER)) {
                    attachment.setCheckStatus("1");
                    attachment.setCheckTime(new Date());
                    attachment.setCheckUserId(userDetails.getUserId());
                    attachment.setCheckUserName(userDetails.getUsername());
                }
            }
            attachment.setCstmId(userDetails.getCstmId());
            attachment.setCreatorId(userDetails.getUserId());
            attachment.setCreatorName(userDetails.getUsername());

            // 检测附件md5码是否已经存在，如果不存在，则是新的文件，写入文件系统
            String fullPath = attachment.getPath();
            String filename = FilenameUtils.getName(fullPath);
            String path = FilenameUtils.getFullPath(fullPath).replaceAll("/$", "");
            if (!mongoHandler.exists(path, filename, new ObjectId(attachment.getMd5()))) {
                uploadMaterialToMongodb(attachment, path, filename, file.getInputStream());
            }

            // 数据库VARCHAR针对名称定义的长度为256大小
            if (attachment.getName().length() > CommonConstant.DB_COMMON_VARCHAR_MAX_LENGTH) {
                attachment.setName(attachment.getName()
                        .substring(0, CommonConstant.DB_COMMON_VARCHAR_MAX_LENGTH));
            }
            attachmentDao.insert(attachment);

            // 更新父目录的更新时间
            Attachment parentAttachment = attachmentDao.find(parentId);
            if (parentAttachment != null) {
                attachmentDao.update(parentAttachment);
            }

            return attachment;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 上传图片并根据素材类型截取预览图
     *
     * @param attachment  素材
     * @param path        路径
     * @param filename    文件名
     * @param inputStream 文件流
     */
    private void uploadMaterialToMongodb(Attachment attachment, String path, String filename, InputStream inputStream) {
        try {
            if (attachment.getType() == 1) { // 图片
                File tempFile = new File(Objects.requireNonNull(Objects.requireNonNull(ContextLoader.getCurrentWebApplicationContext())
                        .getServletContext()).getRealPath(attachment.getMd5()));
                org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, tempFile);

                File previewFile = new File(Objects.requireNonNull(ContextLoader.getCurrentWebApplicationContext()
                        .getServletContext()).getRealPath(attachment.getMd5()) + "_preview." + attachment.getSuffix());

                Thumbnails.of(tempFile).size(160, 160).toFile(previewFile);

                InputStream previewInputStream = new FileInputStream(previewFile);
                mongoHandler.addOne(path, filename + "_preview", new ObjectId(attachment.getMd5() + "_preview"), previewInputStream);
                previewInputStream.close();
                previewFile.delete();

                InputStream tempInputStream = new FileInputStream(tempFile);
                mongoHandler.addOne(path, filename, new ObjectId(attachment.getMd5()), tempInputStream);
                tempInputStream.close();
                tempFile.delete();
            } else if (attachment.getType() == 4) {  // 视频
                File tempFile = new File(Objects.requireNonNull(Objects.requireNonNull(ContextLoader.getCurrentWebApplicationContext()).getServletContext()).getRealPath(attachment.getMd5()));
                org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, tempFile);
                for (int i = 0; i < 6; i++) {
                    String videoImagePath = Objects.requireNonNull(ContextLoader.getCurrentWebApplicationContext()
                            .getServletContext()).getRealPath(attachment.getMd5()) + "_preview_" + (i + 1);
                    ImageUtil.captureVideoImage(tempFile.getPath(), videoImagePath,
                            (i + 1) * 2, 800, 600);
                    File file = new File(videoImagePath);
                    boolean exists = file.exists();
                    if (!exists) {
                        break;
                    }
                    InputStream stream = new FileInputStream(file);
                    mongoHandler.addOne(path, filename + "_preview_" + (i + 1),
                            new ObjectId(attachment.getMd5() + "_preview_" + (i + 1)), stream);
                    stream.close();
                    file.delete();
                }

                InputStream tempInputStream = new FileInputStream(tempFile);
                mongoHandler.addOne(path, filename, new ObjectId(attachment.getMd5()), tempInputStream);
                tempInputStream.close();
                tempFile.delete();
            } else if (attachment.getType() == 2) {
                String[] pdfName = filename.split("\\.");
                File tempFile = File.createTempFile(attachment.getMd5(), ".doc");
                org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, tempFile);
                if (pdfName.length > 1) {
                    String suffix = pdfName[pdfName.length - 1].toLowerCase();
                    if (!"pdf".equals(suffix) && !"txt".equals(suffix)) {
                        //转化成PDF文件
                        File pdfFile = DocToPdf.createPdf(tempFile);
                        FileInputStream pdfStream = new FileInputStream(pdfFile);
                        mongoHandler.addOne(path, pdfName[0] + ".pdf", new ObjectId(attachment.getMd5() + "_pdf"), pdfStream);
                        pdfStream.close();
                        pdfFile.delete();
                    }
                }
                InputStream tempStream = new FileInputStream(tempFile);
                mongoHandler.addOne(path, filename, new ObjectId(attachment.getMd5()), tempStream);
                tempStream.close();

                tempFile.delete();

            } else {
                // 将文件写入存储介质
                mongoHandler.addOne(path, filename, new ObjectId(attachment.getMd5()), inputStream);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String ids) {
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                Attachment attachment = attachmentDao.find(id);
                attachmentDao.delete(id);
                deleteMongodbFile(attachment.getPath(), attachment.getMd5(), attachment.getType());
            }
        } catch (Exception e) {
            log.error("删除文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void transferRecycle(String ids) {
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                Attachment attachment = attachmentDao.find(id);
                if (attachment.getType() == AttachmentType.NULL.id()) {
                    List<Attachment> attachments = attachmentDao.getByParentId(attachment.getId());
                    if (CollectionUtils.isEmpty(attachments)) {
                        // 没有子项则直接删除目录
                        attachmentDao.delete(attachment.getId());
                    }
                } else {
                    // 文件则直接移到回收站
                    attachmentDao.updateDelete(id, "1");
                }

                // 更新父目录的更新时间
                Attachment parentAttachment = attachmentDao.find(attachment.getParentId());
                if (parentAttachment != null) {
                    attachmentDao.update(parentAttachment);
                }

            }
        } catch (Exception e) {
            log.error("移到回收站失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void recoveryFile(String ids) {
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                Attachment attachment = attachmentDao.find(id);
                Attachment parentAttachment = attachmentDao.find(attachment.getParentId());
                if (parentAttachment == null) {
                    attachment.setParentId("0");
                }
                attachment.setIsDelete("0"); // 修改删除标记为不删除
                attachmentDao.update(attachment);
            }
        } catch (Exception e) {
            log.error("还原文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void clearRecycle() {
        try {
            Map param = new HashMap();
            List<Attachment> attachmentList = this.findRecycles(param);
            for (Attachment attachment : attachmentList) {
                attachmentDao.delete(attachment.getId());
                deleteMongodbFile(attachment.getPath(), attachment.getMd5(), attachment.getType());
            }
        } catch (Exception e) {
            log.error("清空回收站失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void check(String id, String checkStatus) {
        try {
            Attachment attachment = attachmentDao.find(id);

            CustomUserDetails userDetails = getUserDetails();
            attachment.setCheckUserId(userDetails.getUserId());
            attachment.setCheckUserName(userDetails.getUsername());
            attachment.setCheckStatus(checkStatus);
            attachment.setCheckTime(new Date());

            attachmentDao.update(attachment);
        } catch (Exception e) {
            log.error("审核文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void transfer(String ids, String newParentId) {
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                if (!this.isContainFile(id, newParentId)) {
                    Attachment attachment = attachmentDao.find(id);
                    transferAndUpdate(attachment, newParentId);
                }
            }
        } catch (Exception e) {
            log.error("文件/目录移动失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void copy(String ids, String newParentId) {
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                if (!this.isContainFile(id, newParentId)) {
                    Attachment attachment = attachmentDao.find(id);
                    copyAndPase(attachment, newParentId);
                }
            }
        } catch (Exception e) {
            log.error("文件/目录复制失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Page<Attachment> query(PageRequest pageRequest, Map terms, String orderName, String order) {
        try {
            terms.put("isDelete", "0");

            long count;
            List<Attachment> attachmentList;

            String libraryType = (String) terms.get("libraryType");
            if ("2".equals(libraryType)) {
                String userId = getUserDetails().getUserId();
                terms.put("userId", userId);

                count = attachmentDao.countCloud(terms);
                attachmentList = attachmentDao.queryCloud(pageRequest, terms,
                        StringUtils.propertyToField(orderName), StringUtils.propertyToField(order));
            } else {
                count = attachmentDao.count(terms);
                attachmentList = attachmentDao.query(pageRequest, terms,
                        StringUtils.propertyToField(orderName), StringUtils.propertyToField(order));
            }

            return new PageImpl<>(attachmentList, pageRequest, count);
        } catch (Exception e) {
            log.error("文件/目录搜索失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List<Attachment> finds(Map terms) {
        try {
            terms.put("isDelete", "0");

            List<Attachment> attachmentList;

            String libraryType = (String) terms.get("libraryType");
            if ("2".equals(libraryType)) {
                String userId = getUserDetails().getUserId();
                terms.put("userId", userId);

                attachmentList = attachmentDao.findsCloud(terms);
            } else {
                attachmentList = attachmentDao.finds(terms);
            }

            return attachmentList;
        } catch (Exception e) {
            log.error("文件/目录搜索失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<Attachment> queryRecycle(PageRequest pageRequest, Map terms, String orderName, String order) {
        try {
            terms.put("isDelete", "1");
            long count = attachmentDao.count(terms);
            List<Attachment> attachmentList = attachmentDao.query(pageRequest, terms, StringUtils.propertyToField(orderName), StringUtils.propertyToField(order));

            return new PageImpl<>(attachmentList, pageRequest, count);
        } catch (Exception e) {
            log.error("获取回收站分页列表失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List<Attachment> findRecycles(Map terms) {
        try {
            terms.put("isDelete", "1");
            return (List<Attachment>) attachmentDao.finds(terms);
        } catch (Exception e) {
            log.error("获取回收站列表失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Object read(String path, String md5) {
        try {
            String filename = FilenameUtils.getName(path);
            path = FilenameUtils.getFullPath(path).replaceAll("/$", "");
            return mongoHandler.getOne(path, filename, new ObjectId(md5));
        } catch (Exception e) {
            log.error("读取文件失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Object readCache(String path, String md5, long size) {
        try {
            String filename = FilenameUtils.getName(path);
            path = FilenameUtils.getFullPath(path).replaceAll("/$", "");

            if (size > MAX_FILE_SIZE) {
                while (queue.size() >= 3) {
                    Thread.sleep(100);
                }
                queue.offer(md5);
            }

            return mongoHandler.getOne(path, filename, new ObjectId(md5));
        } catch (Exception e) {
            log.error("读取文件失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteCache(String path, String md5, long size) {
        if (size > MAX_FILE_SIZE) {
            queue.poll();
        }
    }

    @Override
    public boolean exists(String path, String md5) {
        String filename = FilenameUtils.getName(path);
        path = FilenameUtils.getFullPath(path).replaceAll("/$", "");
        return mongoHandler.exists(path, filename, new ObjectId(md5));
    }

    @Override
    public Attachment find(String id) {
        try {
            return attachmentDao.find(id);
        } catch (Exception e) {
            log.error("读取文件信息失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 移动文件/目录并更新记录
     *
     * @param attachment  文件/目录
     * @param newParentId 新的目录id
     */
    private void transferAndUpdate(Attachment attachment, String newParentId) {
        String id = attachment.getId();

        // 根目录
        if ("0".equals(newParentId) || "1".equals(newParentId) || "2".equals(newParentId)) {
            attachment.setLibraryType(newParentId);
            newParentId = "0";
        } else {
            Attachment parentAttachment = attachmentDao.find(newParentId);
            attachment.setLibraryType(parentAttachment.getLibraryType());
        }

        attachment.setParentId(newParentId);

        attachmentDao.update(attachment);

        List<Attachment> subAttachments = attachmentDao.getByParentId(id);
        for (Attachment subAttachment : subAttachments) {
            copyAndPase(subAttachment, id);
        }
    }

    /**
     * 复制文件/目录并重新插入新的记录
     *
     * @param attachment  文件/目录
     * @param newParentId 新的目录id
     */
    private void copyAndPase(Attachment attachment, String newParentId) {
        String newId = Identities.uuid2();
        String oldId = attachment.getId();

        // 根目录
        if ("0".equals(newParentId) || "1".equals(newParentId) || "2".equals(newParentId)) {
            attachment.setLibraryType(newParentId);
            newParentId = "0";
        } else {
            Attachment parentAttachment = attachmentDao.find(newParentId);
            attachment.setLibraryType(parentAttachment.getLibraryType());
        }

        attachment.setId(newId);
        attachment.setParentId(newParentId);
        attachmentDao.insert(attachment);

        List<Attachment> subAttachments = attachmentDao.getByParentId(oldId);
        for (Attachment subAttachment : subAttachments) {
            copyAndPase(subAttachment, newId);
        }
    }

    /**
     * 检测md5码是否存在
     *
     * @param md5 md5字符串
     * @return true：存在，false:不存在
     */
    private boolean checkMd5(String md5) {
        try {
            long count = attachmentDao.checkmd5(md5);
            return count > 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 删除mongodb中的文件checkmd5
     * <p>如果文件被重复使用则不删除mongodb中的文件</p>
     *  @param path 文件路径
     * @param md5  文件md5
     * @param type 文件类型
     */
    public void deleteMongodbFile(String path, String md5, int type) {
        if (!checkMd5(md5)) {
            String filename = FilenameUtils.getName(path);
            path = FilenameUtils.getFullPath(path).replaceAll("/$", "");
            mongoHandler.deleteOne(path, filename, new ObjectId(md5));

            // 删除预览图
            if (type == 1) { // 图片
                mongoHandler.deleteOne(path, filename + "_preview", new ObjectId(md5 + "_preview"));
            } else if (type == 4) { // 视频
                for (int i = 0; i < 6; i++) {
                    mongoHandler.deleteOne(path, filename + "_preview_" + (i + 1), new ObjectId(md5 + "_preview_" + (i + 1)));
                }
            }

        }
    }

    /**
     * 获取session中当前登录的用户的信息
     */
    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @Override
    public Message<String> create(Attachment attachment) {
        try {
            CustomUserDetails userDetails = getUserDetails();

            Attachment folder = attachmentDao.getFolderByName(attachment.getName(), userDetails.getCstmId(), userDetails.getUserId());

            if (folder != null) {
                return new Message<>(45000, "文件夹不能重名", null);
            }
            attachment.setId(Identities.uuid2());
            attachment.setType(AttachmentType.NULL.id());

            // 设置创建者信息
            attachment.setCstmId(userDetails.getCstmId());
            attachment.setCreatorId(userDetails.getUserId());
            attachment.setCreatorName(userDetails.getUsername());
            attachmentDao.insert(attachment);
            return new Message<>(45001, "创建成功", null);
        } catch (Exception e) {
            log.error("创建目录失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Attachment attachment) {
        try {
            attachmentDao.update(attachment);
        } catch (Exception e) {
            log.error("更新目录失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<TotalClass> getTotalAttach(String cstmId) {
        return attachmentDao.getTotalAttach(cstmId);
    }

    @Override
    public List<Attachment> findByNameAndSuffix(String name, String suffix, String cstmId, String userId) {
        // 数据库VARCHAR针对名称定义的长度为256大小
        if (name != null && name.length() > CommonConstant.DB_COMMON_VARCHAR_MAX_LENGTH) {
            name = name.substring(0, CommonConstant.DB_COMMON_VARCHAR_MAX_LENGTH);
        }

        return attachmentDao.findByNameAndSuffix(name, suffix, cstmId, userId);
    }

    @Override
    public Message<String> createTxt(String id, String parentId, String libraryType, String txtTitle, String txtContent) {
        try {
            String suffix = "txt";
            String basePath = FileUtils.getBasePath() + "tempFile" + File.separator;
            String fileName = basePath + txtTitle + "." + suffix;
            System.out.println(fileName);
            // 如果路径不存在则新建目录
            if (!FileUtils.isFileExist(basePath)) {
                new File(basePath).mkdirs();
            }

            boolean isSuccess = FileUtils.writeFile(fileName, txtContent, false);
            if (isSuccess) {
                File file = new File(fileName);

                if (StringUtils.isEmpty(id)) {
                    // 新建文本文件
                    Attachment attachment = new Attachment();
                    attachment.setParentId(parentId);
                    attachment.setLibraryType(libraryType);

                    attachment.setName(txtTitle);
                    attachment.setSuffix(suffix);

                    String fileDir = CommonConstant.MongoDBCollections.TOUCHSYS_CLOUD +
                            "/" +
                            txtTitle +
                            "." +
                            suffix;
                    attachment.setPath(fileDir);

                    attachment.setSize(file.length());

                    InputStream inputStream = new FileInputStream(file);
                    String fileVerifyCode = DigestUtils.md5DigestAsHex(inputStream);
                    attachment.setMd5(fileVerifyCode);

                    attachment.setType(AttachmentSuffixConvert.convert(attachment.getSuffix()).id());

                    InputStream tempInputStream = new FileInputStream(file);
                    this.insert(attachment, tempInputStream);

                    inputStream.close();
                    tempInputStream.close();
                    FileUtils.deleteFile(fileName);
                } else {
                    // 编辑文本文件：更新名称和内容
                    Attachment attachment = this.find(id);

                    attachment.setName(txtTitle);

                    String fileDir = CommonConstant.MongoDBCollections.TOUCHSYS_CLOUD +
                            "/" +
                            txtTitle +
                            "." +
                            suffix;
                    attachment.setPath(fileDir);

                    attachment.setSize(file.length());

                    InputStream inputStream = new FileInputStream(file);
                    String fileVerifyCode = DigestUtils.md5DigestAsHex(inputStream);
                    attachment.setMd5(fileVerifyCode);

                    // 删除原文本文件
                    deleteMongodbFile(attachment.getPath(), attachment.getMd5(), attachment.getType());

                    // 检测附件md5码是否已经存在，如果不存在，则是新的文件，写入文件系统
                    String fullPath = attachment.getPath();
                    String filename = FilenameUtils.getName(fullPath);
                    String path = FilenameUtils.getFullPath(fullPath).replaceAll("/$", "");
                    InputStream tempInputStream = new FileInputStream(file);
                    if (!mongoHandler.exists(path, filename, new ObjectId(attachment.getMd5()))) {
                        uploadMaterialToMongodb(attachment, path, filename, tempInputStream);
                    }

                    // 更新文件信息
                    this.update(attachment);

                    inputStream.close();
                    tempInputStream.close();
                    FileUtils.deleteFile(fileName);
                }

                // 更新父目录的更新时间
                Attachment parentAttachment = attachmentDao.find(parentId);
                if (parentAttachment != null) {
                    attachmentDao.update(parentAttachment);
                }
            }

            return new Message<>(45001, "创建文本文件成功", null);
        } catch (Exception e) {
            log.error("创建文本文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void createCloudMaterial(TbTerminal terminal) {
        String cstmId = terminal.getCstmId();
        String terminalId = terminal.getId();
        String terminalName = terminal.getTerminalName();

        // 判断终端云素材是否存在
        Attachment folder = attachmentDao.getCloudWidgetTerminalFolder(terminalId, cstmId);
        if (folder != null) {
            folder.setName(terminalName);
            folder.setCreatorName(terminalName);
            attachmentDao.update(folder);
            return;
        }

        Attachment attachment = new Attachment();
        attachment.setId(Identities.uuid2());
        attachment.setType(AttachmentType.NULL.id());
        attachment.setName(terminalName);
        attachment.setParentId("0");
        attachment.setLibraryType("2");

        // 设置创建者信息
        attachment.setCstmId(cstmId);
        attachment.setCreatorId(terminalId);
        attachment.setCreatorName(terminalName);
        attachmentDao.insert(attachment);

        // 云文档
        Attachment wordAttachment = new Attachment();
        wordAttachment.setId(Identities.uuid2());
        wordAttachment.setType(AttachmentType.NULL.id());
        wordAttachment.setName("云文档");
        wordAttachment.setParentId(attachment.getId());
        wordAttachment.setLibraryType("2");

        // 设置创建者信息
        wordAttachment.setCstmId(cstmId);
        wordAttachment.setCreatorId(terminalId);
        wordAttachment.setCreatorName(terminalName);
        attachmentDao.insert(wordAttachment);

        // 云图片
        Attachment imageAttachment = new Attachment();
        imageAttachment.setId(Identities.uuid2());
        imageAttachment.setType(AttachmentType.NULL.id());
        imageAttachment.setName("云图片");
        imageAttachment.setParentId(attachment.getId());
        imageAttachment.setLibraryType("2");

        // 设置创建者信息
        imageAttachment.setCstmId(cstmId);
        imageAttachment.setCreatorId(terminalId);
        imageAttachment.setCreatorName(terminalName);
        attachmentDao.insert(imageAttachment);

        // 云文字
        Attachment textAttachment = new Attachment();
        textAttachment.setId(Identities.uuid2());
        textAttachment.setType(AttachmentType.NULL.id());
        textAttachment.setName("云文字");
        textAttachment.setParentId(attachment.getId());
        textAttachment.setLibraryType("2");

        // 设置创建者信息
        textAttachment.setCstmId(cstmId);
        textAttachment.setCreatorId(terminalId);
        textAttachment.setCreatorName(terminalName);
        attachmentDao.insert(textAttachment);
    }

    @Override
    public void deleteCloudMaterial(TbTerminal terminal) {
        // 判断终端云素材是否存在
        Attachment folder = attachmentDao.getCloudWidgetTerminalFolder(terminal.getTerminalId(), terminal.getCstmId());
        if (folder != null) {
            deleteSubCloudMaterial(folder.getId());
        }
    }

    /**
     * 递归删除子目录及子文件
     *
     * @param parentId 父目录编号
     */
    private void deleteSubCloudMaterial(String parentId) {
        List<Attachment> attachments = attachmentDao.getByParentId(parentId);
        for (Attachment attachment : attachments) {
            attachmentDao.delete(attachment.getId());
            if (AttachmentType.NULL.id() == attachment.getType()) {
                deleteSubCloudMaterial(attachment.getId());
            } else {
                deleteMongodbFile(attachment.getPath(), attachment.getMd5(), attachment.getType());
            }
        }
    }

    @Override
    public HashMap<String, Attachment> getCloudMaterialList(String cstmId, String terminalId, String widgetName, String catalogId, String updateTime) throws Exception {
        Attachment attachment;
        if (StringUtils.isNotEmpty(catalogId)) {
            attachment = attachmentDao.find(catalogId);
        } else {
            if ("NewCloudImageView".equals(widgetName)) {
                widgetName = "云图片";
            } else if ("NewCloudTextView".equals(widgetName)) {
                widgetName = "云文字";
            } else if ("NewCloudDOM".equals(widgetName)) {
                widgetName = "云文档";
            }
            attachment = attachmentDao.getCloudWidgetFolder(terminalId, widgetName, cstmId);
        }

        HashMap map = new HashMap<>(64);
        if (updateTime == null || updateTime.equals("")) {  // 第一次调用
            List<Attachment> attachments = attachmentDao.getByParentId(attachment.getId());
            for (Attachment material : attachments) {
                map.put(material.getMd5(), material);
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.LONG_DATE_FORMAT);
            Date updateDate = sdf.parse(updateTime);
            if (updateDate.getTime() < attachment.getUpdateTime().getTime()) {
                List<Attachment> attachments = attachmentDao.getByParentId(attachment.getId());
                for (Attachment material : attachments) {
                    map.put(material.getMd5(), material);
                }
            } else {
                throw new Exception("get cloud material list failed");
            }
        }

        return map;
    }

    @Override
    public void deleteByCstmId(String cstmId) {
        //monngo删除会验证不用加事务
        List<String> delMd5s = attachmentDao.deleteMd5s(cstmId);
        //删除md5文件
        for (String md5 : delMd5s) {
            mongoHandler.deleteOne(CommonConstant.MongoDBCollections.TOUCHSYS_CLOUD, null, new ObjectId(md5));
        }
        attachmentDao.deleteByCstmId(cstmId);
    }

    @Override
    public boolean isContainFile(String parentId, String subId) {
        boolean isContain = false;
        if (parentId.equals(subId)) {
            isContain = true;
        } else {
            List<Attachment> attachments = attachmentDao.getByParentId(parentId);
            for (Attachment attachment : attachments) {
                isContain = isContainFile(attachment.getId(), subId);
                if (isContain) {
                    return isContain;
                }
            }
        }
        return isContain;
    }

}
