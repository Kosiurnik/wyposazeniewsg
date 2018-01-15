package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZamowienieStale")
public class EntityZamowienieStale {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ZamowienieStaleID", unique = true, nullable = false)
	private int ZamowienieStaleID;
	
	private Boolean Poniedzialek;
	private Boolean Wtorek;
	private Boolean Sroda;
	private Boolean Czwartek;
	private Boolean Piatek;
	private Boolean Sobota;
	private Boolean Niedziela;
	
	public EntityZamowienieStale() {
		Poniedzialek = false;
		Wtorek = false;
		Sroda = false;
		Czwartek = false;
		Piatek = false;
		Sobota = false;
		Niedziela = false;
	}
	public EntityZamowienieStale(Boolean poniedzialek, Boolean wtorek, Boolean sroda, Boolean czwartek, Boolean piatek,
			Boolean sobota, Boolean niedziela) {
		super();
		Poniedzialek = poniedzialek;
		Wtorek = wtorek;
		Sroda = sroda;
		Czwartek = czwartek;
		Piatek = piatek;
		Sobota = sobota;
		Niedziela = niedziela;
	}

	public int getZamowienieStaleID() {
		return ZamowienieStaleID;
	}
	public void setZamowienieStaleID(int zamowienieStaleID) {
		ZamowienieStaleID = zamowienieStaleID;
	}
	public Boolean getPoniedzialek() {
		return Poniedzialek;
	}
	public void setPoniedzialek(Boolean poniedzialek) {
		Poniedzialek = poniedzialek;
	}
	public Boolean getWtorek() {
		return Wtorek;
	}
	public void setWtorek(Boolean wtorek) {
		Wtorek = wtorek;
	}
	public Boolean getSroda() {
		return Sroda;
	}
	public void setSroda(Boolean sroda) {
		Sroda = sroda;
	}
	public Boolean getCzwartek() {
		return Czwartek;
	}
	public void setCzwartek(Boolean czwartek) {
		Czwartek = czwartek;
	}
	public Boolean getPiatek() {
		return Piatek;
	}
	public void setPiatek(Boolean piatek) {
		Piatek = piatek;
	}
	public Boolean getSobota() {
		return Sobota;
	}
	public void setSobota(Boolean sobota) {
		Sobota = sobota;
	}
	public Boolean getNiedziela() {
		return Niedziela;
	}
	public void setNiedziela(Boolean niedziela) {
		Niedziela = niedziela;
	}
}
