package ch.elca.training.utils;

import java.util.Comparator;

import ch.elca.training.dom.Project;

/**
 * Compare between Projects in Test cases.
 * 
 * @author DTR
 */
public class ProjectComparator implements Comparator<Project> {

	public int compare(Project p1, Project p2) {
		if (p1.getNumber() < p2.getNumber()) {
			return -1;
		} else if (p1.getNumber() == p2.getNumber()) {
			return 0;
		} else {
			return 1;
		}
	}
}
