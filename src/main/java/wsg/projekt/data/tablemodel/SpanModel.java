package wsg.projekt.data.tablemodel;

import javax.swing.table.TableModel;

public interface SpanModel extends TableModel {


    Cell getVisibleCell(int row, int column);
    boolean isCellVisible(int row, int column);
    int getRowSpan(int row, int column);
    int getColumnSpan(int row, int column);
    void removeSpan(int row, int column);
    public static interface Cell {

        int getRow();

        int getColumn();
    }
}
