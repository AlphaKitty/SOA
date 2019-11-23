package com.proshine.terminal.terminal.service;

import com.proshine.expo.terminal.terminal.entity.TbTerminal;
import com.proshine.expo.terminal.terminal.entity.TbTerminalCategory;
import com.proshine.expo.terminal.terminal.entity.TbTerminalDownload;
import com.proshine.expo.terminal.terminal.setting.CommonSetting;
import com.proshine.expo.terminal.terminal.vo.TerminalVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * 终端管理接口 API
 * etc
 * 注册
 * 删除
 * 修改
 * 搜索
 */
@SuppressWarnings("restriction")
public interface TerminalManageService {
    /**
     * 终端注册
     *
     * @param terminal {@link TbTerminal} 实体类
     * @return String: 终端注册后的ID
     */
    Object register(TbTerminal terminal);

    /**
     * 删除终端
     *
     * @param ids 终端数据库ID列表, 多个id以 ',' 分隔
     * @return int: 删除终端的数量
     */
    int deleteTerminal(String ids);

    /**
     * 更新终端
     *
     * @param terminal {@link TbTerminal} 需要更新的数据
     * @return true: 更新成功
     * false: 更新失败
     */
    boolean updateTerminal(TbTerminal terminal);

    /**
     * 移动终端到指定目录
     *
     * @param terminal {@link TbTerminal}
     * @param category {@link TbTerminalCategory}
     * @return true: 移动成功
     * false:移动失败
     */
    boolean moveCategory(TbTerminal terminal, TbTerminalCategory category);

    /**
     * 终端分页搜索
     *
     * @param pageRequest 分页信息
     * @param terms       搜索参数
     * @param order       排序
     * @return Page
     */
    Page<TbTerminal> query(PageRequest pageRequest, Map terms, String order, String name, Map pair) throws Exception;

    /**
     * 终端搜索
     *
     * @param terms 搜索参数
     * @return Page
     */
    List<TbTerminal> finds(Map terms);

    /**
     * 通过终端数据库ID查找终端
     *
     * @param id 终端数据库ID
     * @return {@link TbTerminal} 终端实体类
     */
    TbTerminal findById(String id);

    /**
     * 通过终端ID查找终端
     *
     * @param terminalId 终端ID
     * @param cstmId     客户ID
     * @return {@link TbTerminal} 终端实体类
     */
    TbTerminal findByTerminalId(String terminalId, String cstmId);

    /**
     * 校验终端ID是否存在
     *
     * @param terminalId 终端ID
     * @param cstmId     客户ID
     * @return true 表示存在
     * false 表示不存在
     */
    boolean checkTerminalId(String terminalId, String cstmId);

    /**
     * 终端向后台报告设置
     *
     * @param <T>        <T>
     * @param settings   设置属性实体，继承于CommonSetting
     * @param clazz      设置属性实体类，继承于CommonSetting
     * @param individual individual
     */
    <T extends CommonSetting> void reportSettings(T settings, Class<T> clazz, boolean individual);

    /**
     * 终端请求后台的管理者设置
     *
     * @param <T>        <T>
     * @param terminalId 终端ID
     * @param cstmId     客户ID
     * @param clazz      设置属性实体类，继承于CommonSetting, 该类决定了获取的设置类型
     * @param individual true代表终端本地配置，false代表服务端配置
     * @return <T extends CommonSetting>
     */
    <T extends CommonSetting> T getSettings(String terminalId, String cstmId, Class<T> clazz, boolean individual);

    /**
     * 通过cstmId来查询对应的所有终端
     *
     * @return List
     */
    List<TbTerminal> findTerminalBycstmId(String cstmId);

    List<TbTerminal> getTotalOnline(String cstmId);

    void setTbTerminalDownload(TbTerminalDownload terminalDownload);

    /**
     * 更新终端为离线状态
     *
     * @param terminalId terminalId
     * @param status     status
     */
    void updateTerminalOffline(String terminalId, int status);

    TbTerminalDownload findByTermIdAndCstmId(String cstmId, String terminalId);

    void updateTerminalDown(long totalSize, long downloadedSize, String id);

    TbTerminalDownload getTerminalDown(String cstmId, String terminalId);

    void deleteTerminalDown(String DownId);

    /**
     * 批量更新终端状态为在线
     *
     * @param list List<String>
     */
    void batchUpdateOnline(List<String> list);

    /**
     * 批量更新终端状态为离线
     *
     * @param list List<String>
     */
    void batchUpdateOffline(List<String> list);

    Map getTerminalPermission(PageRequest pageRequest, String id, String cstmId, String key);

    void insertTerminalPermission(Map map);

    void insertBatchTerminalPermission(List list);

    void deleteTerminalPermission(String list);

    Map getUserTerminalPermission(PageRequest page, String userId, String cstmId);

    Page<TbTerminal> queryByPermission(PageRequest pageRequest, Map terms, String order, String name);

    List<TerminalVo> findAllTerminal(Map map);

    List<TbTerminal> getIsNotNull(String cstmId);

    List<TbTerminal> getIsNull(String cstmId);

    List<TerminalVo> getIsNullByPage(String cstmId, Integer start, Integer size);

    void updateNull(TbTerminal terminal);

    Integer findAllTerminalCount(Map<String, Object> map);

    List<TbTerminal> findLikeTerminal(Map<String, Object> map);

    Integer findLikeTerminalCount(Map<String, Object> map);

    List<TbTerminal> queryMyTerminal(String userId);

    /**
     * 终端关机
     */
    void powerOff(String terminalIds);

    /**
     * 终端解锁
     */
    void delock(String terminalIds);

    /**
     * 终端锁屏
     */
    void lock(String terminalIds);

    /**
     * 终端休眠
     */
    void sleep(String terminalIds);

    /**
     * 终端唤醒
     */
    void wakeup(String terminalIds);

    /**
     * 终端重启
     */
    void reboot(String terminalIds);

    Map<String, Object> getRoomConfig(String customerId);

    List<TerminalVo> queryManagerTerminal(Map<String, Object> params);

    /**
     * 通过userId查找用户分配的设备id
     */
    List<String> queryTerminalByUserId(String userId);

    /**
     * 批量移动设备分组
     */
    void batchMoveCategory(String ids, String catId);

    void deleteByCstmId(String cstmId);

    int findSameNameByCstmId(String terminalName, String cstmId);

    void updateTerminalNew(TbTerminal te);

    /**
     * 用来androd改变客户域上报,不传cstm当换客户域后离线了状态又会变为在线
     *
     * @param id     id
     * @param cstmId cstmId
     * @return TbTerminal
     */
    TbTerminal findByIdAndCstm(String id, String cstmId);
}
