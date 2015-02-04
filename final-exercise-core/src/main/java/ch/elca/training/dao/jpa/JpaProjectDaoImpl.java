package ch.elca.training.dao.jpa;

import ch.elca.training.dao.ProjectDao;
import ch.elca.training.dom.Project;

public class JpaProjectDaoImpl extends JpaGenericDaoImpl<Project> implements ProjectDao {

	public JpaProjectDaoImpl() {
		super(Project.class);
	}
}
