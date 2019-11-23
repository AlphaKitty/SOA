package com.proshine.midware.quartz.course.data.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: VBksKb
 * Description: 本科生课表 字段顺序不要动
 * Author: zyl
 * Date: 2019/9/12 16:27
 */
@Data
public class VBksKb implements Serializable {

    private String course_id;
    private String course_name;
    private String course_section;// 课程节次
    private String teacher_id;
    private String teacher_name;
    private String class_room_id;
    private String optional_class_name;
    private String course_start_time;// 上课时间
    private String course_end_time;// 下课时间
    // 上课时间
    private Date courseStartTime;
    // 下课时间
    private Date courseEndTime;
    private String extend1;// 本科生
    private String week_day;// 0101001
    private String xq;// 星期几
    private String course_sum;// 连续几节

}