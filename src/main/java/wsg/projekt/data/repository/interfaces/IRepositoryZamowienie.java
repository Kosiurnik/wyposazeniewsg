package wsg.projekt.data.repository.interfaces;

import java.util.List;

import wsg.projekt.data.entity.EntityZamowienie;

public interface IRepositoryZamowienie {
	
	public EntityZamowienie getZamowienieByID(int ID);
	public List<EntityZamowienie> getAllZamowienie();
}
