package org.marks.test;

import org.marks.bean.jpa.SpecialitiesEntity;

public class SpecialitiesEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SpecialitiesEntity newSpecialitiesEntity() {

		Integer id = mockValues.nextInteger();

		SpecialitiesEntity specialitiesEntity = new SpecialitiesEntity();
		specialitiesEntity.setId(id);
		return specialitiesEntity;
	}
	
}
