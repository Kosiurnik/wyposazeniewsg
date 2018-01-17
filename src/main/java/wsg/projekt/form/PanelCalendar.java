package wsg.projekt.form;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import wsg.projekt.data.component.SpanTable;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;
import wsg.projekt.data.repository.RepositoryZamowienie;
import wsg.projekt.data.tablemodel.DefaultSpanModel;
import wsg.projekt.data.tablerenderer.HtmlTableCell;
import wsg.projekt.data.util.DateLabelFormatter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

//tu ma być ten cały kalendarz...
public class PanelCalendar extends JPanel {

	private static final long serialVersionUID = 1L;

	private SpanTable spanTable;
	private JDatePickerImpl dpStart;
	private JDatePickerImpl dpEnd;
	List<String> timeList;
	List<EntityZamowienie> zamowienia;

	String[] headers;
	DefaultTableModel model;
	DefaultSpanModel spanModel;

	public PanelCalendar(RepositoryZamowienie repositoryZamowienie) {

		timeList = getTimeList(6, 22, 15);
		zamowienia = repositoryZamowienie.getAllZamowienie();

		// spanModel.setRowSpan(1, 1, 2);
		// spanModel.setRowSpan(2, 0, 3);
		setLayout(new BorderLayout(0, 0));

		// górne menu tabelki
		JPanel upperPanel = new JPanel();
		add(upperPanel, BorderLayout.NORTH);

		Properties p = new Properties();
		p.put("text.today", "Dziś");
		p.put("text.month", "Miesiąc");
		p.put("text.year", "Rok");

		SimpleDateFormat ftdate = new SimpleDateFormat("dd-MM-yyyy");
		UtilDateModel dateModel = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
		upperPanel.setLayout(new GridLayout(3, 2, 15, 0));

		JLabel lbDataStart = new JLabel("Data od:");
		upperPanel.add(lbDataStart);

		JLabel lblDataEnd = new JLabel("Data do:");
		upperPanel.add(lblDataEnd);
		dpStart = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		dpStart.getJFormattedTextField().setFont(new Font("Tahoma", Font.BOLD, 13));

		upperPanel.add(dpStart);

		dpStart.getJFormattedTextField().setText(ftdate.format(new Date()));

		UtilDateModel dateModel2 = new UtilDateModel();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(dateModel2);
		dpEnd = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		dpEnd.getJFormattedTextField().setFont(new Font("Tahoma", Font.BOLD, 13));
		upperPanel.add(dpEnd);

		dpEnd.getJFormattedTextField().setText("30-01-2018");

		JButton btnToday = new JButton("Pokaż dzisiejsze");
		upperPanel.add(btnToday);

		JComboBox cbMode = new JComboBox();
		upperPanel.add(cbMode);

		dpStart.getJFormattedTextField().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try {
					if (ileDni(ftdate.parse(dpStart.getJFormattedTextField().getText()),
							ftdate.parse(dpEnd.getJFormattedTextField().getText())) >= 0) {
						reloadTable();
					} else {
						JOptionPane.showMessageDialog(null, "Podany zakres dat nie jest właściwy", "Błąd zakresu dat",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
			}

		});
		dpEnd.getJFormattedTextField().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try {
					if (ileDni(ftdate.parse(dpStart.getJFormattedTextField().getText()),
							ftdate.parse(dpEnd.getJFormattedTextField().getText())) >= 0) {
						reloadTable();
					} else {
						JOptionPane.showMessageDialog(null, "Podany zakres dat nie jest właściwy", "Błąd zakresu dat",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
			}

		});
		try {
			reloadTable();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPaneCalendar = new JScrollPane();
		scrollPaneCalendar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneCalendar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrollPaneCalendar);
		scrollPaneCalendar.setViewportView(spanTable);

	}

	private String[] generateHeaders(Date start, Date koniec) {
		SimpleDateFormat ftdate = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat fttime = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		long ileDni = ileDni(start, koniec) + 1;
		List<String> headerList = new ArrayList<String>();
		headerList.add("Godzina");
		int iloscZamowien = 0;
		c.add(Calendar.DATE, -1);
		for (int dzien = 0; dzien < ileDni; dzien++) {
			iloscZamowien = 0;
			c.add(Calendar.DATE, 1);
			for (EntityZamowienie zamowienie : zamowienia) {
				// zamówienia jednorazowe
				if (zamowienie.getZamowienieStale() == null) {
					if (ftdate.format(c.getTime()).equals(ftdate.format(zamowienie.getDataStart()))) {
						iloscZamowien++;
					}
				}
				// zamówienia stałe
				else {
					switch (c.get(Calendar.DAY_OF_WEEK)) {
					case 1:
						if (zamowienie.getZamowienieStale().getNiedziela())
							iloscZamowien++;
						break;
					case 2:
						if (zamowienie.getZamowienieStale().getPoniedzialek())
							iloscZamowien++;
						break;
					case 3:
						if (zamowienie.getZamowienieStale().getWtorek())
							iloscZamowien++;
						break;
					case 4:
						if (zamowienie.getZamowienieStale().getSroda())
							iloscZamowien++;
						break;
					case 5:
						if (zamowienie.getZamowienieStale().getCzwartek())
							iloscZamowien++;
						break;
					case 6:
						if (zamowienie.getZamowienieStale().getPiatek())
							iloscZamowien++;
						break;
					case 7:
						if (zamowienie.getZamowienieStale().getSobota())
							iloscZamowien++;
						break;
					}
				}
			}
			headerList.add(ftdate.format(c.getTime()) + " ("
					+ new SimpleDateFormat("EEEE", Locale.getDefault()).format(c.getTime()) + ")");
			if (iloscZamowien >= 2) {
				for (int x = 0; x < iloscZamowien - 1; x++)
					headerList.add("");
			}
		}

		String[] a = new String[headerList.size()];
		String[] headers = headerList.toArray(a);
		return headers;
	}

