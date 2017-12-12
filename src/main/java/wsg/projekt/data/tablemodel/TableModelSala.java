package wsg.projekt.data.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import wsg.projekt.data.entity.EntitySala;
import wsg.projekt.data.repository.RepositorySala;

public class TableModelSala extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<EntitySala> sale;
	
	public TableModelSala(RepositorySala repository){
		super();
		this.sale = repository.getAllSala();
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return sale.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EntitySala s=sale.get(rowIndex);
		Object[] values=new Object[]{s.getSalaID(),s.getNumerSali(),s.getOpis(),"Edytuj","Usuń"};
		return values[columnIndex];
	}
	
    @Override
    public String getColumnName(int column)
    {
        String[] columnNames=new String[]{"ID","Numer Sali","Opis","",""};
        return columnNames[column];
    }
	
	@Override
	public boolean isCellEditable(int row, int col) {
		if(col==3||col==4)
			return true;
		else
			return false;
	}

}
