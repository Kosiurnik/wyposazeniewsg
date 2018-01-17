package wsg.projekt.form;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntitySala;
import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.entity.EntityWykladowca;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;
import wsg.projekt.data.entity.EntityZamowienieStale;
import wsg.projekt.data.listcellrenderer.ComboRendererSala;
import wsg.projekt.data.listcellrenderer.ComboRendererSprzet;
import wsg.projekt.data.listcellrenderer.ComboRendererWykladowca;
import wsg.projekt.data.repository.RepositorySala;
import wsg.projekt.data.repository.RepositorySprzet;
import wsg.projekt.data.repository.RepositoryWykladowca;
import wsg.projekt.data.repository.RepositoryZamowienie;
import wsg.projekt.data.tablemodel.TableModelSprzetForm;
import wsg.projekt.data.tablerenderer.ButtonColumn;
import wsg.projekt.data.util.ComboBoxFilterDecorator;
import wsg.projekt.data.util.DateLabelFormatter;

public class FrameZamowienieEdit extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/*
	 * Spring - ładuję beany z repozytoriami danych do ładowania ich w listach
	 * do modelu wypełniającego tabelki
	 */
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("wyposazeniewsg-context.xml");
	private RepositoryZamowienie repositoryZamowienie = context.getBean("RepositoryZamowienie",
			RepositoryZamowienie.class);
	private RepositoryWykladowca repositoryWykladowca = context.getBean("RepositoryWykladowca",
			RepositoryWykladowca.class);
	private RepositorySala repositorySala = context.getBean("RepositorySala", RepositorySala.class);
	private RepositorySprzet repositorySprzet = context.getBean("RepositorySprzet", RepositorySprzet.class);
	private JTable table;
	private JTextField tfRodzajZajec;
	private JTextPane tpUwagi;
	private JComboBox<EntityWykladowca> cbWykladowca;
	private JComboBox<EntitySala> cbSala;
	private JComboBox<EntitySprzet> cbSprzet;
	private JDatePickerImpl dpStart;
	private JComboBox<Object> cbRozpoczecie;
	private JComboBox<Object> cbZakonczenie;
	private List<EntityZamowienieSprzetAlloc> sprzetyforma;
	private JCheckBox chckbxZamwienieStale;
	private JCheckBox chckStalePn;
	private JCheckBox chckStaleWt;
	private JCheckBox chckStaleSr;
	private JCheckBox chckStaleCz;
	private JCheckBox chckStalePt;
	private JCheckBox chckStaleSb;
	private JCheckBox chckStaleNd;

	public FrameZamowienieEdit(EntityZamowienie zamowienie) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setTitle("Zmień Zamówienie");
		setResizable(false);
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// labelki
		JLabel lblWykadowcaZlecajcy = new JLabel("Wykładowca zlecający:");
		lblWykadowcaZlecajcy.setBounds(12, 13, 133, 25);
		contentPane.add(lblWykadowcaZlecajcy);

		JLabel label = new JLabel("-");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(227, 83, 35, 25);
		contentPane.add(label);

		JLabel lblDataIGodziny = new JLabel("Data i godzina:");
		lblDataIGodziny.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataIGodziny.setBounds(12, 83, 133, 25);
		contentPane.add(lblDataIGodziny);

		JLabel lblDodajSprzt = new JLabel("Dodaj sprzęt do listy:");
		lblDodajSprzt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDodajSprzt.setBounds(12, 121, 133, 25);
		contentPane.add(lblDodajSprzt);

		JButton btnAccept = new JButton("Zmień");
		btnAccept.setBounds(12, 427, 97, 25);
		contentPane.add(btnAccept);

		JButton btnCancel = new JButton("Anuluj");
		btnCancel.setBounds(435, 427, 97, 25);
		contentPane.add(btnCancel);

		JLabel lblSala = new JLabel("Sala:");
		lblSala.setBounds(115, 48, 30, 25);
		contentPane.add(lblSala);

		JLabel lblRodzajZaj = new JLabel("Rodzaj zajęć:");
		lblRodzajZaj.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRodzajZaj.setBounds(12, 264, 133, 25);
		contentPane.add(lblRodzajZaj);

		JLabel lblUwagi = new JLabel("Uwagi:");
		lblUwagi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUwagi.setBounds(12, 302, 133, 25);
		contentPane.add(lblUwagi);

		// podgląd okienek w Eclipse świruje, kiedy widzi pola zawierające
		// obiekty niestandardowego typu...

		// combobox z listą wykładowców + filtr szukający
		List<EntityWykladowca> wykladowcy = repositoryWykladowca.getAllWykladowca();
		// cbWykladowca = new JComboBox<EntityWykladowca>();
		cbWykladowca = new JComboBox<>(wykladowcy.toArray(new EntityWykladowca[wykladowcy.size()]));
		cbWykladowca.setBounds(157, 13, 375, 25);
		ComboBoxFilterDecorator<EntityWykladowca> dekoratorWykladowca = ComboBoxFilterDecorator.decorate(cbWykladowca,
				ComboRendererWykladowca::getWykladowcaText, FrameZamowienieEdit::wykladowcaFilter);
		cbWykladowca.setRenderer(new ComboRendererWykladowca(dekoratorWykladowca.getFilterTextSupplier()));
		cbWykladowca.setSelectedItem(zamowienie.getWykladowca());
		contentPane.add(cbWykladowca);

		// combobox z listą sal + filtr szukający
		List<EntitySala> sale = repositorySala.getAllSala();
		// cbSala = new JComboBox<EntitySala>();
		cbSala = new JComboBox<>(sale.toArray(new EntitySala[sale.size()]));
		cbSala.setBounds(157, 48, 176, 25);
		ComboBoxFilterDecorator<EntitySala> dekoratorSala = ComboBoxFilterDecorator.decorate(cbSala,
				ComboRendererSala::getSalaText, FrameZamowienieEdit::salaFilter);
		cbSala.setRenderer(new ComboRendererSala(dekoratorSala.getFilterTextSupplier()));
		cbSala.setSelectedItem(zamowienie.getSala());
		contentPane.add(cbSala);

		// combobox z listą sprzętów + filtr szukający
		List<EntitySprzet> sprzety = repositorySprzet.getAllSprzet();
		// cbSprzet = new JComboBox<EntitySprzet>();
		cbSprzet = new JComboBox<EntitySprzet>(sprzety.toArray(new EntitySprzet[sprzety.size()]));
		ComboBoxFilterDecorator<EntitySprzet> dekoratorSprzet = ComboBoxFilterDecorator.decorate(cbSprzet,
				ComboRendererSprzet::getSprzetText, FrameZamowienieEdit::sprzetFilter);
		cbSprzet.setRenderer(new ComboRendererSprzet(dekoratorSprzet.getFilterTextSupplier()));
		cbSprzet.setBounds(157, 121, 333, 25);
		contentPane.add(cbSprzet);

		// okienko wyboru daty
		SimpleDateFormat ftdatetime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		SimpleDateFormat ftdate = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat fttime = new SimpleDateFormat("HH:mm");
		Properties p = new Properties();
		p.put("text.today", "Dziś");
		p.put("text.month", "Miesiąc");
		p.put("text.year", "Rok");
		UtilDateModel dateModel = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
		dpStart = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		dpStart.getJFormattedTextField().setFont(new Font("Tahoma", Font.BOLD, 13));
		dpStart.setBounds(345, 83, 187, 25);
		contentPane.add(dpStart);
		dpStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date data = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
				System.out.println(
						"Kontrola daty: " + ft.format(data) + " >= " + dpStart.getJFormattedTextField().getText());
				try {
					Date data2 = ft.parse(dpStart.getJFormattedTextField().getText());
					long d1 = data.getTime();
					long d2 = data2.getTime();
					long comp = ((d1 - d2) / (1000 * 60 * 60 * 24)) * -1;
					if (comp >= 0) {
					} else {
						System.out.println("Wybrana data jest wcześniejsza od daty dzisiejszej!");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		dpStart.getJFormattedTextField().setText(ftdate.format(zamowienie.getDataStart()));

		// combobox wyboru godziny startu zajęć
		cbRozpoczecie = new JComboBox<Object>();
		cbRozpoczecie.setModel(new DefaultComboBoxModel<Object>(getTimeList(6, 22, 15).toArray()));
		cbRozpoczecie.setBounds(157, 83, 70, 25);
		contentPane.add(cbRozpoczecie);
		cbRozpoczecie.setSelectedItem(fttime.format(zamowienie.getDataStart()));

		// combobox wyboru godziny końca zajęć
		cbZakonczenie = new JComboBox<Object>();
		cbZakonczenie.setModel(new DefaultComboBoxModel<Object>(getTimeList(6, 22, 15).toArray()));
		cbZakonczenie.setBounds(263, 83, 70, 25);
		contentPane.add(cbZakonczenie);
		cbZakonczenie.setSelectedItem(fttime.format(zamowienie.getDataKoniec()));

		// scroll dla tabelki
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(22, 147, 510, 110);
		contentPane.add(scrollPane);

		// tabelka do dodawania sprzętu
		sprzetyforma = new ArrayList<EntityZamowienieSprzetAlloc>();
		sprzetyforma = zamowienie.getZamowieniaSprzety();
		JTable tableSprzet = new JTable();
		scrollPane.setViewportView(tableSprzet);
		tableSprzet.setModel(new TableModelSprzetForm(sprzetyforma));
		tableSprzet.setCellSelectionEnabled(true);
		tableSprzet.getCellEditor(0, 1).addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingCanceled(ChangeEvent arg0) {
			}

			@Override
			public void editingStopped(ChangeEvent arg0) {
				System.out.println("Próba zmiany wertości z: "
						+ tableSprzet.getValueAt(tableSprzet.getSelectedRow(), tableSprzet.getSelectedColumn()) + " na "
						+ tableSprzet.getCellEditor(tableSprzet.getSelectedRow(), tableSprzet.getSelectedColumn())
								.getCellEditorValue().toString()
						+ " w: wiersz:" + tableSprzet.getSelectedRow() + "|kolumna:" + tableSprzet.getSelectedColumn());
				try {
					int iloscZmieniona = Integer.parseInt(
							tableSprzet.getCellEditor(tableSprzet.getSelectedRow(), tableSprzet.getSelectedColumn())
									.getCellEditorValue().toString());
					if (iloscZmieniona > 0) {
						sprzetyforma.get(tableSprzet.getSelectedRow()).setIloscSprzetu(iloscZmieniona);
						System.out.println("Powodzenie");
					} else {
						JOptionPane.showMessageDialog(null, "Ilość sztuk wybranej pozycji musi być dodatnia!",
								"Błąd wprowadzania danych", JOptionPane.ERROR_MESSAGE);
						System.out.println("Błąd - wartość niepoprawna");
					}

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Ilość sztuk wybranej pozycji musi być liczbą!",
							"Błąd wprowadzania danych", JOptionPane.ERROR_MESSAGE);
					System.out.println("Błąd - niepowodzenie konwersji na liczbę");
				}
			}
		});

		// przycisk dodawania sprzętu do tabelki ze sprzętami
		JButton btnSprzetAdd = new JButton("+");
		btnSprzetAdd.setBounds(491, 121, 41, 25);
		contentPane.add(btnSprzetAdd);
		btnSprzetAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!cbSprzet.getSelectedItem().equals(null)) {
					EntitySprzet sprzet = (EntitySprzet) cbSprzet.getSelectedItem();
					sprzetyforma.add(new EntityZamowienieSprzetAlloc(sprzet, 0));
					tableSprzet.setModel(new TableModelSprzetForm(sprzetyforma));
					Action deletePozycja = new AbstractAction() {
						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent e) {
							int index = Integer.valueOf(e.getActionCommand());
							System.out.println("Usunięto wpis w pozycji: " + index);
							sprzetyforma.remove(index);
							System.out.println("Pozostało pozycji: " + sprzetyforma.size());

							System.out.println("Odświeżam listę i przyciski");
							tableSprzet.setModel(new TableModelSprzetForm(sprzetyforma));
							ButtonColumn btnDeletePozycja = new ButtonColumn(tableSprzet, this, 2);
							btnDeletePozycja.setMnemonic(KeyEvent.VK_D);
						}
					};
					ButtonColumn btnDeletePozycja = new ButtonColumn(tableSprzet, deletePozycja, 2);
					btnDeletePozycja.setMnemonic(KeyEvent.VK_D);
				}
			}
		});

		// definicja dla istniejących pozycji
		Action deletePozycja = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = Integer.valueOf(e.getActionCommand());
				System.out.println("Usunięto wpis w pozycji: " + index);
				sprzetyforma.remove(index);
				System.out.println("Pozostało pozycji: " + sprzetyforma.size());

				System.out.println("Odświeżam listę i przyciski");
				tableSprzet.setModel(new TableModelSprzetForm(sprzetyforma));
				ButtonColumn btnDeletePozycja = new ButtonColumn(tableSprzet, this, 2);
				btnDeletePozycja.setMnemonic(KeyEvent.VK_D);
			}
		};
		ButtonColumn btnDeletePozycja = new ButtonColumn(tableSprzet, deletePozycja, 2);
		btnDeletePozycja.setMnemonic(KeyEvent.VK_D);

		tfRodzajZajec = new JTextField();
		tfRodzajZajec.setBounds(157, 265, 375, 25);
		contentPane.add(tfRodzajZajec);
		tfRodzajZajec.setColumns(10);
		tfRodzajZajec.setText(zamowienie.getRodzajZajec());

		tpUwagi = new JTextPane();
		tpUwagi.setBounds(157, 297, 375, 87);
		contentPane.add(tpUwagi);
		tpUwagi.setText(zamowienie.getUwagi());

		// checkbox dla zamówienia stałego Poniedziałek
		chckStalePn = new JCheckBox("Pn");
		chckStalePn.setEnabled(false);
		chckStalePn.setHorizontalAlignment(SwingConstants.LEFT);
		chckStalePn.setBounds(157, 393, 50, 25);
		contentPane.add(chckStalePn);
		try {
			chckStalePn.setSelected(zamowienie.getZamowienieStale().getPoniedzialek());
		} catch (NullPointerException e) {
		}

		// checkbox dla zamówienia stałego Wtorek
		chckStaleWt = new JCheckBox("Wt");
		chckStaleWt.setEnabled(false);
		chckStaleWt.setHorizontalAlignment(SwingConstants.LEFT);
		chckStaleWt.setBounds(212, 393, 50, 25);
		contentPane.add(chckStaleWt);
		try {
			chckStaleWt.setSelected(zamowienie.getZamowienieStale().getWtorek());
		} catch (NullPointerException e) {
		}

		// checkbox dla zamówienia stałego Środa
		chckStaleSr = new JCheckBox("Śr");
		chckStaleSr.setEnabled(false);
		chckStaleSr.setHorizontalAlignment(SwingConstants.LEFT);
		chckStaleSr.setBounds(263, 393, 50, 25);
		contentPane.add(chckStaleSr);
		try {
			chckStaleSr.setSelected(zamowienie.getZamowienieStale().getSroda());
		} catch (NullPointerException e) {
		}

		// checkbox dla zamówienia stałego Czwartek
		chckStaleCz = new JCheckBox("Cz");
		chckStaleCz.setEnabled(false);
		chckStaleCz.setHorizontalAlignment(SwingConstants.LEFT);
		chckStaleCz.setBounds(317, 393, 50, 25);
		contentPane.add(chckStaleCz);
		try {
			chckStaleCz.setSelected(zamowienie.getZamowienieStale().getCzwartek());
		} catch (NullPointerException e) {
		}

		// checkbox dla zamówienia stałego Piątek
		chckStalePt = new JCheckBox("Pt");
		chckStalePt.setEnabled(false);
		chckStalePt.setHorizontalAlignment(SwingConstants.LEFT);
		chckStalePt.setBounds(371, 393, 50, 25);
		contentPane.add(chckStalePt);
		try {
			chckStalePt.setSelected(zamowienie.getZamowienieStale().getPiatek());
		} catch (NullPointerException e) {
		}

		// checkbox dla zamówienia stałego Sobota
		chckStaleSb = new JCheckBox("Sb");
		chckStaleSb.setEnabled(false);
		chckStaleSb.setHorizontalAlignment(SwingConstants.LEFT);
		chckStaleSb.setBounds(425, 393, 50, 25);
		contentPane.add(chckStaleSb);
		try {
			chckStaleSb.setSelected(zamowienie.getZamowienieStale().getSobota());
		} catch (NullPointerException e) {
		}

		// checkbox dla zamówienia stałego Niedziela
		chckStaleNd = new JCheckBox("Nd");
		chckStaleNd.setEnabled(false);
		chckStaleNd.setHorizontalAlignment(SwingConstants.LEFT);
		chckStaleNd.setBounds(482, 393, 50, 25);
		contentPane.add(chckStaleNd);
		try {
			chckStaleNd.setSelected(zamowienie.getZamowienieStale().getNiedziela());
		} catch (NullPointerException e) {
		}

		// checkbox definiujący zamówienie stałe
		chckbxZamwienieStale = new JCheckBox("Zamówienie stałe");
		chckbxZamwienieStale.setBounds(8, 393, 137, 25);
		contentPane.add(chckbxZamwienieStale);
		try {
			chckbxZamwienieStale.setSelected(!zamowienie.getZamowienieStale().equals(null));
			if (chckbxZamwienieStale.isSelected()) {
				chckStalePn.setEnabled(true);
				chckStaleWt.setEnabled(true);
				chckStaleSr.setEnabled(true);
				chckStaleCz.setEnabled(true);
				chckStalePt.setEnabled(true);
				chckStaleSb.setEnabled(true);
				chckStaleNd.setEnabled(true);
				dpStart.setVisible(false);
			}
		} catch (NullPointerException e) {
		}

		chckbxZamwienieStale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (chckbxZamwienieStale.isSelected()) {
					chckStalePn.setEnabled(true);
					chckStaleWt.setEnabled(true);
					chckStaleSr.setEnabled(true);
					chckStaleCz.setEnabled(true);
					chckStalePt.setEnabled(true);
					chckStaleSb.setEnabled(true);
					chckStaleNd.setEnabled(true);
					dpStart.setVisible(false);
				} else {
					chckStalePn.setEnabled(false);
					chckStaleWt.setEnabled(false);
					chckStaleSr.setEnabled(false);
					chckStaleCz.setEnabled(false);
					chckStalePt.setEnabled(false);
					chckStaleSb.setEnabled(false);
					chckStaleNd.setEnabled(false);
					dpStart.setVisible(true);
					if(dpStart.getJFormattedTextField().getText().equals("01-01-1970"))
						dpStart.getJFormattedTextField().setText(ftdate.format(new Date()));
				}
			}
		});

		// akcja dla przycisku Zmień
		btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try (AppEntityManager em = new AppEntityManager()) {
					if (formValidate(zamowienie)) {
						/*em.getEntityManager().getTransaction().begin();
						em.getEntityManager().merge(zamowienie);
						em.getEntityManager().getTransaction().commit();*/
						repositoryZamowienie.updateZamowienie(zamowienie);
						dispose();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// akcja dla przycisku Anuluj
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

	}

	// filtry szukające dla comboboxów
	private static boolean wykladowcaFilter(EntityWykladowca wyk, String textToFilter) {
		if (textToFilter.isEmpty()) {
			return true;
		}
		return ComboRendererWykladowca.getWykladowcaText(wyk).toLowerCase().contains(textToFilter.toLowerCase());
	}

	private static boolean salaFilter(EntitySala sala, String textToFilter) {
		if (textToFilter.isEmpty()) {
			return true;
		}
		return ComboRendererSala.getSalaText(sala).toLowerCase().contains(textToFilter.toLowerCase());
	}

	private static boolean sprzetFilter(EntitySprzet sprzet, String textToFilter) {
		if (textToFilter.isEmpty()) {
			return true;
		}
		return ComboRendererSprzet.getSprzetText(sprzet).toLowerCase().contains(textToFilter.toLowerCase());
	}

	// generator listy godzin (taki chaotyczny ale działa)
	@SuppressWarnings("deprecation")
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

	private boolean formValidate(EntityZamowienie zamowienie) {
		// EntityZamowienie zamowienie = new EntityZamowienie();
		EntityWykladowca wykladowca = (EntityWykladowca) cbWykladowca.getSelectedItem();
		if (wykladowca.equals(null)) {
			JOptionPane.showMessageDialog(null, "Nie wybrano wykładowcy!", "Błąd wprowadzania danych",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		EntitySala sala = (EntitySala) cbSala.getSelectedItem();
		if (sala.equals(null)) {
			JOptionPane.showMessageDialog(null, "Nie wybrano sali!", "Błąd wprowadzania danych",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Date dataRozpoczecia = null, dataZakonczenia = null;
		SimpleDateFormat ftdatetime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		SimpleDateFormat fttime = new SimpleDateFormat("HH:mm");
		if (cbRozpoczecie.getSelectedItem().equals(cbZakonczenie.getSelectedItem())) {
			JOptionPane.showMessageDialog(null, "Godziny rozpoczęcia i zakończenia nie mogą być takie same!",
					"Błąd wprowadzania danych", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (cbRozpoczecie.getSelectedIndex() > cbZakonczenie.getSelectedIndex()) {
			JOptionPane.showMessageDialog(null, "Godzina rozpoczęcia nie może być późniejsza od godziny rozpoczęcia!",
					"Błąd wprowadzania danych", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (chckbxZamwienieStale.isSelected()) {
			try {
				dataRozpoczecia = fttime.parse((String) cbRozpoczecie.getSelectedItem());
				dataZakonczenia = fttime.parse((String) cbZakonczenie.getSelectedItem());
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas parsowania daty!", "Błąd wewnętrzny",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			if (!chckStalePn.isSelected() && !chckStaleWt.isSelected() && !chckStaleSr.isSelected()
					&& !chckStaleCz.isSelected() && !chckStalePt.isSelected() && !chckStaleSb.isSelected()
					&& !chckStaleNd.isSelected()) {
				JOptionPane.showMessageDialog(null,
						"Przy zamówieniu stałym przynajmniej jeden dzień tygodnia musi zostać zaznaczony!",
						"Błąd wewnętrzny", JOptionPane.ERROR_MESSAGE);
				return false;
			}

		} else {
			if (!dpStart.getJFormattedTextField().getText().equals(null)) {
				try {
					dataRozpoczecia = ftdatetime
							.parse(dpStart.getJFormattedTextField().getText() + " " + cbRozpoczecie.getSelectedItem());
					dataZakonczenia = ftdatetime
							.parse(dpStart.getJFormattedTextField().getText() + " " + cbZakonczenie.getSelectedItem());

					SimpleDateFormat ft2 = new SimpleDateFormat("dd-MM-yyyy");
					Date dzisiaj = new Date();
					Date datazamowienia = ft2.parse(dpStart.getJFormattedTextField().getText());

					long d1 = dzisiaj.getTime();
					long d2 = datazamowienia.getTime();
					long comp = ((d1 - d2) / (1000 * 60 * 60 * 24)) * -1;
					if (comp >= 0) {
					} else {
						JOptionPane.showMessageDialog(null, "Nie można ustawić zamówienia na wcześniejszą datę!",
								"Błąd wprowadzania danych", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				} catch (ParseException e) {
					dataRozpoczecia = null;
					dataZakonczenia = null;
					JOptionPane.showMessageDialog(null, "Data nie może być pusta!", "Błąd wprowadzania danych",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Nie wprowadzono daty, lub są one niepoprawne!",
						"Błąd wprowadzania danych", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		if (sprzetyforma.size() == 0) {
			JOptionPane.showMessageDialog(null, "Lista sprzętów do zamówienia jest pusta!", "Błąd wprowadzania danych",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			for (EntityZamowienieSprzetAlloc sprzet : sprzetyforma) {
				if (sprzet.getIloscSprzetu() == 0) {
					JOptionPane.showMessageDialog(null,
							"Ilość sprzętu dla " + sprzet.getSprzet().getNazwa() + " nie może być zerowa!",
							"Błąd wprowadzania danych", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}

		if (tfRodzajZajec.getText().equals(null)) {
			JOptionPane.showMessageDialog(null, "Rodzaj zajęć nie może być pusty!", "Błąd wprowadzania danych",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (tpUwagi.getText().equals(null)) {
			JOptionPane.showMessageDialog(null, "Uwagi nie mogą być puste!", "Błąd wprowadzania danych",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		System.out.println("Wykładowca: " + wykladowca);
		System.out.println("Sala: " + sala);
		if (chckbxZamwienieStale.isSelected()) {
			System.out.println("Zamówienie stałe: TAK");
			System.out.println("Godzina rozpoczęcia: " + fttime.format(dataRozpoczecia));
			System.out.println("Godzina zakończenia: " + fttime.format(dataZakonczenia));
			zamowienie.setDataStart(dataRozpoczecia);
			zamowienie.setDataKoniec(dataZakonczenia);
			zamowienie.setZamowienieStale(new EntityZamowienieStale(chckStalePn.isSelected(), chckStaleWt.isSelected(),
					chckStaleSr.isSelected(), chckStaleCz.isSelected(), chckStalePt.isSelected(),
					chckStaleSb.isSelected(), chckStaleNd.isSelected()));
		} else {
			System.out.println("Zamówienie stałe: NIE");
			System.out.println("Data rozpoczęcia: " + ftdatetime.format(dataRozpoczecia));
			System.out.println("Data zakończenia: " + ftdatetime.format(dataZakonczenia));
			zamowienie.setDataStart(dataRozpoczecia);
			zamowienie.setDataKoniec(dataZakonczenia);
			zamowienie.setZamowienieStale(null);
		}
		System.out.println("Ilość pozycji w tabelce sprzętów: " + sprzetyforma.size());
		zamowienie.setWykladowca(wykladowca);
		zamowienie.setSala(sala);
		for (EntityZamowienieSprzetAlloc sprzet : sprzetyforma) {
			sprzet.setZamowienie(zamowienie);
		}
		zamowienie.setZamowieniaSprzety(sprzetyforma);
		zamowienie.setRodzajZajec(tfRodzajZajec.getText());
		zamowienie.setUwagi(tpUwagi.getText());

		return true;
	}
}
