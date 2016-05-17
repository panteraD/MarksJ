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

import org.marks.bean.Roles;
import org.marks.bean.jpa.RolesEntity;
import java.util.List;
import org.marks.business.service.mapping.RolesServiceMapper;
import org.marks.persistence.services.jpa.RolesPersistenceJPA;
import org.marks.test.RolesFactoryForTest;
import org.marks.test.RolesEntityFactoryForTest;
import org.marks.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of RolesService
 */
@RunWith(MockitoJUnitRunner.class)
public class RolesServiceImplTest {

	@InjectMocks
	private RolesServiceImpl rolesService;
	@Mock
	private RolesPersistenceJPA rolesPersistenceJPA;
	@Mock
	private RolesServiceMapper rolesServiceMapper;
	
	private RolesFactoryForTest rolesFactoryForTest = new RolesFactoryForTest();

	private RolesEntityFactoryForTest rolesEntityFactoryForTest = new RolesEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		RolesEntity rolesEntity = rolesPersistenceJPA.load(id);
		
		Roles roles = rolesFactoryForTest.newRoles();
		when(rolesServiceMapper.mapRolesEntityToRoles(rolesEntity)).thenReturn(roles);

		// When
		Roles rolesFound = rolesService.findById(id);

		// Then
		assertEquals(roles.getId(),rolesFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<RolesEntity> rolesEntitys = new ArrayList<RolesEntity>();
		RolesEntity rolesEntity1 = rolesEntityFactoryForTest.newRolesEntity();
		rolesEntitys.add(rolesEntity1);
		RolesEntity rolesEntity2 = rolesEntityFactoryForTest.newRolesEntity();
		rolesEntitys.add(rolesEntity2);
		when(rolesPersistenceJPA.loadAll()).thenReturn(rolesEntitys);
		
		Roles roles1 = rolesFactoryForTest.newRoles();
		when(rolesServiceMapper.mapRolesEntityToRoles(rolesEntity1)).thenReturn(roles1);
		Roles roles2 = rolesFactoryForTest.newRoles();
		when(rolesServiceMapper.mapRolesEntityToRoles(rolesEntity2)).thenReturn(roles2);

		// When
		List<Roles> rolessFounds = rolesService.findAll();

		// Then
		assertTrue(roles1 == rolessFounds.get(0));
		assertTrue(roles2 == rolessFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Roles roles = rolesFactoryForTest.newRoles();

		RolesEntity rolesEntity = rolesEntityFactoryForTest.newRolesEntity();
		when(rolesPersistenceJPA.load(roles.getId())).thenReturn(null);
		
		rolesEntity = new RolesEntity();
		rolesServiceMapper.mapRolesToRolesEntity(roles, rolesEntity);
		RolesEntity rolesEntitySaved = rolesPersistenceJPA.save(rolesEntity);
		
		Roles rolesSaved = rolesFactoryForTest.newRoles();
		when(rolesServiceMapper.mapRolesEntityToRoles(rolesEntitySaved)).thenReturn(rolesSaved);

		// When
		Roles rolesResult = rolesService.create(roles);

		// Then
		assertTrue(rolesResult == rolesSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Roles roles = rolesFactoryForTest.newRoles();

		RolesEntity rolesEntity = rolesEntityFactoryForTest.newRolesEntity();
		when(rolesPersistenceJPA.load(roles.getId())).thenReturn(rolesEntity);

		// When
		Exception exception = null;
		try {
			rolesService.create(roles);
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
		Roles roles = rolesFactoryForTest.newRoles();

		RolesEntity rolesEntity = rolesEntityFactoryForTest.newRolesEntity();
		when(rolesPersistenceJPA.load(roles.getId())).thenReturn(rolesEntity);
		
		RolesEntity rolesEntitySaved = rolesEntityFactoryForTest.newRolesEntity();
		when(rolesPersistenceJPA.save(rolesEntity)).thenReturn(rolesEntitySaved);
		
		Roles rolesSaved = rolesFactoryForTest.newRoles();
		when(rolesServiceMapper.mapRolesEntityToRoles(rolesEntitySaved)).thenReturn(rolesSaved);

		// When
		Roles rolesResult = rolesService.update(roles);

		// Then
		verify(rolesServiceMapper).mapRolesToRolesEntity(roles, rolesEntity);
		assertTrue(rolesResult == rolesSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		rolesService.delete(id);

		// Then
		verify(rolesPersistenceJPA).delete(id);
		
	}

}
