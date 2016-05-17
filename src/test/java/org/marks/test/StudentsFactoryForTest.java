package org.marks.test;

import org.marks.bean.Students;

public class StudentsFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Students newStudents() {

		Integer id = mockValues.nextInteger();

		Students students = new Students();
		students.setId(id);
		return students;
	}
	
}
