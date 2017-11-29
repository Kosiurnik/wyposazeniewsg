package wsg.projekt.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateInit {
	private static EntityManagerFactory entityManagerFactory;
	
	public HibernateInit() {
		HibernateInit.entityManagerFactory = Persistence.createEntityManagerFactory("wyposazeniewsgDB");
		//this.entityManager = entityManagerFactory.createEntityManager();
	}

	/*public void contextDestroyed(ServletContextEvent servletContextEvent) {
		APILoggingAspect.debugMessage("Wyłączam TaterWEB API");
		closeManager();
	}*/
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static void closeManager() {
		if (entityManagerFactory.isOpen())
			entityManagerFactory.close();
	}
}
