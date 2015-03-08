package ch.elca.training.dom;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

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
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Version
	 */
	public static final String COLUMN_VERSION = "OBJ_VERSION";
	
	@Version
	@Column(name = COLUMN_VERSION)
	protected long version;
	
	public long getVersion() {
		return version;
	}
	
	public void setVersion(long version) {
		this.version = version;
	}
}
