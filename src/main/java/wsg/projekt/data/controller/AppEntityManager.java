package wsg.projekt.data.controller;

import javax.persistence.EntityManager;

import wsg.projekt.data.HibernateInit;

public class AppEntityManager implements AutoCloseable {

	private EntityManager entityManager;

	public AppEntityManager() {
		entityManager = HibernateInit.getEntityManagerFactory().createEntityManager();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void close() throws Exception {
		if (!entityManager.isOpen())
			entityManager.close();
		if(!entityManager.equals(null))
			entityManager.clear();
	}
}
