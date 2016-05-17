/*
 * Created on 17 May 2016 ( Time 04:14:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.marks.bean.Roles;
import org.marks.bean.jpa.RolesEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class RolesServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public RolesServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'RolesEntity' to 'Roles'
	 * @param rolesEntity
	 */
	public Roles mapRolesEntityToRoles(RolesEntity rolesEntity) {
		if(rolesEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Roles roles = map(rolesEntity, Roles.class);

		return roles;
	}
	
	/**
	 * Mapping from 'Roles' to 'RolesEntity'
	 * @param roles
	 * @param rolesEntity
	 */
	public void mapRolesToRolesEntity(Roles roles, RolesEntity rolesEntity) {
		if(roles == null) {
			return;
		}

		//--- Generic mapping 
		map(roles, rolesEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}