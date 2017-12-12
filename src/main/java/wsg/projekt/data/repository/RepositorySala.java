package wsg.projekt.data.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntitySala;
import wsg.projekt.data.repository.interfaces.IRepositorySala;

@Repository
public class RepositorySala implements IRepositorySala {

	@Override
	public EntitySala getSalaByID(int salaID) {
		try(AppEntityManager em = new AppEntityManager()){
			TypedQuery<EntitySala> query = em.getEntityManager().createQuery("select s from EntitySala s where s.SalaID = :sID",EntitySala.class).setParameter("sID", salaID);
			EntitySala sala = query.getSingleResult();
			if(!sala.equals(null))
				return sala;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<EntitySala> getAllSala() {
		try(AppEntityManager em = new AppEntityManager()){
			List<EntitySala> sala = em.getEntityManager().createQuery("select s from EntitySala s order by s.SalaID",EntitySala.class).getResultList();	
			if(sala.size()!=0){
				return sala;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
