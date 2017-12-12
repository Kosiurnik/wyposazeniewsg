package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/*model tabeli bazodanowej dla sprzętu*/
@Entity
@Table(name = "Sprzet")
public class EntitySprzet implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SprzetID", unique = true, nullable = false)
	private int SprzetID;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "Sprzety")
	private List<EntityZamowienie> Zamowienia = new ArrayList<EntityZamowienie>(0);
	@Column(name = "Nazwa", columnDefinition="VARCHAR(255) NOT NULL")
	private String Nazwa;
	@Column(name = "Ilosc", columnDefinition="INT NOT NULL")
	private int Ilosc;
	
	public EntitySprzet() {}
	
	public EntitySprzet(String nazwa, int ilosc) {
		super();
		Zamowienia = new ArrayList<EntityZamowienie>();
		Nazwa = nazwa;
		Ilosc = ilosc;
	}


	public List<EntityZamowienie> getZamowienia() {
		return Zamowienia;
	}
	public void setZamowienia(List<EntityZamowienie> zamowienia) {
		Zamowienia = zamowienia;
	}
	public String getNazwa() {
		return Nazwa;
	}
	public void setNazwa(String nazwa) {
		Nazwa = nazwa;
	}
	public int getIlosc() {
		return Ilosc;
	}
	public void setIlosc(int ilosc) {
		Ilosc = ilosc;
	}

}
