
/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.mock;

import java.util.LinkedList;
import java.util.List;

import org.marks.bean.jpa.StudentsDisciplineSemestrEntity;
import org.marks.mock.tool.MockValues;

public class StudentsDisciplineSemestrEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public StudentsDisciplineSemestrEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public StudentsDisciplineSemestrEntity createInstance( Integer id ) {
		StudentsDisciplineSemestrEntity entity = new StudentsDisciplineSemestrEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setStudentScore( mockValues.nextInteger() ) ; // java.lang.Integer 
		// Init Link fields (if any)
		// setDisciplineParams( TODO ) ; // DisciplineParams 
		// setStudents( TODO ) ; // Students 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<StudentsDisciplineSemestrEntity> createList(int count) {
		List<StudentsDisciplineSemestrEntity> list = new LinkedList<StudentsDisciplineSemestrEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
