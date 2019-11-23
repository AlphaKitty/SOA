package com.proshine.midware.clouddisk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.proshine.base.common.constant.CommonConstant;
import com.proshine.base.common.utils.FileUtils;
import com.proshine.base.common.utils.StringUtils;
import com.proshine.expo.base.BaseExpo;
import com.proshine.expo.base.annotation.SystemLog;
import com.proshine.expo.base.dto.Message;
import com.proshine.expo.gateway.security.dto.CustomUserDetails;
import com.proshine.expo.midware.clouddisk.entity.Attachment;
import com.proshine.expo.midware.clouddisk.entity.TotalClass;
import com.proshine.midware.clouddisk.service.CloudDiskHandler;
import com.proshine.midware.clouddisk.support.AttachmentType;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.*;

/**
 * 云盘模块页面控制器
 *
 * @author 高孙琼
 */
@SuppressWarnings("ALL")
@Log4j2
@Controller
@RequestMapping(value = "/clouddisk")
public class CloudDiskController {

    @Autowired
    private CloudDiskHandler cloudDiskHandler;
    @Reference
    private BaseExpo baseExpo;

    /**
     * 云盘首页
     * url:${ctx}/clouddisk/home
     */
    @RequestMapping(value = "home")
    public ModelAndView home() {
        ModelAndView view = new ModelAndView();
        view.setViewName("WEB-INF/clouddisk/cloud");
        return view;
    }

    /**
     * 文件上传
     * url:${ctx}/clouddisk/todoUpload
     */
    @RequestMapping(value = "todoUpload")
    public ModelAndView todoUpload(@RequestParam("parentId") String parentId, @RequestParam("libraryType") String libraryType, @RequestParam(value = "type", defaultValue = "") String type) {
        ModelAndView view = new ModelAndView();
        view.addObject("parentId", parentId);
        view.addObject("libraryType", libraryType);
        view.addObject("type", "".equals(type.trim()) ? null : type);
        view.setViewName("WEB-INF/clouddisk/upload");
        return view;
    }

    /**
     * 新建目录
     * url:${ctx}/clouddisk/todoNewFile
     */
    @RequestMapping(value = "todoNewFile")
    public ModelAndView todoNewFile(@RequestParam("parentId") String parentId, @RequestParam("libraryType") String libraryType) {
        ModelAndView view = new ModelAndView();
        view.addObject("parentId", parentId);
        view.addObject("libraryType", libraryType);
        view.setViewName("WEB-INF/clouddisk/new-file");
        return view;
    }

    /**
     * 编辑目录
     * url:${ctx}/clouddisk/todoEdit
     */
    @RequestMapping(value = "todoEdit")
    public ModelAndView todoEdit(@RequestParam("id") String id) {
        ModelAndView view = new ModelAndView();
        Attachment file = cloudDiskHandler.find(id);
        view.addObject("file", file);
        view.setViewName("WEB-INF/clouddisk/edit");
        return view;
    }

    /**
     * 复制文件
     * url:${ctx}/clouddisk/todoCopy
     */
    @RequestMapping(value = "todoCopy")
    public ModelAndView todoCopy(@RequestParam("ids") String ids) {
        ModelAndView view = new ModelAndView();
        view.addObject("ids", ids);
        view.setViewName("WEB-INF/clouddisk/copy");
        return view;
    }

    /**
     * 移动文件
     * url:${ctx}/clouddisk/todoTransfer
     */
    @RequestMapping(value = "todoTransfer")
    public ModelAndView todoTransfer(@RequestParam("ids") String ids) {
        ModelAndView view = new ModelAndView();
        view.addObject("ids", ids);
        view.setViewName("WEB-INF/clouddisk/transfer");
        return view;
    }

    /**
     * 文件审核
     * url:${ctx}/clouddisk/todoCheck
     */
    @RequestMapping(value = "todoCheck")
    public ModelAndView todoCheck(@RequestParam("id") String id) {
        ModelAndView view = new ModelAndView();
        Attachment file = cloudDiskHandler.find(id);
        view.addObject("file", file);
        view.setViewName("WEB-INF/clouddisk/check");
        return view;
    }

