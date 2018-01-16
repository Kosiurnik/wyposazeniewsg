package wsg.projekt.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;
import wsg.projekt.data.repository.interfaces.IRepositoryZamowienie;

@Repository
public class RepositoryZamowienie implements IRepositoryZamowienie {

	public EntityZamowienie getZamowienieByID(int zamowienieID) {
		try (AppEntityManager em = new AppEntityManager()) {
			TypedQuery<EntityZamowienie> query = em.getEntityManager()
					.createQuery("select z from EntityZamowienie z where z.ZamowienieID = :zID", EntityZamowienie.class)
					.setParameter("zID", zamowienieID);
			EntityZamowienie zamowienie = query.getSingleResult();
			if (zamowienie.getZamowieniaSprzety().size() != 0) {
				for (EntityZamowienieSprzetAlloc zamowieniasprzetu : zamowienie.getZamowieniaSprzety()) {
					zamowieniasprzetu.getSprzet().setZamowieniaSprzety(new ArrayList<EntityZamowienieSprzetAlloc>());
				}
				zamowienie.setZamowieniaSprzety(zamowienie.getZamowieniaSprzety());
			} else
				zamowienie.setZamowieniaSprzety(null);
			return zamowienie;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<EntityZamowienie> getAllZamowienie() {
		try (AppEntityManager em = new AppEntityManager()) {
			List<EntityZamowienie> zamowienia = em.getEntityManager()
					.createQuery("select z from EntityZamowienie z order by z.ZamowienieID desc",
							EntityZamowienie.class)
					.getResultList();
			if (zamowienia.size() != 0) {
				for (EntityZamowienie zamowienie : zamowienia) {
					if (zamowienie.getZamowieniaSprzety().size() != 0) {
						for (EntityZamowienieSprzetAlloc zamowieniasprzetu : zamowienie.getZamowieniaSprzety()) {
							zamowieniasprzetu.getSprzet()
									.setZamowieniaSprzety(new ArrayList<EntityZamowienieSprzetAlloc>());
						}
						zamowienie.setZamowieniaSprzety(zamowienie.getZamowieniaSprzety());
					} else
						zamowienie.setZamowieniaSprzety(null);
				}
				return zamowienia;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional 
	public boolean updateZamowienie(EntityZamowienie update) {
		try (AppEntityManager em = new AppEntityManager()) {
			Query query = em.getEntityManager().createQuery(
					"update EntityZamowienie z set z.DataKoniec = :zamowieniedatakoniec, z.DataStart = :zamowieniedatastart, z.RodzajZajec = :rodzajzajec, z.Uwagi = :uwagi, z.Sala = :sala, z.Wykladowca = :wykladowca, z.ZamowienieStale = :zamowieniestale where z.ZamowienieID = '"+update.getZamowienieID()+"'");
			query.setParameter("zamowieniedatakoniec", update.getDataKoniec());
			query.setParameter("zamowieniedatastart", update.getDataStart());
			query.setParameter("rodzajzajec", update.getRodzajZajec());
			query.setParameter("uwagi", update.getUwagi());
			query.setParameter("sala", update.getSala());
			query.setParameter("wykladowca", update.getWykladowca());
			query.setParameter("zamowieniestale", update.getZamowienieStale());
					
			EntityZamowienie tempZamowienie = getZamowienieByID(update.getZamowienieID());
			em.getEntityManager().getTransaction().begin();
			try{
				em.getEntityManager().persist(update.getZamowienieStale());
			}catch(IllegalArgumentException e){
				try{
					Query delete = em.getEntityManager().createQuery("delete from EntityZamowienieStale zs where zs.ZamowienieStaleID = '"+tempZamowienie.getZamowienieStale().getZamowienieStaleID()+"'");
					delete.executeUpdate();
				}catch(NullPointerException e2){}
			}
			
			for(EntityZamowienieSprzetAlloc stare : tempZamowienie.getZamowieniaSprzety())
				em.getEntityManager().remove(em.getEntityManager().contains(stare) ? stare : em.getEntityManager().merge(stare));
			
			/*muszę wsadzać nowe pozycje w kolejności odwróconej, inaczej kolejność istniejących pozycji będzie odwracana przy każdej edycji*/
			List<EntityZamowienieSprzetAlloc> noweSprzety = update.getZamowieniaSprzety();
			for(int x = (noweSprzety.size()-1); x>=0; x--)
				em.getEntityManager().persist(noweSprzety.get(x));
			
			query.executeUpdate();
			em.getEntityManager().getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
