/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.test.persistence;


import org.marks.bean.jpa.TeachersEntity;
import org.marks.mock.TeachersEntityMock;
import org.marks.persistence.PersistenceServiceProvider;
import org.marks.persistence.services.TeachersPersistence;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for Teachers persistence service
 * 
 * @author Telosys Tools Generator
 *
 */
public class TeachersPersistenceTest 
{
	@Test
	public void test1() {
		
		System.out.println("Test count ..." );
		
		TeachersPersistence service = PersistenceServiceProvider.getService(TeachersPersistence.class);
		System.out.println("CountAll = " + service.countAll() );
	}
	
	@Test
	public void test2() {
		
		System.out.println("Test Teachers persistence : delete + load ..." );
		
		TeachersPersistence service = PersistenceServiceProvider.getService(TeachersPersistence.class);
		
		TeachersEntityMock mock = new TeachersEntityMock();
		
		// TODO : set primary key values here 
		process( service, mock, 0  );
		// process( service, mock, ... );
	}

	private void process(TeachersPersistence service, TeachersEntityMock mock, Integer id ) {
		System.out.println("----- "  );
		System.out.println(" . load : " );
		TeachersEntity entity = service.load( id );
		if ( entity != null ) {
			// Found 
			System.out.println("   FOUND : " + entity );
			
			// Save (update) with the same values to avoid database integrity errors  
			System.out.println(" . save : " + entity );
			service.save(entity);
			System.out.println("   saved : " + entity );
		}
		else {
			// Not found 
			System.out.println("   NOT FOUND" );
			// Create a new instance 
			entity = mock.createInstance( id ) ;
			Assert.assertNotNull(entity);

			// This entity references the following entities : 
			// . Roles
			/* Insert only if references are OK
			// Try to insert the new instance
			System.out.println(" . insert : " + entity );
			service.insert(entity);
			System.out.println("   inserted : " + entity );
			*/

			System.out.println(" . delete : " );
			boolean deleted = service.delete( id );
			System.out.println("   deleted = " + deleted);
		}		
	}
}
