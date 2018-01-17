package wsg.projekt.data.tablemodel;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;
import wsg.projekt.data.repository.RepositoryZamowienie;

/*niestandardowy model tabeli zamówień*/
public class TableModelZamowienie extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private List<EntityZamowienie> zamowienia;

	public TableModelZamowienie(RepositoryZamowienie repository){
		super();
		this.zamowienia = repository.getAllZamowienie();
	}
	
	public int getColumnCount() {
		return 9;
	}

	public int getRowCount() {
		return zamowienia.size();
	}

    public Object getValueAt(int rowIndex, int columnIndex)
    {
    	SimpleDateFormat fttime = new SimpleDateFormat("HH:mm");
    	SimpleDateFormat ftdate = new SimpleDateFormat("dd-MM-yyyy");
    	EntityZamowienie z=zamowienia.get(rowIndex);
    	String data;
    	if(z.getZamowienieStale()==null)
    		data = "Data realizacji: "+ftdate.format(z.getDataStart())+"<br>W godzinach "+fttime.format(z.getDataStart())+" - "+fttime.format(z.getDataKoniec());
    	else
    		data = "Dni realizacji: "+getDniRealizacji(z)+"<br>W godzinach "+fttime.format(z.getDataStart())+" - "+fttime.format(z.getDataKoniec());
        Object[] values=new Object[]{z.getZamowienieID(),z.getWykladowca().getImie()+" "+z.getWykladowca().getNazwisko(),z.getSala().getNumerSali(),
        		sprzetyListString(z.getZamowieniaSprzety()),data,z.getRodzajZajec(),z.getUwagi(),"Edytuj","Usuń"};
        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column)
    {
        String[] columnNames=new String[]{"ID","Wykładowca","Sala","Sprzęty","Data","Rodzaj Zajęć","Uwagi","",""};
        return columnNames[column];
    }
    
    private String sprzetyListString(List<EntityZamowienieSprzetAlloc> sprzety){
    	if(sprzety.size()!=0){
    		String[] sprzetyString = new String[sprzety.size()];
    		int index = 0;
    		for(EntityZamowienieSprzetAlloc sprzet : sprzety){
    			sprzetyString[index]=sprzet.getSprzet().getNazwa()+" - "+sprzet.getIloscSprzetu()+" "+IloscSprzetuLabel(sprzet.getIloscSprzetu());
    			index++;
    		}
    		return String.join("<br>", sprzetyString);
    	}else
    		return "brak";
    }
    
    private String IloscSprzetuLabel(int ilosc){
    	switch(ilosc){
    	case 0: return "sztuk";
    	case 1: return "sztuka";
    	case 2:
    	case 3: 
    	case 4: return "sztuki";
    	}
    	return "sztuk";
    }
	
    private String getDniRealizacji(EntityZamowienie z){
    	String out = "";
    	if(z.getZamowienieStale().getPoniedzialek()) out+="Pn ";
    	if(z.getZamowienieStale().getWtorek()) out+="Wt ";
    	if(z.getZamowienieStale().getSroda()) out+="Śr ";
    	if(z.getZamowienieStale().getCzwartek()) out+="Cz ";
    	if(z.getZamowienieStale().getPiatek()) out+="Pt ";
    	if(z.getZamowienieStale().getSobota()) out+="Sb ";
    	if(z.getZamowienieStale().getNiedziela()) out+="Nd ";
    	return out;
    }
    
	@Override
	public boolean isCellEditable(int row, int col) {
		if(col==7||col==8)
			return true;
		else
			return false;
	}

}
