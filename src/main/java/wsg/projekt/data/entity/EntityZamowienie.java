package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Zamowienie")
public class EntityZamowienie implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ZamowienieID", unique = true, nullable = false)
	private int ZamowienieID;
	@ManyToOne
	@JoinColumn(name="WykladowcaID", nullable=false)
	private EntityWykladowca Wykladowca;
	@ManyToOne
	@JoinColumn(name="SalaID", nullable=false)
	private EntitySala Sala;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.zamowienie", cascade=CascadeType.ALL)
	private List<EntityZamowienieSprzetAlloc> ZamowieniaSprzety = new ArrayList<EntityZamowienieSprzetAlloc>(0);
	@Column(name = "ZamowienieDataStart", columnDefinition="DATETIME NOT NULL DEFAULT NOW()")
	private Date DataStart;
	@Column(name = "ZamowienieDataKoniec", columnDefinition="DATETIME NOT NULL DEFAULT NOW()")
	private Date DataKoniec;
	@Column(name = "RodzajZajec", columnDefinition="VARCHAR(255) NOT NULL")
	private String RodzajZajec;
	@Column(name = "Uwagi", columnDefinition="VARCHAR(255) NOT NULL")
	private String Uwagi;
	
	public EntityZamowienie(){}
	public EntityZamowienie(EntityWykladowca wykladowca, EntitySala sala, List<EntityZamowienieSprzetAlloc> sprzety, Date dataStart,
			Date dataKoniec, String rodzajZajec, String uwagi) {
		super();
		Wykladowca = wykladowca;
		Sala = sala;
		ZamowieniaSprzety = sprzety;
		DataStart = dataStart;
		DataKoniec = dataKoniec;
		RodzajZajec = rodzajZajec;
		Uwagi = uwagi;
	}


	public int getZamowienieID() {
		return ZamowienieID;
	}
	public void setZamowienieID(int zamowienieID) {
		ZamowienieID = zamowienieID;
	}
	public EntityWykladowca getWykladowca() {
		return Wykladowca;
	}
	public void setWykladowca(EntityWykladowca wykladowca) {
		Wykladowca = wykladowca;
	}
	public EntitySala getSala() {
		return Sala;
	}
	public void setSala(EntitySala sala) {
		Sala = sala;
	}
	public Date getDataStart() {
		return DataStart;
	}
	public void setDataStart(Date dataStart) {
		DataStart = dataStart;
	}
	public Date getDataKoniec() {
		return DataKoniec;
	}
	public void setDataKoniec(Date dataKoniec) {
		DataKoniec = dataKoniec;
	}
	public String getRodzajZajec() {
		return RodzajZajec;
	}
	public void setRodzajZajec(String rodzajZajec) {
		RodzajZajec = rodzajZajec;
	}
	public String getUwagi() {
		return Uwagi;
	}
	public void setUwagi(String uwagi) {
		Uwagi = uwagi;
	}
	
	public List<EntityZamowienieSprzetAlloc> getZamowieniaSprzety() {
		return this.ZamowieniaSprzety;
	}

	public void setZamowieniaSprzety(List<EntityZamowienieSprzetAlloc> ZamowieniaSprzety) {
		this.ZamowieniaSprzety = ZamowieniaSprzety;
	}
}
