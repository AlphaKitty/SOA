package com.proshine.midware.quartz.course.data;

import com.proshine.base.common.utils.DateUtil;
import com.proshine.midware.quartz.course.data.content.FetchData;
import com.proshine.midware.quartz.course.data.pojo.VBksKb;
import com.proshine.midware.quartz.course.data.pojo.VYjsKb;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: PullDataManagerImpl
 * Description: TODO
 * Author: zyl
 * Date: 2019/9/17 18:41
 */
@Log4j2
@Service
public class PullDataManagerImpl implements PullDataManager {
    @Override
    @SuppressWarnings("unchecked")
    public void pullData() throws Exception {
        String start = DateUtil.getCurrentTime("hh:mm:ss");
        System.out.println("开始时间:" + start);
        FetchData handler = new FetchData();
        // 拿到本周是第几周
        int weekNo = handler.getCurrentWeekNo();
        // 拿到本周有课的本科生数据 不直接取全部数据
        List<VBksKb> bksWeekList = handler.queryAll("select * from USR_GXSJ.V_BKS_KB where substr(WEEK_DAY," + weekNo + ",1) = '1'", VBksKb.class);
        log.fatal("本科生课表拉取成功,一共" + bksWeekList.size() + "条");
        // 拿到本周有课的研究生数据 不直接取全部数据 研究生不存在连续上的课程 所以也不需要克隆
        List<VYjsKb> yjsWeekList = handler.queryAll("select * from USR_GXSJ.V_YJS_KB where " + weekNo + " >= (select substr(WEEK,1,(select instr(WEEK,' ',1,1) from dual)-1) from dual) and " + weekNo + " <= (select substr(WEEK,(select instr(WEEK,' ',1,2) from dual) + 1,2) from dual)", VYjsKb.class);
        log.fatal("研究生课表拉取成功,一共" + yjsWeekList.size() + "条");
        // 拿到根据连续节次克隆的完整本周有课的本科生数据
        List<VBksKb> bksExtWeekList = handler.extendWeekList(bksWeekList);
        log.fatal("本科生课表扩展成功,一共" + bksExtWeekList.size() + "条");
        // 获得转换成了Date类型之后的本科生数据
        List<VBksKb> bksDateWeekList = handler.transformDate(bksExtWeekList);
        log.fatal("本科生课表转换成功,一共" + bksDateWeekList.size() + "条");
        // 获得转换成了Date类型之后的研究生数据
        List<VYjsKb> yjsDateWeekList = handler.transformDate(yjsWeekList);
        log.fatal("研究生课表转换成功,一共" + yjsDateWeekList.size() + "条");
        // 清空本地tb_course_planning数据
        handler.deleteAll();
        // 本科生数据加入到MySQL
        handler.addToMysql(bksDateWeekList);
        log.fatal("本科生课表插入完成");
        // 研究生数据加入到MySQL
        handler.addToMysql(yjsDateWeekList);
        log.fatal("研究生课表插入完成");
        // 关闭连接
        handler.close();
        String end = DateUtil.getCurrentTime("hh:mm:ss");
        System.out.println("结束时间:" + end);
    }
}
