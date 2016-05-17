
/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.mock;

import java.util.LinkedList;
import java.util.List;

import org.marks.bean.jpa.DisciplinesSemestrEntity;
import org.marks.mock.tool.MockValues;

public class DisciplinesSemestrEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public DisciplinesSemestrEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public DisciplinesSemestrEntity createInstance( Integer id ) {
		DisciplinesSemestrEntity entity = new DisciplinesSemestrEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setSemestr( mockValues.nextInteger() ) ; // java.lang.Integer 
		// Init Link fields (if any)
		// setDisciplines( TODO ) ; // Disciplines 
		// setSpecialities( TODO ) ; // Specialities 
		// setListOfDisciplineParams( TODO ) ; // List<DisciplineParams> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<DisciplinesSemestrEntity> createList(int count) {
		List<DisciplinesSemestrEntity> list = new LinkedList<DisciplinesSemestrEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}