    /**
     * 播放视频
     * url:${ctx}/clouddisk/todoPlayerVideo
     */
    @RequestMapping(value = "todoPlayerVideo")
    public ModelAndView todoPlayerVideo(@RequestParam("id") String id) {
        ModelAndView view = new ModelAndView();
        Attachment attachment = cloudDiskHandler.find(id);
        view.addObject("attachment", attachment);
        view.setViewName("WEB-INF/clouddisk/player-video");
        return view;
    }

    /**
     * 新建文本文件
     * url:${ctx}/clouddisk/todoNewTxt
     */
    @RequestMapping(value = "todoNewTxt")
    public ModelAndView todoNewTxt(@RequestParam("parentId") String parentId, @RequestParam("libraryType") String libraryType) {
        ModelAndView view = new ModelAndView();
        view.addObject("parentId", parentId);
        view.addObject("libraryType", libraryType);
        view.setViewName("WEB-INF/clouddisk/new-txt");
        return view;
    }

    /**
     * 编辑文本文件
     * url:${ctx}/clouddisk/todoEditTxt
     */
    @RequestMapping(value = "todoEditTxt")
    public ModelAndView todoEditTxt(@RequestParam("id") String id) {
        Attachment attachment = cloudDiskHandler.find(id);
        String txtTitle = attachment.getName();
        String suffix = attachment.getSuffix();

        InputStream inputStream = (InputStream) cloudDiskHandler.read(attachment.getPath(), attachment.getMd5());

        String basePath = FileUtils.getBasePath() + "tempFile" + File.separator;
        String fileName = basePath + txtTitle + "." + suffix;
        // 如果路径不存在则新建目录
        if (!FileUtils.isFileExist(basePath)) {
            new File(basePath).mkdirs();
        }
        FileUtils.writeFile(fileName, inputStream);

        String txtContent = FileUtils.readFile(fileName);
        FileUtils.deleteFile(fileName);

        ModelAndView view = new ModelAndView();
        view.addObject("id", id);
        view.addObject("parentId", attachment.getParentId());
        view.addObject("txtTitle", attachment.getName());
        view.addObject("txtContent", txtContent);
        view.setViewName("WEB-INF/clouddisk/new-txt");
        return view;
    }

    /**
     * 文件上传
     * url: {ctx}/clouddisk/upload
     * method: POST
     * Content-Type: multipart/form-data
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam("parentId") String parentId, @RequestParam("libraryType") String libraryType, MultipartHttpServletRequest request, HttpServletResponse response) {
        try {
            MultipartFile file = null;
            Attachment attachment = null;
            CustomUserDetails customUserDetails = baseExpo.getSessionUser();
            Iterator<String> itr = request.getFileNames();
            while (itr.hasNext()) {
                // 获取文件的文件名和后缀名
                file = request.getFile(itr.next());
                String fileFullName = file.getOriginalFilename();
                String fileName = "";
                String fileSuffix = "";
                int lastDotIndex = fileFullName.lastIndexOf(".");
                if (lastDotIndex <= 0) {
                    fileName = fileFullName;
                } else {
                    fileName = fileFullName.substring(0, lastDotIndex);
                    fileSuffix = fileFullName.substring(lastDotIndex + 1, fileFullName.length());
                }
                // 判断数据库中是否有同名文件 相册可以上传同样的文件名 云盘不行
                List<Attachment> attachments = cloudDiskHandler
                        .findByNameAndSuffix(fileName,
                                fileSuffix, customUserDetails.getCstmId(), customUserDetails.getUserId());
                if (attachments != null && attachments.size() > 0) {
                    return new Message<>(45001, "不能上传文件名相同的文件！", null);
                }
                attachment = cloudDiskHandler.upload(parentId, libraryType, file);

            }
            return new Message<>(45001, "上传文件成功", attachment);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return new Message<>(45000, "上传文件失败", null);
        }
    }

    /**
     * 删除文件
     * url: {ctx}/clouddisk/delete
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    @SystemLog(module = CommonConstant.ATTACHMENT_LOG)
    public Object delete(@RequestParam("ids") String ids) {
        try {
            cloudDiskHandler.delete(ids);
            return new Message<>(45001, "删除文件成功", null);
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return new Message<>(45000, "删除文件失败", null);
        }
    }

    /**
     * 回收站-还原文件
     * url: {ctx}/clouddisk/recoveryFile
     */
    @RequestMapping(value = "/recoveryFile", method = RequestMethod.GET)
    @ResponseBody
    public Object recoveryFile(@RequestParam("ids") String ids) {
        try {
            cloudDiskHandler.recoveryFile(ids);
            return new Message<>(45001, "还原文件成功", null);
        } catch (Exception e) {
            log.error("还原文件失败", e);
            return new Message<>(45000, "还原文件失败", null);
        }
    }

