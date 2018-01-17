package wsg.projekt.data.tablemodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.apache.commons.lang.Validate;

public class ForwardingTableModel implements TableModel {
    private TableModel delegate;

    public ForwardingTableModel(TableModel model) {
        Validate.notNull(model, "model nie może być pusty");
        
        this.delegate = model;
    }

    public int getRowCount() { return delegate.getRowCount(); }

    public int getColumnCount() { return delegate.getColumnCount(); }

    public String getColumnName(int columnIndex) { return delegate.getColumnName(columnIndex); }

    public Class<?> getColumnClass(int columnIndex) { return delegate.getColumnClass(columnIndex); }

    public boolean isCellEditable(int rowIndex, int columnIndex) { return delegate.isCellEditable(rowIndex, columnIndex); }

    public Object getValueAt(int rowIndex, int columnIndex) { return delegate.getValueAt(rowIndex, columnIndex); }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { delegate.setValueAt(aValue, rowIndex, columnIndex); }

    public void addTableModelListener(TableModelListener l) { delegate.addTableModelListener(l); }

    public void removeTableModelListener(TableModelListener l) { delegate.removeTableModelListener(l); }
}

