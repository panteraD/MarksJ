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

import org.marks.bean.Groups;
import org.marks.bean.jpa.GroupsEntity;
import java.util.List;
import org.marks.business.service.mapping.GroupsServiceMapper;
import org.marks.persistence.services.jpa.GroupsPersistenceJPA;
import org.marks.test.GroupsFactoryForTest;
import org.marks.test.GroupsEntityFactoryForTest;
import org.marks.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of GroupsService
 */
@RunWith(MockitoJUnitRunner.class)
public class GroupsServiceImplTest {

	@InjectMocks
	private GroupsServiceImpl groupsService;
	@Mock
	private GroupsPersistenceJPA groupsPersistenceJPA;
	@Mock
	private GroupsServiceMapper groupsServiceMapper;
	
	private GroupsFactoryForTest groupsFactoryForTest = new GroupsFactoryForTest();

	private GroupsEntityFactoryForTest groupsEntityFactoryForTest = new GroupsEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		GroupsEntity groupsEntity = groupsPersistenceJPA.load(id);
		
		Groups groups = groupsFactoryForTest.newGroups();
		when(groupsServiceMapper.mapGroupsEntityToGroups(groupsEntity)).thenReturn(groups);

		// When
		Groups groupsFound = groupsService.findById(id);

		// Then
		assertEquals(groups.getId(),groupsFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<GroupsEntity> groupsEntitys = new ArrayList<GroupsEntity>();
		GroupsEntity groupsEntity1 = groupsEntityFactoryForTest.newGroupsEntity();
		groupsEntitys.add(groupsEntity1);
		GroupsEntity groupsEntity2 = groupsEntityFactoryForTest.newGroupsEntity();
		groupsEntitys.add(groupsEntity2);
		when(groupsPersistenceJPA.loadAll()).thenReturn(groupsEntitys);
		
		Groups groups1 = groupsFactoryForTest.newGroups();
		when(groupsServiceMapper.mapGroupsEntityToGroups(groupsEntity1)).thenReturn(groups1);
		Groups groups2 = groupsFactoryForTest.newGroups();
		when(groupsServiceMapper.mapGroupsEntityToGroups(groupsEntity2)).thenReturn(groups2);

		// When
		List<Groups> groupssFounds = groupsService.findAll();

		// Then
		assertTrue(groups1 == groupssFounds.get(0));
		assertTrue(groups2 == groupssFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Groups groups = groupsFactoryForTest.newGroups();

		GroupsEntity groupsEntity = groupsEntityFactoryForTest.newGroupsEntity();
		when(groupsPersistenceJPA.load(groups.getId())).thenReturn(null);
		
		groupsEntity = new GroupsEntity();
		groupsServiceMapper.mapGroupsToGroupsEntity(groups, groupsEntity);
		GroupsEntity groupsEntitySaved = groupsPersistenceJPA.save(groupsEntity);
		
		Groups groupsSaved = groupsFactoryForTest.newGroups();
		when(groupsServiceMapper.mapGroupsEntityToGroups(groupsEntitySaved)).thenReturn(groupsSaved);

		// When
		Groups groupsResult = groupsService.create(groups);

		// Then
		assertTrue(groupsResult == groupsSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Groups groups = groupsFactoryForTest.newGroups();

		GroupsEntity groupsEntity = groupsEntityFactoryForTest.newGroupsEntity();
		when(groupsPersistenceJPA.load(groups.getId())).thenReturn(groupsEntity);

		// When
		Exception exception = null;
		try {
			groupsService.create(groups);
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
		Groups groups = groupsFactoryForTest.newGroups();

		GroupsEntity groupsEntity = groupsEntityFactoryForTest.newGroupsEntity();
		when(groupsPersistenceJPA.load(groups.getId())).thenReturn(groupsEntity);
		
		GroupsEntity groupsEntitySaved = groupsEntityFactoryForTest.newGroupsEntity();
		when(groupsPersistenceJPA.save(groupsEntity)).thenReturn(groupsEntitySaved);
		
		Groups groupsSaved = groupsFactoryForTest.newGroups();
		when(groupsServiceMapper.mapGroupsEntityToGroups(groupsEntitySaved)).thenReturn(groupsSaved);

		// When
		Groups groupsResult = groupsService.update(groups);

		// Then
		verify(groupsServiceMapper).mapGroupsToGroupsEntity(groups, groupsEntity);
		assertTrue(groupsResult == groupsSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		groupsService.delete(id);

		// Then
		verify(groupsPersistenceJPA).delete(id);
		
	}

}
