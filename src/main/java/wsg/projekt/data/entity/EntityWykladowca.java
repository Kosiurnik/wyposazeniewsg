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
@Table(name = "Wykladowca")
public class EntityWykladowca {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int WykladowcaID;
	@OneToMany(mappedBy="Wykladowca")
	private List<EntityZamowienie> Zamowienie;
	@Column(name = "Imie", columnDefinition="VARCHAR(50) NOT NULL")
	private String Imie;
	@Column(name = "Nazwisko", columnDefinition="VARCHAR(50) NOT NULL")
	private String Nazwisko;
	
	public EntityWykladowca(){}
	
	public List<EntityZamowienie> getZamowienie() {
		return Zamowienie;
	}
	public void setZamowienie(List<EntityZamowienie> zamowienie) {
		Zamowienie = zamowienie;
	}
	public String getImie() {
		return Imie;
	}
	public void setImie(String imie) {
		Imie = imie;
	}
	public String getNazwisko() {
		return Nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		Nazwisko = nazwisko;
	}

	
}
