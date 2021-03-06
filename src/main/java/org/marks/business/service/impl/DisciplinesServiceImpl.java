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

import org.marks.bean.Disciplines;
import org.marks.bean.jpa.DisciplinesEntity;
import java.util.List;
import org.marks.business.service.DisciplinesService;
import org.marks.business.service.mapping.DisciplinesServiceMapper;
import org.marks.persistence.PersistenceServiceProvider;
import org.marks.persistence.services.DisciplinesPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of DisciplinesService
 */
@Component
public class DisciplinesServiceImpl implements DisciplinesService {

	private DisciplinesPersistence disciplinesPersistence;

	@Resource
	private DisciplinesServiceMapper disciplinesServiceMapper;
	
	public DisciplinesServiceImpl() {
		disciplinesPersistence = PersistenceServiceProvider.getService(DisciplinesPersistence.class);
	}
		
	@Override
	public Disciplines findById(Integer id) {
		DisciplinesEntity entity = disciplinesPersistence.load(id);
		return disciplinesServiceMapper.mapDisciplinesEntityToDisciplines(entity);
	}

	@Override
	public List<Disciplines> findAll() {
		List<DisciplinesEntity> entities = disciplinesPersistence.loadAll();
		List<Disciplines> beans = new ArrayList<Disciplines>();
		for(DisciplinesEntity entity : entities) {
			beans.add(disciplinesServiceMapper.mapDisciplinesEntityToDisciplines(entity));
		}
		return beans;
	}

	@Override
	public Disciplines save(Disciplines disciplines) {
		return update(disciplines) ;
	}

	@Override
	public Disciplines create(Disciplines disciplines) {
		if(disciplinesPersistence.load(disciplines.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		DisciplinesEntity disciplinesEntity = new DisciplinesEntity();
		disciplinesServiceMapper.mapDisciplinesToDisciplinesEntity(disciplines, disciplinesEntity);
		DisciplinesEntity disciplinesEntitySaved = disciplinesPersistence.save(disciplinesEntity);
		return disciplinesServiceMapper.mapDisciplinesEntityToDisciplines(disciplinesEntitySaved);
	}

	@Override
	public Disciplines update(Disciplines disciplines) {
		DisciplinesEntity disciplinesEntity = disciplinesPersistence.load(disciplines.getId());
		disciplinesServiceMapper.mapDisciplinesToDisciplinesEntity(disciplines, disciplinesEntity);
		DisciplinesEntity disciplinesEntitySaved = disciplinesPersistence.save(disciplinesEntity);
		return disciplinesServiceMapper.mapDisciplinesEntityToDisciplines(disciplinesEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		disciplinesPersistence.delete(id);
	}

	public DisciplinesPersistence getDisciplinesPersistence() {
		return disciplinesPersistence;
	}

	public void setDisciplinesPersistence(DisciplinesPersistence disciplinesPersistence) {
		this.disciplinesPersistence = disciplinesPersistence;
	}

	public DisciplinesServiceMapper getDisciplinesServiceMapper() {
		return disciplinesServiceMapper;
	}

	public void setDisciplinesServiceMapper(DisciplinesServiceMapper disciplinesServiceMapper) {
		this.disciplinesServiceMapper = disciplinesServiceMapper;
	}

}
