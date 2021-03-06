/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.test.persistence;


import org.marks.bean.jpa.StudentsDisciplineSemestrEntity;
import org.marks.mock.StudentsDisciplineSemestrEntityMock;
import org.marks.persistence.PersistenceServiceProvider;
import org.marks.persistence.services.StudentsDisciplineSemestrPersistence;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for StudentsDisciplineSemestr persistence service
 * 
 * @author Telosys Tools Generator
 *
 */
public class StudentsDisciplineSemestrPersistenceTest 
{
	@Test
	public void test1() {
		
		System.out.println("Test count ..." );
		
		StudentsDisciplineSemestrPersistence service = PersistenceServiceProvider.getService(StudentsDisciplineSemestrPersistence.class);
		System.out.println("CountAll = " + service.countAll() );
	}
	
	@Test
	public void test2() {
		
		System.out.println("Test StudentsDisciplineSemestr persistence : delete + load ..." );
		
		StudentsDisciplineSemestrPersistence service = PersistenceServiceProvider.getService(StudentsDisciplineSemestrPersistence.class);
		
		StudentsDisciplineSemestrEntityMock mock = new StudentsDisciplineSemestrEntityMock();
		
		// TODO : set primary key values here 
		process( service, mock, 0  );
		// process( service, mock, ... );
	}

	private void process(StudentsDisciplineSemestrPersistence service, StudentsDisciplineSemestrEntityMock mock, Integer id ) {
		System.out.println("----- "  );
		System.out.println(" . load : " );
		StudentsDisciplineSemestrEntity entity = service.load( id );
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
			// . DisciplineParams
			// . Students
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
