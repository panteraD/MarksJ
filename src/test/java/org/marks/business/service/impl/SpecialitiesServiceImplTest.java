/*
 * Created on 17 May 2016 ( Time 04:14:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.marks.bean.Specialities;
import org.marks.bean.jpa.SpecialitiesEntity;
import java.util.List;
import org.marks.business.service.mapping.SpecialitiesServiceMapper;
import org.marks.persistence.services.jpa.SpecialitiesPersistenceJPA;
import org.marks.test.SpecialitiesFactoryForTest;
import org.marks.test.SpecialitiesEntityFactoryForTest;
import org.marks.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of SpecialitiesService
 */
@RunWith(MockitoJUnitRunner.class)
public class SpecialitiesServiceImplTest {

	@InjectMocks
	private SpecialitiesServiceImpl specialitiesService;
	@Mock
	private SpecialitiesPersistenceJPA specialitiesPersistenceJPA;
	@Mock
	private SpecialitiesServiceMapper specialitiesServiceMapper;
	
	private SpecialitiesFactoryForTest specialitiesFactoryForTest = new SpecialitiesFactoryForTest();

	private SpecialitiesEntityFactoryForTest specialitiesEntityFactoryForTest = new SpecialitiesEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		SpecialitiesEntity specialitiesEntity = specialitiesPersistenceJPA.load(id);
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		when(specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(specialitiesEntity)).thenReturn(specialities);

		// When
		Specialities specialitiesFound = specialitiesService.findById(id);

		// Then
		assertEquals(specialities.getId(),specialitiesFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<SpecialitiesEntity> specialitiesEntitys = new ArrayList<SpecialitiesEntity>();
		SpecialitiesEntity specialitiesEntity1 = specialitiesEntityFactoryForTest.newSpecialitiesEntity();
		specialitiesEntitys.add(specialitiesEntity1);
		SpecialitiesEntity specialitiesEntity2 = specialitiesEntityFactoryForTest.newSpecialitiesEntity();
		specialitiesEntitys.add(specialitiesEntity2);
		when(specialitiesPersistenceJPA.loadAll()).thenReturn(specialitiesEntitys);
		
		Specialities specialities1 = specialitiesFactoryForTest.newSpecialities();
		when(specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(specialitiesEntity1)).thenReturn(specialities1);
		Specialities specialities2 = specialitiesFactoryForTest.newSpecialities();
		when(specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(specialitiesEntity2)).thenReturn(specialities2);

		// When
		List<Specialities> specialitiessFounds = specialitiesService.findAll();

		// Then
		assertTrue(specialities1 == specialitiessFounds.get(0));
		assertTrue(specialities2 == specialitiessFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();

		SpecialitiesEntity specialitiesEntity = specialitiesEntityFactoryForTest.newSpecialitiesEntity();
		when(specialitiesPersistenceJPA.load(specialities.getId())).thenReturn(null);
		
		specialitiesEntity = new SpecialitiesEntity();
		specialitiesServiceMapper.mapSpecialitiesToSpecialitiesEntity(specialities, specialitiesEntity);
		SpecialitiesEntity specialitiesEntitySaved = specialitiesPersistenceJPA.save(specialitiesEntity);
		
		Specialities specialitiesSaved = specialitiesFactoryForTest.newSpecialities();
		when(specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(specialitiesEntitySaved)).thenReturn(specialitiesSaved);

		// When
		Specialities specialitiesResult = specialitiesService.create(specialities);

		// Then
		assertTrue(specialitiesResult == specialitiesSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();

		SpecialitiesEntity specialitiesEntity = specialitiesEntityFactoryForTest.newSpecialitiesEntity();
		when(specialitiesPersistenceJPA.load(specialities.getId())).thenReturn(specialitiesEntity);

		// When
		Exception exception = null;
		try {
			specialitiesService.create(specialities);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();

		SpecialitiesEntity specialitiesEntity = specialitiesEntityFactoryForTest.newSpecialitiesEntity();
		when(specialitiesPersistenceJPA.load(specialities.getId())).thenReturn(specialitiesEntity);
		
		SpecialitiesEntity specialitiesEntitySaved = specialitiesEntityFactoryForTest.newSpecialitiesEntity();
		when(specialitiesPersistenceJPA.save(specialitiesEntity)).thenReturn(specialitiesEntitySaved);
		
		Specialities specialitiesSaved = specialitiesFactoryForTest.newSpecialities();
		when(specialitiesServiceMapper.mapSpecialitiesEntityToSpecialities(specialitiesEntitySaved)).thenReturn(specialitiesSaved);

		// When
		Specialities specialitiesResult = specialitiesService.update(specialities);

		// Then
		verify(specialitiesServiceMapper).mapSpecialitiesToSpecialitiesEntity(specialities, specialitiesEntity);
		assertTrue(specialitiesResult == specialitiesSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		specialitiesService.delete(id);

		// Then
		verify(specialitiesPersistenceJPA).delete(id);
		
	}

}
