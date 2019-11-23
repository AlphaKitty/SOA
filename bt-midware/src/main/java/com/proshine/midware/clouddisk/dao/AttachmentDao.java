package com.proshine.midware.clouddisk.dao;

import com.proshine.expo.midware.clouddisk.entity.Attachment;
import com.proshine.expo.midware.clouddisk.entity.TotalClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 文件/目录数据接口
 * @author 高孙琼
 */
@Repository
public interface AttachmentDao {

    /**
     * 插入文件/目录
     * @param attachment 文件/目录
     */
    void insert(@Param("entity") Attachment attachment);

    /**
     * 更新文件/目录
     * @param attachment 文件/目录
     */
    void update(@Param("entity") Attachment attachment);

    /**
     * 删除文件/目录
     * @param id 文件/目录编号
     */
    void delete(@Param("id") String id);

    /**
     * 获取文件/目录
     * @param id 文件/目录编号
     * @return
     */
    Attachment find(@Param("id") String id);

    /**
     * 按条件查询文件/目录总数
     * @param terms 查询条件集合
     */
    long count(@Param("terms") Map<String, Object> terms);

    /**
     * 按条件查询文件/目录分页结果集
     * @param page  分页对象
     * @param terms 查询条件集合
     * @param order 排序：nameOrder=文件名；sizeOrder=大小；updateTimeOrder=修改日期
     * @return
     */
    List<Attachment> query(@Param("page") PageRequest page, @Param("terms") Map<String, Object> terms, @Param("orderName") String orderName, @Param("order") String order);

    /**
     * 按条件查询实体所有结果集
     * @param terms 查询条件集合
     * @return
     */
    List<Attachment> finds(@Param("terms") Map<String, Object> terms);

    /**
     * 检测md5码是否存在
     * @param md5 md5字符串
     * @return md5数量
     */
    long checkmd5(@Param("md5") String md5);

    /**
     * 更新删除状态
     * @param id       文件id
     * @param isDelete 是否删除：1表示删除，0表示不删除
     * @return
     */
    void updateDelete(@Param("id") String id, @Param("isDelete") String isDelete);

    /**
     * 通过目录id获取子目录/文件,过滤掉已经删除的子目录/文件
     * @param parentId 目录id
     * @return
     */
    List<Attachment> getByParentId(@Param("parentId") String parentId);

    /**
     * 获取素材的统计类
     * @return
     */
    List<TotalClass> getTotalAttach(@Param("cstmId") String cstmId);

    /**
     * 通过文件夹名字查询是否有已经存在的文件夹。
     * @param name
     * @return
     */
    Attachment getFolderByName(@Param("name") String name, @Param("cstmId") String cstmId, @Param("userId") String userId);

    /**
     * 通过终端id查询云素材中的终端文件夹。
     * @param terminalId 终端唯一编号
     * @param cstmId     客户域
     * @return
     */
    Attachment getCloudWidgetTerminalFolder(@Param("terminalId") String terminalId, @Param("cstmId") String cstmId);

    /**
     * 通过文件夹名称查询云素材文件夹。
     * @param terminalId 终端唯一编号
     * @param widgetName 控件名称
     * @param cstmId     客户域
     * @return
     */
    Attachment getCloudWidgetFolder(@Param("terminalId") String terminalId, @Param("widgetName") String widgetName, @Param("cstmId") String cstmId);

    /**
     * 通过文件名加后缀
     * @param name
     * @param suffix
     * @return
     */
    List<Attachment> findByNameAndSuffix(@Param("name") String name, @Param("suffix") String suffix, @Param("cstmId") String cstmId, @Param("userId") String userId);

    /**
     * 获取云素材总数
     * @param terms 查询条件集合
     */
    long countCloud(@Param("terms") Map<String, Object> terms);

    /**
     * 获取云素材分页结果集
     * @param page  分页对象
     * @param terms 查询条件集合
     * @param order 排序：nameOrder=文件名；sizeOrder=大小；updateTimeOrder=修改日期
     * @return
     */
    List<Attachment> queryCloud(@Param("page") PageRequest page, @Param("terms") Map<String, Object> terms, @Param("orderName") String orderName, @Param("order") String order);

    /**
     * 获取云素材分页结果集
     * @param terms 查询条件集合
     * @return
     */
    List<Attachment> findsCloud(@Param("terms") Map<String, Object> terms);

    void deleteByCstmId(String cstmId);

    List<String> selectMd5UsedByOtherCstm(String cstmId);

    List<String> deleteMd5s(String cstmId);

    Attachment findByMd5(String md5);
}
