package wsg.projekt.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.repository.interfaces.IRepositoryZamowienie;

@Repository
public class RepositoryZamowienie implements IRepositoryZamowienie {

	public EntityZamowienie getZamowienieByID(int zamowienieID) {
	/*	try(AppEntityManager em = new AppEntityManager()){
			TypedQuery<EntityZamowienie> query = em.getEntityManager().createQuery("select z from EntityZamowienie z where z.ZamowienieID = :zID",EntityZamowienie.class).setParameter("zID", zamowienieID);
			EntityZamowienie zamowienie = query.getSingleResult();
			if(zamowienie.getSprzety().size()!=0){
				for(EntitySprzet sprzet : zamowienie.getSprzety()){
					//to takie moje prowizoryczne zabezpieczenie przed nieskończonym zapętlaniem się obiektów w obiekcie, które dzieje się przy relacji "wiele do wielu". Ważne, że działa!
					sprzet.setZamowienia(new ArrayList<EntityZamowienie>());
				}
				zamowienie.setSprzety(zamowienie.getSprzety());
			}else zamowienie.setSprzety(null);
			return zamowienie;
		}catch(Exception e){
			e.printStackTrace();
		}*/
		return null;
	}

	public List<EntityZamowienie> getAllZamowienie() {
	/*	try(AppEntityManager em = new AppEntityManager()){
			List<EntityZamowienie> zamowienia = em.getEntityManager().createQuery("select z from EntityZamowienie z",EntityZamowienie.class).getResultList();
			if(zamowienia.size()!=0){
				for(EntityZamowienie zamowienie : zamowienia){
					if(zamowienie.getSprzety().size()!=0){
						for(EntitySprzet sprzet : zamowienie.getSprzety()){
							sprzet.setZamowienia(new ArrayList<EntityZamowienie>());
						}
						zamowienie.setSprzety(zamowienie.getSprzety());
					}else zamowienie.setSprzety(null);
				}
				return zamowienia;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
	}

}
