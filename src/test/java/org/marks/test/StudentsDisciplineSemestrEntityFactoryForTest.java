package org.marks.test;

import org.marks.bean.jpa.StudentsDisciplineSemestrEntity;

public class StudentsDisciplineSemestrEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public StudentsDisciplineSemestrEntity newStudentsDisciplineSemestrEntity() {

		Integer id = mockValues.nextInteger();

		StudentsDisciplineSemestrEntity studentsDisciplineSemestrEntity = new StudentsDisciplineSemestrEntity();
		studentsDisciplineSemestrEntity.setId(id);
		return studentsDisciplineSemestrEntity;
	}
	
}
