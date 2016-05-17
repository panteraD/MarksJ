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

import org.marks.bean.DisciplineParams;
import org.marks.bean.jpa.DisciplineParamsEntity;
import java.util.List;
import org.marks.business.service.DisciplineParamsService;
import org.marks.business.service.mapping.DisciplineParamsServiceMapper;
import org.marks.persistence.PersistenceServiceProvider;
import org.marks.persistence.services.DisciplineParamsPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of DisciplineParamsService
 */
@Component
public class DisciplineParamsServiceImpl implements DisciplineParamsService {

	private DisciplineParamsPersistence disciplineParamsPersistence;

	@Resource
	private DisciplineParamsServiceMapper disciplineParamsServiceMapper;
	
	public DisciplineParamsServiceImpl() {
		disciplineParamsPersistence = PersistenceServiceProvider.getService(DisciplineParamsPersistence.class);
	}
		
	@Override
	public DisciplineParams findById(Integer id) {
		DisciplineParamsEntity entity = disciplineParamsPersistence.load(id);
		return disciplineParamsServiceMapper.mapDisciplineParamsEntityToDisciplineParams(entity);
	}

	@Override
	public List<DisciplineParams> findAll() {
		List<DisciplineParamsEntity> entities = disciplineParamsPersistence.loadAll();
		List<DisciplineParams> beans = new ArrayList<DisciplineParams>();
		for(DisciplineParamsEntity entity : entities) {
			beans.add(disciplineParamsServiceMapper.mapDisciplineParamsEntityToDisciplineParams(entity));
		}
		return beans;
	}

	@Override
	public DisciplineParams save(DisciplineParams disciplineParams) {
		return update(disciplineParams) ;
	}

	@Override
	public DisciplineParams create(DisciplineParams disciplineParams) {
		if(disciplineParamsPersistence.load(disciplineParams.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		DisciplineParamsEntity disciplineParamsEntity = new DisciplineParamsEntity();
		disciplineParamsServiceMapper.mapDisciplineParamsToDisciplineParamsEntity(disciplineParams, disciplineParamsEntity);
		DisciplineParamsEntity disciplineParamsEntitySaved = disciplineParamsPersistence.save(disciplineParamsEntity);
		return disciplineParamsServiceMapper.mapDisciplineParamsEntityToDisciplineParams(disciplineParamsEntitySaved);
	}

	@Override
	public DisciplineParams update(DisciplineParams disciplineParams) {
		DisciplineParamsEntity disciplineParamsEntity = disciplineParamsPersistence.load(disciplineParams.getId());
		disciplineParamsServiceMapper.mapDisciplineParamsToDisciplineParamsEntity(disciplineParams, disciplineParamsEntity);
		DisciplineParamsEntity disciplineParamsEntitySaved = disciplineParamsPersistence.save(disciplineParamsEntity);
		return disciplineParamsServiceMapper.mapDisciplineParamsEntityToDisciplineParams(disciplineParamsEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		disciplineParamsPersistence.delete(id);
	}

	public DisciplineParamsPersistence getDisciplineParamsPersistence() {
		return disciplineParamsPersistence;
	}

	public void setDisciplineParamsPersistence(DisciplineParamsPersistence disciplineParamsPersistence) {
		this.disciplineParamsPersistence = disciplineParamsPersistence;
	}

	public DisciplineParamsServiceMapper getDisciplineParamsServiceMapper() {
		return disciplineParamsServiceMapper;
	}

	public void setDisciplineParamsServiceMapper(DisciplineParamsServiceMapper disciplineParamsServiceMapper) {
		this.disciplineParamsServiceMapper = disciplineParamsServiceMapper;
	}

}