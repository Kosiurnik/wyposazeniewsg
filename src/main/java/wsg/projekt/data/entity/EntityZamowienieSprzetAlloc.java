package wsg.projekt.data.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ZamowienieSprzetAlloc")
@AssociationOverrides({
	@AssociationOverride(name = "pk.zamowienie",
		joinColumns = @JoinColumn(name = "ZamowienieID")),
	@AssociationOverride(name = "pk.sprzet",
		joinColumns = @JoinColumn(name = "SprzetID")) })
public class EntityZamowienieSprzetAlloc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private ZamowienieSprzetID pk = new ZamowienieSprzetID();
	private int iloscSprzetu;
	
	public EntityZamowienieSprzetAlloc(){}
	public EntityZamowienieSprzetAlloc(EntitySprzet sprzet, int i) {
		getPk().setSprzet(sprzet);
		this.iloscSprzetu = i;
	}

	@EmbeddedId
	public ZamowienieSprzetID getPk() {
		return pk;
	}
	
	public void setPk(ZamowienieSprzetID pk) {
		this.pk = pk;
	}
	
	@Transient
	public EntityZamowienie getZamowienie() {
		return getPk().getZamowienie();
	}
	
	public void setZamowienie(EntityZamowienie zamowienie) {
		getPk().setZamowienie(zamowienie);
	}
	
	@Transient
	public EntitySprzet getSprzet() {
		return getPk().getSprzet();
	}
	
	public void setSprzet(EntitySprzet sprzet) {
		getPk().setSprzet(sprzet);
	}
	
	@Column(name = "IloscSprzetu", nullable = false)
	public int getIloscSprzetu() {
		return this.iloscSprzetu;
	}
	
	public void setIloscSprzetu(int iloscSprzetu) {
		this.iloscSprzetu = iloscSprzetu;
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EntityZamowienieSprzetAlloc that = (EntityZamowienieSprzetAlloc) o;

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return true;
	}
	
	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
}
