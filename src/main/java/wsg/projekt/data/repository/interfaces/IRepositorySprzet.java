package wsg.projekt.data.repository.interfaces;

import java.util.List;

import wsg.projekt.data.entity.EntitySprzet;

public interface IRepositorySprzet {
	public EntitySprzet getSprzetByID(int ID);
	public List<EntitySprzet> getAllSprzet();
}
