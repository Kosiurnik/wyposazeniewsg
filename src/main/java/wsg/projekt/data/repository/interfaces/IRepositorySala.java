package wsg.projekt.data.repository.interfaces;

import java.util.List;

import wsg.projekt.data.entity.EntitySala;

public interface IRepositorySala {
	public EntitySala getSalaByID(int ID);
	public List<EntitySala> getAllSala();
}
