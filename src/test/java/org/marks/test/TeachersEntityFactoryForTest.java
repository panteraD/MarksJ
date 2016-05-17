package org.marks.test;

import org.marks.bean.jpa.TeachersEntity;

public class TeachersEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TeachersEntity newTeachersEntity() {

		Integer id = mockValues.nextInteger();

		TeachersEntity teachersEntity = new TeachersEntity();
		teachersEntity.setId(id);
		return teachersEntity;
	}
	
}
