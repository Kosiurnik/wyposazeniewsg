package wsg.projekt.data.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import wsg.projekt.data.entity.EntitySprzet;
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
    	EntityZamowienie z=zamowienia.get(rowIndex);
        Object[] values=new Object[]{z.getZamowienieID(),z.getWykladowca().getImie()+" "+z.getWykladowca().getNazwisko(),z.getSala().getNumerSali(),
        		sprzetyListString(z.getZamowieniaSprzety()),z.getDataStart(),z.getRodzajZajec(),z.getUwagi(),"Edytuj","Usuń"};
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
    			sprzetyString[index]=sprzet.getSprzet().getNazwa();
    			index++;
    		}
    		return "<html>"+String.join("<br>", sprzetyString)+"</html>";
    	}else
    		return "brak";
    }

	public void removeRow(int rowID) {
		System.out.println(rowID);
	}
	
	public void editRow(int rowID) {
		System.out.println(rowID);
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		if(col==7||col==8)
			return true;
		else
			return false;
	}

}