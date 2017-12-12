package wsg.projekt.data.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import wsg.projekt.data.entity.EntityWykladowca;
import wsg.projekt.data.repository.RepositoryWykladowca;

public class TableModelWykladowca extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private List<EntityWykladowca> wykladowcy;
	
	public TableModelWykladowca(RepositoryWykladowca repository){
		super();
		this.wykladowcy = repository.getAllWykladowca();
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {

		return wykladowcy.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EntityWykladowca w=wykladowcy.get(rowIndex);
		Object[] values=new Object[]{w.getWykladowcaID(),w.getImie(),w.getNazwisko(),"Edytuj","Usuń"};
		return values[columnIndex];
	}
	
    @Override
    public String getColumnName(int column)
    {
        String[] columnNames=new String[]{"ID","Imię","Nazwisko","",""};
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
		if(col==3||col==4)
			return true;
		else
			return false;
	}

}
