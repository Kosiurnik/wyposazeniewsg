package wsg.projekt.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*Tworzy globalny EntityManagerFactory (i tak mam tylko jedną definicję persistence), wymagana dla Hibernate*/
public class HibernateInit {
	private static EntityManagerFactory entityManagerFactory;
	
	public HibernateInit() {
		HibernateInit.entityManagerFactory = Persistence.createEntityManagerFactory("wyposazeniewsgDB");
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static void closeManager() {
		if (entityManagerFactory.isOpen())
			entityManagerFactory.close();
	}
}
