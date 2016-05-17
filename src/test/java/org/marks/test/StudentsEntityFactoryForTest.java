package org.marks.test;

import org.marks.bean.jpa.StudentsEntity;

public class StudentsEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public StudentsEntity newStudentsEntity() {

		Integer id = mockValues.nextInteger();

		StudentsEntity studentsEntity = new StudentsEntity();
		studentsEntity.setId(id);
		return studentsEntity;
	}
	
}
