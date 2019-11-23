package com.proshine.midware.quartz.course.data.content;

import com.proshine.base.common.utils.DateUtil;
import com.proshine.midware.quartz.course.data.pojo.VBksKb;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.proshine.midware.quartz.course.PlanningUtil.getNextCourseEndTime;
import static com.proshine.midware.quartz.course.PlanningUtil.getNextCourseStartTime;

/**
 * 从数据源拉数据
 * ClassName: FetchData
 * Author: zyl
 * Date: 2019/9/12 14:30
 */
// TODO: 2019/10/18 zylTodo Oracle驱动依赖要随这个类移动 还要考虑其他功能用到Oracle驱动 需要放到一起
@Log4j2
public class FetchData<T> {

    /**
     * 数据源驱动
     */
    private static final String FROM_DRIVER = "oracle.jdbc.driver.OracleDriver";
    /**
     * 数据源连接字符串
     */
    private static final String FROM_URL = "jdbc:oracle:thin:@//202.204.193.137:1521/orcl";
    /**
     * 数据源用户名
     */
    private static final String FROM_USERNAME = "USR_DZBP";
    /**
     * 数据源密码
     */
    private static final String FROM_PASSWORD = "cup@BP2019";

    /**
     * 目标库驱动
     */
    private static final String TO_DRIVER = "com.mysql.jdbc.Driver";
    /**
     * 目标库连接字符串
     */
    // TODO: 2019/10/18 zylTodo 这里还是暂时先改成本地测试 要想办法把它改成配置
    private static final String TO_URL = "jdbc:mysql://202.204.193.81:12100/bunny_v3_3_3?useSSL=false";
    /**
     * 目标库用户名
     */
    private static final String TO_USERNAME = "bunny";
    /**
     * 目标库密码
     */
    private static final String TO_PASSWORD = "bunny";
    /**
     * 2019年开学第一周的周一
     */
    private static final Date BEGIN_DATE = DateUtil.getDateObject("2019-08-31", 0);
    /**
     * 年月日格式
     */
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 2019年开学第一周是本年的第几周
     */
    private final int beginWeekNo = DateUtil.getWeeksNoInYear(BEGIN_DATE);
    /**
     * 数据源连接
     */
    private Connection fromConn = null;
    /**
     * 目标库连接
     */
    private Connection toConn = null;

