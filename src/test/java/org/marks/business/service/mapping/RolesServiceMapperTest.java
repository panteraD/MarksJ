/*
 * Created on 17 May 2016 ( Time 04:14:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.marks.bean.Roles;
import org.marks.bean.jpa.RolesEntity;
import org.marks.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class RolesServiceMapperTest {

	private RolesServiceMapper rolesServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		rolesServiceMapper = new RolesServiceMapper();
		rolesServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'RolesEntity' to 'Roles'
	 * @param rolesEntity
	 */
	@Test
	public void testMapRolesEntityToRoles() {
		// Given
		RolesEntity rolesEntity = new RolesEntity();
		rolesEntity.setRoleName(mockValues.nextString(2147483647));
		
		// When
		Roles roles = rolesServiceMapper.mapRolesEntityToRoles(rolesEntity);
		
		// Then
		assertEquals(rolesEntity.getRoleName(), roles.getRoleName());
	}
	
	/**
	 * Test : Mapping from 'Roles' to 'RolesEntity'
	 */
	@Test
	public void testMapRolesToRolesEntity() {
		// Given
		Roles roles = new Roles();
		roles.setRoleName(mockValues.nextString(2147483647));

		RolesEntity rolesEntity = new RolesEntity();
		
		// When
		rolesServiceMapper.mapRolesToRolesEntity(roles, rolesEntity);
		
		// Then
		assertEquals(roles.getRoleName(), rolesEntity.getRoleName());
	}

}