package ch.elca.training.propertyeditors;

import java.beans.PropertyEditorSupport;

import ch.elca.training.dom.Status;

/**
 * {@link CustomEditor} for {@link Status}.
 * 
 * @author DTR
 */
public class StatusEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		return getValue() == null ? "" : ((Status) getValue()).getDesc();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Status status = null;
		
		if (text.compareTo(Status.FIN.toString()) == 0) {
			status = Status.FIN;
		} else if (text.compareTo(Status.INV.toString()) == 0) {
			status = Status.INV;
		} else if (text.compareTo(Status.TOV.toString()) == 0) {
			status = Status.TOV;
		} else if (text.compareTo(Status.VAL.toString()) == 0) {
			status = Status.VAL;
		}
		
		setValue(status);
	};
}