    /**
     * 获得数据源连接对象
     *
     * @return 连接对象
     */
    private synchronized Connection getOracleConn() {
        if (fromConn == null) {
            try {
                Class.forName(FROM_DRIVER);
                fromConn = DriverManager.getConnection(FROM_URL, FROM_USERNAME, FROM_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return fromConn;
    }

    /**
     * 获得目标MySQL连接对象
     *
     * @return 连接对象
     */
    private synchronized Connection getMysqlConn() {
        if (toConn == null) {
            try {
                Class.forName(TO_DRIVER);
                toConn = DriverManager.getConnection(TO_URL, TO_USERNAME, TO_PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return toConn;
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            getOracleConn().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空tb_course_planning表
     */
    public void deleteAll() {
        String sql = "delete from tb_course_planning where 1=1";
        try (PreparedStatement pstmt = getMysqlConn().prepareStatement(sql)) {
            pstmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从数据源中拉取全部数据
     *
     * @param sql select *
     * @param t   Class<T>
     * @return 所有课程
     */
    public List<T> queryAll(String sql, Class<T> t) {
        try (PreparedStatement pstmt = getOracleConn().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            return rsToListT(t, rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前课表相对周次
     *
     * @return 相对周次
     */
    public int getCurrentWeekNo() {
        return DateUtil.getWeeksNoInYear(DateUtil.getThisWeekDay(1)) - beginWeekNo;
    }

    /**
     * 返回指定泛型的实体集合
     *
     * @param t  指定泛型Class
     * @param rs 查询结果集
     * @return 指定泛型的实体集合
     * @throws Exception IllegalAccessException, InstantiationException, NoSuchFieldException, SQLException 等
     */
    private List<T> rsToListT(Class<T> t, ResultSet rs) throws Exception {

        List<T> list = new ArrayList<>();
        // int count = 0;
        T obj;

        Field[] fs = t.getDeclaredFields();
        if (rs != null) {
            // TODO: 2019/9/13 zylTodo 限制10条 方便调试
            // while (rs.next() && count < 10) {
            while (rs.next()) {
                obj = t.newInstance();
                for (Field field : fs) {
                    Field f = obj.getClass().getDeclaredField(field.getName());
                    f.setAccessible(true);
                    if (f.getType().getName().equals(String.class.getName())) {
                        f.set(obj, rs.getString(field.getName()));
                    } else if (f.getType().getName().equals(int.class.getName())) {
                        f.set(obj, rs.getInt(field.getName()));
                    }
                }
                list.add(obj);
                // count++;
            }
        }
        return list;
    }

    /**
     * 用反射把上下课时间转换varchar为Date类型
     * 第几周 + 周几 + 上课时间 = 上课具体年月日时分秒
     *
     * @param weekList 本周上课集合
     * @return 存有正确时间格式的课程集合
     * @throws IllegalAccessException IllegalAccessException
     */
    public List<T> transformDate(List<T> weekList) throws IllegalAccessException {
        // 本科生
        if (weekList.get(0) instanceof VBksKb) {
            for (T t : weekList) {
                // 周几
                int week = 0;
                // 原生字符串格式时分秒上时间
                String startTime = "";
                // 原生字符串格式时分秒下时间
                String endTime = "";
                for (Field field : t.getClass().getDeclaredFields()) {
                    switch (field.getName()) {
                        case "course_start_time":
                            field.setAccessible(true);
                            startTime = field.get(t).toString();
                            break;
                        case "course_end_time":
                            field.setAccessible(true);
                            endTime = field.get(t).toString();
                            break;
                        case "courseStartTime": {
                            field.setAccessible(true);
                            // offset好像是从0起算的
                            String yyyymmdd = format.format(DateUtil.getThisWeekDay(week - 1));
                            field.set(t, DateUtil.converToDate(yyyymmdd + " " + startTime, "yyyy-MM-dd hh:mm:ss"));
                            break;
                        }
                        case "courseEndTime": {
                            field.setAccessible(true);
                            // offset好像是从0起算的
                            String yyyymmdd = format.format(DateUtil.getThisWeekDay(week - 1));
                            field.set(t, DateUtil.converToDate(yyyymmdd + " " + endTime, "yyyy-MM-dd hh:mm:ss"));
                            break;
                        }
                        case "xq":
                            field.setAccessible(true);
                            // DateUtil.getThisWeekDay()
                            week = Integer.parseInt(field.get(t).toString());
                            break;
                        default:
                    }
                }
            }
        }
        // 研究生
        else {
            for (T t : weekList) {
                // 周几
                int week = 0;
                // 原生字符串格式时分秒上时间
                String startTime = "";
                // 原生字符串格式时分秒下时间
                String endTime = "";
                for (Field field : t.getClass().getDeclaredFields()) {
                    switch (field.getName()) {
                        case "btime":
                            field.setAccessible(true);
                            startTime = field.get(t).toString();
                            break;
                        case "etime":
                            field.setAccessible(true);
                            endTime = field.get(t).toString();
                            break;
                        case "courseStartTime": {
                            field.setAccessible(true);
                            // offset好像是从0起算的
                            String yyyymmdd = format.format(DateUtil.getThisWeekDay(week - 1));
                            field.set(t, DateUtil.converToDate(yyyymmdd + " " + startTime, "yyyy-MM-dd hh:mm:ss"));
                            break;
                        }
                        case "courseEndTime": {
                            field.setAccessible(true);
                            // offset好像是从0起算的
                            String yyyymmdd = format.format(DateUtil.getThisWeekDay(week - 1));
                            field.set(t, DateUtil.converToDate(yyyymmdd + " " + endTime, "yyyy-MM-dd hh:mm:ss"));
                            break;
                        }
                        case "wekkday":
                            field.setAccessible(true);
                            week = Integer.parseInt(field.get(t).toString());
                            break;
                        case "jc_name":
                            field.setAccessible(true);
                            switch (field.get(t).toString()) {
                                case "11":
                                    field.set(t, "1");
                                    break;
                                case "12":
                                    field.set(t, "2");
                                    break;
                                case "13":
                                    field.set(t, "3");
                                    break;
                                case "14":
                                    field.set(t, "4");
                                    break;
                                case "21":
                                    field.set(t, "5");
                                    break;
                                case "22":
                                    field.set(t, "6");
                                    break;
                                case "23":
                                    field.set(t, "7");
                                    break;
                                case "24":
                                    field.set(t, "8");
                                    break;
                                case "31":
                                    field.set(t, "9");
                                    break;
                                case "32":
                                    field.set(t, "10");
                                    break;
                                case "33":
                                    field.set(t, "11");
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + field.get(t).toString());
                            }
                            break;
                        default:
                    }
                }
            }
        }
        return weekList;
    }

    /**
     * 补充因为连续上省略掉的课程
     *
     * @param weekList 带连续节数的周课程
     * @return 完整的周课程
     */
    public List<T> extendWeekList(List<T> weekList) throws IllegalAccessException, IOException, ClassNotFoundException {
        List<T> resList = new ArrayList<>();
        // 上课节次
        int courseSection = -1;
        // 连续上课节数
        int courseSum = 1;
        // 检查每条记录的连续课程数n 并插入n-1条上课时间段随节次递增的课程记录
        for (T t : weekList) {
            // 插入本体
            resList.add(t);
            // 记录连续上课节数和上课节次
            for (Field field : t.getClass().getDeclaredFields()) {
                switch (field.getName()) {
                    case "course_section":
                        field.setAccessible(true);
                        courseSection = Integer.parseInt(field.get(t).toString());
                        break;
                    case "course_sum":
                        field.setAccessible(true);
                        courseSum = Integer.parseInt(field.get(t).toString());
                        break;
                    default:
                }
            }
            for (int i = 0; i < courseSum - 1; i++) {
                T cloneT = cloneObj(t);
                // 遍历字段 找到连续上课节数字段 根据此字段插入对应个数-1的分身
                for (Field field : cloneT.getClass().getDeclaredFields()) {
                    switch (field.getName()) {
                        case "course_section":
                            field.setAccessible(true);
                            field.set(cloneT, String.valueOf(++courseSection));
                            break;
                        case "course_start_time":
                            field.setAccessible(true);
                            field.set(cloneT, getNextCourseStartTime(courseSection));
                            break;
                        case "course_end_time":
                            field.setAccessible(true);
                            field.set(cloneT, getNextCourseEndTime(courseSection));
                            break;
                        default:
                    }
                }
                resList.add(cloneT);
            }
        }
        return resList;
    }

    /**
     * 通过序列化克隆泛型实体
     *
     * @param t 被克隆的泛型实体
     * @return 克隆后的泛型实体
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    private T cloneObj(T t) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(t);
        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        return (T) inputStream.readObject();
    }

    /**
     * 把数据插入到MySQL
     *
     * @param inList 需要插入的数据集合
     * @throws Exception Exception
     */
    public void addToMysql(List<T> inList) throws Exception {
        int count = 1;
        Connection c = getMysqlConn();
        StringBuilder builder = new StringBuilder(
                "INSERT INTO tb_course_planning (course_id, course_name, course_section,\n" +
                        "teacher_id, teacher_name, class_room_id,\n" +
                        "optional_class_name, course_start_time, course_end_time,\n" +
                        "extend1, week_day)\n" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        for (int i = 0; i < inList.size() - 1; i++)
            builder.append(",(?,?,?,?,?,?,?,?,?,?,?)");

        PreparedStatement ps = c.prepareStatement(builder.toString());
        if (inList.get(0) instanceof VBksKb) {
            for (T t : inList) {
                for (Field field : t.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    switch (field.getName()) {
                        case "course_id":
                        case "course_name":
                        case "teacher_id":
                        case "class_room_id":
                        case "extend1":
                            ps.setString(count++, field.get(t).toString());
                            break;
                        case "course_section":
                        case "xq":
                            ps.setInt(count++, Integer.parseInt(field.get(t).toString()));
                            break;
                        case "teacher_name":
                        case "optional_class_name":
                            ps.setString(count++, field.get(t) == null ? null : field.get(t).toString());
                            break;
                        case "courseStartTime":
                        case "courseEndTime":
                            ps.setTimestamp(count++, new Timestamp(((Date) field.get(t)).getTime()));
                            break;
                        default:
                    }
                }
            }
        } else {
            for (T t : inList) {
                for (Field field : t.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    switch (field.getName()) {
                        case "course_id":
                        case "course_name":
                        case "teacher_id":
                        case "class_room_id":
                        case "extend1":
                            ps.setString(count++, field.get(t).toString());
                            break;
                        case "jc_name":
                        case "wekkday":
                            ps.setInt(count++, Integer.parseInt(field.get(t).toString()));
                            break;
                        case "teacher_name":
                        case "optional_class_name":
                            ps.setString(count++, field.get(t) == null ? null : field.get(t).toString());
                            break;
                        case "courseStartTime":
                        case "courseEndTime":
                            ps.setTimestamp(count++, new Timestamp(((Date) field.get(t)).getTime()));
                            break;
                        default:
                    }
                }
            }
        }
        ps.execute();
    }

}