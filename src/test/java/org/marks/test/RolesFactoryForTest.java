package org.marks.test;

import org.marks.bean.Roles;

public class RolesFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Roles newRoles() {

		Integer id = mockValues.nextInteger();

		Roles roles = new Roles();
		roles.setId(id);
		return roles;
	}
	
}
