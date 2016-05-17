package org.marks.test;

import org.marks.bean.jpa.RolesEntity;

public class RolesEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RolesEntity newRolesEntity() {

		Integer id = mockValues.nextInteger();

		RolesEntity rolesEntity = new RolesEntity();
		rolesEntity.setId(id);
		return rolesEntity;
	}
	
}
