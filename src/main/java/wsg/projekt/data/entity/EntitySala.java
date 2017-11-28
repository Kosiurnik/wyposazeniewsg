package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
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
	private int SalaID;
	
	@OneToMany(mappedBy="Sala")
	private List<EntityZamowienie> Zamowienie;

	@Column(name = "NumerSali", columnDefinition="VARCHAR(10) NOT NULL")
	private String NumerSali;

	@Column(name = "Opis", columnDefinition="VARCHAR(255) NOT NULL")
	private String Opis;

	public EntitySala(){}
	
	public List<EntityZamowienie> getZamowienie() {
		return Zamowienie;
	}

	public void setZamowienie(List<EntityZamowienie> zamowienie) {
		Zamowienie = zamowienie;
	}

	public String getNumerSali() {
		return NumerSali;
	}

	public void setNumerSali(String numerSali) {
		NumerSali = numerSali;
	}

	public String getOpis() {
		return Opis;
	}

	public void setOpis(String opis) {
		Opis = opis;
	}
	
	
}
