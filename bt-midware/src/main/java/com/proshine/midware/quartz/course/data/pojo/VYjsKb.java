package com.proshine.midware.quartz.course.data.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: VYjsKb
 * Description: 研究生课表 字段顺序不要动
 * Author: zyl
 * Date: 2019/9/13 11:07
 */
@Data
public class VYjsKb implements Serializable {
    private String course_id;
    private String course_name;
    private String jc_name;// 课程节次
    private String teacher_id;
    private String teacher_name;
    private String class_room_id;
    private String optional_class_name;
    private String btime;// 上课时间
    private String etime;// 上课时间
    // 上课时间
    private Date courseStartTime;
    // 下课时间
    private Date courseEndTime;
    private String extend1;// 研究生
    private String week;// 1 - 9
    private String wekkday;// 星期几
}
