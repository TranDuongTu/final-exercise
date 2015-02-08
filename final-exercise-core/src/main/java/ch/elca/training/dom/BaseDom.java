package ch.elca.training.dom;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Based class for all persistence objects.
 * 
 * @author DTR
 */
@MappedSuperclass
public abstract class BaseDom {
	
	/**
	 * Generated ID.
	 */
	public static final String COLUMN_ID = "id";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
