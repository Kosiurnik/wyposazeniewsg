package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Sprzet")
public class EntitySprzet {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int sprzetID;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sprzety")
	private List<EntityZamowienie> zamowienia = new ArrayList<EntityZamowienie>(0);
	
}
