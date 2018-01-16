package wsg.projekt.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*Tworzy globalny EntityManagerFactory (i tak mam tylko jedną definicję persistence), wymagana dla Hibernate*/
public class HibernateInit {
	
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	
	public HibernateInit() {
		HibernateInit.entityManagerFactory = Persistence.createEntityManagerFactory("wyposazeniewsgDB");
		HibernateInit.entityManager = HibernateInit.entityManagerFactory.createEntityManager();
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static void closeManager() {
		if (entityManagerFactory.isOpen())
			entityManagerFactory.close();
	}
	public static void reopenManager(){
		entityManagerFactory = Persistence.createEntityManagerFactory("wyposazeniewsgDB");
	}
}
