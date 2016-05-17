package org.marks.test;

import org.marks.bean.Specialities;

public class SpecialitiesFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Specialities newSpecialities() {

		Integer id = mockValues.nextInteger();

		Specialities specialities = new Specialities();
		specialities.setId(id);
		return specialities;
	}
	
}
