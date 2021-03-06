/*
 * Created on 17 May 2016 ( Time 04:14:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.web.listitem;

import org.marks.bean.Specialities;
import org.marks.web.common.ListItem;

public class SpecialitiesListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public SpecialitiesListItem(Specialities specialities) {
		super();

		this.value = ""
			 + specialities.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = specialities.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
