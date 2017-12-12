package wsg.projekt.data.tablerenderer;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;


/*wybrana kolumna zachowuje się jak JTextPane z obsługą html'a*/
public class HtmlTableCell extends JTextPane implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

		public HtmlTableCell() {
			setContentType("text/html");
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }  
}