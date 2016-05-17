package org.marks.test;

import org.marks.bean.jpa.DisciplineParamsEntity;

public class DisciplineParamsEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DisciplineParamsEntity newDisciplineParamsEntity() {

		Integer id = mockValues.nextInteger();

		DisciplineParamsEntity disciplineParamsEntity = new DisciplineParamsEntity();
		disciplineParamsEntity.setId(id);
		return disciplineParamsEntity;
	}
	
}
