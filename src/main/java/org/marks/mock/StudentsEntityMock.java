
/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.mock;

import java.util.LinkedList;
import java.util.List;

import org.marks.bean.jpa.StudentsEntity;
import org.marks.mock.tool.MockValues;

public class StudentsEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public StudentsEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public StudentsEntity createInstance( Integer id ) {
		StudentsEntity entity = new StudentsEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setFullName( mockValues.nextString(2147483647) ) ; // java.lang.String 
		entity.setRecordBookNumber( mockValues.nextInteger() ) ; // java.lang.Integer 
		entity.setStartYear( mockValues.nextDate() ) ; // java.util.Date 
		entity.setEndYear( mockValues.nextDate() ) ; // java.util.Date 
		// Init Link fields (if any)
		// setGroups( TODO ) ; // Groups 
		// setListOfStudentsDisciplineSemestr( TODO ) ; // List<StudentsDisciplineSemestr> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<StudentsEntity> createList(int count) {
		List<StudentsEntity> list = new LinkedList<StudentsEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}