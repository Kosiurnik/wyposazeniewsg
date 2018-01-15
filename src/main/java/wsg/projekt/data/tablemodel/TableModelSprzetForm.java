package wsg.projekt.data.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;

public class TableModelSprzetForm extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<EntityZamowienieSprzetAlloc> sprzety;
	
	public TableModelSprzetForm(List<EntityZamowienieSprzetAlloc> sprzety){
		super();
		this.sprzety = sprzety;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return sprzety.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EntityZamowienieSprzetAlloc s=sprzety.get(rowIndex);
		Object[] values=new Object[]{s.getSprzet().getNazwa(),s.getIloscSprzetu(),"x"};
		return values[columnIndex];
	}
	
    @Override
    public String getColumnName(int column)
    {
        String[] columnNames=new String[]{"Nazwa","Ilość",""};
        return columnNames[column];
    }
	
	@Override
	public boolean isCellEditable(int row, int col) {
		if(col==1||col==2)
			return true;
		else
			return false;
	}

}
