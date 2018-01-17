package wsg.projekt.data.component;

import javax.swing.JTable;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.plaf.TableUI;
import javax.swing.table.TableModel;

import wsg.projekt.data.tablemodel.SpanModel;


public class SpanTable extends JTable {
	private static final long serialVersionUID = 1L;
	private boolean isSpanModel;

    public SpanTable(TableModel model) {
        super(model);
        super.setUI(new SpanTableUI());
    }

    @Override
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        if (isSpanModel) {
            Rectangle cellRect = super.getCellRect(row, column, includeSpacing);

            for (int i = 1, n = ((SpanModel) getModel()).getRowSpan(row, column); i < n; i++) {
                cellRect.height += getRowHeight(row + i);
            }

            for (int i = 1, n = ((SpanModel) getModel()).getColumnSpan(row, column); i < n; i++) {
                cellRect.width += getColumnModel().getColumn(column + i).getWidth();
            }

            return cellRect;
        }

        return super.getCellRect(row, column, includeSpacing);
    }

    @Override
    public void setUI(TableUI ui) { }

    @Override
    public void setModel(TableModel dataModel) {
        isSpanModel = dataModel instanceof SpanModel;
        super.setModel(dataModel);
    }

    @Override
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        repaint();
    }

    @Override
    public int columnAtPoint(Point point) {
        if(isSpanModel) {
           int row = super.rowAtPoint(point);
           int column = super.columnAtPoint(point);

           return ((SpanModel) getModel()).getVisibleCell(row, column).getColumn();
        }

        return super.columnAtPoint(point);
    }

    @Override
    public int rowAtPoint(Point point) {
        if(isSpanModel) {
           int row = super.rowAtPoint(point);
           int column = super.columnAtPoint(point);

           return ((SpanModel) getModel()).getVisibleCell(row, column).getRow();
        }

        return super.rowAtPoint(point);
    }
}

