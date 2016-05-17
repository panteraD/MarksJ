package org.marks.test;

import org.marks.bean.jpa.DisciplinesSemestrEntity;

public class DisciplinesSemestrEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DisciplinesSemestrEntity newDisciplinesSemestrEntity() {

		Integer id = mockValues.nextInteger();

		DisciplinesSemestrEntity disciplinesSemestrEntity = new DisciplinesSemestrEntity();
		disciplinesSemestrEntity.setId(id);
		return disciplinesSemestrEntity;
	}
	
}