    /**
     * 回收站-清空回收站
     * url: {ctx}/clouddisk/clearRecycle
     */
    @RequestMapping(value = "/clearRecycle", method = RequestMethod.GET)
    @ResponseBody
    @SystemLog(module = CommonConstant.ATTACHMENT_LOG)
    public Object clearRecycle() {
        try {
            cloudDiskHandler.clearRecycle();
            return new Message<>(45001, "清空回收站成功", null);
        } catch (Exception e) {
            log.error("清空回收站失败", e);
            return new Message<>(45000, "清空回收站失败", null);
        }
    }

    /**
     * 移到回收站
     * url: {ctx}/clouddisk/transferRecycle
     */
    @RequestMapping(value = "/transferRecycle", method = RequestMethod.GET)
    @ResponseBody
    public Object transferRecycle(@RequestParam("ids") String ids) {
        try {
            cloudDiskHandler.transferRecycle(ids);
            return new Message<>(45001, "移到回收站成功", null);
        } catch (Exception e) {
            log.error("移到回收站失败", e);
            return new Message<>(45000, "移到回收站失败", null);
        }
    }

    /**
     * 审核文件
     * url: {ctx}/clouddisk/check
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    @SystemLog(module = CommonConstant.ATTACHMENT_LOG)
    public Object check(@RequestParam("id") String id, @RequestParam("checkStatus") String checkStatus) {
        try {
            cloudDiskHandler.check(id, checkStatus);
            return new Message<>(45001, "审核文件成功", null);
        } catch (Exception e) {
            log.error("审核文件失败", e);
            return new Message<>(45000, "审核文件失败", null);
        }
    }

    /**
     * 文件/目录移动
     * url: {ctx}/clouddisk/transfer
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    @ResponseBody
    public Object transfer(@RequestParam("ids") String ids, @RequestParam("newParentId") String newParentId) {
        try {
            cloudDiskHandler.transfer(ids, newParentId);
            return new Message<>(45001, "文件/目录移动成功", null);
        } catch (Exception e) {
            log.error("文件/目录移动失败", e);
            return new Message<>(45000, "文件/目录移动失败", null);
        }
    }

    /**
     * 文件/目录复制
     * url: {ctx}/clouddisk/copy
     */
    @RequestMapping(value = "/copy", method = RequestMethod.GET)
    @ResponseBody
    public Object copy(@RequestParam("ids") String ids, @RequestParam("newParentId") String newParentId) {
        try {
            cloudDiskHandler.copy(ids, newParentId);
            return new Message<>(45001, "文件/目录复制成功", null);
        } catch (Exception e) {
            log.error("文件/目录移动失败", e);
            return new Message<>(45000, "文件/目录复制失败", null);
        }
    }

