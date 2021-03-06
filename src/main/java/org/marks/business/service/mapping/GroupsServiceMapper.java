/*
 * Created on 17 May 2016 ( Time 04:14:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.marks.bean.Groups;
import org.marks.bean.jpa.GroupsEntity;
import org.marks.bean.jpa.SpecialitiesEntity;
import org.marks.bean.jpa.SpecialitiesEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class GroupsServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public GroupsServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'GroupsEntity' to 'Groups'
	 * @param groupsEntity
	 */
	public Groups mapGroupsEntityToGroups(GroupsEntity groupsEntity) {
		if(groupsEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Groups groups = map(groupsEntity, Groups.class);

		//--- Link mapping ( link to Specialities )
		if(groupsEntity.getSpecialities() != null) {
			groups.setSpecialityId(groupsEntity.getSpecialities().getId());
		}

		return groups;
	}
	
	/**
	 * Mapping from 'Groups' to 'GroupsEntity'
	 * @param groups
	 * @param groupsEntity
	 */
	public void mapGroupsToGroupsEntity(Groups groups, GroupsEntity groupsEntity) {
		if(groups == null) {
			return;
		}

		//--- Generic mapping 
		map(groups, groupsEntity);

		//--- Link mapping ( link : groups )
		if( hasLinkToSpecialities(groups) ) {
			SpecialitiesEntity specialities1 = new SpecialitiesEntity();
			specialities1.setId( groups.getSpecialityId() );
			groupsEntity.setSpecialities( specialities1 );
		} else {
			groupsEntity.setSpecialities( null );
		}



	}
	
	

	/**
	 * Verify that Specialities id is valid.
	 * @param Specialities Specialities
	 * @return boolean
	 */
	private boolean hasLinkToSpecialities(Groups groups) {
		if(groups.getSpecialityId() != null) {
			return true;
		}
		return false;
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