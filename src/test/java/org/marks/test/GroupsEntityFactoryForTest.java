package org.marks.test;

import org.marks.bean.jpa.GroupsEntity;

public class GroupsEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public GroupsEntity newGroupsEntity() {

		Integer id = mockValues.nextInteger();

		GroupsEntity groupsEntity = new GroupsEntity();
		groupsEntity.setId(id);
		return groupsEntity;
	}
	
}
