/*
 * CustomBaseDomEditor.java
 *
 * Project: TECHWATCH - TESTING TECHNOLOGIES
 *
 * Copyright 2008 by ELCA Informatique SA
 * Av. de la Harpe 22-24, 1000 Lausanne 13
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ELCA Informatique SA. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with ELCA.
 */

package ch.elca.training.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import ch.elca.training.dom.BaseDom;

public class CustomBaseDomEditor<T extends BaseDom> extends
		PropertyEditorSupport {
	private List<T> entities = new ArrayList<T>();

	public CustomBaseDomEditor(List<T> entities) {
		this.entities = entities;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getAsText() {
		T value = (T) getValue();
		return (value != null ? "" + value.getId() : "");
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long id = Long.parseLong(text);
		for (T entity : entities) {
			if (entity.getId() == id) {
				setValue(entity);
				return;
			}
		}

		setValue(null);
	}
}
