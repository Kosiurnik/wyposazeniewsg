package wsg.projekt.data.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntityWykladowca;
import wsg.projekt.data.repository.interfaces.IRepositoryWykladowca;

@Repository
public class RepositoryWykladowca implements IRepositoryWykladowca {

	@Override
	public EntityWykladowca getWykladowcaByID(int wykladowcaID) {
		try(AppEntityManager em = new AppEntityManager()){
			TypedQuery<EntityWykladowca> query = em.getEntityManager().createQuery("select w from EntityWykladowca w where w.WykladowcaID = :wID",EntityWykladowca.class).setParameter("wID", wykladowcaID);
			EntityWykladowca wykladowca = query.getSingleResult();
			if(!wykladowca.equals(null))
				return wykladowca;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<EntityWykladowca> getAllWykladowca() {
		try(AppEntityManager em = new AppEntityManager()){
			List<EntityWykladowca> wykladowca = em.getEntityManager().createQuery("select w from EntityWykladowca w order by w.WykladowcaID",EntityWykladowca.class).getResultList();	
			if(wykladowca.size()!=0){
				return wykladowca;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