	private void reloadTable() throws ParseException {
		SimpleDateFormat ftdate = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("Przeładowuje kalendarz na zakres dat " + dpStart.getJFormattedTextField().getText() + " : "
				+ dpEnd.getJFormattedTextField().getText());

		headers = generateHeaders(ftdate.parse(dpStart.getJFormattedTextField().getText()),
				ftdate.parse(dpEnd.getJFormattedTextField().getText()));
		model = new DefaultTableModel(generateModel(headers, ftdate.parse(dpStart.getJFormattedTextField().getText()),
				ftdate.parse(dpEnd.getJFormattedTextField().getText())), headers);
		spanModel = new DefaultSpanModel(model);

		spanTable = new SpanTable(spanModel);
		spanTable.setEnabled(false);
		spanTable.setCellSelectionEnabled(true);
		spanTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		spanTable.getTableHeader().setReorderingAllowed(false);
		spanColumns(ftdate.parse(dpStart.getJFormattedTextField().getText()),
				ftdate.parse(dpEnd.getJFormattedTextField().getText()));
		for (int x = 0; x < spanTable.getColumnCount(); x++) {
			spanTable.getColumnModel().getColumn(x).setCellRenderer(new HtmlTableCell());
			spanTable.getColumnModel().getColumn(x).setPreferredWidth(150);
		}

	}

	private String[][] generateModel(String[] headers, Date start, Date koniec) {
		SimpleDateFormat fttime = new SimpleDateFormat("HH:mm");
		SimpleDateFormat ftdate = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		String[][] model = new String[timeList.size()][headers.length];

		for (int row = 0; row < timeList.size(); row++) {
			for (int column = 0; column < headers.length; column++) {
				if(column==0)
					model[row][column] = "<div style='display:table-cell; background-color:#ffffff; border-bottom:solid 1px #000;'>"+timeList.get(row)+"</div>";
				else
					model[row][column] = "<div style='display:table-cell; background-color:#ffffff; border-bottom:solid 1px #000;'></div>";
			}
		}
		
		for(int col = 1; col<headers.length; col++){
			try{
				List<EntityZamowienie> zamowienieDzien = new ArrayList<EntityZamowienie>();
				if(!headers[col].equals("")){
					Date dateCol = ftdate.parse(headers[col].substring(0, 10));
					c.setTime(dateCol);
					for (EntityZamowienie zamowienie : zamowienia) {
						if (zamowienie.getZamowienieStale() == null) {
							if (ftdate.format(c.getTime()).equals(ftdate.format(zamowienie.getDataStart()))) {
								zamowienieDzien.add(zamowienie);
							}
						}else{
								switch (c.get(Calendar.DAY_OF_WEEK)) {
								case 1:
									if (zamowienie.getZamowienieStale().getNiedziela())
										zamowienieDzien.add(zamowienie);
									break;
								case 2:
									if (zamowienie.getZamowienieStale().getPoniedzialek())
										zamowienieDzien.add(zamowienie);
									break;
								case 3:
									if (zamowienie.getZamowienieStale().getWtorek())
										zamowienieDzien.add(zamowienie);
									break;
								case 4:
									if (zamowienie.getZamowienieStale().getSroda())
										zamowienieDzien.add(zamowienie);
									break;
								case 5:
									if (zamowienie.getZamowienieStale().getCzwartek())
										zamowienieDzien.add(zamowienie);
									break;
								case 6:
									if (zamowienie.getZamowienieStale().getPiatek())
										zamowienieDzien.add(zamowienie);
									break;
								case 7:
									if (zamowienie.getZamowienieStale().getSobota())
										zamowienieDzien.add(zamowienie);
									break;
								}
						}
					}
				}
				if(zamowienieDzien.size()!=0){
					for(int i=0;i<zamowienieDzien.size();i++){
						int timeIndexStart = getTimeIndex(fttime.format(zamowienieDzien.get(i).getDataStart()));
						int timeIndexEnd = getTimeIndex(fttime.format(zamowienieDzien.get(i).getDataKoniec()));
						int rowSpan = timeIndexEnd-timeIndexStart;
						String color = "#a3fffd";
						if(zamowienieDzien.get(i).getZamowienieStale()==null)	color = "#a3cdff";
						String htmlStart = "<div style='height:"+20*rowSpan+"px; width:100%; background-color:"+color+"; position:relative;'>";
						String htmlEnd = "</div>";
						
						model[timeIndexStart][col]+=htmlStart;
						model[timeIndexStart][col]+="<b>Zamówienie ID:"+zamowienieDzien.get(i).getZamowienieID()+"</b><br>";
						model[timeIndexStart][col]+=zamowienieDzien.get(i).getRodzajZajec()+"<br><br>";
						model[timeIndexStart][col]+="<b>Sprzęty zamówione:</b>";
						for(EntityZamowienieSprzetAlloc sprzet : zamowienieDzien.get(i).getZamowieniaSprzety()){
							model[timeIndexStart][col]+="<span style='font-size:9px;'>"+sprzet.getSprzet().getNazwa()+" ("+sprzet.getIloscSprzetu()+" sztuk)</span><br>";
						}
						model[timeIndexStart][col]+="<b>Uwagi:</b><br>";
						model[timeIndexStart][col]+=zamowienieDzien.get(i).getUwagi();
						model[timeIndexStart][col]+=htmlEnd;
						//spanModel.setRowSpan(timeIndexStart,col,rowSpan);
						col++;
					}
				}	
			}catch(Exception e){
				
			}
		}
		return model;
	}

