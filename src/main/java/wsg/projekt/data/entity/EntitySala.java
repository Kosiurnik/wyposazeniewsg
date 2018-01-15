package wsg.projekt.data.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*model tabeli bazodanowej dla sal*/
@Entity
@Table(name = "Sala")
public class EntitySala implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SalaID", unique = true, nullable = false)
	private int SalaID;
	
	@OneToMany(mappedBy="Sala")
	private List<EntityZamowienie> Zamowienie;

	@Column(name = "NumerSali", columnDefinition="VARCHAR(10) NOT NULL")
	private String NumerSali;

	@Column(name = "Opis", columnDefinition="VARCHAR(255) NOT NULL")
	private String Opis;

	public EntitySala(){}
	public EntitySala(String numerSali, String opis) {
		super();
		Zamowienie = new ArrayList<EntityZamowienie>();
		NumerSali = numerSali;
		Opis = opis;
	}


	public int getSalaID() {
		return SalaID;
	}
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
	
	@Override
	public String toString() {
		return NumerSali;
	}
}
