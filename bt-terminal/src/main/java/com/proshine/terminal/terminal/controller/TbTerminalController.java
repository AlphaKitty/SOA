// package com.proshine.terminal.terminal.controller;
//
//
// import com.alibaba.fastjson.JSON;
// import com.alibaba.fastjson.JSONArray;
// import com.alibaba.fastjson.JSONObject;
// import com.proshine.expo.gateway.security.dto.CustomUserDetails;
// import com.proshine.terminal.terminal.service.TerminalManageService;
// import org.apache.commons.io.IOUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.util.CollectionUtils;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.servlet.ModelAndView;
//
// import javax.servlet.ServletInputStream;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.BufferedReader;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.io.OutputStream;
// import java.net.SocketException;
// import java.util.*;
//
// /**
//  * 前端控制器
//  *
//  * @author zyl
//  * @since 2019-10-21
//  */
// @RestController
// @RequestMapping("/terminal/tb-terminal")
// public class TbTerminalController {
//
//     @Autowired
//     private TerminalManageService terminalManageService;
//
//     @Autowired
//     private TerminalControlService terminalControlService;
//
//     @Autowired
//     private TerminalCategoryService terminalCategoryService;
//
//     @Autowired
//     private SoftwareService softwareService;
//
//     @Autowired
//     private TerminalCaptureService terminalCaptureService;
//
//     @Autowired
//     private TerminalDownloadService terminalDownloadService;
//
//     @Autowired
//     private RoleService roleService;
//
//     private List<String> globalCatIdsList = null;
//
//     @Autowired
//     private TerminalService terminalService;
//
//
//     /**
//      * 从session中获取用户数据
//      */
//     private CustomUserDetails getUserDetails() {
//         return (CustomUserDetails) SecurityContextHolder.getContext()
//                 .getAuthentication()
//                 .getPrincipal();
//     }
//
//     /**
//      * 终端管理首页
//      * url:${ctx}/touchsys/terminal/home
//      */
//     @RequestMapping(value = "home")
//     public ModelAndView home() {
//         view.setViewName("WEB-INF/touchsys/terminal/terminal");
//         return view;
//     }
//
//     /**
//      * 弹出终端注册Dialog
//      * url:${ctx}/touchsys/terminal/todoRegister
//      */
//     @RequestMapping(value = "todoRegister")
//     public ModelAndView todoRegister(@RequestParam("catId") String catId) {
//         view.addObject("terminal", null);
//         TerminalCategory terminalCategory = terminalCategoryService.findById(catId);
//         view.addObject("terminalCategory", terminalCategory);
//         view.setViewName("WEB-INF/touchsys/terminal/register");
//         return view;
//     }
//
//     /**
//      * 弹出终端移动分组Dialog
//      * url:${ctx}/touchsys/terminal/todoMove
//      * *@author tp
//      */
//     @RequestMapping(value = "todoMove")
//     public ModelAndView todoMove(@RequestParam("terminalIds") String terminalIds) {
//         view.addObject("terminalIds", terminalIds);
//         view.setViewName("WEB-INF/touchsys/terminal/move");
//         return view;
//     }
//
//     /**
//      * 弹出终端设置Dialog
//      * url:${ctx}/touchsys/terminal/todoSetting
//      */
//     @RequestMapping(value = "todoSetting")
//     public ModelAndView todoSetting(@RequestParam("terminalIds") String terminalIds) {
//         view.addObject("terminalIds", terminalIds);
//         view.addObject("version", DateUtil.getCurrentTime(DateUtil.LONG_DATE_FORMAT));
//         view.setViewName("WEB-INF/touchsys/terminal/setting");
//         return view;
//     }
//
//     /**
//      * 弹出网络设置Dialog
//      * url:${ctx}/touchsys/terminal/todoNetwork
//      */
//     @RequestMapping(value = "todoNetwork")
//     public ModelAndView todoNetwork(@RequestParam("terminalIds") String terminalIds) {
//         view.addObject("terminalIds", terminalIds);
//         view.addObject("version", DateUtil.getCurrentTime(DateUtil.LONG_DATE_FORMAT));
//         view.setViewName("WEB-INF/touchsys/terminal/network");
//         return view;
//     }
//
//     /**
//      * 弹出终端升级Dialog
//      * url:${ctx}/touchsys/terminal/todoUpgrade
//      */
//     @RequestMapping(value = "todoUpgrade")
//     public ModelAndView todoUpgrade(@RequestParam("terminalIds") String terminalIds) {
//         String[] terminalIdList = terminalIds.split(",");
//         if (terminalIdList.length == 1) {
//             String terminalId = terminalIdList[0];
//             UpgradeSetting upgradeSetting = terminalManageService.getSettings(terminalId, getSessionUser().getCstmId(),
//                     UpgradeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             if (upgradeSetting != null) {
//                 view.addObject("Daemon", JSON.toJSONString(upgradeSetting.getDaemonSoftware()));
//                 view.addObject("Touch", JSON.toJSONString(upgradeSetting.getTouchSoftware()));
//                 view.addObject("Firmware", JSON.toJSONString(upgradeSetting.getFirmwareSoftware()));
//                 view.addObject("other", JSON.toJSONString(upgradeSetting.getOtherSoftware()));
//             }
//             //3个不同类型的程
//         }
//         view.addObject("terminalIds", terminalIds);
//         view.setViewName("WEB-INF/touchsys/terminal/upgrade");
//         return view;
//     }
//
//
//     /**
//      * 弹出节目下发Dialog
//      * url:${ctx}/touchsys/terminal/todoIssued
//      * *@author 戴杏梅
//      */
//     @RequestMapping(value = "todoIssued")
//     public ModelAndView todoIssued(@RequestParam("terminalIds") String terminalIds) {
//         String[] terminalIdList = terminalIds.split(",");
//         if (terminalIdList.length == 1) {
//             String terminalId = terminalIdList[0];
//             ProgramSetting programSetting = terminalManageService.getSettings(terminalId, getSessionUser().getCstmId(),
//                     ProgramSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             view.addObject("programSetting", programSetting);
//         }
//
//         view.addObject("terminalIds", terminalIds);
//         view.setViewName("WEB-INF/touchsys/terminal/issued");
//         return view;
//     }
//
//     /**
//      * 弹出终端监控Dialog
//      * url:${ctx}/touchsys/terminal/todoMonitor
//      * *@author 戴杏梅
//      */
//     @RequestMapping(value = "todoMonitor")
//     public ModelAndView todoMonitor(@RequestParam("terminalId") String terminalId,
//                                     @RequestParam("androidId") String androidId) {
//         view.addObject("terminalId", terminalId);
//         view.addObject("androidId", androidId);
//         view.setViewName("WEB-INF/touchsys/terminal/monitor");
//         return view;
//     }
//
//     /**
//      * url:${ctx}/touchsys/terminal/todoTerminalCategory
//      * *@author 戴杏梅
//      */
//     @RequestMapping(value = "todoTerminalCategory")
//     public ModelAndView todoTerminalCategoryZtree() {
//         view.setViewName("WEB-INF/touchsys/terminal/terminalCategoryZtree");
//         return view;
//     }
//
//     /**
//      * 弹出终端编辑Dialog
//      * url:${ctx}/touchsys/terminal/todoEdit
//      * *@author 戴杏梅
//      */
//     @RequestMapping(value = "todoEdit")
//     public ModelAndView todoEdit(@RequestParam("id") String id) {
//         Terminal terminal = terminalManageService.findById(id);
//         view.addObject("terminal", terminal);
//
//         TerminalCategory terminalCategory = terminalCategoryService.findById(terminal.getCatId());
//         view.addObject("terminalCategory", terminalCategory);
//         view.setViewName("WEB-INF/touchsys/terminal/register");
//         return view;
//     }
//
//     /**
//      * 弹出终端信息Dialog
//      * url:${ctx}/touchsys/terminal/todoInfo
//      * *@author 戴杏梅
//      */
//     @RequestMapping(value = "todoInfo")
//     public ModelAndView todoInfo(@RequestParam("id") String id) {
//         Terminal terminal = terminalManageService.findById(id);
//         long max = terminal.getTotalSpace();
//         long free = terminal.getFreeSpace();
//         if (max != 0 || free != 0) {
//             String maxStr = StringUtils.sizeUnitConversion(max);
//             String freeStr = StringUtils.sizeUnitConversion(free);
//             view.addObject("max", maxStr);
//             view.addObject("free", freeStr);
//         }
//         view.addObject("terminal", terminal);
//
//         view.setViewName("WEB-INF/touchsys/terminal/info");
//         return view;
//     }
//
//     /**
//      * 终端日志
//      */
//     @RequestMapping(value = "todolog")
//     public ModelAndView todolog(@RequestParam("id") String id) {
//         view.addObject("id", id);
//         view.setViewName("WEB-INF/touchsys/terminal/log");
//         return view;
//     }
//
//     /**
//      * 终端搜索搜索
//      * url: {ctx}/touchsys/terminal/query
//      */
//     @RequestMapping(value = "/query", method = RequestMethod.GET)
//     @ResponseBody
//     public Object query(@RequestParam(value = "total") int total,
//                         @RequestParam(value = "currentPage") int currentPage,
//                         HttpServletRequest request) {
//         try {
//             Map count = new HashMap<>(16);
//             count.put("value", 1000);
//             count.put("status", ActivationCount.DEMO);
//             if (count.get("value") != null && (Integer) count.get("value") > 0) {
//                 Map<Object, Object> terms = new HashMap<>(16);
//                 String catId = request.getParameter("catId");
//                 if (catId != null && !"0".equals(catId)) {
//                     //如果等于null说明是其余地方调用的,不等于null说明是本类queryByPermission方法调用
//                     if (globalCatIdsList == null) {
//                         List<String> CatIdsList = terminalCategoryService.getCatIds(catId);
//                         terms.put("catId", CatIdsList);
//                     } else {
//                         terms.put("catId", globalCatIdsList);
//                     }
//                 }
//                 terms.put("terminalName", request.getParameter("terminalName"));
//                 String name = request.getParameter("name");
//                 terms.put("cstmId", getSessionUser().getCstmId());
//                 String order = request.getParameter("order");
//                 PageRequest pageRequest = new PageRequest(currentPage - 1, total);
//                 Page<Terminal> page = terminalManageService.query(pageRequest, terms, order, name, count);
//                 return new Message<Object>(45001, "终端搜索成功", page);
//             }
//             return new Message<Object>(401, "终端搜索失败，软件未授权", null);
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//             System.out.println(e.getMessage());
//             return new Message<>(45000, "终端搜索失败", null);
//         }
//     }
//
//     /**
//      * 终端注册
//      * url: {ctx}/touchsys/terminal/register
//      * method: POST
//      */
//     @RequestMapping(value = "/register", method = RequestMethod.POST)
//     @ResponseBody
//     public Object register(@RequestBody Terminal terminal) {
//         try {
//             terminal.setCstmId(getSessionUser().getCstmId());
//             terminal.setCreateUser(getSessionUser().getUsername());
//             return terminalManageService.register(terminal);
//         } catch (Exception e) {
//             log.error("终端注册失败", e);
//             return new Message<>(45000, "终端注册失败", null);
//         }
//     }
//
//     /**
//      * 终端编辑
//      * url: {ctx}/touchsys/terminal/update
//      * method: POST
//      */
//     @RequestMapping(value = "/update", method = RequestMethod.POST)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端编辑")
//     public Object update(@RequestBody Terminal terminal) {
//         try {
//             int count = terminalManageService.findSameNameByCstmId(terminal.getTerminalName(),
//                     SecurityUtil.getCstmId());
//             Terminal te = terminalManageService.findById(terminal.getId());
//             String terminalName = te.getTerminalName();
//             if (count > 0 && !terminalName.equals(terminal.getTerminalName())) {
//                 return new Message<>(45000, "终端名称已使用", null);
//             }
//             te.setTerminalName(terminal.getTerminalName());
//             te.setClassroomName(terminal.getClassroomName());
//             te.setDeviceModelId(terminal.getDeviceModelId());
//             te.setTerminalId(terminal.getTerminalId());
//             te.setExtraId2(terminal.getExtraId2());
//             te.setCatId(terminal.getCatId());
//             if (terminal.getTerminalId() == null) {
//                 te.setOnlineState(0);
//                 te.setIpaddr(null);
//             }
//             terminalManageService.updateTerminalNew(te);
//             terminalControlService.modifyTerminal(te.getTerminalId(), te.getId());
//             return new Message<>(45001, "终端编辑成功", null);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error("终端编辑失败", e);
//             return new Message<>(45000, "终端编辑失败", null);
//         }
//     }
//
//     /**
//      * 终端远程控制
//      * url: {ctx}/touchsys/terminal/remoteControl
//      * method: GET
//      */
//     @RequestMapping(value = "/remoteControl", method = RequestMethod.GET)
//     @ResponseBody
//     public Object remoteControl(@RequestParam("terminalId") String terminalId,
//                                 @RequestParam("keyCode") int keyCode) {
//         try {
//             terminalControlService.remoteControl(terminalId, keyCode);
//             return new Message<>(45001, "终端远程控制成功", null);
//         } catch (Exception e) {
//             log.error("终端远程控制失败", e);
//             return new Message<>(45000, "终端远程控制失败", null);
//         }
//     }
//
//
//     /**
//      * 终端删除
//      * url: {ctx}/touchsys/terminal/delete
//      * method: GET
//      */
//     @RequestMapping(value = "/delete", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端删除")
//     public Object delete(@RequestParam("ids") String ids) {
//         try {
//             terminalManageService.deleteTerminal(ids);
//             return new Message<>(45001, "终端删除成功", null);
//         } catch (Exception e) {
//             log.error("终端删除失败", e);
//             return new Message<>(45000, "终端删除失败", null);
//         }
//     }
//
//     /**
//      * 重启终端
//      * url: {ctx}/touchsys/terminal/reboot
//      * method: GET
//      */
//     @RequestMapping(value = "/reboot", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端重启")
//     public Object reboot(@RequestParam("terminalIds") String terminalIds) {
//         try {
//             terminalManageService.reboot(terminalIds);
//             return new Message<>(45001, "重启终端成功", null);
//         } catch (Exception e) {
//             log.error("重启终端失败", e);
//             return new Message<>(45000, "重启终端失败", null);
//         }
//     }
//
//     /**
//      * 终端关机
//      * url: {ctx}/touchsys/terminal/poweroff
//      * method: GET
//      */
//     @RequestMapping(value = "/poweroff", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端关机")
//     public Object poweroff(@RequestParam("terminalIds") String terminalIds) {
//         try {
//             terminalManageService.poweroff(terminalIds);
//             return new Message<>(45001, "终端关机成功", null);
//         } catch (Exception e) {
//             log.error("终端关机失败", e);
//             return new Message<>(45000, "终端关机失败", null);
//         }
//     }
//
//     /**
//      * 终端解锁
//      * url: {ctx}/touchsys/terminal/delock
//      * method: GET
//      */
//     @RequestMapping(value = "/delock", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端解锁")
//     public Object delock(@RequestParam("terminalIds") String terminalIds) {
//         try {
//             terminalManageService.delock(terminalIds);
//             return new Message<>(45001, "终端解锁成功", null);
//         } catch (Exception e) {
//             log.error("终端解锁失败", e);
//             return new Message<>(45000, "终端解锁失败", null);
//         }
//     }
//
//     /**
//      * 终端锁屏
//      * url: {ctx}/touchsys/terminal/lock
//      * method: GET
//      */
//     @RequestMapping(value = "/lock", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端锁屏")
//     public Object lock(@RequestParam("terminalIds") String terminalIds) {
//         try {
//             terminalManageService.lock(terminalIds);
//             return new Message<>(45001, "终端锁屏成功", null);
//         } catch (Exception e) {
//             log.error("终端锁屏失败", e);
//             return new Message<>(45000, "终端锁屏失败", null);
//         }
//     }
//
//     /**
//      * 终端休眠
//      * url: {ctx}/touchsys/terminal/sleep
//      * method: GET
//      */
//     @RequestMapping(value = "/sleep", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端休眠")
//     public Object sleep(@RequestParam("terminalIds") String terminalIds) {
//         try {
//             terminalManageService.sleep(terminalIds);
//             return new Message<>(45001, "终端休眠成功", null);
//         } catch (Exception e) {
//             log.error("终端休眠失败", e);
//             return new Message<>(45000, "终端休眠失败", null);
//         }
//     }
//
//     /**
//      * 调整音量
//      * url: {ctx}/touchsys/terminal/volume
//      * method: GET
//      */
//     @RequestMapping(value = "/volume", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "终端调整音量")
//     public Object volume(@RequestParam("terminalIds") String terminalIds,
//                          @RequestParam("lv") String lv) {
//         try {
//             String[] terminalIdList = terminalIds.split(",");
//             for (String terminalId : terminalIdList) {
//                 terminalControlService.volume(terminalId, lv);
//             }
//             return new Message<>(45001, "终端设置成功", null);
//         } catch (Exception e) {
//             log.error("终端休眠失败", e);
//             return new Message<>(45000, "终端设置失败", null);
//         }
//     }
//
//     /**
//      * 终端唤醒
//      * url: {ctx}/touchsys/terminal/wakeup
//      * method: GET
//      */
//     @RequestMapping(value = "/wakeup", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端唤醒")
//     public Object wakeup(@RequestParam("terminalIds") String terminalIds) {
//         try {
//             terminalManageService.wakeup(terminalIds);
//             return new Message<>(45001, "终端唤醒成功", null);
//         } catch (Exception e) {
//             log.error("终端唤醒失败", e);
//             return new Message<>(45000, "终端唤醒失败", null);
//         }
//     }
//
//     /**
//      * 终端截屏
//      * url: {ctx}/touchsys/terminal/capture
//      * method: GET
//      */
//     @RequestMapping(value = "/capture", method = RequestMethod.GET)
//     @ResponseBody
//     public Object captureScreen(@RequestParam("androidId") String androidId) {
//         try {
//             terminalControlService.captureScreen(androidId);
//             return new Message<>(45001, "终端截屏成功", null);
//         } catch (Exception e) {
//             log.error("终端截屏失败", e);
//             return new Message<>(45000, "终端截屏失败", null);
//         }
//     }
//
//
//     /**
//      * 清空磁盘
//      * url: {ctx}/touchsys/terminal/cleanDisk
//      * method: GET
//      */
//     @RequestMapping(value = "/cleanDisk", method = RequestMethod.GET)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端磁盘清空")
//     public Object cleanDisk(@RequestParam("terminalIds") String terminalIds) {
//         try {
//             String[] terminalIdList = terminalIds.split(",");
//             for (String terminalId : terminalIdList) {
//                 terminalControlService.cleanDisk(terminalId);
//             }
//             return new Message<>(45001, "清空磁盘成功", null);
//         } catch (Exception e) {
//             log.error("清空磁盘失败", e);
//             return new Message<>(45000, "清空磁盘失败", null);
//         }
//     }
//
//     /**
//      * 校验终端ID是否存在
//      * url: {ctx}/touchsys/terminal/checkTerminalId
//      * method: GET
//      */
//     @SuppressWarnings({"rawtypes", "unchecked"})
//     @RequestMapping(value = "/checkTerminalId", method = RequestMethod.GET)
//     @ResponseBody
//     public String checkTerminalId(@RequestParam("terminalId") String terminalId) {
//         Map map = new HashMap(16);
//         try {
//             boolean valid = terminalManageService.checkTerminalId(terminalId, getSessionUser().getCstmId());
//             map.put("valid", !valid);
//             return JSONObject.toJSONString(map);
//         } catch (Exception e) {
//             log.error("校验终端ID失败", e);
//             map.put("valid", true);
//             return JSONObject.toJSONString(map);
//         }
//     }
//
//     /**
//      * 节目下发
//      * url: {ctx}/touchsys/terminal/issued
//      * method: POST
//      */
//     @RequestMapping(value = "/issued", method = RequestMethod.POST)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行节目下发")
//     public Object issued(@RequestParam("terminalIds") String terminalIds,
//                          @RequestParam("programType") ProgramType programType,
//                          @RequestBody VersionSetting versionSetting) {
//         int downloadType = 0;
//         TerminalDownload terminalDownload = null;
//         try {
//             String[] terminalIdList = terminalIds.split(",");
//             for (String terminalId : terminalIdList) {
//                 terminalControlService.issueProgram(versionSetting.getNewId(), terminalId);
//
//                 ProgramSetting programSetting = terminalManageService.getSettings(terminalId, getSessionUser().getCstmId(),
//                         ProgramSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                 if (programSetting != null) {
//                     if (programSetting.getDemandProgram() != null) {
//                         downloadType = 4;
//                     } else if (programSetting.getLoopProgram() != null) {
//                         downloadType = 3;
//                     } else {
//                         downloadType = 5;
//                     }
//                 } else {
//                     downloadType = 4;
//                 }
//                 terminalDownload = terminalManageService.getTerminalDown(this.getSessionUser().getCstmId(), terminalId);
//                 if (terminalDownload == null) {
//                     terminalDownload = new TerminalDownload();
//                 }
//                 if (programSetting == null) {
//                     programSetting = new ProgramSetting();
//                     programSetting.setCstmId(getSessionUser().getCstmId());
//                     programSetting.setTerminalId(terminalId);
//                 }
//                 if (ProgramType.DEMAND == programType) {
//                     programSetting.setDemandProgram(versionSetting);
//                 } else if (ProgramType.LOOP == programType) {
//                     programSetting.setLoopProgram(versionSetting);
//                 } else if (ProgramType.PLUGIN == programType) {
//                     programSetting.setPluginProgram(versionSetting);
//                 }
//
//                 terminalManageService.reportSettings(programSetting, ProgramSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             }
//             JSONObject json = new JSONObject();
//             json.put("terminalDownload", terminalDownload);
//             json.put("downloadType", downloadType);
//             return new Message<>(45001, "节目下发成功", json);
//         } catch (Exception e) {
//             log.error("节目下发失败", e);
//             return new Message<>(45000, "节目下发失败", null);
//         }
//     }
//
//     /**
//      * 终端APP升级
//      * url: {ctx}/touchsys/terminal/upgradeApp
//      * method: POST
//      */
//     @RequestMapping(value = "/upgradeApp", method = RequestMethod.POST)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端APP升级")
//     public Object upgradeApp(@RequestParam("terminalIds") String terminalIds,
//                              @RequestBody VersionSetting appVersionSetting) {
//         TerminalDownload terminalDownload = null;
//         try {
//             int downloadType = 1;
//             String[] terminalIdList = terminalIds.split(",");
//             for (String terminalId : terminalIdList) {
//                 Terminal terminal = terminalManageService.findById(terminalId);
//                 String teId = terminal.getTerminalId();
//                 if (teId == null) {
//                     continue;
//                 }
//                 terminalDownload = terminalManageService.getTerminalDown(this.getSessionUser().getCstmId(), terminalId);
//                 if (terminalDownload == null) {
//                     throw new RuntimeException("未查到该设备的下载实体类,terminalId=" + terminalId);
//                 }
//                 String cstmId = this.getSessionUser().getCstmId();
//                 Software app = softwareService.find(appVersionSetting.getNewVersion(), cstmId, CommonConstant.SOFTWARE_TYPE_APP);
//                 terminalControlService.upgradeApp(teId, appVersionSetting.getNewVersion(),
//                         app.getChecksum(), app.getAppType(), app.getSoftwareId(), app.getName());
//                 int appType = Integer.parseInt(app.getAppType());
//                 VersionSetting daemonSoftware = null;
//                 VersionSetting touchSoftware = null;
//                 VersionSetting otherSoftware = null;
//                 if (appType == 1) {
//                     //deamo
//                     daemonSoftware = new VersionSetting();
//                     daemonSoftware.setNewName(appVersionSetting.getNewName());
//                     daemonSoftware.setNewVersion(appVersionSetting.getNewVersion());
//                     downloadType = 1;
//                     terminalDownload.setDaemonDownloadedSize(0L);
//                     terminalDownload.setDaemonTotalSize(0L);
//                 } else if (appType == 0) {
//                     //touch
//                     touchSoftware = new VersionSetting();
//                     touchSoftware.setNewName(appVersionSetting.getNewName());
//                     touchSoftware.setNewVersion(appVersionSetting.getNewVersion());
//                     downloadType = 6;
//                     terminalDownload.setTouchDownloadedSize(0L);
//                     terminalDownload.setTouchTotalSize(0L);
//                 } else if (appType == 2) {
//                     //第三方
//                     otherSoftware = new VersionSetting();
//                     otherSoftware.setNewName(appVersionSetting.getNewName());
//                     otherSoftware.setNewVersion(appVersionSetting.getNewVersion());
//                     downloadType = 7;
//                     terminalDownload.setOtherAppDownloadedSize(0L);
//                     terminalDownload.setOtherAppTotalSize(0L);
//                 }
//                 terminalDownloadService.updateByPrimaryKeySelective(terminalDownload);
//                 UpgradeSetting upgradeSetting = terminalManageService.getSettings(terminalId, getSessionUser().getCstmId(),
//                         UpgradeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                 if (upgradeSetting == null) {
//                     upgradeSetting = new UpgradeSetting();
//                     upgradeSetting.setCstmId(getSessionUser().getCstmId());
//                     upgradeSetting.setTerminalId(terminalId);
//                 }
//                 if (daemonSoftware != null) {
//                     upgradeSetting.setDaemonSoftware(daemonSoftware);
//                 }
//                 if (touchSoftware != null) {
//                     upgradeSetting.setTouchSoftware(touchSoftware);
//                 }
//                 if (otherSoftware != null) {
//                     upgradeSetting.setOtherSoftware(otherSoftware);
//                 }
//
//                 terminalManageService.reportSettings(upgradeSetting, UpgradeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             }
//             JSONObject json = new JSONObject();
//             json.put("terminalDownload", terminalDownload);
//             json.put("downloadType", downloadType);
//             return new Message<>(45001, "终端APP升级成功", json);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error("终端APP升级失败", e);
//             return new Message<>(45000, "终端APP升级失败", null);
//         }
//     }
//
//     /**
//      * 终端固件升级
//      * url: {ctx}/touchsys/terminal/upgradeFirmware
//      * method: POST
//      */
//     @RequestMapping(value = "/upgradeFirmware", method = RequestMethod.POST)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行固件升级")
//     public Object upgradeFirmware(@RequestParam("terminalIds") String terminalIds,
//                                   @RequestBody VersionSetting firmwareVersionSetting) {
//         TerminalDownload terminalDownload = null;
//         try {
//             int downloadType = 2;
//             String[] terminalIdList = terminalIds.split(",");
//             for (String terminalId : terminalIdList) {
//                 String cstmId = this.getSessionUser().getCstmId();
//                 Software app = softwareService.find(firmwareVersionSetting.getNewVersion(), cstmId, CommonConstant.SOFTWARE_TYPE_FIRMWARE);
//                 //terminalControlService.upgradeFirmware(terminalId, firmwareVersionSetting.getNewVersion(), app.getChecksum());
//
//                 terminalDownload = terminalManageService.getTerminalDown(this.getSessionUser().getCstmId(), terminalId);
//                 if (terminalDownload == null) {
//                     terminalDownload = new TerminalDownload();
//                 }
//                 UpgradeSetting upgradeSetting = terminalManageService.getSettings(terminalId, getSessionUser().getCstmId(),
//                         UpgradeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                 if (upgradeSetting == null) {
//                     upgradeSetting = new UpgradeSetting();
//                     upgradeSetting.setCstmId(getSessionUser().getCstmId());
//                     upgradeSetting.setTerminalId(terminalId);
//                 }
//                 upgradeSetting.setFirmwareSoftware(firmwareVersionSetting);
//                 terminalManageService.reportSettings(upgradeSetting, UpgradeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             }
//             JSONObject json = new JSONObject();
//             json.put("terminalDownload", terminalDownload);
//             json.put("downloadType", downloadType);
//             return new Message<>(45001, "终端固件升级成功", json);
//         } catch (Exception e) {
//             log.error("终端固件升级失败d", e);
//             return new Message<>(45000, "终端固件升级失败", null);
//         }
//     }
//
//     /**
//      * 设置终端设置
//      * url: {ctx}/touchsys/terminal/settingTerminal/{settingType}
//      * method: POST
//      */
//     @RequestMapping(value = "/settingTerminal/{settingType}", method = RequestMethod.POST)
//     @ResponseBody
//     @SystemLog(module = CommonConstant.TERMINAL_LOG, methods = "进行终端设置")
//     public Object settingTerminal(@PathVariable("settingType") String settingType,
//                                   @RequestParam("terminalIds") String terminalIds,
//                                   @RequestBody String settingJson) {
//
//
//         try {
//             String[] terminalIdArray = terminalIds.split(",");
//
//             switch (settingType) {
//                 case "PowerScheduleSetting":
//                     PowerScheduleSetting powerScheduleSetting = JSONObject.parseObject(settingJson, PowerScheduleSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         powerScheduleSetting.setTerminalId(terminalId);
//                         powerScheduleSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(powerScheduleSetting, PowerScheduleSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 case "ServerSetting":
//                     ServerSetting serverSetting = JSONObject.parseObject(settingJson, ServerSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         serverSetting.setTerminalId(terminalId);
//                         serverSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(serverSetting, ServerSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 case "EthernetSetting":
//                     EthernetSetting ethernetSetting = JSONObject.parseObject(settingJson, EthernetSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         ethernetSetting.setTerminalId(terminalId);
//                         ethernetSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(ethernetSetting, EthernetSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//
//                     }
//                     break;
//                 case "WifiSetting":
//                     WifiSetting wifiSetting = JSONObject.parseObject(settingJson, WifiSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         wifiSetting.setTerminalId(terminalId);
//                         wifiSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(wifiSetting, WifiSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 case "TimingVolumeSetting":
//                     TimingVolumeSetting timingVolumeSetting = JSONObject.parseObject(settingJson, TimingVolumeSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         timingVolumeSetting.setTerminalId(terminalId);
//                         timingVolumeSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(timingVolumeSetting, TimingVolumeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 case "TimingTouchSetting":
//                     TimingTouchSetting timingTouchSetting = JSONObject.parseObject(settingJson, TimingTouchSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         timingTouchSetting.setTerminalId(terminalId);
//                         timingTouchSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(timingTouchSetting, TimingTouchSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 case "TimingDownloadSetting":
//                     TimingDownloadSetting timingDownloadSetting = JSONObject.parseObject(settingJson, TimingDownloadSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         timingDownloadSetting.setTerminalId(terminalId);
//                         timingDownloadSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(timingDownloadSetting, TimingDownloadSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 case "AdvancedSetting":
//                     AdvancedSetting advancedSetting = JSONObject.parseObject(settingJson, AdvancedSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         advancedSetting.setTerminalId(terminalId);
//                         advancedSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(advancedSetting, AdvancedSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 case "PasswordSetting":
//                     PasswordSetting passwordSetting = JSONObject.parseObject(settingJson, PasswordSetting.class);
//                     for (String terminalId : terminalIdArray) {
//                         passwordSetting.setTerminalId(terminalId);
//                         passwordSetting.setCstmId(getSessionUser().getCstmId());
//                         terminalManageService.reportSettings(passwordSetting, PasswordSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                         try {
//                             sendMqttSetting(settingType, terminalId);
//                         } catch (Exception e) {
//                         }
//                     }
//                     break;
//                 default:
//
//             }
//
//             return new Message<>(45001, "设置终端设置成功", null);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error("设置终端设置失败", e);
//             return new Message<>(45000, "设置终端设置失败", null);
//         }
//     }
//
//     /**
//      * 获取终端设置
//      * url: {ctx}/touchsys/terminal/getSettings/{settingType}
//      * method: GET
//      *
//      * @param isTerminal true:获取服务器 false:获取终端
//      * @param isFlag     0:自动获取mongodb文件 1:手动点击获取按钮,需要发送指令,重新上报
//      */
//     @RequestMapping(value = "/getSettings/{settingType}", method = RequestMethod.GET)
//     @ResponseBody
//     public Object getSettings(@PathVariable("settingType") String settingType,
//                               @RequestParam("terminalId") String terminalId, boolean isTerminal, String isFlag) {
//
//         try {
//             String cstmId = getSessionUser().getCstmId();
//             CommonSetting commonSetting = null;
//             switch (settingType) {
//                 case "PowerScheduleSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, PowerScheduleSetting.class, isTerminal);
//                     break;
//                 case "ServerSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, ServerSetting.class, isTerminal);
//                     break;
//                 case "EthernetSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, EthernetSetting.class, isTerminal);
//                     break;
//                 case "WifiSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, WifiSetting.class, isTerminal);
//                     break;
//                 case "TimingVolumeSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, TimingVolumeSetting.class, isTerminal);
//                     break;
//                 case "TimingTouchSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, TimingTouchSetting.class, isTerminal);
//                     break;
//                 case "TimingDownloadSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, TimingDownloadSetting.class, isTerminal);
//                     break;
//                 case "AdvancedSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, AdvancedSetting.class, isTerminal);
//                     break;
//                 case "PasswordSetting":
//                     commonSetting = terminalManageService.getSettings(terminalId, cstmId, PasswordSetting.class, isTerminal);
//                     break;
//                 default:
//             }
//             if ("1".equals(isFlag)) {
//                 if (isTerminal) {
//                     Terminal terminal = terminalManageService.findById(terminalId);
//                     if (terminal.getTerminalId() != null) {
//                         terminalControlService.reportTerminalSetting(terminal.getTerminalId(), settingType);
//                     } else {
//                         return new Message<>(45000, "未绑定设备", commonSetting);
//                     }
//                     return new Message<>(45001, "获取成功.", commonSetting);
//                 }
//             } else {
//                 return new Message<>(45001, "", commonSetting);
//             }
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "获取终端设置失败", null);
//         }
//         return new Message<>(45001, "获取终端设置成功", null);
//     }
//
//     /**
//      * 获取统计 在线情况的信息
//      * /touchsys/terminal/getTotalOnline
//      *
//      * @return
//      */
//     @RequestMapping(value = "/getTotalOnline", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
//     @ResponseBody
//     public String getTotalOnline() {
//
//         List<TotalOnline> list = terminalManageService.getTotalOnline(getSessionUser().getCstmId());
//
//         ArrayList<TotalOnline> onlineList = new ArrayList<>();
//
//         int offLine = 0;
//         int onLine = 0;
//
//         for (int i = 0; i < list.size(); i++) {
//             TotalOnline totalOnline = list.get(i);
//             String isOnline = totalOnline.getIsOnline();
//             if ("0".equals(isOnline)) {
//                 offLine++;
//             } else {
//                 onLine++;
//             }
//         }
//         TotalOnline Online = new TotalOnline();
//         Online.setTotal(onLine);
//         Online.setColor(CommonConstant.COLOR_BLUE);
//         Online.setIsOnline("在线");
//
//         TotalOnline offOnline = new TotalOnline();
//         offOnline.setTotal(offLine);
//         offOnline.setColor(CommonConstant.COLOR_ORANGE);
//         offOnline.setIsOnline("离线");
//
//         onlineList.add(Online);
//         onlineList.add(offOnline);
//
//         JSONObject jsonObject = new JSONObject();
//         jsonObject.put("list", onlineList);
//
//         return jsonObject.toJSONString();
//     }
//
//     /**
//      * 在线状态更新
//      * /touchsys/terminal/mqttControOffline
//      */
//     @RequestMapping(value = "/mqttControOffline", method = RequestMethod.POST)
//     public void mqttControOffline(HttpServletRequest request) {
//         System.out.println("进入");
//         String params = acceptJSON(request);
//         JSONObject jsonob = (JSONObject) JSONObject.parse(params);
//         //获取动作
//         String action = jsonob.getString("action");
//         String terminalId = (String) jsonob.get("client_id");
//         int status = 0;
//         //连接的时候
//         if (CommonConstant.CLIENT_ACTION_CONNEDCTED.equals(action)) {
//             status = 1;
//             System.out.println(terminalId + "连接");
//             //失联的时候
//         } else if (CommonConstant.CLIENT_ACTION_DISCONNEDCTED.equals(action)) {
//             status = 0;
//             System.out.println(terminalId + "断线");
//         }
//
//         terminalManageService.updateTerminalOffline(terminalId, status);
//     }
//
//     /**
//      * json数据转字符串
//      *
//      * @param request
//      * @return
//      */
//     public String acceptJSON(HttpServletRequest request) {
//         String acceptjson = "";
//         try {
//             BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
//             StringBuffer sb = new StringBuffer("");
//             String temp;
//             while ((temp = br.readLine()) != null) {
//                 sb.append(temp);
//             }
//             br.close();
//             acceptjson = sb.toString();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return acceptjson;
//     }
//
//     /**
//      * 获取屏幕截屏
//      * url: {ctx}/touchsys/terminal/showCapture
//      * method: GET
//      */
//     @RequestMapping(value = "/showCapture", method = RequestMethod.GET)
//     @ResponseBody
//     public void showCapture(@RequestParam("terminalId") String terminalId,
//                             @RequestParam("timestrap") String timestrap,
//                             HttpServletResponse response) {
//         try {
//             response.reset();
//             OutputStream outputStream = response.getOutputStream();
//             InputStream inputStream;
//             inputStream = terminalCaptureService.read(terminalId, null);
//
//             if (null != inputStream)
//                 IOUtils.copy(inputStream, outputStream);
//             //关闭流
//             IOUtils.closeQuietly(inputStream);
//             IOUtils.closeQuietly(outputStream);
//         } catch (SocketException e) {
//             log.error("客户端终止下载", e);
//         } catch (Exception e2) {
//             log.error("下载文件失败", e2);
//         }
//     }
//
//     /**
//      * touchsys/terminal/setTerminalDownload
//      *
//      * @return
//      */
//     @RequestMapping(value = "/setTerminalDownload", method = RequestMethod.POST)
//     @ResponseBody
//     public Object setTerminalDownload(@RequestBody JSONObject json) {
//
//         try {
//             String terminalId = json.getString("terminalId");
//             String cstmId = json.getString("cstmId");
//             Long totalSize = json.getLong("totalSize");
//             Long downLoadSize = json.getLong("downLoadSize");
//             String id = json.getString("id");
//             String name = json.getString("name");
//             String version = json.getString("version");
//             String type = json.getString("type");
//             TerminalDownload query = new TerminalDownload();
//             query.setCstmId(cstmId);
//             query.setTerminalId(terminalId);
//             List<TerminalDownload> terminalDownloads = terminalDownloadService.selectByCriteria(query);
//             //创建终端的时候会一起创建downLoad
//             TerminalDownload terminalDownload = terminalDownloads.get(0);
//
//             if ("1".equals(type)) {
//                 terminalDownload.setDaemonTotalSize(totalSize);
//                 terminalDownload.setDaemonDownloadedSize(downLoadSize);
//                 terminalDownload.setDaemonVersion(Integer.valueOf(version));
//                 terminalDownload.setDaemonId(id);
//             }
//
//             if ("2".equals(type)) {
//                 terminalDownload.setFirmwareTotalSize(totalSize);
//                 terminalDownload.setFirmwareDownloadedSize(downLoadSize);
//                 terminalDownload.setFirmwareVersion(Integer.valueOf(version));
//                 terminalDownload.setFirmwareId(id);
//             }
//             if ("3".equals(type)) {
//                 terminalDownload.setLoopTotalSize(totalSize);
//                 terminalDownload.setLoopDownloadedSize(downLoadSize);
//                 terminalDownload.setLoopProgramId(id);
//                 terminalDownload.setLoopName(name);
//                 terminalDownload.setLoopVersion(Integer.valueOf(version));
//             }
//             if ("4".equals(type)) {
//                 terminalDownload.setThemeTotalSize(totalSize);
//                 terminalDownload.setThemeDownloadedSize(downLoadSize);
//                 terminalDownload.setThemeName(name);
//                 terminalDownload.setThemeVersion(Integer.valueOf(version));
//                 terminalDownload.setThemeId(id);
//             }
//
//             if ("5".equals(type)) {
//                 terminalDownload.setPluginTotalSize(totalSize);
//                 terminalDownload.setPluginDownloadedSize(downLoadSize);
//                 terminalDownload.setPluginName(name);
//                 terminalDownload.setPluginVersion(Integer.valueOf(version));
//                 terminalDownload.setPluginProgramId(id);
//             }
//
//             if ("6".equals(type)) {
//                 terminalDownload.setTouchTotalSize(totalSize);
//                 terminalDownload.setTouchDownloadedSize(downLoadSize);
//                 terminalDownload.setTouchId(id);
//                 terminalDownload.setTouchVersion(Integer.valueOf(version));
//             }
//
//             if ("7".equals(type)) {
//                 terminalDownload.setOtherAppTotalSize(totalSize);
//                 terminalDownload.setOtherAppDownloadedSize(downLoadSize);
//                 terminalDownload.setOtherAppId(id);
//                 terminalDownload.setOtherAppVersion(version);
//             }
//
//             terminalDownloadService.updateByPrimaryKeySelective(terminalDownload);
//             return new Message<>(45001, "获取成功", null);
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "获取失败", null);
//         }
//     }
//
//
//     /**
//      * {ctx}/touchsys/terminal/getTermimalDown
//      */
//     @RequestMapping(value = "/getTermimalDown", method = RequestMethod.GET)
//     @ResponseBody
//     public Object getTermimalDown(@RequestParam("downloadType") Integer downloadType, @RequestParam("terminalId") String terminalId) {
//         try {
//             TerminalDownload query = new TerminalDownload();
//             query.setCstmId(SecurityUtil.getCstmId());
//             query.setTerminalId(terminalId);
//             List<TerminalDownload> terminalDownloads = terminalDownloadService.selectByCstmAndTerminal(query);
//             if (CollectionUtils.isEmpty(terminalDownloads)) {
//                 return new Message<>(45000, "未找到该设备", null);
//             }
//             TerminalDownload terminalDownload = terminalDownloads.get(0);
//             UpgradeSetting upgradeSetting = terminalManageService.getSettings(terminalDownload.getTerminalId(),
//                     this.getSessionUser().getCstmId(), UpgradeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             ProgramSetting programSetting = terminalManageService.getSettings(terminalId, getSessionUser().getCstmId(),
//                     ProgramSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             if (upgradeSetting == null) {
//                 upgradeSetting = new UpgradeSetting();
//             }
//
//             if (programSetting == null) {
//                 programSetting = new ProgramSetting();
//             }
//             boolean isFlag = false;
//             if (!isFlag) {
//                 if (downloadType == 1 && terminalDownload.getDaemonTotalSize() != 0 && terminalDownload.getDaemonTotalSize().equals(terminalDownload.getDaemonDownloadedSize())) {
//                     isFlag = true;
//                     upgradeSetting.getDaemonSoftware().setCurrentVersion(upgradeSetting.getDaemonSoftware().getNewVersion());
//                     upgradeSetting.getDaemonSoftware().setCurrentName(upgradeSetting.getDaemonSoftware().getNewName());
//                 }
//                 if (downloadType == 2 && terminalDownload.getFirmwareTotalSize() != 0 && terminalDownload.getFirmwareTotalSize().equals(terminalDownload.getFirmwareDownloadedSize())) {
//                     isFlag = true;
//                     upgradeSetting.getFirmwareSoftware().setCurrentVersion(upgradeSetting.getFirmwareSoftware().getNewVersion());
//                     upgradeSetting.getFirmwareSoftware().setCurrentName(upgradeSetting.getFirmwareSoftware().getNewName());
//                 }
//                 if (downloadType == 3 && terminalDownload.getLoopTotalSize() != 0 && terminalDownload.getLoopTotalSize().equals(terminalDownload.getLoopDownloadedSize())) {
//                     isFlag = true;
//                     programSetting.getLoopProgram().setCurrentVersion(programSetting.getLoopProgram().getNewVersion());
//                     programSetting.getLoopProgram().setCurrentName(programSetting.getLoopProgram().getNewName());
//                 }
//                 if (downloadType == 4 && terminalDownload.getThemeTotalSize() != 0 && terminalDownload.getThemeTotalSize().equals(terminalDownload.getThemeDownloadedSize())) {
//                     isFlag = true;
//                     programSetting.getDemandProgram().setCurrentVersion(programSetting.getDemandProgram().getNewVersion());
//                     programSetting.getDemandProgram().setCurrentName(programSetting.getDemandProgram().getNewName());
//                 }
//                 if (downloadType == 5 && terminalDownload.getPluginTotalSize() != 0 && terminalDownload.getPluginTotalSize().equals(terminalDownload.getPluginDownloadedSize())) {
//                     isFlag = true;
//                     programSetting.getPluginProgram().setCurrentVersion(programSetting.getPluginProgram().getNewVersion());
//                     programSetting.getPluginProgram().setCurrentName(programSetting.getPluginProgram().getNewName());
//                 }
//                 if (downloadType == 6 && terminalDownload.getTouchTotalSize() != 0 && terminalDownload.getTouchTotalSize().equals(terminalDownload.getTouchDownloadedSize())) {
//                     isFlag = true;
//                     upgradeSetting.getTouchSoftware().setCurrentVersion(upgradeSetting.getTouchSoftware().getNewVersion());
//                     upgradeSetting.getTouchSoftware().setCurrentName(upgradeSetting.getTouchSoftware().getNewName());
//                 }
//                 if (downloadType == 7 && terminalDownload.getOtherAppTotalSize() != 0 && terminalDownload.getOtherAppTotalSize().equals(terminalDownload.getOtherAppDownloadedSize())) {
//                     isFlag = true;
//                     upgradeSetting.getOtherSoftware().setCurrentVersion(upgradeSetting.getOtherSoftware().getNewVersion());
//                     upgradeSetting.getOtherSoftware().setCurrentVersion(upgradeSetting.getOtherSoftware().getNewName());
//                 }
//             }
//             if (isFlag) {
//                 terminalManageService.reportSettings(upgradeSetting, UpgradeSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//                 terminalManageService.reportSettings(programSetting, ProgramSetting.class, TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER);
//             }
//             JSONObject json = new JSONObject();
//             json.put("terminalDownload", terminalDownload);
//             json.put("downloadType", downloadType);
//             return new Message<>(45001, "获取成功", json);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "获取失败", null);
//         }
//     }
//
//     @RequestMapping(value = "deleteTerminalDown", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
//     @ResponseBody
//     public Object deleteTerminalDown(HttpServletRequest request) {
//         String downId = request.getParameter("downId");
//         terminalManageService.deleteTerminalDown(downId);
//         return new Message<>(45000, "删除成功", null);
//     }
//
//     /**
//      * 终端搜索搜索
//      * url: {ctx}/touchsys/terminal/queryByPermission
//      */
//     @RequestMapping(value = "/queryByPermission", method = RequestMethod.GET)
//     @ResponseBody
//     public Object queryByPermission(@RequestParam(value = "total") int total,
//                                     @RequestParam(value = "currentPage") int currentPage,
//                                     HttpServletRequest request) {
//
//         Map<Object, Object> terms = new HashMap<>(16);
//         String catId = request.getParameter("catId");
//         if (catId != null && !"0".equals(catId)) {
//             List<String> catIdsList = terminalCategoryService.getCatIds(catId);
//             terms.put("catId", catIdsList);
//             globalCatIdsList = catIdsList;
//         }
//         String terminalName = request.getParameter("terminalName");
//         terms.put("terminalName", terminalName);
//         String name = request.getParameter("name");
//         terms.put("cstmId", getSessionUser().getCstmId());
//         String order = request.getParameter("order");
//         PageRequest pageRequest = new PageRequest(currentPage - 1, total);
//         try {
//             Set<GrantedAuthority> set = this.getSessionUser().getAuthorities();
//
//             for (Iterator it = set.iterator(); it.hasNext(); ) {
//                 //TODO 待修改,权限点
//                 SimpleGrantedAuthority grantedAuthority = (SimpleGrantedAuthority) it.next();
//                 String authority = grantedAuthority.getAuthority();
//                 Role role = roleService.selectById(authority);
//                 if (role.getRoleName().equals("超级管理员")) {
//                     return this.query(total, currentPage, request);
//                 }
//             }
//             terms.put("userId", getSessionUser().getUserId());
//             Page<Terminal> page = terminalManageService.queryByPermission(pageRequest, terms, order, name);
//             return new Message<Object>(45001, "终端搜索成功", page);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error("文件/目录搜索失败", e);
//             return new Message<>(45000, "终端搜索失败", null);
//         }
//     }
//
//
//     /**
//      * 终端远程触控控制
//      * url: {ctx}/touchsys/terminal/touchScreen
//      * method: GET
//      */
//     @RequestMapping(value = "/touchScreen", method = RequestMethod.GET)
//     @ResponseBody
//     public Object touchScreen(@RequestParam("terminalId") String terminalId,
//                               @RequestParam("x") int x,
//                               @RequestParam("y") int y) {
//         try {
//             terminalControlService.touchScreen(terminalId, x, y);
//             return new Message<>(45001, "终端远程触控控制成功", null);
//         } catch (Exception e) {
//             log.error("终端远程触控控制失败", e);
//             return new Message<>(45000, "终端远程触控控制失败", null);
//         }
//     }
//
//     /**
//      * @return java.lang.Object
//      * @Description 查找所有设备 和关联的教室
//      * @Date 14:34 2018/9/25
//      * @Param []
//      **/
//     @RequestMapping(value = "/findAllTerminal", method = RequestMethod.GET)
//     @ResponseBody
//     public Object findAllTerminal(@RequestParam("page") Integer page,
//                                   @RequestParam("size") Integer size,
//                                   @RequestParam(value = "terminalName", required = false) String terminalName,
//                                   @RequestParam(value = "terminalId", required = false) String terminalId) {
//         try {
//             CustomUserDetails userDetails = getUserDetails();
//             String cstmId = userDetails.getCstmId();
//             Integer start = (page - 1) * size;
//             Map<String, Object> map = new HashMap<>();
//             map.put("cstmId", cstmId);
//             map.put("start", start);
//             map.put("size", size);
//             map.put("terminalId", terminalId);
//             map.put("terminalName", terminalName);
//             List<TerminalVo> list = terminalManageService.findAllTerminal(map);
//             Integer total = terminalManageService.findAllTerminalCount(map);
//             JSONObject json = new JSONObject();
//             json.put("list", list);
//             json.put("total", total);
//             return new Message<>(45001, "获取信息成功", json);
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "获取信息失败", null);
//         }
//     }
//
//     /***
//      * @Description 获取本校区没有绑定教室的设备
//      * @Date 16:27 2018/9/25
//      * @Param []
//      * @return java.lang.Object
//      **/
//     @RequestMapping(value = "/findAllTerminalNoBind", method = RequestMethod.GET)
//     @ResponseBody
//     public Object findAllTerminalNoBind() {
//         try {
//             CustomUserDetails userDetails = getUserDetails();
//             String cstmId = userDetails.getCstmId();
//             List<Terminal> list = terminalManageService.getIsNull(cstmId);
//             return new Message<>(45001, "获取信息成功", list);
//         } catch (Exception e) {
//             log.error(e);
//             return new Message<>(45000, "获取信息失败", null);
//         }
//     }
//
//     @RequestMapping(value = "/findTerminalByPager", method = RequestMethod.GET)
//     @ResponseBody
//     public Object findTerminalByPager(@RequestParam("page") Integer page,
//                                       @RequestParam("size") Integer size) {
//         try {
//             CustomUserDetails userDetails = getUserDetails();
//             String cstmId = userDetails.getCstmId();
//             Integer start = (page - 1) * size;
//             List<TerminalVo> list = terminalManageService.getIsNullByPage(cstmId, start, size);
//             return new Message<>(45001, "获取信息成功", list);
//         } catch (Exception e) {
//             log.error(e);
//             System.out.println(e);
//             return new Message<>(45000, "获取信息失败", null);
//         }
//     }
//
//     /***
//      * @Description 解除关联 和 添加关联
//      * @Date 9:45 2018/9/27
//      * @Param [json]
//      * @return java.lang.Object
//      **/
//     @RequestMapping(value = "/relieveTerminal", method = RequestMethod.POST)
//     @ResponseBody
//     public Object relieveTerminal(@RequestBody JSONObject json) {
//         try {
//             String tId = json.getString("tId");
//             String classId = json.getString("classId");
//             Terminal terminal = terminalManageService.findById(tId);
//             terminal.setExtraId2(classId);
//             terminalManageService.updateNull(terminal);
//             return new Message<>(45001, "获取信息成功", null);
//         } catch (Exception e) {
//             log.error(e);
//             return new Message<>(45000, "获取信息失败", null);
//         }
//     }
//
//     /**
//      * @return java.lang.Object
//      * @Description 批量解除关联
//      * @Date 15:38 2018/10/9
//      * @Param [json]
//      **/
//     @RequestMapping(value = "/someDelete", method = RequestMethod.POST)
//     @ResponseBody
//     public Object someDelete(@RequestBody JSONObject json) {
//         try {
//             JSONArray arr = json.getJSONArray("arr");
//             for (int i = 0; i < arr.size(); i++) {
//                 String id = arr.getString(i);
//                 Terminal terminal = terminalManageService.findById(id);
//                 terminal.setExtraId2(null);
//                 terminalManageService.updateNull(terminal);
//             }
//             return new Message<>(45001, "获取信息成功", null);
//         } catch (Exception e) {
//             log.error(e);
//             return new Message<>(45000, "获取信息失败", null);
//         }
//     }
//
//     /***
//      * @Description 整体更新
//      * @Date 16:16 2018/10/11
//      * @Param [json]
//      * @return java.lang.Object
//      **/
//     @RequestMapping(value = "/updateTeminal", method = RequestMethod.POST)
//     @ResponseBody
//     public Object updateTeminal(@RequestBody JSONObject json) {
//         try {
//             Terminal terminal = JSONObject.toJavaObject(json, Terminal.class);
//             terminalManageService.updateTerminal(terminal);
//             terminalControlService.modifyTerminal(terminal.getTerminalId(), terminal.getId());
//             return new Message<>(45001, "更新成功", null);
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "更新失败", e.getMessage());
//         }
//     }
//
//     @RequestMapping(value = "/findLikeTerminal", method = RequestMethod.GET)
//     @ResponseBody
//     public Object findLikeTerminal(@RequestParam Integer page,
//                                    @RequestParam Integer size,
//                                    @RequestParam String name) {
//         try {
//             CustomUserDetails userDetails = getUserDetails();
//             String cstmId = userDetails.getCstmId();
//             Integer start = (page - 1) * size;
//             Map<String, Object> map = new HashMap<>();
//             map.put("cstmId", cstmId);
//             map.put("start", start);
//             map.put("size", size);
//             map.put("name", name);
//             List<Terminal> list = terminalManageService.findLikeTerminal(map);
//             Integer total = terminalManageService.findLikeTerminalCount(map);
//             JSONObject json = new JSONObject();
//             json.put("list", list);
//             json.put("total", total);
//             return new Message<>(45001, "获取成功", json);
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "获取失败", e.getMessage());
//         }
//     }
//
//     @RequestMapping(value = "/queryMyTerminal", method = RequestMethod.POST)
//     @ResponseBody
//     public Object queryMyTerminal(@RequestBody JSONObject json) {
//         try {
//             List<Terminal> list = terminalManageService.queryMyTerminal(SecurityUtil.getUserId());
//             return new Message<>(45001, "获取成功", list);
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "获取失败", e.getMessage());
//         }
//     }
//
//     @RequestMapping(value = "/queryManagerTerminal", method = RequestMethod.GET)
//     @ResponseBody
//     public Object queryManagerTerminal(@RequestParam("page") int page,
//                                        @RequestParam("size") int size,
//                                        @RequestParam(value = "order", required = false) String order,
//                                        @RequestParam(value = "orderName", required = false) String orderName,
//                                        @RequestParam(value = "catId", required = false) String catId,
//                                        @RequestParam(value = "terminalName", required = false) String terminalName) {
//         try {
//             Map<String, Object> params = new HashMap<>();
//             if (catId != null && !"0".equals(catId)) {
//                 List<String> catIdsList = terminalCategoryService.getCatIds(catId);
//                 params.put("catId", catIdsList);
//             }
//             if (terminalName != null && !terminalName.equals("")) {
//                 params.put("terminalName", terminalName);
//             }
//             params.put("cstmId", SecurityUtil.getCstmId());
//             params.put("order", order);
//             params.put("orderName", orderName);
//             PageHelper.startPage(page, size);
//             List<TerminalVo> list = terminalManageService.queryManagerTerminal(params);
//             PageSerializable<TerminalVo> of = PageSerializable.of(list);
//             return new Message<>(45001, "获取成功", of);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "获取失败", null);
//         }
//     }
//
//     private void sendMqttSetting(String settingType, String terminalId) {
//         Terminal terminal = terminalManageService.findById(terminalId);
//         String id = terminal.getTerminalId();
//         if (id != null) {
//             terminalControlService.settingTerminal(id, settingType);
//         }
//     }
//
//     @RequestMapping(value = "/moveCategory", method = RequestMethod.POST)
//     @ResponseBody
//     public Object moveCategory(@RequestBody JSONObject json) {
//         try {
//             String ids = json.getString("ids");
//             String catId = json.getString("catId");
//             terminalManageService.batchMoveCategory(ids, catId);
//             return new Message<>(45001, "更新成功", null);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error(e.getMessage(), e);
//             return new Message<>(45000, "更新失败", e.getMessage());
//         }
//     }
//
//
// }
