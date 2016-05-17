
/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.mock;

import java.util.LinkedList;
import java.util.List;

import org.marks.bean.jpa.RolesEntity;
import org.marks.mock.tool.MockValues;

public class RolesEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public RolesEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public RolesEntity createInstance( Integer id ) {
		RolesEntity entity = new RolesEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setRoleName( mockValues.nextString(2147483647) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfTeachers( TODO ) ; // List<Teachers> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<RolesEntity> createList(int count) {
		List<RolesEntity> list = new LinkedList<RolesEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