    /**
     * 文件/目录搜索，过滤掉已经删除的目录和文件
     * url: {ctx}/clouddisk/query
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(@RequestParam(value = "total") int total, @RequestParam(value = "currentPage") int currentPage, HttpServletRequest request) {
        try {
            @SuppressWarnings("rawtypes")
            Map terms = new HashMap<>();
            terms.put("parentId", request.getParameter("parentId"));
            terms.put("name", request.getParameter("name"));
            terms.put("cstmId", baseExpo.getSessionUser().getCstmId());

            String libraryType = request.getParameter("libraryType");
            if (StringUtils.isEmpty(libraryType) || "0".equals(libraryType)) {
                libraryType = "0";
                String creatorId = baseExpo.getSessionUser().getUserId();
                terms.put("creatorId", creatorId);
            }
            terms.put("libraryType", libraryType);

            String typeStr = request.getParameter("type");
            if (StringUtils.isNotEmpty(typeStr)) {
                String[] type = typeStr.split(",");
                terms.put("type", type);
            }
            String order = request.getParameter("order");
            String orderName = request.getParameter("orderName");
            if (StringUtils.isEmpty(orderName)) {
                orderName = "updateTime";
            }
            if (StringUtils.isEmpty(order)) {
                order = "desc";
            }
            PageRequest pageRequest = new PageRequest(currentPage - 1, total);
            return new Message<>(45001, "文件/目录搜索成功", cloudDiskHandler.query(pageRequest, terms, orderName, order));
        } catch (Exception e) {
            log.error("文件/目录搜索失败", e);
            return new Message<>(45000, "文件/目录搜索失败", null);
        }
    }

    /**
     * 获取文件/目录，过滤掉已经删除的目录和文件
     * url: {ctx}/clouddisk/finds
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/finds", method = RequestMethod.GET)
    @ResponseBody
    public Object finds(HttpServletRequest request) {
        try {
            @SuppressWarnings("rawtypes")
            Map terms = new HashMap<>();
            terms.put("parentId", request.getParameter("parentId"));
            terms.put("name", request.getParameter("name"));
            terms.put("cstmId", baseExpo.getSessionUser().getCstmId());

            String libraryType = request.getParameter("libraryType");
            if (StringUtils.isNotEmpty(libraryType)) {
                terms.put("libraryType", libraryType);
            }

            String idsStr = request.getParameter("ids");
            if (StringUtils.isNotEmpty(idsStr)) {
                String[] ids = idsStr.split(",");
                terms.put("ids", ids);
            }

            String typeStr = request.getParameter("type");
            if (StringUtils.isNotEmpty(typeStr)) {
                String[] type = typeStr.split(",");
                terms.put("type", type);
            }

            return new Message<>(45001, "文件/目录搜索成功", cloudDiskHandler.finds(terms));
        } catch (Exception e) {
            log.error("文件/目录搜索失败", e);
            return new Message<>(45000, "文件/目录搜索失败", null);
        }
    }

    /**
     * 获取回收站分页列表
     * url: {ctx}/clouddisk/queryRecycle
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/queryRecycle", method = RequestMethod.GET)
    @ResponseBody
    public Object queryRecycle(@RequestParam(value = "total") int total, @RequestParam(value = "currentPage") int currentPage, HttpServletRequest request) {
        try {
            @SuppressWarnings("rawtypes")
            Map terms = new HashMap<>();
            terms.put("name", request.getParameter("serchName"));
            terms.put("cstmId", baseExpo.getSessionUser().getCstmId());
            String orderName = request.getParameter("orderName");
            String order = request.getParameter("order");

            if (StringUtils.isEmpty(orderName)) {
                orderName = "updateTime";
            }
            if (StringUtils.isEmpty(order)) {
                order = "desc";
            }
            PageRequest pageRequest = new PageRequest(currentPage - 1, total);
            return new Message<>(45001, "获取回收站分页列表成功", cloudDiskHandler.queryRecycle(pageRequest, terms, orderName, order));
        } catch (Exception e) {
            log.error("获取回收站分页列表失败", e);
            return new Message<>(45000, "获取回收站分页列表失败", null);
        }
    }

    /**
     * 获取回收站列表
     * url: {ctx}/clouddisk/findRecycles
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/findRecycles", method = RequestMethod.GET)
    @ResponseBody
    public Object findRecycles(HttpServletRequest request) {
        try {
            Map terms = new HashMap<>();
            terms.put("name", request.getParameter("name"));
            terms.put("cstmId", baseExpo.getSessionUser().getCstmId());

            String idsStr = request.getParameter("ids");
            if (StringUtils.isNotEmpty(idsStr)) {
                String[] ids = idsStr.split(",");
                terms.put("ids", ids);
            }

            return new Message<>(45001, "获取回收站列表成功", cloudDiskHandler.findRecycles(terms));
        } catch (Exception e) {
            log.error("获取回收站列表失败", e);
            return new Message<>(45000, "获取回收站列表失败", null);
        }
    }

    /**
     * 下载文件
     * url: {ctx}/clouddisk/download/id
     */
    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @SystemLog(module = CommonConstant.ATTACHMENT_LOG)
    public void download(@PathVariable("id") String id, HttpServletResponse response) {
        Attachment attachment = cloudDiskHandler.find(id);
        try {
            response.reset();
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((attachment.getName() + "." + attachment.getSuffix()).getBytes(), "iso-8859-1"));
            response.addHeader("Content-Length", "" + attachment.getSize());
            OutputStream outputStream = response.getOutputStream();

            InputStream inputStream = (InputStream) cloudDiskHandler.read(attachment.getPath(), attachment.getMd5());

            IOUtils.copy(inputStream, outputStream);
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (SocketException e) {
            log.error("客户端终止下载", e);
        } catch (Exception e2) {
            log.error("下载文件", e2);
        }
    }