	private void spanColumns(Date start, Date koniec) {
		SimpleDateFormat fttime = new SimpleDateFormat("HH:mm");
		SimpleDateFormat ftdate = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		String[][] model = new String[timeList.size()][headers.length];
		
		for(int col = 1; col<headers.length; col++){
			try{
				List<EntityZamowienie> zamowienieDzien = new ArrayList<EntityZamowienie>();
				if(!headers[col].equals("")){
					Date dateCol = ftdate.parse(headers[col].substring(0, 10));
					c.setTime(dateCol);
					for (EntityZamowienie zamowienie : zamowienia) {
						if (zamowienie.getZamowienieStale() == null) {
							if (ftdate.format(c.getTime()).equals(ftdate.format(zamowienie.getDataStart()))) {
								zamowienieDzien.add(zamowienie);
							}
						}else{
								switch (c.get(Calendar.DAY_OF_WEEK)) {
								case 1:
									if (zamowienie.getZamowienieStale().getNiedziela())
										zamowienieDzien.add(zamowienie);
									break;
								case 2:
									if (zamowienie.getZamowienieStale().getPoniedzialek())
										zamowienieDzien.add(zamowienie);
									break;
								case 3:
									if (zamowienie.getZamowienieStale().getWtorek())
										zamowienieDzien.add(zamowienie);
									break;
								case 4:
									if (zamowienie.getZamowienieStale().getSroda())
										zamowienieDzien.add(zamowienie);
									break;
								case 5:
									if (zamowienie.getZamowienieStale().getCzwartek())
										zamowienieDzien.add(zamowienie);
									break;
								case 6:
									if (zamowienie.getZamowienieStale().getPiatek())
										zamowienieDzien.add(zamowienie);
									break;
								case 7:
									if (zamowienie.getZamowienieStale().getSobota())
										zamowienieDzien.add(zamowienie);
									break;
								}
						}
					}
				}
				if(zamowienieDzien.size()!=0){
					for(int i=0;i<zamowienieDzien.size();i++){
						int timeIndexStart = getTimeIndex(fttime.format(zamowienieDzien.get(i).getDataStart()));
						int timeIndexEnd = getTimeIndex(fttime.format(zamowienieDzien.get(i).getDataKoniec()));
						int rowSpan = timeIndexEnd-timeIndexStart;
						spanModel.setRowSpan(timeIndexStart,col,rowSpan);
						//spanTable.getColumnModel().getColumn(x).setCellRenderer(new HtmlTableCell());
						col++;
					}
				}	
			}catch(Exception e){
				
			}
		}
	}
	
	private List<String> getTimeList(int start, int end, int offset) {
		List<String> czas = new ArrayList<String>();

		SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(0));
		calendar.add(Calendar.HOUR, +start);
		czas.add(ft.format(calendar.getTime()));
		while ((calendar.getTime().getHours() + 1) <= end) {
			calendar.add(Calendar.MINUTE, +offset);
			czas.add(ft.format(calendar.getTime()));
		}

		return czas;
	}
	
	private int getTimeIndex(String hour){
		for(int x=0;x<timeList.size();x++){
			if(timeList.get(x).equals(hour))
				return x;
		}
		return -1;
	}

	private long ileDni(Date d1, Date d2) {
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		long comp = ((l1 - l2) / (1000 * 60 * 60 * 24)) * -1;
		return comp;
	}
}
