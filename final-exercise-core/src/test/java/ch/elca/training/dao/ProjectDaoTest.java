package ch.elca.training.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test {@link ProjectDao}
 * 
 * @author DTR
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:database/dummy-beans.xml"})
public class ProjectDaoTest {
	
	@Test
	public void afterInsertShouldReturnSameProject() {
		
	}
}
