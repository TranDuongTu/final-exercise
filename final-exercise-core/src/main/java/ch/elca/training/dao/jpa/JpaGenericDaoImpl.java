package ch.elca.training.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.elca.training.dao.GenericDao;
import ch.elca.training.dom.BaseDom;

public class JpaGenericDaoImpl<T extends BaseDom> implements GenericDao<T> {
	
	/**
	 * Generic DAO need type information to do generic queries.
	 */
	private Class<T> type;
	
	public Class<T> getType() {
		return type;
	}
	
	public void setType(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * Core {@link EntityManager} to deal with persistence objects.
	 */
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Type information must be set in constructor.
	 */
	public JpaGenericDaoImpl(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int count() {
		return (Integer) entityManager.createQuery(
					"SELECT count(o) FROM " + type.getName() + " o")
				.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return entityManager.createQuery(
					"SELECT o FROM " + type.getName() + " o")
				.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public T get(long id) {
		return entityManager.find(type, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(T object) {
		entityManager.persist(object);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void delete(T object) {
		entityManager.remove(object);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteAll() {
		entityManager.createQuery(
				"DELETE FROM " + type.getName())
			.executeUpdate();
	}
}
