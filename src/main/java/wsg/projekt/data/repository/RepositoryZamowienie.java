package wsg.projekt.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;
import wsg.projekt.data.repository.interfaces.IRepositoryZamowienie;

@Repository
public class RepositoryZamowienie implements IRepositoryZamowienie {

	public EntityZamowienie getZamowienieByID(int zamowienieID) {
		try(AppEntityManager em = new AppEntityManager()){
			TypedQuery<EntityZamowienie> query = em.getEntityManager().createQuery("select z from EntityZamowienie z where z.ZamowienieID = :zID",EntityZamowienie.class).setParameter("zID", zamowienieID);
			EntityZamowienie zamowienie = query.getSingleResult();
			if(zamowienie.getZamowieniaSprzety().size()!=0){
				for(EntityZamowienieSprzetAlloc zamowieniasprzetu : zamowienie.getZamowieniaSprzety()){
					zamowieniasprzetu.getSprzet().setZamowieniaSprzety(new ArrayList<EntityZamowienieSprzetAlloc>());
				}
				zamowienie.setZamowieniaSprzety(zamowienie.getZamowieniaSprzety());
			}else zamowienie.setZamowieniaSprzety(null);
			return zamowienie;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<EntityZamowienie> getAllZamowienie() {
		try(AppEntityManager em = new AppEntityManager()){
			List<EntityZamowienie> zamowienia = em.getEntityManager().createQuery("select z from EntityZamowienie z order by z.ZamowienieID desc",EntityZamowienie.class).getResultList();
			if(zamowienia.size()!=0){
				for(EntityZamowienie zamowienie : zamowienia){
					if(zamowienie.getZamowieniaSprzety().size()!=0){
						for(EntityZamowienieSprzetAlloc zamowieniasprzetu : zamowienie.getZamowieniaSprzety()){
							zamowieniasprzetu.getSprzet().setZamowieniaSprzety(new ArrayList<EntityZamowienieSprzetAlloc>());
						}
						zamowienie.setZamowieniaSprzety(zamowienie.getZamowieniaSprzety());
					}else zamowienie.setZamowieniaSprzety(null);
				}
				return zamowienia;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
