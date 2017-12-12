package wsg.projekt.data.repository.interfaces;

import java.util.List;

import wsg.projekt.data.entity.EntityWykladowca;

public interface IRepositoryWykladowca {
	public EntityWykladowca getWykladowcaByID(int ID);
	public List<EntityWykladowca> getAllWykladowca();
}
