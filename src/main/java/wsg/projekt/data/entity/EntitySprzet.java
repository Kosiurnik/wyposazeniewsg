package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	/*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "Sprzety") trochę podrasowujemy relacje*/
	/*private List<EntityZamowienie> Zamowienia = new ArrayList<EntityZamowienie>(0);*/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.sprzet")
	private List<EntityZamowienieSprzetAlloc> ZamowieniaSprzety = new ArrayList<EntityZamowienieSprzetAlloc>(0);
	@Column(name = "Nazwa", columnDefinition="VARCHAR(255) NOT NULL")
	private String Nazwa;
	@Column(name = "MaxIlosc", columnDefinition="INT NOT NULL")
	private int MaxIlosc;
	@Column(name = "DostepnaIlosc", columnDefinition="INT NOT NULL")
	private int DostepnaIlosc;
	
	public EntitySprzet() {}
	
	public EntitySprzet(String nazwa, int ilosc) {
		super();
		Nazwa = nazwa;
		DostepnaIlosc = ilosc;
		MaxIlosc = ilosc;
	}
	
	public EntitySprzet(String nazwa, int ilosc, List<EntityZamowienieSprzetAlloc> zamowienia) {
		super();
		Nazwa = nazwa;
		DostepnaIlosc = ilosc;
		ZamowieniaSprzety = zamowienia;
	}

	
	public int getSprzetID() {
		return SprzetID;
	}
	public String getNazwa() {
		return Nazwa;
	}
	public void setNazwa(String nazwa) {
		Nazwa = nazwa;
	}
	public int getDostepnaIlosc() {
		return DostepnaIlosc;
	}
	public void setDostepnaIlosc(int ilosc) {
		DostepnaIlosc = ilosc;
	}
	public int getMaxIlosc() {
		return MaxIlosc;
	}

	public void setMaxIlosc(int maxIlosc) {
		MaxIlosc = maxIlosc;
	}

	public List<EntityZamowienieSprzetAlloc> getZamowieniaSprzety() {
		return this.ZamowieniaSprzety;
	}

	public void setZamowieniaSprzety(List<EntityZamowienieSprzetAlloc> ZamowieniaSprzety) {
		this.ZamowieniaSprzety = ZamowieniaSprzety;
	}

}
