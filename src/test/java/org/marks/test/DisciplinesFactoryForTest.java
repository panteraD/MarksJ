package org.marks.test;

import org.marks.bean.Disciplines;

public class DisciplinesFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Disciplines newDisciplines() {

		Integer id = mockValues.nextInteger();

		Disciplines disciplines = new Disciplines();
		disciplines.setId(id);
		return disciplines;
	}
	
}
