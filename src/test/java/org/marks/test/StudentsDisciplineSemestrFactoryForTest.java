package org.marks.test;

import org.marks.bean.StudentsDisciplineSemestr;

public class StudentsDisciplineSemestrFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public StudentsDisciplineSemestr newStudentsDisciplineSemestr() {

		Integer id = mockValues.nextInteger();

		StudentsDisciplineSemestr studentsDisciplineSemestr = new StudentsDisciplineSemestr();
		studentsDisciplineSemestr.setId(id);
		return studentsDisciplineSemestr;
	}
	
}
