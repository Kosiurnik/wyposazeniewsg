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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Zamowienie")
public class EntityZamowienie {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int ZamowienieID;
	@ManyToOne
	@JoinColumn(name="WykladowcaID", nullable=false)
	private EntityWykladowca Wykladowca;
	@ManyToOne
	@JoinColumn(name="SalaID", nullable=false)
	private EntitySala Sala;
	@ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.ALL })
    @JoinTable(
            name = "ZamowienieSprzetAlloc", 
            joinColumns = { @JoinColumn(name = "ZamowienieID") }, 
            inverseJoinColumns = { @JoinColumn(name = "SprzetID") }
        )
	private List<EntitySprzet> Sprzety = new ArrayList<EntitySprzet>(0);
	@Column(name = "ZamowienieDataStart", columnDefinition="DATETIME NOT NULL DEFAULT NOW()")
	private Date DataStart;
	@Column(name = "ZamowienieDataKoniec", columnDefinition="DATETIME NOT NULL DEFAULT NOW()")
	private Date DataKoniec;
	@Column(name = "RodzajZajec", columnDefinition="VARCHAR(255) NOT NULL")
	private String RodzajZajec;
	@Column(name = "Uwagi", columnDefinition="VARCHAR(255) NOT NULL")
	private String Uwagi;
	
	public EntityZamowienie(){}
	
	public int getZamowienieID() {
		return ZamowienieID;
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
	public List<EntitySprzet> getSprzety() {
		return Sprzety;
	}
	public void setSprzety(List<EntitySprzet> sprzety) {
		Sprzety = sprzety;
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
	
	
}
