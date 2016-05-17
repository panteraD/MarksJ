/*
 * Created on 17 May 2016 ( Time 04:14:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.marks.bean.Teachers;
import org.marks.bean.jpa.TeachersEntity;
import java.util.List;
import org.marks.business.service.TeachersService;
import org.marks.business.service.mapping.TeachersServiceMapper;
import org.marks.persistence.PersistenceServiceProvider;
import org.marks.persistence.services.TeachersPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of TeachersService
 */
@Component
public class TeachersServiceImpl implements TeachersService {

	private TeachersPersistence teachersPersistence;

	@Resource
	private TeachersServiceMapper teachersServiceMapper;
	
	public TeachersServiceImpl() {
		teachersPersistence = PersistenceServiceProvider.getService(TeachersPersistence.class);
	}
		
	@Override
	public Teachers findById(Integer id) {
		TeachersEntity entity = teachersPersistence.load(id);
		return teachersServiceMapper.mapTeachersEntityToTeachers(entity);
	}

	@Override
	public List<Teachers> findAll() {
		List<TeachersEntity> entities = teachersPersistence.loadAll();
		List<Teachers> beans = new ArrayList<Teachers>();
		for(TeachersEntity entity : entities) {
			beans.add(teachersServiceMapper.mapTeachersEntityToTeachers(entity));
		}
		return beans;
	}

	@Override
	public Teachers save(Teachers teachers) {
		return update(teachers) ;
	}

	@Override
	public Teachers create(Teachers teachers) {
		if(teachersPersistence.load(teachers.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		TeachersEntity teachersEntity = new TeachersEntity();
		teachersServiceMapper.mapTeachersToTeachersEntity(teachers, teachersEntity);
		TeachersEntity teachersEntitySaved = teachersPersistence.save(teachersEntity);
		return teachersServiceMapper.mapTeachersEntityToTeachers(teachersEntitySaved);
	}

	@Override
	public Teachers update(Teachers teachers) {
		TeachersEntity teachersEntity = teachersPersistence.load(teachers.getId());
		teachersServiceMapper.mapTeachersToTeachersEntity(teachers, teachersEntity);
		TeachersEntity teachersEntitySaved = teachersPersistence.save(teachersEntity);
		return teachersServiceMapper.mapTeachersEntityToTeachers(teachersEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		teachersPersistence.delete(id);
	}

	public TeachersPersistence getTeachersPersistence() {
		return teachersPersistence;
	}

	public void setTeachersPersistence(TeachersPersistence teachersPersistence) {
		this.teachersPersistence = teachersPersistence;
	}

	public TeachersServiceMapper getTeachersServiceMapper() {
		return teachersServiceMapper;
	}

	public void setTeachersServiceMapper(TeachersServiceMapper teachersServiceMapper) {
		this.teachersServiceMapper = teachersServiceMapper;
	}

}