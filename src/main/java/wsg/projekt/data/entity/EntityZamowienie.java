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
	private int zamowienieID;
	@ManyToOne
	@JoinColumn(name="wykladowcaID", nullable=false)
	private EntityWykladowca wykladowca;
	@ManyToOne
	@JoinColumn(name="salaID", nullable=false)
	private EntitySala sala;
	@ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.ALL })
    @JoinTable(
            name = "zamowienieSprzetAlloc", 
            joinColumns = { @JoinColumn(name = "zamowienieID") }, 
            inverseJoinColumns = { @JoinColumn(name = "sprzetID") }
        )
	private List<EntitySprzet> sprzety = new ArrayList<EntitySprzet>(0);
	
	@Column(name = "zamowienieDataStart", columnDefinition="DATETIME NOT NULL DEFAULT NOW()")
	private Date dataStart;
	@Column(name = "zamowienieDataKoniec", columnDefinition="DATETIME NOT NULL DEFAULT NOW()")
	private Date dataKoniec;
	@Column(name = "rodzajZajec", columnDefinition="VARCHAR(255) NOT NULL")
	private String rodzajZajec;
	@Column(name = "uwagi", columnDefinition="VARCHAR(255) NOT NULL")
	private String Uwagi;
	
	
}
