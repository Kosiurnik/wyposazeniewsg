package wsg.projekt.data.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.repository.RepositorySprzet;

public class TableModelSprzet extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<EntitySprzet> sprzety;
	
	public TableModelSprzet(RepositorySprzet repository){
		super();
		this.sprzety = repository.getAllSprzet();
	}
	
	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return sprzety.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EntitySprzet s=sprzety.get(rowIndex);
		Object[] values=new Object[]{s.getSprzetID(),s.getNazwa(),s.getMaxIlosc(),s.getDostepnaIlosc(),"Edytuj","Usuń"};
		return values[columnIndex];
	}
	
    @Override
    public String getColumnName(int column)
    {
        String[] columnNames=new String[]{"ID","Nazwa","Ilość dysponowana","Ilość dostępna","",""};
        return columnNames[column];
    }
	
	public void removeRow(int rowID) {
		System.out.println(rowID);
	}
	
	public void editRow(int rowID) {
		System.out.println(rowID);
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		if(col==4||col==5)
			return true;
		else
			return false;
	}

}