    /**
     * 预览文件（预览图预览），根据文件id
     * url: {ctx}/clouddisk/preview/id
     */
    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void preview(@PathVariable("id") String id, HttpServletResponse response, @RequestParam(value = "No", defaultValue = "1") int No) {
        Attachment attachment = cloudDiskHandler.find(id);
        try {
            response.reset();
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream;
            if (attachment.getType() == 1) { // 图片预览
                inputStream = (InputStream) cloudDiskHandler.read(attachment.getPath(), attachment.getMd5());
            } else if (attachment.getType() == 4) { // 视频预览
                inputStream = (InputStream) cloudDiskHandler.read(attachment.getPath(), attachment.getMd5() + "_preview_" + String.valueOf(No));
            } else {
                inputStream = (InputStream) cloudDiskHandler.read(attachment.getPath(), attachment.getMd5());
            }
            IOUtils.copy(inputStream, outputStream);
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (SocketException e) {
            log.error("客户端终止下载", e);
        } catch (Exception e2) {
            log.error("下载文件", e2);
        }
    }

    /**
     * 预览文件(原始文件预览)，根据文件id
     * url: {ctx}/clouddisk/online/id
     */
    @RequestMapping(value = "/online/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void online(@PathVariable("id") String id, HttpServletResponse response) {

        String tempStr[] = id.split("_");
        String temp = "";
        Attachment attachment = null;
        if (tempStr.length > 1) {
            attachment = cloudDiskHandler.find(tempStr[0]);
            temp = "_" + tempStr[tempStr.length - 1];
        } else {
            attachment = cloudDiskHandler.find(id);
        }
        try {
            response.reset();
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream;
            inputStream = (InputStream) cloudDiskHandler.read(attachment.getPath(), attachment.getMd5() + temp);
            IOUtils.copy(inputStream, outputStream);
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (SocketException e) {
            log.error("客户端终止下载", e);
        } catch (Exception e2) {
            log.error("下载文件", e2);
        }
    }

    /**
     * 预览文件，根据文件md5
     * url: {ctx}/clouddisk/onlineByMd5/md5
     */
    @RequestMapping(value = "/onlineByMd5/{md5}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void onlineByMd5(@PathVariable("md5") String md5, HttpServletResponse response) {
        try {
            response.reset();
            OutputStream outputStream = response.getOutputStream();

            InputStream inputStream = (InputStream) cloudDiskHandler.read(CommonConstant.MongoDBCollections.TOUCHSYS_CLOUD + "/", md5);

            IOUtils.copy(inputStream, outputStream);
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (SocketException e) {
            log.error("客户端终止下载", e);
        } catch (Exception e2) {
            log.error("下载文件", e2);
        }
    }

    /**
     * 创建目录
     * url: {ctx}/clouddisk/create
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody Attachment attachment) {
        try {
            return cloudDiskHandler.create(attachment);
        } catch (Exception e) {
            log.error("创建文件夹失败", e);
            return new Message<>(45000, "创建文件夹失败", null);
        }
    }

    /**
     * 更新目录
     * url: {ctx}/clouddisk/update
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @SystemLog(module = CommonConstant.ATTACHMENT_LOG)
    public Object update(@RequestBody Attachment attachment) {
        try {
            CustomUserDetails customUserDetails = baseExpo.getSessionUser();
            String suffix = "";
            if (attachment.getSuffix() != null) {
                suffix = attachment.getSuffix().substring(attachment.getSuffix().lastIndexOf(".") + 1);
            }
            List<Attachment> attachmentList = cloudDiskHandler.findByNameAndSuffix(attachment.getName(), suffix,
                    customUserDetails.getCstmId(), customUserDetails.getUserId());
            attachment.setSuffix(suffix);
            if (attachmentList != null && attachmentList.size() > 0) {
                return new Message<>(45000, "文件名/目录名不能重复", attachment);
            }


            cloudDiskHandler.update(attachment);
            return new Message<>(45001, "更新文件／目录成功", attachment);
        } catch (Exception e) {
            log.error("更新文件／目录失败", e);
            return new Message<>(45000, "更新文件／目录失败", null);
        }
    }

    /**
     * 获取素材的统计资料
     * url: {ctx}/clouddisk/getTotalAttach
     */
    @RequestMapping(value = "/getTotalAttach", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getTotalAttach() {
        List<TotalClass> totalClassList = cloudDiskHandler.getTotalAttach(baseExpo.getSessionUser().getCstmId());
        List<String> colorList = new ArrayList();
        colorList.add(CommonConstant.COLOR_ORANGE);
        colorList.add(CommonConstant.COLOR_BLUE);
        colorList.add(CommonConstant.COLOR_GREEN);
        colorList.add(CommonConstant.COLOR_CBULUE);
        colorList.add(CommonConstant.COLOR_VIOLET);
        for (int i = 0; i < totalClassList.size(); i++) {
            TotalClass totalClass = totalClassList.get(i);
            String type = totalClass.getType();
            switch (type) {
                case "1":
                    totalClass.setType("图片");
                    break;
                case "2":
                    totalClass.setType("文档");
                    break;
                case "3":
                    totalClass.setType("音频");
                    break;
                case "4":
                    totalClass.setType("视频");
                    break;
                default:
                    totalClass.setType("其他");
                    break;
            }

            totalClass.setColor(colorList.get(i));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", totalClassList);
        return jsonObject.toJSONString();
    }

    /**
     * 预览文件(原始文件预览)，根据文件id
     * url: {ctx}/clouddisk/online/id
     */
    @RequestMapping(value = "/musicPlay/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ModelAndView musicPlay(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        Attachment attachment = cloudDiskHandler.find(id);
        ModelAndView view = new ModelAndView();
        try {
            response.reset();
            InputStream inputStream;
            inputStream = (InputStream) cloudDiskHandler.read(attachment.getPath(), attachment.getMd5());
            File musicFile = new File(request.getServletContext().getRealPath("/") + "/js/soundJS/temp/audio.mp3");
            String tmpPath = request.getContextPath() + "/js/soundJS/temp/";
            view.addObject("tmpPath", tmpPath + musicFile.getName());
            view.setViewName("/js/soundJS/sound");

            OutputStream outputStream = new FileOutputStream(musicFile);
            IOUtils.copy(inputStream, outputStream);
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (SocketException e) {
            log.error("客户端终止下载", e);
        } catch (Exception e2) {
            log.error("下载文件", e2);
        }
        return view;
    }

    @RequestMapping("/deleteTempFile")
    @SystemLog(module = CommonConstant.ATTACHMENT_LOG)
    public void deleteTempFile(HttpServletRequest request, @RequestParam(value = "tempPath") String tempPath) {
        String path = request.getContextPath() + "/js/soundJS";
        File f = new File(path + "/temp/" + tempPath);
        if (f.exists()) {
            f.delete();
        }
    }

    @RequestMapping("transformTs")
    @ResponseBody
    public void transformTs() {

        FileUtils.transformTs("C:\\Users\\proshine001\\IdeaProjects\\bt_backend_v3\\src\\main\\webapp\\js\\soundJS\\temp\\audio.mp3", "C:\\Users\\proshine001\\IdeaProjects\\bt_backend_v3\\src\\main\\webapp\\js\\soundJS\\temp");
    }

    /**
     * 创建文本文件
     * url: {ctx}/clouddisk/createTxt
     */
    @RequestMapping(value = "/createTxt", method = RequestMethod.POST)
    @ResponseBody
    public Object createTxt(@RequestBody Attachment attachment) {
        try {
            return cloudDiskHandler.createTxt(attachment.getId(), attachment.getParentId(),
                    attachment.getLibraryType(), attachment.getName(), attachment.getDesc());
        } catch (Exception e) {
            log.error("创建文本文件失败", e);
            return new Message<>(45000, "创建文本文件失败", null);
        }
    }

    /**
     * 获取目录树，区分公有库和私有库
     * url: {ctx}/clouddisk/getTree
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getTree", method = RequestMethod.GET)
    @ResponseBody
    public Object getTree(HttpServletRequest request) {
        try {
            @SuppressWarnings("rawtypes")
            Map terms = new HashMap<>();
            terms.put("cstmId", baseExpo.getSessionUser().getCstmId());

            // 获取所有的目录
            String typeStr = String.valueOf(AttachmentType.NULL.id());
            String[] type = typeStr.split(",");
            terms.put("type", type);

            List<Attachment> attachments = new ArrayList<>();

            terms.put("libraryType", "0");
            List<Attachment> privateAttachments = cloudDiskHandler.finds(terms);
            attachments.addAll(privateAttachments);

            terms.put("libraryType", "1");
            List<Attachment> publicAttachments = cloudDiskHandler.finds(terms);
            for (Attachment attachment : publicAttachments) {
                if ("0".equals(attachment.getParentId())) {
                    // 将根目录下的文件或目录的parentId设置为libraryType，便于展示私有库、公有库和云素材。
                    attachment.setParentId(attachment.getLibraryType());
                }
            }
            attachments.addAll(publicAttachments);

            terms.put("libraryType", "2");
            List<Attachment> cloudAttachments = cloudDiskHandler.finds(terms);
            for (Attachment attachment : cloudAttachments) {
                if ("0".equals(attachment.getParentId())) {
                    // 将根目录下的文件或目录的parentId设置为libraryType，便于展示私有库、公有库和云素材。
                    attachment.setParentId(attachment.getLibraryType());
                }
            }
            attachments.addAll(cloudAttachments);

            Attachment privateAttachment = new Attachment();
            privateAttachment.setId("0");
            privateAttachment.setName("私有库(根目录)");
            attachments.add(privateAttachment);

            Attachment publicAttachment = new Attachment();
            publicAttachment.setId("1");
            publicAttachment.setName("公有库(根目录)");
            attachments.add(publicAttachment);

            Attachment cloudAttachment = new Attachment();
            cloudAttachment.setId("2");
            cloudAttachment.setName("云素材(根目录)");
            attachments.add(cloudAttachment);

            return new Message<>(45001, "获取目录树成功", attachments);
        } catch (Exception e) {
            log.error("获取目录树失败", e);
            return new Message<>(45000, "获取目录树失败", null);
        }
    }

}
