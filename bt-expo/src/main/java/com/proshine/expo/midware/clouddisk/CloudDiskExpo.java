package com.proshine.expo.midware.clouddisk;

import com.proshine.expo.base.dto.Message;
import com.proshine.expo.midware.clouddisk.entity.Attachment;
import com.proshine.expo.midware.clouddisk.entity.TotalClass;
import com.proshine.expo.terminal.terminal.entity.TbTerminal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件服务接口
 *
 * @author 高孙琼
 */
public interface CloudDiskExpo {

    /**
     * 插人文件
     *
     * @param attachment  文件对象
     * @param inputStream 文件流
     */
    Attachment insert(Attachment attachment, InputStream inputStream);

    /**
     * 上传文件
     *
     * @param parentId    所属目录
     * @param libraryType 所属素材库
     * @param file        文件对象
     */
    Attachment upload(String parentId, String libraryType, MultipartFile file);

    /**
     * 删除文件（物理删除）
     *
     * @param ids 文件id集，多个id使用英文字符","分割
     */
    void delete(String ids);

    /**
     * 移到回收站
     * <p>目录：没有子项则直接删除,有子项则不操作；文件：移到回收站</p>
     *
     * @param ids 文件id集，多个id使用英文字符","分割
     */
    void transferRecycle(String ids);

    /**
     * 回收站-还原文件
     * <p>如果所属目录存在则还原到原目录，否则还原到根目录</p>
     *
     * @param ids 文件id集，多个id使用英文字符","分割
     */
    void recoveryFile(String ids);

    /**
     * 回收站-清空回收站
     */
    void clearRecycle();

    /**
     * 审核文件
     *
     * @param id          文件id
     * @param checkStatus 审核状态
     */
    void check(String id, String checkStatus);

    /**
     * 文件/目录移动
     *
     * @param ids         文件id集，多个id使用英文字符","分割
     * @param newParentId 移动到目录id
     */
    void transfer(String ids, String newParentId);

    /**
     * 文件/目录复制
     *
     * @param ids         文件id集，多个id使用英文字符","分割
     * @param newParentId 移动到目录id
     */
    void copy(String ids, String newParentId);

    /**
     * 文件/目录搜索，过滤掉已经删除的目录和文件
     *
     * @param pageRequest 分页信息
     * @param terms       搜索参数
     * @param order       排序：nameOrder=文件名；sizeOrder=大小；updateTimeOrder=修改日期
     */
    @SuppressWarnings("rawtypes")
    Page<Attachment> query(PageRequest pageRequest, Map terms, String orderName, String order);

    /**
     * 文件/目录搜索，过滤掉已经删除的目录和文件
     *
     * @param terms 搜索参数
     */
    @SuppressWarnings("rawtypes")
    List<Attachment> finds(Map terms);

    /**
     * 获取回收站分页列表
     *
     * @param pageRequest 分页信息
     * @param terms       搜索参数
     * @param order       排序：nameOrder=文件名；sizeOrder=大小；updateTimeOrder=修改日期
     */
    @SuppressWarnings("rawtypes")
    Page<Attachment> queryRecycle(PageRequest pageRequest, Map terms, String orderName, String order);

    /**
     * 获取回收站列表
     *
     * @param terms 搜索参数
     */
    @SuppressWarnings("rawtypes")
    List<Attachment> findRecycles(Map terms);

    /**
     * 读取文件
     *
     * @param path 文件路径
     * @param md5  文件md5
     */
    Object read(String path, String md5);

    /**
     * 读取文件/缓存到本地，完成后毁掉deleteCache方法
     *
     * @param path 文件路径
     * @param md5  文件md5
     * @param size 文件大小
     */
    Object readCache(String path, String md5, long size);

    /**
     * 清空缓存
     *
     * @param path 文件路径
     * @param md5  文件md5
     * @param size 文件大小
     */
    void deleteCache(String path, String md5, long size);

    /**
     * 校验文件是否存在
     *
     * @param path 文件路径
     * @param md5  文件md5
     * @return true表示存在，false表示不存在
     */
    boolean exists(String path, String md5);

    /**
     * 读取文件信息
     *
     * @param id 文件id
     */
    Attachment find(String id);

    /**
     * 创建目录
     *
     * @param attachment 目录信息
     */
    Message<String> create(Attachment attachment);

    /**
     * 更新文件／目录
     *
     * @param attachment 文件／目录信息
     */
    void update(Attachment attachment);

    List<TotalClass> getTotalAttach(String cstmId);

    List<Attachment> findByNameAndSuffix(String name, String suffix, String cstmId, String userId);

    /**
     * 创建文本文件
     *
     * @param id          文件id
     * @param parentId    父目录id
     * @param libraryType 所属素材库
     * @param txtTitle    文件名称
     * @param txtContent  文件内容
     */
    Message<String> createTxt(String id, String parentId, String libraryType, String txtTitle, String txtContent);

    /**
     * 根据终端Id创建云素材目录
     *
     * @param terminal 终端信息
     */
    void createCloudMaterial(TbTerminal terminal);

    /**
     * 根据终端Id删除云素材目录
     */
    void deleteCloudMaterial(TbTerminal terminal);

    /**
     * 获取云素材对应的文件夹内素材列表
     *
     * @param cstmId     客户域
     * @param terminalId 终端唯一编号
     * @param widgetName 控件名称：云文档、云图片、云文字
     * @param catalogId  任意目录id
     * @param updateTime 更新时间
     */
    HashMap<String, Attachment> getCloudMaterialList(String cstmId, String terminalId, String widgetName,
                                                     String catalogId, String updateTime) throws Exception;

    /**
     * 删除其他客户域没有引用的文件
     */
    void deleteByCstmId(String cstmId);

    /**
     * 判断目录是否存在于另一个目录下.
     *
     * @param parentId 父目录(需要移动/复制的目录)
     * @param subId    子目录（判断该目录是否存在于父目录下，移动/复制到该目录下）
     * @return true-存在/false-不存在（不存在才能移动和复制）
     */
    boolean isContainFile(String parentId, String subId);

}
