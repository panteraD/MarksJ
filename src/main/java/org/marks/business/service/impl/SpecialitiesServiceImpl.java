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

import org.marks.bean.Specialities;
import org.marks.bean.jpa.SpecialitiesEntity;
import java.util.List;
import org.marks.business.service.SpecialitiesService;
import org.marks.business.service.mapping.SpecialitiesServiceMapper;
import org.marks.persistence.PersistenceServiceProvider;
import org.marks.persistence.services.SpecialitiesPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of SpecialitiesService
 */
@Component
public class SpecialitiesServiceImpl implements SpecialitiesService {

	private SpecialitiesPersistence specialitiesPersistence;

	@Resource
	private SpecialitiesServiceMapper specialitiesServiceMapper;
	
	public SpecialitiesServiceImpl() {
		specialitiesPersistence = PersistenceServiceProvider.getService(SpecialitiesPersistence.class);
	}
		
	@Override
	public Specialities findById(Integer id) {
		SpecialitiesEntity entity = specialitiesPersistence.load(id);
		return specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(entity);
	}

	@Override
	public List<Specialities> findAll() {
		List<SpecialitiesEntity> entities = specialitiesPersistence.loadAll();
		List<Specialities> beans = new ArrayList<Specialities>();
		for(SpecialitiesEntity entity : entities) {
			beans.add(specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(entity));
		}
		return beans;
	}

	@Override
	public Specialities save(Specialities specialities) {
		return update(specialities) ;
	}

	@Override
	public Specialities create(Specialities specialities) {
		if(specialitiesPersistence.load(specialities.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		SpecialitiesEntity specialitiesEntity = new SpecialitiesEntity();
		specialitiesServiceMapper.mapSpecialitiesToSpecialitiesEntity(specialities, specialitiesEntity);
		SpecialitiesEntity specialitiesEntitySaved = specialitiesPersistence.save(specialitiesEntity);
		return specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(specialitiesEntitySaved);
	}

	@Override
	public Specialities update(Specialities specialities) {
		SpecialitiesEntity specialitiesEntity = specialitiesPersistence.load(specialities.getId());
		specialitiesServiceMapper.mapSpecialitiesToSpecialitiesEntity(specialities, specialitiesEntity);
		SpecialitiesEntity specialitiesEntitySaved = specialitiesPersistence.save(specialitiesEntity);
		return specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(specialitiesEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		specialitiesPersistence.delete(id);
	}

	public SpecialitiesPersistence getSpecialitiesPersistence() {
		return specialitiesPersistence;
	}

	public void setSpecialitiesPersistence(SpecialitiesPersistence specialitiesPersistence) {
		this.specialitiesPersistence = specialitiesPersistence;
	}

	public SpecialitiesServiceMapper getSpecialitiesServiceMapper() {
		return specialitiesServiceMapper;
	}

	public void setSpecialitiesServiceMapper(SpecialitiesServiceMapper specialitiesServiceMapper) {
		this.specialitiesServiceMapper = specialitiesServiceMapper;
	}

}
