package wsg.projekt.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;
import wsg.projekt.data.repository.interfaces.IRepositorySprzet;

@Repository
public class RepositorySprzet implements IRepositorySprzet {

	@Override
	public EntitySprzet getSprzetByID(int sprzetID) {
		try(AppEntityManager em = new AppEntityManager()){
			TypedQuery<EntitySprzet> query = em.getEntityManager().createQuery("select s from EntitySprzet s where s.SprzetID = :sID",EntitySprzet.class).setParameter("sID", sprzetID);
			EntitySprzet sprzet = query.getSingleResult();
			if(sprzet.getZamowieniaSprzety().size()!=0){
				for(EntityZamowienieSprzetAlloc zamowieniasprzetu : sprzet.getZamowieniaSprzety()){
					zamowieniasprzetu.getZamowienie().setZamowieniaSprzety(new ArrayList<EntityZamowienieSprzetAlloc>());
				}
				sprzet.setZamowieniaSprzety(sprzet.getZamowieniaSprzety());
			}else sprzet.setZamowieniaSprzety(null);
			return sprzet;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<EntitySprzet> getAllSprzet() {
		try(AppEntityManager em = new AppEntityManager()){
			List<EntitySprzet> sprzety = em.getEntityManager().createQuery("select s from EntitySprzet s",EntitySprzet.class).getResultList();
			if(sprzety.size()!=0){
				for(EntitySprzet sprzet : sprzety){
					if(sprzet.getZamowieniaSprzety().size()!=0){
						for(EntityZamowienieSprzetAlloc zamowieniasprzetu : sprzet.getZamowieniaSprzety()){
							zamowieniasprzetu.getZamowienie().setZamowieniaSprzety(new ArrayList<EntityZamowienieSprzetAlloc>());
						}
						sprzet.setZamowieniaSprzety(sprzet.getZamowieniaSprzety());
					}else sprzet.setZamowieniaSprzety(null);
				}
				return sprzety;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
