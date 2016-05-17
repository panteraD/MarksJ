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
import org.marks.bean.StudentsDisciplineSemestr;
import org.marks.bean.jpa.StudentsDisciplineSemestrEntity;
import org.marks.bean.jpa.DisciplineParamsEntity;
import org.marks.bean.jpa.StudentsEntity;
import org.marks.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class StudentsDisciplineSemestrServiceMapperTest {

	private StudentsDisciplineSemestrServiceMapper studentsDisciplineSemestrServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		studentsDisciplineSemestrServiceMapper = new StudentsDisciplineSemestrServiceMapper();
		studentsDisciplineSemestrServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'StudentsDisciplineSemestrEntity' to 'StudentsDisciplineSemestr'
	 * @param studentsDisciplineSemestrEntity
	 */
	@Test
	public void testMapStudentsDisciplineSemestrEntityToStudentsDisciplineSemestr() {
		// Given
		StudentsDisciplineSemestrEntity studentsDisciplineSemestrEntity = new StudentsDisciplineSemestrEntity();
		studentsDisciplineSemestrEntity.setStudentScore(mockValues.nextInteger());
		studentsDisciplineSemestrEntity.setDisciplineParams(new DisciplineParamsEntity());
		studentsDisciplineSemestrEntity.getDisciplineParams().setId(mockValues.nextInteger());
		studentsDisciplineSemestrEntity.setStudents(new StudentsEntity());
		studentsDisciplineSemestrEntity.getStudents().setId(mockValues.nextInteger());
		
		// When
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrServiceMapper.mapStudentsDisciplineSemestrEntityToStudentsDisciplineSemestr(studentsDisciplineSemestrEntity);
		
		// Then
		assertEquals(studentsDisciplineSemestrEntity.getStudentScore(), studentsDisciplineSemestr.getStudentScore());
		assertEquals(studentsDisciplineSemestrEntity.getDisciplineParams().getId(), studentsDisciplineSemestr.getDisciplineParamId());
		assertEquals(studentsDisciplineSemestrEntity.getStudents().getId(), studentsDisciplineSemestr.getStudentId());
	}
	
	/**
	 * Test : Mapping from 'StudentsDisciplineSemestr' to 'StudentsDisciplineSemestrEntity'
	 */
	@Test
	public void testMapStudentsDisciplineSemestrToStudentsDisciplineSemestrEntity() {
		// Given
		StudentsDisciplineSemestr studentsDisciplineSemestr = new StudentsDisciplineSemestr();
		studentsDisciplineSemestr.setStudentScore(mockValues.nextInteger());
		studentsDisciplineSemestr.setDisciplineParamId(mockValues.nextInteger());
		studentsDisciplineSemestr.setStudentId(mockValues.nextInteger());

		StudentsDisciplineSemestrEntity studentsDisciplineSemestrEntity = new StudentsDisciplineSemestrEntity();
		
		// When
		studentsDisciplineSemestrServiceMapper.mapStudentsDisciplineSemestrToStudentsDisciplineSemestrEntity(studentsDisciplineSemestr, studentsDisciplineSemestrEntity);
		
		// Then
		assertEquals(studentsDisciplineSemestr.getStudentScore(), studentsDisciplineSemestrEntity.getStudentScore());
		assertEquals(studentsDisciplineSemestr.getDisciplineParamId(), studentsDisciplineSemestrEntity.getDisciplineParams().getId());
		assertEquals(studentsDisciplineSemestr.getStudentId(), studentsDisciplineSemestrEntity.getStudents().getId());
	}

}