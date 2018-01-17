package wsg.projekt.form;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import wsg.projekt.data.component.SpanTable;
import wsg.projekt.data.tablemodel.DefaultSpanModel;
import wsg.projekt.data.tablerenderer.HtmlTableCell;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


//tu ma być ten cały kalendarz...
public class PanelCalendar extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelCalendar() {
        String[][] data = new String[][]{
            {"a1", "a2", "a3", "a4"},
            {"b1", "b2", "b3", "b4"},
            {"c1", "c2", "c3", "c4"},
            {"d1", "d2", "d3", "d4"},
            {"e1", "e2", "e3", "e4"}
        };
		
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"1", "2", "3", "4"});
        DefaultSpanModel spanModel = new DefaultSpanModel(model);
        
        spanModel.setRowSpan(1, 1, 2);
        spanModel.setRowSpan(2, 0, 3);
        setLayout(new BorderLayout(0, 0));
        
        SpanTable spanTable = new SpanTable(spanModel);
        spanTable.setCellSelectionEnabled(true);
        
        for(int x=0; x<spanTable.getColumnCount(); x++)
        	spanTable.getColumnModel().getColumn(x).setCellRenderer(new HtmlTableCell());
        
        add(new JScrollPane(spanTable));
	}
	
	private String[][] generateModel(){
		
		return null;
	}
	
	private List<String> getTimeList(int start, int end, int offset){
    	List<String> czas = new ArrayList<String>();
    	
    	SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(0));
        calendar.add(Calendar.HOUR, +start);
        czas.add(ft.format(calendar.getTime()));
        while((calendar.getTime().getHours()+1)<=end){
            calendar.add(Calendar.MINUTE, +offset);
            czas.add(ft.format(calendar.getTime()));
        }
    	
    	return czas;
    }

}
