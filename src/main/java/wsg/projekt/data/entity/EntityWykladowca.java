package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Wykladowca")
public class EntityWykladowca {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int wykladowcaID;
	@OneToMany(mappedBy="wykladowca")
	private List<EntityZamowienie> zamowienie;
	
}
