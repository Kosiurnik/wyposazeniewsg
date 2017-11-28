package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Sala")
public class EntitySala {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int salaID;
	
	@OneToMany(mappedBy="sala")
	private List<EntityZamowienie> zamowienie;
}
