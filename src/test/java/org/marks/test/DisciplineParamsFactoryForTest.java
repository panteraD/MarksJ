package org.marks.test;

import org.marks.bean.DisciplineParams;

public class DisciplineParamsFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DisciplineParams newDisciplineParams() {

		Integer id = mockValues.nextInteger();

		DisciplineParams disciplineParams = new DisciplineParams();
		disciplineParams.setId(id);
		return disciplineParams;
	}
	
}
