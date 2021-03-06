/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */

package org.marks.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.marks.bean.jpa.StudentsEntity;
import org.marks.persistence.commons.jpa.GenericJpaService;
import org.marks.persistence.commons.jpa.JpaOperation;
import org.marks.persistence.services.StudentsPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Students" )
 * 
 * @author Telosys Tools Generator
 *
 */
public class StudentsPersistenceJPA extends GenericJpaService<StudentsEntity, Integer> implements StudentsPersistence {

	/**
	 * Constructor
	 */
	public StudentsPersistenceJPA() {
		super(StudentsEntity.class);
	}

	@Override
	public StudentsEntity load( Integer id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Integer id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(StudentsEntity entity) {
		if ( entity != null ) {
			return super.delete( entity.getId() );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("StudentsEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
