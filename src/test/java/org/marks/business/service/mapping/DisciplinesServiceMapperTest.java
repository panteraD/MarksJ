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
import org.marks.bean.Disciplines;
import org.marks.bean.jpa.DisciplinesEntity;
import org.marks.bean.jpa.TeachersEntity;
import org.marks.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class DisciplinesServiceMapperTest {

	private DisciplinesServiceMapper disciplinesServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		disciplinesServiceMapper = new DisciplinesServiceMapper();
		disciplinesServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'DisciplinesEntity' to 'Disciplines'
	 * @param disciplinesEntity
	 */
	@Test
	public void testMapDisciplinesEntityToDisciplines() {
		// Given
		DisciplinesEntity disciplinesEntity = new DisciplinesEntity();
		disciplinesEntity.setName(mockValues.nextString(2147483647));
		disciplinesEntity.setStudyPlanCode(mockValues.nextInteger());
		disciplinesEntity.setTeachers(new TeachersEntity());
		disciplinesEntity.getTeachers().setId(mockValues.nextInteger());
		
		// When
		Disciplines disciplines = disciplinesServiceMapper.mapDisciplinesEntityToDisciplines(disciplinesEntity);
		
		// Then
		assertEquals(disciplinesEntity.getName(), disciplines.getName());
		assertEquals(disciplinesEntity.getStudyPlanCode(), disciplines.getStudyPlanCode());
		assertEquals(disciplinesEntity.getTeachers().getId(), disciplines.getTeacherId());
	}
	
	/**
	 * Test : Mapping from 'Disciplines' to 'DisciplinesEntity'
	 */
	@Test
	public void testMapDisciplinesToDisciplinesEntity() {
		// Given
		Disciplines disciplines = new Disciplines();
		disciplines.setName(mockValues.nextString(2147483647));
		disciplines.setStudyPlanCode(mockValues.nextInteger());
		disciplines.setTeacherId(mockValues.nextInteger());

		DisciplinesEntity disciplinesEntity = new DisciplinesEntity();
		
		// When
		disciplinesServiceMapper.mapDisciplinesToDisciplinesEntity(disciplines, disciplinesEntity);
		
		// Then
		assertEquals(disciplines.getName(), disciplinesEntity.getName());
		assertEquals(disciplines.getStudyPlanCode(), disciplinesEntity.getStudyPlanCode());
		assertEquals(disciplines.getTeacherId(), disciplinesEntity.getTeachers().getId());
	}

}