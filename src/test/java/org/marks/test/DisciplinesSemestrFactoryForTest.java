package org.marks.test;

import org.marks.bean.DisciplinesSemestr;

public class DisciplinesSemestrFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DisciplinesSemestr newDisciplinesSemestr() {

		Integer id = mockValues.nextInteger();

		DisciplinesSemestr disciplinesSemestr = new DisciplinesSemestr();
		disciplinesSemestr.setId(id);
		return disciplinesSemestr;
	}
	
}
