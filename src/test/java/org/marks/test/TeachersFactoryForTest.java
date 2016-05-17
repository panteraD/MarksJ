package org.marks.test;

import org.marks.bean.Teachers;

public class TeachersFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Teachers newTeachers() {

		Integer id = mockValues.nextInteger();

		Teachers teachers = new Teachers();
		teachers.setId(id);
		return teachers;
	}
	
}
