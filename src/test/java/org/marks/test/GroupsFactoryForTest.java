package org.marks.test;

import org.marks.bean.Groups;

public class GroupsFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Groups newGroups() {

		Integer id = mockValues.nextInteger();

		Groups groups = new Groups();
		groups.setId(id);
		return groups;
	}
	
}
