package com.proshine.midware.quartz.course;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * ClassName: PlanningUtil
 * Description: TODO
 * Author: zyl
 * Date: 2019/8/28 9:34
 */
public class PlanningUtil {

    // 上课时间键值对集合
    private static final HashMap<Integer, String> COURSE_START_TIMES_MAP = new LinkedHashMap<>();
    // 下课时间键值对集合
    private static final HashMap<Integer, String> COURSE_END_TIMES_MAP = new LinkedHashMap<>();

    static {
        // 上课时间键值对初始化
        COURSE_START_TIMES_MAP.put(1, "08:00:00");
        COURSE_START_TIMES_MAP.put(2, "08:55:00");
        COURSE_START_TIMES_MAP.put(3, "10:05:00");
        COURSE_START_TIMES_MAP.put(4, "11:00:00");
        COURSE_START_TIMES_MAP.put(5, "13:30:00");
        COURSE_START_TIMES_MAP.put(6, "14:25:00");
        COURSE_START_TIMES_MAP.put(7, "15:30:00");
        COURSE_START_TIMES_MAP.put(8, "16:25:00");
        COURSE_START_TIMES_MAP.put(9, "18:30:00");
        COURSE_START_TIMES_MAP.put(10, "19:25:00");
        COURSE_START_TIMES_MAP.put(11, "20:20:00");
        // 下课时间键值对初始化
        COURSE_END_TIMES_MAP.put(1, "08:50:00");
        COURSE_END_TIMES_MAP.put(2, "09:45:00");
        COURSE_END_TIMES_MAP.put(3, "10:55:00");
        COURSE_END_TIMES_MAP.put(4, "11:50:00");
        COURSE_END_TIMES_MAP.put(5, "14:20:00");
        COURSE_END_TIMES_MAP.put(6, "15:15:00");
        COURSE_END_TIMES_MAP.put(7, "16:20:00");
        COURSE_END_TIMES_MAP.put(8, "17:15:00");
        COURSE_END_TIMES_MAP.put(9, "19:20:00");
        COURSE_END_TIMES_MAP.put(10, "20:15:00");
        COURSE_END_TIMES_MAP.put(11, "21:10:00");
    }

    /**
     * 获取连续课程前提下下节课上课时间
     *
     * @param i 连续的第几节
     * @return str上课时间
     */
    public static String getNextCourseStartTime(int i) {
        return COURSE_START_TIMES_MAP.get(i);
    }

    /**
     * 获取连续课程前提下下节课下课时间
     *
     * @param i 连续的第几节
     * @return str下课时间
     */
    public static String getNextCourseEndTime(int i) {
        return COURSE_END_TIMES_MAP.get(i);
    }

}
