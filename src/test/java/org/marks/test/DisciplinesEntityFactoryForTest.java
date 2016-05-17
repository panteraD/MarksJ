package org.marks.test;

import org.marks.bean.jpa.DisciplinesEntity;

public class DisciplinesEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DisciplinesEntity newDisciplinesEntity() {

		Integer id = mockValues.nextInteger();

		DisciplinesEntity disciplinesEntity = new DisciplinesEntity();
		disciplinesEntity.setId(id);
		return disciplinesEntity;
	}
	
}
