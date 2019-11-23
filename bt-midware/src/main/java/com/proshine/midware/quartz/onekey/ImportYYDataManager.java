// package com.proshine.midware.quartz.onekey;
//
// import com.alibaba.fastjson.JSONArray;
// import com.alibaba.fastjson.JSONObject;
// import com.proshine.base.common.utils.DateUtil;
// import com.proshine.base.common.utils.HttpClientUtil;
// import com.proshine.campus.classes.mapper.TbClassMapper;
// import com.proshine.campus.classroom.entity.TbClassroom;
// import com.proshine.campus.classroom.mapper.TbClassroomMapper;
// import com.proshine.campus.course.entity.TbCourse;
// import com.proshine.campus.course.entity.TbCoursePlanning;
// import com.proshine.campus.course.mapper.TbCourseMapper;
// import com.proshine.campus.course.mapper.TbTbCoursePlanningMapper;
// import com.proshine.campus.discipline.entity.TbDisciplineInfo;
// import com.proshine.campus.discipline.mapper.TbDisciplineInfoMapper;
// import com.proshine.campus.grade.entity.TbGrade;
// import com.proshine.campus.grade.mapper.TbGradeMapper;
// import com.proshine.campus.school.entity.TbSchool;
// import com.proshine.campus.school.entity.TbSchoolCompus;
// import com.proshine.campus.school.entity.TbSchoolStage;
// import com.proshine.campus.school.mapper.TbSchoolCompusMapper;
// import com.proshine.campus.school.mapper.TbSchoolMapper;
// import com.proshine.campus.school.mapper.TbSchoolStageMapper;
// import com.proshine.campus.student.entity.TbStudentPlanning;
// import com.proshine.campus.student.mapper.TbStudentInfoMapper;
// import com.proshine.campus.student.mapper.TbStudentPlanningMapper;
// import com.proshine.campus.teacher.mapper.TbTeacherMapper;
// import lombok.extern.log4j.Log4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.util.CollectionUtils;
//
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// /**
//  * Description 华人启星 永定八中/首师大
//  * Date 2019-05-16 17:16
//  */
// @Service
// @Log4j
// public class ImportYYDataManager {
//
//     /**
//      * 客户域id
//      */
//     private static final String CSTM_ID = "demo";
//
//     /**
//      * 学校名称
//      */
//     private static final String SCHOOL_NAME = "北京八中永定实验学校（育园中学）";
//     /**
//      * 查询学校信息
//      */
//     private static final String GetSchools = "/api/services/app/electronicClassBrand/GetSchools";
//     /**
//      * 查询学期信息
//      */
//     private static final String GetSemesters = "/api/services/app/electronicClassBrand/GetSemesters";
//     /**
//      * 查询教室课表信息
//      */
//     private static final String GetClassroomSchedule = "/api/services/app/electronicClassBrand/GetClassroomSchedule";
//     /**
//      * 查询学生信息
//      */
//     private static final String GetStudents = "/api/services/app/electronicClassBrand/GetStudents";
//     /**
//      * 查询老师信息
//      */
//     private static final String GetTeachers = "/api/services/app/electronicClassBrand/GetTeachers";
//     /**
//      * 全局学校对象
//      */
//     private TbSchool globalSchool = null;
//     /**
//      * 默认校区
//      */
//     private TbSchoolCompus globalSchoolCompus = null;
//     /**
//      * 默认学段
//      */
//     private TbSchoolStage globalSchoolStage = null;
//     /**
//      * 默认教室
//      */
//     private TbClassroom globalClassroom = null;
//     /**
//      * 默认班级
//      */
//     private Class globalClass = null;
//     /**
//      * 默认届
//      */
//     private TbGrade globalGrade = null;
//     /**
//      * 默认课程
//      */
//     private TbCourse globalCourse = null;
//     /**
//      * 默认科目
//      */
//     private TbDisciplineInfo globalDisciplineInfo = null;
//     /**
//      * 学生更新标致
//      */
//     private Integer studentFlag = null;
//     /**
//      * 老师更新标致
//      */
//     private Integer teacherFlag = null;
//     @Autowired
//     private TbPartnerInfoMapper partnerInfoMapper;
//
//     @Autowired
//     private TbSchoolMapper schoolMapper;
//
//     @Autowired
//     private TbSchoolCompusMapper schoolCompusMapper;
//
//     @Autowired
//     private TbSchoolStageMapper schoolStageMapper;
//
//     @Autowired
//     private TbClassroomMapper classRoomMapper;
//
//     @Autowired
//     private TbGradeMapper gradeMapper;
//
//     @Autowired
//     private TbClassMapper classMapper;
//
//     @Autowired
//     private TbDisciplineInfoMapper disciplineInfoMapper;
//
//     @Autowired
//     private TbCourseMapper courseMapper;
//
//     @Autowired
//     private TbTeacherMapper teacherMapper;
//
//     @Autowired
//     private TbStudentInfoMapper studentMapper;
//
//     @Autowired
//     private TbTbCoursePlanningMapper coursePlanningMapper;
//
//     @Autowired
//     private TbStudentPlanningMapper studentPlanningMapper;
//
//
//     public void oneKeyImport() throws Exception {
//
//         Map<String, Object> pMap = new HashMap<>();
//         pMap.put("CSTM_ID", CSTM_ID);
//         List<PartnerInfo> list = partnerInfoMapper.list(pMap);
//         if (list.size() != 1) {
//             throw new RuntimeException("合作商信息不正确!");
//         }
//         PartnerInfo partnerInfo = list.get(0);
//         //String partnerUrl = partnerInfo.getPartnerUrl();
//         String partnerUrl = "http://pkb.chinese-star.cn";
//
//         //插入学校 校区信息 学段信息
//         importSchool(partnerUrl);
//
//         //导入其他信息
//         importOtherMsg(partnerUrl);
//     }
//
//     private void importOtherMsg(String partnerUrl) throws Exception {
//         if (globalSchool == null) {
//             Map<String, Object> schoolMap = new HashMap<>();
//             schoolMap.put("SCHOOL_NAME", SCHOOL_NAME);
//             List<TbSchool> schools = schoolMapper.list(schoolMap);
//             globalSchool = schools.get(0);
//             //查询校区
//             Map<String, Object> compusMap = new HashMap<>();
//             compusMap.put("CSTM_ID", CSTM_ID);
//             List<TbSchoolCompus> compuses = schoolCompusMapper.list(compusMap);
//             globalSchoolCompus = compuses.get(0);
//             //查询学段
//             Map<String, Object> states = new HashMap<>();
//             states.put("CSTM_ID", CSTM_ID);
//             List<TbSchoolStage> stages = schoolStageMapper.list(states);
//             globalSchoolStage = stages.get(0);
//         }
//         //导入其它信息
//         Map<String, String> params = new HashMap<>();
//         params.put("input.name", SCHOOL_NAME);
//         JSONArray jsonArray = importMethod(partnerUrl + GetClassroomSchedule, params);
//         for (int i = 0; i < jsonArray.size(); i++) {
//             JSONObject json = jsonArray.getJSONObject(i);
//             String classRoomId = json.getString("classRoomId"); //第三方教室id
//             String classRoomName = json.getString("classRoomName"); //第三方教室名称
//             // 导入教室信息
//             // 导入默认教室
//             if (globalClassroom == null) {
//                 Map<String, Object> roomMap = new HashMap<>();
//                 roomMap.put("roomName", "默认教室");
//                 List<TbClassroom> list = classRoomMapper.list(roomMap);
//                 if (CollectionUtils.isEmpty(list)) {
//                     TbClassroom classRoom = new TbClassroom();
//                     classRoom.setCstmId(CSTM_ID);
//                     classRoom.setRoomName("默认教室");
//                     classRoom.setRoomCompusId(globalSchoolCompus.getId());
//                     classRoom.setLastupdatetime(new Date(System.currentTimeMillis()));
//                     classRoom.setCreatetime(new Date(System.currentTimeMillis()));
//                     classRoomMapper.insert(classRoom);
//                     globalClassroom = classRoom;
//                 } else {
//                     globalClassroom = list.get(0);
//                 }
//             }
//             Map<String, Object> cMap = new HashMap<>();
//             cMap.put("roomId", classRoomId);
//             List<TbClassroom> roomList = classRoomMapper.list(cMap);
//             TbClassroom classRoom = null;
//             if (CollectionUtils.isEmpty(roomList)) {
//                 TbClassroom room = new TbClassroom();
//                 room.setRoomId(classRoomId);
//                 room.setCstmId(CSTM_ID);
//                 room.setRoomName(classRoomName);
//                 room.setRoomCompusId(globalSchoolCompus.getId());
//                 room.setLastupdatetime(new Date(System.currentTimeMillis()));
//                 room.setCreatetime(new Date(System.currentTimeMillis()));
//                 classRoomMapper.insert(room);
//                 classRoom = room;
//             } else {
//                 TbClassroom room = roomList.get(0);
//                 String roomName = room.getRoomName();
//                 if (!roomName.equals(classRoomName)) {
//                     room.setRoomName(classRoomName);
//                     room.setLastupdatetime(new Date(System.currentTimeMillis()));
//                     classRoomMapper.update(room);
//                 }
//                 classRoom = room;
//             }
//             JSONArray courseTableArr = json.getJSONArray("courseTable");//课程实体类
//             for (int j = 0; j < courseTableArr.size(); j++) {
//                 JSONObject courseTable = courseTableArr.getJSONObject(j);
//                 //String courseArrangementId = courseTable.getString("courseArrangementId"); //课程第三方id
//                 Integer weekCode = courseTable.getInteger("weekCode");//星期编码
//                 String weekName = courseTable.getString("weekName");//星期几名称
//                 Long sectionCode = courseTable.getLong("sectionCode");//节次编码
//                 String sectionName = courseTable.getString("sectionName");//节次名称
//                 String tenantId = courseTable.getString("tenantId");//学校id
//                 String classroomId = courseTable.getString("classroomId");//教室id
//                 String classroomName = courseTable.getString("classroomName");//教室名称
//                 String teacherId = courseTable.getString("teacherId");//老师id
//                 String teacherName = courseTable.getString("teacherName");//老师名称
//                 String objectId = courseTable.getString("objectId");//课程id
//                 String objectName = courseTable.getString("objectName");//课程名称
//                 String gradeId = courseTable.getString("gradeId");//年级id (走班为默认值)
//                 String gradeName = courseTable.getString("gradeName");//年级名称 (走班为默认值)
//                 String semesterId = courseTable.getString("semesterId");//学期id
//                 String semesterName = courseTable.getString("semesterName");//学期名称
//                 String classId = courseTable.getString("classId");//班级id (走班为默认值)
//                 String className = courseTable.getString("className");//班级名称 (走班为默认值)
//                 //courseTable.getString("courseSettingId");
//                 //courseTable.getString("courseTimeEdtionId");
//                 //courseTable.getString("onlyId");
//                 //courseTable.getString("weeklys");
//                 // 导入届信息
//                 if (globalGrade == null) {
//                     Map<String, Object> gMap = new HashMap<>();
//                     gMap.put("gradeName", "默认届");
//                     List<TbGrade> grades = gradeMapper.list(gMap);
//                     if (CollectionUtils.isEmpty(grades)) {
//                         TbGrade grade = new TbGrade();
//                         grade.setCstmId(CSTM_ID);
//                         grade.setLocked(1);
//                         grade.setGradeName("默认届");
//                         grade.setStartTime(new Date(System.currentTimeMillis()));
//                         grade.setGraduateTime(new Date(System.currentTimeMillis()));
//                         grade.setCompusId(globalSchoolCompus.getId());
//                         gradeMapper.insert(grade);
//                         globalGrade = grade;
//                     } else {
//                         globalGrade = grades.get(0);
//                     }
//                 }
//                 if (gradeName != null && !gradeName.trim().equals("")) {
//                     Map<String, Object> gMap1 = new HashMap<>();
//                     gMap1.put("extend2", gradeId);
//                     List<TbGrade> list = gradeMapper.list(gMap1);
//                     if (CollectionUtils.isEmpty(list)) {
//                         TbGrade grade = new TbGrade();
//                         grade.setCstmId(CSTM_ID);
//                         grade.setLocked(1);
//                         grade.setGradeName(gradeName);
//                         grade.setStartTime(new Date(System.currentTimeMillis()));
//                         grade.setGraduateTime(new Date(System.currentTimeMillis()));
//                         grade.setCompusId(globalSchoolCompus.getId());
//                         grade.setExtend2(gradeId);
//                         gradeMapper.insert(grade);
//                     } else {
//                         TbGrade grade = list.get(0);
//                         String gradeName1 = grade.getGradeName();
//                         if (!gradeName1.equals(gradeName)) {
//                             grade.setGradeName(gradeName);
//                             gradeMapper.update(grade);
//                         }
//                     }
//                 }
//                 // 导入班级信息
//                 if (globalClass == null) {
//                     Map<String, Object> classMap = new HashMap<>();
//                     classMap.put("className", "默认班级");
//                     List<Class> classes = classMapper.list(classMap);
//                     if (CollectionUtils.isEmpty(classes)) {
//                         Class aClass = new Class();
//                         aClass.setCstmId(CSTM_ID);
//                         aClass.setSchoolStageId(globalSchoolStage.getId());
//                         aClass.setClassroomId(globalClassroom.getId());
//                         aClass.setSchoolCompusId(globalSchoolCompus.getId());
//                         aClass.setClassGradeId(globalGrade.getId());
//                         aClass.setClassName("默认班级");
//                         aClass.setClassType(0L);
//                         aClass.setClassId(Identities.uuid2());
//                         aClass.setLastUpdateTime(new Date(System.currentTimeMillis()));
//                         aClass.setCreateTime(new Date(System.currentTimeMillis()));
//                         classMapper.insert(aClass);
//                         globalClass = aClass;
//                     } else {
//                         globalClass = classes.get(0);
//                     }
//                 }
//                 Class class1 = null;
//                 if (className != null && !className.trim().equals("")) {
//                     Map<String, Object> classMap = new HashMap<>();
//                     classMap.put("classId", classId);
//                     List<Class> list = classMapper.list(classMap);
//                     if (CollectionUtils.isEmpty(list)) {
//                         Class aClass = new Class();
//                         aClass.setCstmId(CSTM_ID);
//                         aClass.setClassId(classId);
//                         aClass.setSchoolStageId(globalSchoolStage.getId());
//                         aClass.setClassroomId(globalClassroom.getId());
//                         aClass.setSchoolCompusId(globalSchoolCompus.getId());
//                         aClass.setClassGradeId(globalGrade.getId());
//                         aClass.setClassName(classRoomName);
//                         aClass.setClassType(0L);
//                         aClass.setLastUpdateTime(new Date(System.currentTimeMillis()));
//                         aClass.setCreateTime(new Date(System.currentTimeMillis()));
//                         classMapper.insert(aClass);
//                         class1 = aClass;
//                     } else {
//                         Class aClass = list.get(0);
//                         String className1 = aClass.getClassName();
//                         if (!className1.equals(className)) {
//                             aClass.setClassName(className);
//                             aClass.setLastUpdateTime(new Date(System.currentTimeMillis()));
//                             classMapper.update(aClass);
//                             class1 = aClass;
//                         }
//                     }
//                 }
//                 // 导入科目信息
//                 if (globalDisciplineInfo == null) {
//                     Map<String, Object> gMap = new HashMap<>();
//                     gMap.put("disciplineName", "默认科目");
//                     List<TbDisciplineInfo> list = disciplineInfoMapper.list(gMap);
//                     if (CollectionUtils.isEmpty(list)) {
//                         TbDisciplineInfo disciplineInfo = new TbDisciplineInfo();
//                         disciplineInfo.setCstmId(CSTM_ID);
//                         disciplineInfo.setSchoolId(globalSchool.getId());
//                         disciplineInfo.setDisciplineName("默认科目");
//                         disciplineInfo.setCourseType(1L);
//                         disciplineInfoMapper.insert(disciplineInfo);
//                         globalDisciplineInfo = disciplineInfo;
//                     } else {
//                         globalDisciplineInfo = list.get(0);
//                     }
//                 }
//                 String[] disciplineNames = objectName.split(" ");
//                 String disciplineName = disciplineNames[0];
//                 Map<String, Object> dMap = new HashMap<>();
//                 dMap.put("disciplineName", disciplineName);
//                 List<TbDisciplineInfo> dList = disciplineInfoMapper.list(dMap);
//                 TbDisciplineInfo disciplineInfo = null;
//                 if (CollectionUtils.isEmpty(dList)) {
//                     TbDisciplineInfo disciplineInfo1 = new TbDisciplineInfo();
//                     disciplineInfo1.setCstmId(CSTM_ID);
//                     disciplineInfo1.setSchoolId(globalSchool.getId());
//                     disciplineInfo1.setDisciplineName(disciplineName);
//                     disciplineInfo1.setCourseType(1L);
//                     disciplineInfoMapper.insert(disciplineInfo1);
//                     disciplineInfo = disciplineInfo1;
//                 } else {
//                     disciplineInfo = dList.get(0);
//                 }
//                 // 导入课程信息
//                 if (globalCourse == null) {
//                     Map<String, Object> courseMap = new HashMap<>();
//                     courseMap.put("courseName", "默认课程");
//                     List<TbCourse> list = courseMapper.list(courseMap);
//                     if (CollectionUtils.isEmpty(list)) {
//                         TbCourse course = new TbCourse();
//                         course.setCstmId(CSTM_ID);
//                         course.setSchoolId(globalSchool.getId());
//                         course.setDisciplineId(globalDisciplineInfo.getId());
//                         course.setCourseName("默认课程");
//                         courseMapper.insert(course);
//                         globalCourse = course;
//                     } else {
//                         globalCourse = list.get(0);
//                     }
//                 }
//                 Map<String, Object> courseMap = new HashMap<>();
//                 courseMap.put("courseName", disciplineName);
//                 courseMap.put("extend1", teacherName);
//                 courseMap.put("courseId", teacherId);
//                 List<TbCourse> list = courseMapper.list(courseMap);
//                 TbCourse course = null;
//                 if (CollectionUtils.isEmpty(list)) {
//                     TbCourse course1 = new TbCourse();
//                     course1.setCstmId(CSTM_ID);
//                     course1.setSchoolId(globalSchool.getId());
//                     course1.setDisciplineId(disciplineInfo.getId());
//                     course1.setCourseId(teacherId);
//                     course1.setCourseName(disciplineName);
//                     course1.setExtend1(teacherName);
//                     courseMapper.insert(course1);
//                     course = course1;
//                 } else {
//                     course = list.get(0);
//                 }
//                 // 导入教师信息
// //                if(teacherFlag == null){
// //                    JSONArray jsonArray1 = importMethod(partnerUrl + GetTeachers, params);
// //                    for (int k = 0; k < jsonArray1.size(); k++) {
// //                        JSONObject teacherJson = jsonArray1.getJSONObject(k);
// //                        String tid = teacherJson.getString("id");
// //                        String tName = teacherJson.getString("name");
// //                        String workNumber = teacherJson.getString("workNumber");
// //                        Map<String, Object> tMap = new HashMap<>();
// //                        tMap.put("teacherName",tName);
// //                        tMap.put("teacherId",tid);
// //                        tMap.put("extend2", workNumber);
// //                        List<Teacher> list1 = teacherMapper.list(tMap);
// //                        if(CollectionUtils.isEmpty(list1)){
// //                            Teacher teacher = new Teacher();
// //                            teacher.setCstmId(CSTM_ID);
// //                            teacher.setTeacherSchoolStageId(globalSchoolStage.getSchoolId());
// //                            teacher.setTeacherSchoolCompusId(globalSchoolCompus.getId());
// //                            teacher.setTeacherSchoolStageId(globalSchoolStage.getSchoolId());
// //                            Map<String, Object> courseMap1 = new HashMap<>();
// //
// //                            teacher.setTeacherCourseId();
// //                            teacher.setTeacherId(tid);
// //                            teacher.setTeacherName(tName);
// //                            teacher.setTeacherSex(2L);
// //                            teacherMapper.insert(teacher);
// //                        }
// //                    }
// //                    teacherFlag = 1;
// //                }
//                 Map<String, Object> tMap = new HashMap<>();
//                 tMap.put("teacherName", teacherName);
//                 tMap.put("teacherId", teacherId);
//                 List<Teacher> list1 = teacherMapper.list(tMap);
//                 Teacher teacher = null;
//                 if (CollectionUtils.isEmpty(list1)) {
//                     Teacher teacher1 = new Teacher();
//                     teacher1.setCstmId(CSTM_ID);
//                     teacher1.setTeacherSchoolStageId(globalSchoolStage.getId());
//                     teacher1.setTeacherSchoolCompusId(globalSchoolCompus.getId());
//                     teacher1.setTeacherCourseId(course.getId());
//                     teacher1.setTeacherId(teacherId);
//                     teacher1.setTeacherName(teacherName);
//                     teacher1.setTeacherSex(2L);
//                     teacherMapper.insert(teacher1);
//                     teacher = teacher1;
//                 } else {
//                     teacher = list1.get(0);
//                 }
//                 // 导入学生信息
//                 if (studentFlag == null) {
//                     JSONArray jsonArray1 = importMethod(partnerUrl + GetStudents, params);
//                     for (int k = 0; k < jsonArray1.size(); k++) {
//                         JSONObject student = jsonArray1.getJSONObject(k);
//                         String sid = student.getString("id");
//                         String sname = student.getString("name");
//                         String studentId = student.getString("studentId");
//                         Map<String, Object> sMap = new HashMap<>();
//                         sMap.put("stuId", sid);
//                         sMap.put("stuName", sname);
//                         sMap.put("extend1", studentId);
//                         List<Student> sList = studentMapper.list(sMap);
//                         if (CollectionUtils.isEmpty(sList)) {
//                             Student student1 = new Student();
//                             student1.setCstmId(CSTM_ID);
//                             student1.setStuId(sid);
//                             student1.setStuName(sname);
//                             student1.setStuSex(2L);
//                             student1.setStuClassId(globalClass.getId());
//                             student1.setStuState(0L);
//                             student1.setExtend1(studentId);
//                             studentMapper.insert(student1);
//                         }
//                     }
//                     studentFlag = 1;
//                 }
//                 // 导入课程计划
//                 Date thisWeekDay = DateUtil.getThisWeekDay(weekCode - 1);
//                 String dateFormat = DateUtil.converToString(thisWeekDay, DateUtil.SHORT_DATE_FORMAT);
//                 String[] names = sectionName.split(" ");
//                 String[] split = names[1].split("-");
//                 String startTime = split[0] + ":00";
//                 String endTime = split[1] + ":00";
//                 if (split[0].length() == 4) {
//                     startTime = "0" + startTime;
//                 }
//                 if (split[1].length() == 4) {
//                     endTime = "0" + endTime;
//                 }
//                 Date start = DateUtil.converToDate(dateFormat + " " + startTime, DateUtil.LONG_DATE_FORMAT);
//                 Date end = DateUtil.converToDate(dateFormat + " " + endTime, DateUtil.LONG_DATE_FORMAT);
//                 Map<String, Object> cpMap = new HashMap<>();
// //                cpMap.put("extend2",courseArrangementId);
// //                List<TbCoursePlanning> courseList = coursePlanningMapper.list(cMap);
// //                TbCoursePlanning coursePlanning1 = null;
//                 //if (CollectionUtils.isEmpty(courseList)) {
//                 TbCoursePlanning coursePlanning = new TbCoursePlanning();
//                 coursePlanning.setCstmId(CSTM_ID);
//                 coursePlanning.setCourseId(course.getId());
//                 coursePlanning.setCourseName(course.getCourseName());
//                 coursePlanning.setCourseType(1L);
//                 coursePlanning.setCourseSection(sectionCode);
//                 coursePlanning.setTeacherId(teacher.getId());
//                 coursePlanning.setTeacherName(teacher.getTeacherName());
//                 coursePlanning.setClassroomId(classRoom.getId());
//                 coursePlanning.setClassroomName(classRoom.getRoomName());
//                 coursePlanning.setCourseStartTime(start);
//                 coursePlanning.setCourseEndTime(end);
//                 coursePlanning.setCourseDate(DateUtil.converToDate(dateFormat, DateUtil.SHORT_DATE_FORMAT));
//                 coursePlanning.setCreateTime(new Date(System.currentTimeMillis()));
//                 coursePlanning.setLastUpdateTime(new Date(System.currentTimeMillis()));
//                 coursePlanning.setWeekDay(weekCode);
//                 coursePlanningMapper.insert(coursePlanning);
//                 //coursePlanning1 = coursePlanning;
// //                }else{
// //                    TbCoursePlanning coursePlanning = courseList.get(0);
// //                    coursePlanning.setCourseId(course.getId());
// //                    coursePlanning.setCourseName(course.getCourseName());
// //                    coursePlanning.setCourseType(1L);
// //                    coursePlanning.setCourseSection(sectionCode);
// //                    coursePlanning.setTeacherId(teacher.getId());
// //                    coursePlanning.setTeacherName(teacher.getTeacherName());
// //                    coursePlanning.setClassroomId(classRoom.getId());
// //                    coursePlanning.setClassroomName(classRoom.getRoomName());
// //                    coursePlanning.setCourseStartTime(start);
// //                    coursePlanning.setCourseEndTime(end);
// //                    coursePlanning.setCourseDate(DateUtil.converToDate(dateFormat, DateUtil.SHORT_DATE_FORMAT));
// //                    coursePlanning.setLastUpdateTime(new Date(System.currentTimeMillis()));
// //                    coursePlanning.setWeekDay(weekCode);
// //                    coursePlanningMapper.update(coursePlanning);
// //                    coursePlanning1 = coursePlanning;
// //                }
//
//                 //导入学生计划
//                 JSONArray students = courseTable.getJSONArray("students");
//                 for (int k = 0; k < students.size(); k++) {
//                     JSONObject student = students.getJSONObject(k);
//                     String id = student.getString("id");  //学生第三方id
//                     String name = student.getString("name");  //学生姓名
//                     String studentId = student.getString("studentId"); //学号
//                     Map<String, Object> map = new HashMap<>();
//                     map.put("stuId", id);
//                     map.put("stuName", name);
//                     List<TbStudent> studentList = studentMapper.list(map);
//                     TbStudent student1 = null;
//                     if (CollectionUtils.isEmpty(studentList)) {
//                         Student student2 = new Student();
//                         student2.setCstmId(CSTM_ID);
//                         student2.setStuId(id);
//                         student2.setStuName(name);
//                         student2.setStuSex(2L);
//                         student2.setStuClassId(globalClass.getId());
//                         student2.setStuState(0L);
//                         student2.setExtend1(studentId);
//                         studentMapper.insert(student2);
//                         student1 = student2;
//                     } else {
//                         student1 = studentList.get(0);
//                     }
//                     TbStudentPlanning studentPlanning = new TbStudentPlanning();
//                     studentPlanning.setCstmId(CSTM_ID);
//                     studentPlanning.setStudentId(student1.getId());
//                     studentPlanning.setStudentName(student1.getStuName());
//                     studentPlanning.setClassroomId(classRoom.getId());
//                     studentPlanning.setClassroomName(classRoom.getRoomName());
//                     studentPlanning.setCourseId(course.getId());
//                     studentPlanning.setCourseName(course.getCourseName());
//                     studentPlanning.setCourseSection(coursePlanning.getCourseSection());
//                     studentPlanning.setCourseDate(coursePlanning.getCourseDate());
//                     studentPlanning.setCourseStartTime(coursePlanning.getCourseStartTime());
//                     studentPlanning.setCourseEndTime(coursePlanning.getCourseEndTime());
//                     studentPlanning.setTbCoursePlanningId(coursePlanning.getId());
//                     studentPlanning.setTeacherId(coursePlanning.getTeacherId());
//                     studentPlanning.setTeacherName(coursePlanning.getTeacherName());
//                     studentPlanning.setWeekDay(coursePlanning.getWeekDay());
//                     studentPlanning.setAccordanceState(0L);
//                     studentPlanning.setCreateDate(new Date(System.currentTimeMillis()));
//                     studentPlanning.setLastUpdateDate(new Date(System.currentTimeMillis()));
//                     if (class1 != null) {
//                         studentPlanning.setExtend1(class1.getClassName());
//                     } else {
//                         studentPlanning.setExtend1(globalClass.getClassName());
//                     }
//                     studentPlanning.setStuId(student1.getStuId());
//                     studentPlanningMapper.insert(studentPlanning);
//                 }
//             }
//         }
//
//     }
//
//     private void importSchool(String partnerUrl) {
//         String url = partnerUrl + GetSchools;
//         JSONArray jsonArr = importMethod(url, null);
//         for (int i = 0; i < jsonArr.size(); i++) {
//             JSONObject json = jsonArr.getJSONObject(i);
//             String name = json.getString("name");
//             if (name.equals(SCHOOL_NAME)) {
//                 Map<String, Object> schoolMap = new HashMap<>();
//                 schoolMap.put("schoolname", SCHOOL_NAME);
//                 List<TbSchool> schools = schoolMapper.list(schoolMap);
//                 if (!CollectionUtils.isEmpty(schools)) {
//                     return;
//                 }
//                 //创建学校
//                 TbSchool school = new TbSchool();
//                 school.setCstmId(CSTM_ID);
//                 school.setCreateTime(new Date(System.currentTimeMillis()));
//                 school.setSchoolName(SCHOOL_NAME);
//                 school.setExtend2(json.getString("id"));
//                 schoolMapper.insert(school);
//                 globalSchool = school;
//                 //创建校区
//                 TbSchoolCompus schoolCompus = new TbSchoolCompus();
//                 schoolCompus.setCompusName("默认校区");
//                 schoolCompus.setCstmId(CSTM_ID);
//                 schoolCompus.setSchoolId(school.getId());
//                 schoolCompusMapper.insert(schoolCompus);
//                 globalSchoolCompus = schoolCompus;
//                 //创建学段
//                 TbSchoolStage schoolStage = new TbSchoolStage();
//                 schoolStage.setCstmId(CSTM_ID);
//                 schoolStage.setStageName("默认学段");
//                 schoolStage.setSchoolId(globalSchool.getId());
//                 schoolStageMapper.insert(schoolStage);
//                 globalSchoolStage = schoolStage;
//             }
//         }
//         System.out.println("导入学校和校区,学段完成!");
//     }
//
//     /**
//      * 公共调用远程接口方法
//      */
//     private JSONArray importMethod(String url, Map<String, String> params) {
//         String data = HttpClientUtil.doGet(url, params, "utf-8", true);
//         if (data == null || data.trim().equals("")) {
//             throw new RuntimeException("第三方应用未获取到信息!");
//         }
//         JSONObject json = JSONObject.parseObject(data);
//         if ("true".equals(json.getString("success"))) {
//             JSONArray jsonArr = json.getJSONArray("result");
//             return jsonArr;
//         } else {
//             log.error("获取信息失败!");
//             throw new RuntimeException("获取信息失败!");
//         }
//     }
// }
