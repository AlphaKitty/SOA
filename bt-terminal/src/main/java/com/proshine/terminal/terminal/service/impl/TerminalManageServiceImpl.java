// package com.proshine.terminal.terminal.service.impl;
//
// import com.alibaba.dubbo.config.annotation.Reference;
// import com.proshine.base.common.utils.ActivationCount;
// import com.proshine.base.common.utils.DecimalTransitionUtil;
// import com.proshine.base.common.utils.Identities;
// import com.proshine.base.common.utils.StringUtils;
// import com.proshine.expo.base.dto.Message;
// import com.proshine.expo.gateway.SecurityExpo;
// import com.proshine.expo.gateway.security.entity.TbUser;
// import com.proshine.expo.midware.clouddisk.CloudDiskExpo;
// import com.proshine.expo.midware.file.CaptureExpo;
// import com.proshine.expo.terminal.terminal.entity.*;
// import com.proshine.expo.terminal.terminal.setting.*;
// import com.proshine.expo.terminal.terminal.vo.TerminalRelevanceVO;
// import com.proshine.expo.terminal.terminal.vo.TerminalVo;
// import com.proshine.terminal.terminal.service.TerminalConfigurationService;
// import com.proshine.terminal.terminal.service.TerminalControlService;
// import com.proshine.terminal.terminal.service.TerminalManageService;
// import lombok.extern.log4j.Log4j2;
// import net.bunnytouch.managersys.security.dao.UserInfoDAO;
// import net.bunnytouch.touchsys.terminal.entity.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.data.mongodb.core.query.Criteria;
// import org.springframework.data.mongodb.core.query.Query;
// import org.springframework.stereotype.Controller;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.util.*;
//
// @SuppressWarnings("unchecked")
// @Log4j2
// // TODO: 2019/10/23 zylTodo @Controller
// @Controller
// public class TerminalManageServiceImpl implements TerminalManageService {
//
//     @Autowired
//     private TbTerminalServiceImpl terminalService;
//
//     @Autowired
//     private TbTerminalPermissionServiceImpl terminalPermissionService;
//
//     @Autowired
//     private UserInfoDAO userInfoDao;
//
//     @Autowired
//     private TerminalConfigurationService configurationService;
//
//     @Reference
//     private CloudDiskExpo cloudDiskExpo;
//
//     @Autowired
//     private TerminalControlService controlService;
//
//     @Autowired
//     private TbTerminalDownloadServiceImpl terminalDownloadService;
//
//     @Autowired
//     private MongoTemplate mongoTemplate;
//
//     @Reference
//     private CaptureExpo captureExpo;
//
//     @Reference
//     private SecurityExpo securityUtil;
//
//     @Autowired
//     private TbTerminalRelevanceServiceImpl terminalRelevanceService;
//
//
//     @Override
//     @Transactional
//     public Object register(TbTerminal terminal) {
//         // 设置基础信息
//         //查询该客户域  terminal名称是否有重复的
//         int terminalCount = terminalService.countByTerminalNameAndCstmId(terminal.getTerminalName(), securityUtil.getCstmId());
//         if (terminalCount > 0) {
//             return new Message<>(45000, "该名称已使用", null);
//         }
//         terminal.setId(Identities.uuid2());
//         terminalService.save(terminal);
//         //设置创建TbTerminalDownload
//         TbTerminalDownload TbTerminalDownload = new TbTerminalDownload();
//         TbTerminalDownload.setId(Identities.uuid2());
//         TbTerminalDownload.setTerminalId(terminal.getId());
//         TbTerminalDownload.setCstmId(securityUtil.getCstmId());
//         terminalDownloadService.save(TbTerminalDownload);
//         cloudDiskExpo.createCloudMaterial(terminal);
//         if (terminal.getTerminalId() != null) {
//             //发送mqtt
//             controlService.modifyTerminal(terminal.getTerminalId(), terminal.getId());
//         }
//         return new Message<>(45001, "终端注册成功", null);
//     }
//
//     @Override
//     @Transactional
//     public int deleteTerminal(String ids) {
//         int count = 0;
//         String[] idList = ids.split(",");
//         for (String id : idList) {
//             //删除downLoad
//             terminalDownloadService.deleteByTerminalId(id);
//             //删除permission
//             terminalPermissionService.deletePermissionByTerminalId(id);
//             //删除relevance
//             terminalRelevanceService.deleteByTerminalId(id);
//             //删除terminal
//             terminalService.removeById(id);
//             count++;
//
//             TbTerminal terminal = terminalService.getById(id);
//             if (terminal != null) {
//                 cloudDiskExpo.deleteCloudMaterial(terminal);
//             }
//         }
//         return count;
//     }
//
//     @Override
//     public boolean updateTerminal(TbTerminal terminal) {
//         terminalService.updateById(terminal);
//         cloudDiskExpo.createCloudMaterial(terminal);
//         //controlService.modifyTerminal(terminal.getTerminalId());
//         return true;
//     }
//
//     @Override
//     public TbTerminal findById(String id) {
//         return terminalService.getById(id);
//     }
//
//     @Override
//     public TbTerminal findByTerminalId(String terminalId, String cstmId) {
//         return terminalService.findByTerminalIdAndCstmId(terminalId, cstmId);
//     }
//
//     @Override
//     public boolean checkTerminalId(String terminalId, String cstmId) {
//         TbTerminal terminal = this.findByTerminalId(terminalId, cstmId);
//         return terminal != null;
//     }
//
//     @Override
//     public boolean moveCategory(TbTerminal terminal, TbTerminalCategory category) {
//         return false;
//     }
//
//     @Override
//     public Page<TbTerminal> query(PageRequest pageRequest, Map terms, String order, String name, Map pair) throws Exception {
//         // TODO: 2019/10/24 zylTodo 看不懂
//         long count = terminalService.countCatIdList(terms);
//         // TODO: 2019/10/24 zylTodo 看不懂
//         List<TbTerminal> terminalList = terminalService.query(pageRequest, terms, order, StringUtils.propertyToField(name));
//
//         //List<TbTerminal> terminalList = terminalDao.queryByPermission(pageRequest,terms,order,StringUtils.propertyToField(name));
//
//         if (ActivationCount.REGISTERED.equals(pair.get("status"))) {
//             count = (Integer) pair.get("value") > count ? count : (Integer) pair.get("value");
//             int i = (Integer) pair.get("value") - (pageRequest.getPageNumber()) * pageRequest.getPageSize();
//             if (i < 0) {
//                 terminalList = null;
//             } else {
//                 for (; i < terminalList.size(); ) {
//                     terminalList.remove(i);
//                 }
//             }
//         }
//         if (terminalList != null) {
//             return new PageImpl<>(terminalList, pageRequest, count);
//         }
//         return null;
//     }
//
//     @SuppressWarnings({"rawtypes"})
//     @Override
//     public List<TbTerminal> finds(Map terms) {
//         // TODO: 2019/10/24 zylTodo 看不懂
//         return terminalService.finds(terms);
//     }
//
//     @Override
//     public <T extends CommonSetting> void reportSettings(T settings, Class<T> clazz, boolean individual) {
//         HashMap<String, Object> params = new HashMap<>();
//         params.put("terminalId", settings.getTerminalId());
//         params.put("cstmId", settings.getCstmId());
//         params.put("individual", individual);
//
//         T exists = configurationService.findOne(params, clazz);
//
//         //插入数据
//         if (exists == null) {
//             settings.setId(null);
//         } else {
//             settings.setId(exists.getId());
//         }
//         settings.setIndividual(individual);
//         configurationService.save(settings, clazz);
//     }
//
//     @Override
//     public <T extends CommonSetting> T getSettings(String terminalId, String cstmId, Class<T> clazz, boolean individual) {
//         HashMap<String, Object> params = new HashMap<>();
//         params.put("terminalId", terminalId);
//         params.put("cstmId", cstmId);
//         params.put("individual", individual);
//
//         return configurationService.findOne(params, clazz);
//     }
//
//     @Override
//     public List<TbTerminal> findTerminalBycstmId(String cstmId) {
//         return terminalService.findByCstmId(cstmId);
//     }
//
//     @Override
//     // TODO: 2019/10/24 zylTodo 返回值改变了
//     public List<TbTerminal> getTotalOnline(String cstmId) {
//         return terminalService.findByCstmId(cstmId);
//     }
//
//     @Override
//     public void setTbTerminalDownload(TbTerminalDownload TbTerminalDownload) {
//         terminalDownloadService.save(TbTerminalDownload);
//     }
//
//     @Override
//     public void updateTerminalOffline(String terminalId, int status) {
//         terminalService.updateTerminalOffline(terminalId, status);
//     }
//
//     @Override
//     public void updateTerminalDown(long totalSize, long downloadedSize, String id) {
//         terminalDownloadService.updateTerminalDown(totalSize, downloadedSize, id);
//     }
//
//     @Override
//     public TbTerminalDownload getTerminalDown(String cstmId, String terminalId) {
//         return terminalDownloadService.findByTermIdAndCstmId(cstmId, terminalId);
//     }
//
//     @Override
//     public void deleteTerminalDown(String DownId) {
//         terminalDownloadService.removeById(DownId);
//     }
//
//     @Override
//     public void batchUpdateOnline(List<String> list) {
//         terminalService.batchUpdateOnline(list);
//     }
//
//     @Override
//     public void batchUpdateOffline(List<String> list) {
//         terminalService.batchUpdateOffline(list);
//     }
//
//
//     @Override
//     public Map getTerminalPermission(PageRequest pageRequest, String id, String cstmId, String key) {
//         // TODO: 2019/10/24 zylTodo 看不懂
//         long countPer = terminalService.countPermission(id, key);
//         // TODO: 2019/10/24 zylTodo 看不懂
//         List<TbTerminalPermission> permissionList = terminalService.getTerminalPermission(pageRequest, id, key);
//
//         Map map = new HashMap();
//         map.put("cstmId", cstmId);
//         long countAll = userInfoDao.countUser(cstmId);
//         List<TbUser> userList = userInfoDao.findUserInfoByCstmId(cstmId, key);
//         if (permissionList.size() > 0) {
//             for (Iterator it = userList.iterator(); it.hasNext(); ) {
//                 TbUser user = (TbUser) it.next();
//                 for (int j = 0; j < permissionList.size(); j++) {
//                     if (user.getUserId().equals(permissionList.get(j).getUserId())) {
//                         it.remove();
//                         countAll--;
//                         break;
//                     }
//                 }
//             }
//         }
//
//         Map dataMap = new HashMap();
//         dataMap.put("perPage", new PageImpl<>(permissionList, pageRequest, countPer));
//         dataMap.put("AllPage", new PageImpl<>(userList, pageRequest, countAll));
//         return dataMap;
//     }
//
//     // TODO: 2019/10/24 zylTodo 脑回路贼清奇
//     @Override
//     public void insertBatchTerminalPermission(List list) {
//         terminalService.insertBatchTerminalPermission(list);
//     }
//
//     @Override
//     public void deleteTerminalPermission(String ids) {
//         terminalPermissionService.deleteTerminalPermission(ids);
//     }
//
//     @Override
//     public Map getUserTerminalPermission(PageRequest page, String userId, String cstmId) {
//         try {
//             long countPer = terminalService.countUserPermission(userId);
//             // TODO: 2019/10/24 zylTodo 弟弟行为
//             List<UserTerminalPermission> permissionList = terminalService.getUserTerminalPermission(page, userId);
//
//             Map map = new HashMap();
//             map.put("cstmId", cstmId);
//             long countAll = terminalService.count(map);
//             List<TbTerminal> terminalsList = terminalService.query(page, map, "desc", "create_time");
//             if (permissionList.size() > 0) {
//                 for (Iterator it = terminalsList.iterator(); it.hasNext(); ) {
//                     TbTerminal terminal = (TbTerminal) it.next();
//                     for (UserTerminalPermission userTerminaPermission : permissionList) {
//                         String perId = userTerminaPermission.getTerId();
//                         String eqId = terminal.getId();
//                         if (eqId.equals(perId)) {
//                             it.remove();
//                             countAll--;
//                             break;
//                         }
//                     }
//                 }
//             }
//             Map dataMap = new HashMap();
//             dataMap.put("perPage", new PageImpl<>(permissionList, page, countPer));
//             dataMap.put("AllPage", new PageImpl<>(terminalsList, page, countAll));
//             return dataMap;
//         } catch (Exception e) {
//             log.error("终端搜索...", e);
//             throw new RuntimeException("终端搜索失败", e);
//         }
//     }
//
//     @Override
//     public Page<TbTerminal> queryByPermission(PageRequest pageRequest, Map terms, String order, String name) {
//         // TODO: 2019/10/24 zylTodo 真会玩
//         long count = terminalService.countMyTerminal(terms);
//         try {
//
//             List<TbTerminal> terminalList = terminalService.queryByPermission(pageRequest, terms, order, StringUtils.propertyToField(name));
//
//             return new PageImpl<>(terminalList, pageRequest, count);
//         } catch (Exception e) {
//             log.error("终端搜索...", e);
//             throw new RuntimeException("终端搜索失败", e);
//         }
//     }
//
//     @Override
//     public List<TerminalVo> findAllTerminal(Map map) {
//         return terminalService.findAllTerminal(map);
//     }
//
//     @Override
//     public List<TbTerminal> getIsNotNull(String cstmId) {
//         return terminalService.getIsNotNull(cstmId);
//     }
//
//     @Override
//     public List<TbTerminal> getIsNull(String cstmId) {
//         return terminalService.getIsNull(cstmId);
//     }
//
//     @Override
//     public List<TerminalVo> getIsNullByPage(String cstmId, Integer start, Integer size) {
//         return terminalService.getIsNullByPage(cstmId, start, size);
//     }
//
//     @Override
//     public void updateNull(TbTerminal terminal) {
//        /* String extraId2 = terminal.getExtraId2();
//         ClassRoom classRoom = classRoomDao.get(extraId2);
//         classRoom.setTerminalId(null);
//         classRoomDao.update(classRoom);*/
//         terminalService.updateNull(terminal);
//         cloudDiskExpo.createCloudMaterial(terminal);
//         controlService.modifyTerminal(terminal.getTerminalId(), terminal.getId());
//     }
//
//     @Override
//     public Integer findAllTerminalCount(Map<String, Object> map) {
//         return terminalService.findAllTerminalCount(map);
//     }
//
//     @Override
//     public List<TbTerminal> findLikeTerminal(Map<String, Object> map) {
//         return terminalService.findLikeTerminal(map);
//     }
//
//     @Override
//     public Integer findLikeTerminalCount(Map<String, Object> map) {
//         return terminalService.findLikeTerminalCount(map);
//     }
//
//     @Override
//     public List<TbTerminal> queryMyTerminal(String userId) {
//         String cstmId = securityUtil.getCstmId();
//         boolean superUser = securityUtil.getSuperUser();
//         if (superUser) {
//             return terminalService.findByCstmId(cstmId);
//         }
//         return terminalService.queryMyTerminal(userId);
//     }
//
//     @Override
//     @Transactional
//     public void powerOff(String terminalIds) {
//         String[] strings = StringUtils.splitByChar(terminalIds, ",");
//         for (String terminalId : strings) {
//             updateOnline(terminalId, 5);
//             controlService.powerOff(terminalId);
//         }
//     }
//
//     @Override
//     @Transactional
//     public void delock(String terminalIds) {
//         String[] terminalIdList = terminalIds.split(",");
//         for (String terminalId : terminalIdList) {
//             updateOnline(terminalId, 4);
//             controlService.deLock(terminalId);
//         }
//     }
//
//     @Override
//     @Transactional
//     public void lock(String terminalIds) {
//         String[] terminalIdList = terminalIds.split(",");
//         for (String terminalId : terminalIdList) {
//             updateOnline(terminalId, 3);
//             controlService.lock(terminalId);
//         }
//     }
//
//     @Override
//     @Transactional
//     public void sleep(String terminalIds) {
//         String[] terminalIdList = terminalIds.split(",");
//         for (String terminalId : terminalIdList) {
//             updateOnline(terminalId, 1);
//             controlService.sleep(terminalId);
//         }
//     }
//
//     @Override
//     @Transactional
//     public void wakeup(String terminalIds) {
//         String[] terminalIdList = terminalIds.split(",");
//         for (String terminalId : terminalIdList) {
//             updateOnline(terminalId, 2);
//             controlService.wakeup(terminalId);
//         }
//     }
//
//     @Override
//     @Transactional
//     public void reboot(String terminalIds) {
//         String[] terminalIdList = terminalIds.split(",");
//         for (String terminalId : terminalIdList) {
//             updateOnline(terminalId, 0);
//             controlService.reboot(terminalId);
//         }
//     }
//
//     @Override
//     public Map<String, Object> getRoomConfig(String customerId) {
//         List<TerminalRelevanceVO> list = terminalService.getRoomConfig(customerId);
//         return terminalRelevanceService.getTreeMap(list);
//     }
//
//     @Override
//     public List<TerminalVo> queryManagerTerminal(Map<String, Object> params) {
//         // TODO: 2019/10/24 zylTodo 联查 会玩
//         return terminalService.queryManagerTerminal(params);
//     }
//
//     @Override
//     public List<String> queryTerminalByUserId(String userId) {
//         return terminalPermissionService.queryTerminalByUserId(userId);
//     }
//
//     @Override
//     @Transactional
//     public void batchMoveCategory(String ids, String catId) {
//         if (ids == null) {
//             return;
//         }
//         List<TbTerminal> list = new ArrayList<>();
//         String[] split = ids.split(",");
//         for (String id : split) {
//             TbTerminal terminal = new TbTerminal();
//             terminal.setId(id);
//             terminal.setCatId(catId);
//             list.add(terminal);
//         }
//         if (list.size() > 0) {
//             terminalService.batchUpdateCategory(list);
//         }
//     }
//
//     @Override
//     public void deleteByCstmId(String cstmId) {
//
//         //删除mongoDB信息
//         Query query = new Query(Criteria.where("cstmId").is(cstmId));
//         //删除配置信息
//         mongoTemplate.remove(query, WifiSetting.class, "touchsys_configuration_WifiSetting");
//         mongoTemplate.remove(query, UpgradeSetting.class, "touchsys_configuration_UpgradeSetting");
//         mongoTemplate.remove(query, TimingVolumeSetting.class, "touchsys_configuration_TimingVolumeSetting");
//         mongoTemplate.remove(query, TimingTouchSetting.class, "touchsys_configuration_TimingTouchSetting");
//         mongoTemplate.remove(query, TimingDownloadSetting.class, "touchsys_configuration_TimingDownloadSetting");
//         mongoTemplate.remove(query, ServerSetting.class, "touchsys_configuration_ServerSetting");
//         mongoTemplate.remove(query, ProgramSetting.class, "touchsys_configuration_ProgramSetting");
//         mongoTemplate.remove(query, PowerScheduleSetting.class, "touchsys_configuration_PowerScheduleSetting");
//         mongoTemplate.remove(query, EthernetSetting.class, "touchsys_configuration_EthernetSetting");
//         mongoTemplate.remove(query, AdvancedSetting.class, "touchsys_configuration_AdvancedSetting");
//         mongoTemplate.remove(query, PasswordSetting.class, "touchsys_configuration_PasswordSetting");
//         mongoTemplate.remove(query, TerminalInfo.class, "touchsys_configuration_TerminalInfo");
//         mongoTemplate.remove(query, ProgramSchedule.class, "touchsys_configuration_programschedule");
//         //删除terminal  mongoDB截图
//         List<TbTerminal> list = terminalService.findByCstmId(cstmId);
//         for (TbTerminal terminal : list) {
//             captureExpo.deleteById(terminal.getId());
//         }
//         terminalService.deleteByCstmId(cstmId);
//     }
//
//     @Override
//     public int findSameNameByCstmId(String terminalName, String cstmId) {
//         return terminalService.countByTerminalNameAndCstmId(terminalName, cstmId);
//     }
//
//     @Override
//     public void updateTerminalNew(TbTerminal tbTerminal) {
//         terminalService.updateTerminalPart(tbTerminal);
//         cloudDiskExpo.createCloudMaterial(tbTerminal);
//     }
//
//     @Override
//     public TbTerminal findByIdAndCstm(String id, String cstmId) {
//         return terminalService.findByIdAndCstm(id, cstmId);
//     }
//
//
//     /**
//      * 更新设备状态
//      *
//      * @param id     设备的主键id
//      * @param status 0:重启 1:关机 2:开机 3:锁屏 4:解锁 5:断电
//      *               设备状态  解析成二进制  高位:在线/离线  中位:亮屏/黑屏  低位:锁屏/解锁 (0表示否 1表示是)
//      */
//     private void updateOnline(String id, int status) {
//         TbTerminal terminal = new TbTerminal();
//         terminal.setCstmId(securityUtil.getCstmId());
//         terminal.setTerminalId(id);
//         List<TbTerminal> terminalList = terminalService.queryByCriteria(terminal);
//         TbTerminal terminalData = terminalList.get(0);
//         int onlineState = terminalData.getOnlineState();
//         if (onlineState == 0) {
//             //离线
//             return;
//         }
//         if (status == 0 || status == 5) {
//             terminalData.setOnlineState(0);
//             terminalService.updateById(terminalData);
//             return;
//         }
//         //解析
//         String state = DecimalTransitionUtil.toOtherBaseString(onlineState, 2);
//         int length = state.length();
//         //获取倒数第三位
//         char middlePlace = state.charAt(length - 1);//倒数第二位 中位
//         char lowPlace = state.charAt(length - 1);//倒数第三位 低位
//         String middlePlaceString = String.valueOf(middlePlace);
//         String lowPlaceString = String.valueOf(lowPlace);
//         //获取倒数第三位
//         if (status == 4 || status == 3) {
//             if (status == 4) {
//                 lowPlaceString = "0";
//             } else {
//                 lowPlaceString = "1";
//             }
//         }
//
//         if (status == 1 || status == 2) {
//             if (status == 1) {
//                 middlePlaceString = "0";
//             } else {
//                 middlePlaceString = "1";
//             }
//         }
//         String onlineStateNow = lowPlaceString + middlePlaceString + "1";
//         long onlineStateNowLong = DecimalTransitionUtil.toDecimal(onlineStateNow, 2);
//         terminalData.setOnlineState((int) onlineStateNowLong);
//         terminalService.updateById(terminalData);
//     }
//
// }
