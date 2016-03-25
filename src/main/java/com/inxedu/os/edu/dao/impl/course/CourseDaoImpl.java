package com.inxedu.os.edu.dao.impl.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.course.CourseDao;
import com.inxedu.os.edu.entity.course.Course;
import com.inxedu.os.edu.entity.course.CourseDto;
import com.inxedu.os.edu.entity.course.QueryCourse;

/**
 * @author www.inxedu.com
 *
 */
@Repository("courseDao")
public class CourseDaoImpl extends GenericDaoImpl implements CourseDao {

	
	public int addCourse(Course course) {
		this.insert("CourseMapper.createCourse", course);
		return course.getCourseId();
	}

	
	public List<CourseDto> queryCourseListPage(QueryCourse query, PageEntity page) {
		return this.queryForListPage("CourseMapper.queryCourseListPage", query, page);
	}

	
	public Course queryCourseById(int courseId) {
		return this.selectOne("CourseMapper.queryCourseById", courseId);
	}

	
	public void updateCourse(Course course) {
		this.update("CourseMapper.updateCourse", course);
	}

	
	public void updateAvaliableCourse(Map<String, Object> map) {
		this.update("CourseMapper.updateAvaliableCourse", map);
	}

	
	public List<CourseDto> queryRecommenCourseList() {
		return this.selectList("CourseMapper.queryRecommenCourseList", null);
	}

	
	public List<CourseDto> queryCourseList(QueryCourse query) {
		return this.selectList("CourseMapper.queryCourseList", query);
	}

	
	public List<CourseDto> queryWebCourseListPage(QueryCourse queryCourse,PageEntity page) {
		return this.queryForListPage("CourseMapper.queryWebCourseListPage", queryCourse, page);
	}

	
	public List<CourseDto> queryInterfixCourseList(Map<String, Object> map) {
		return this.selectList("CourseMapper.queryInterfixCourseList", map);
	}

	
	public List<CourseDto> queryMyCourseList(int userId, PageEntity page) {
		return this.queryForListPage("CourseMapper.queryMyCourseList", userId, page);
	}

	
	public int queryAllCourseCount() {
		return this.selectOne("CourseMapper.queryAllCourseCount", null);
	}

	
	public List<Map<String, Object>> queryMyCourseListByMap(Map<String, Object> map) {
		return this.selectList("CourseMapper.queryMyCourseListByMap", map);
	}


	public List<CourseDto> queryRecommenCourseListByRecommendId(Long recommendId, Long count) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("recommendId", recommendId);
		map.put("count", count);
		return selectList("CourseMapper.queryRecommenCourseListByRecommendId", map);
	}


	public List<CourseDto> queryCourse(QueryCourse queryCourse) {
		return selectList("CourseMapper.queryCourse", queryCourse);
	}
	

}
