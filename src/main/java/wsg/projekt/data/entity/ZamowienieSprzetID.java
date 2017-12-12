package wsg.projekt.data.entity;

import java.io.Serializable;

import javax.persistence.ManyToOne;

public class ZamowienieSprzetID implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private EntityZamowienie zamowienie;
    private EntitySprzet sprzet;

    @ManyToOne
	public EntityZamowienie getZamowienie() {
		return zamowienie;
	}

	public void setZamowienie(EntityZamowienie zamowienie) {
		this.zamowienie = zamowienie;
	}

	@ManyToOne
	public EntitySprzet getSprzet() {
		return sprzet;
	}

	public void setSprzet(EntitySprzet sprzet) {
		this.sprzet = sprzet;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZamowienieSprzetID that = (ZamowienieSprzetID) o;

        if (zamowienie != null ? !zamowienie.equals(that.zamowienie) : that.zamowienie != null) return false;
        if (sprzet != null ? !sprzet.equals(that.sprzet) : that.sprzet != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (zamowienie != null ? zamowienie.hashCode() : 0);
        result = 31 * result + (sprzet != null ? sprzet.hashCode() : 0);
        return result;
    }
}
