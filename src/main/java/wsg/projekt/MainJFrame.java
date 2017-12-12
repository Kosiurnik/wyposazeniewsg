package wsg.projekt;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wsg.projekt.data.HibernateInit;
import wsg.projekt.data.entity.EntitySala;
import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.entity.EntityWykladowca;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;
import wsg.projekt.data.repository.RepositorySala;
import wsg.projekt.data.repository.RepositorySprzet;
import wsg.projekt.data.repository.RepositoryWykladowca;
import wsg.projekt.data.repository.RepositoryZamowienie;
import wsg.projekt.data.tablemodel.TableModelSala;
import wsg.projekt.data.tablemodel.TableModelSprzet;
import wsg.projekt.data.tablemodel.TableModelWykladowca;
import wsg.projekt.data.tablemodel.TableModelZamowienie;
import wsg.projekt.data.tablerenderer.ButtonColumn;
import wsg.projekt.data.tablerenderer.HtmlTableCell;
import wsg.projekt.form.FrameZamowienieAdd;

/*klasa startowa apki, tworzy pierwsze okienko i ładuje potrzebne rzeczy*/
public class MainJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public EntityManagerFactory emf;
	public EntityManager em;
	private JTable tableZamowienia, tableWykladowcy, tableWyposazenia, tableSale;

	/*Spring - ładuję beany z repozytoriami danych do ładowania ich w listach do modelu wypełniającego tabelki*/
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("wyposazeniewsg-context.xml");
	private RepositoryZamowienie repositoryZamowienie = context.getBean("RepositoryZamowienie",RepositoryZamowienie.class);
	private RepositoryWykladowca repositoryWykladowca = context.getBean("RepositoryWykladowca",RepositoryWykladowca.class);
	private RepositorySala repositorySala = context.getBean("RepositorySala",RepositorySala.class);
	private RepositorySprzet repositorySprzet = context.getBean("RepositorySprzet",RepositorySprzet.class);
	
	public static void main(String[] args) {
		new HibernateInit();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void HibernateTest(){
		/* wpisuje jakieś dane z palca, żeby sprawdzić, czy Hibernate w ogóle działa*/
		EntityManagerFactory emf;
		EntityManager em;
		emf = Persistence.createEntityManagerFactory("wyposazeniewsgDB");
		em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		EntityZamowienie zamowienie = new EntityZamowienie();
		EntitySala sala = new EntitySala("E101","Jakaś sala");
		EntityWykladowca wykladowca = new EntityWykladowca("Hans","Kloss");
		zamowienie.setWykladowca(wykladowca);
		zamowienie.setSala(sala);
		zamowienie.setRodzajZajec("Zajęcia");
		zamowienie.setUwagi("uwagi");
		zamowienie.setDataStart(new Date());
		zamowienie.setDataKoniec(new Date());
		
		
		List<EntitySprzet> sprzety = new ArrayList<EntitySprzet>();
		sprzety.add(new EntitySprzet("Projektor BenQ MH534 DLP 1080p",5));
		sprzety.add(new EntitySprzet("Tłuczek do mięsa",5));
		sprzety.add(new EntitySprzet("Laptop Lenovo IdeaPad Y700-15ISK",10));
		sprzety.add(new EntitySprzet("Wałek do ciasta",2));
		sprzety.add(new EntitySprzet("Pisaki różnego koloru do tablicy",25));
		sprzety.add(new EntitySprzet("Trebusz",1));
		sprzety.add(new EntitySprzet("Zestaw geometryczny duży",5));
		sprzety.add(new EntitySprzet("Monitor LED 27' Dell U2715H HDMI",10));
		for(EntitySprzet sprzet : sprzety)	em.persist(sprzet);
		
		
		EntityZamowienieSprzetAlloc wpis1 = new EntityZamowienieSprzetAlloc();
		EntityZamowienieSprzetAlloc wpis2 = new EntityZamowienieSprzetAlloc();
		wpis1.setSprzet(sprzety.get(1));	wpis1.setIloscSprzetu(4);	wpis1.setZamowienie(zamowienie);
		wpis2.setSprzet(sprzety.get(3));	wpis2.setIloscSprzetu(1);	wpis2.setZamowienie(zamowienie);
		zamowienie.getZamowieniaSprzety().add(wpis1);
		zamowienie.getZamowieniaSprzety().add(wpis2);
		
		
		System.out.println(zamowienie.getZamowieniaSprzety().get(0).getZamowienie().getRodzajZajec());
		
		em.persist(sala);
		em.persist(wykladowca);
		em.persist(zamowienie);
		em.getTransaction().commit();
	
		em.close();
		emf.close();
	}
	
	public MainJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 583);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		/*Tworzę zakładkowe menu górne*/
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(tabbedPane);
		
		/*Tworzę zakładki i tabelki*/
		JScrollPane scrollPaneZamowienia = new JScrollPane();
		tabbedPane.addTab("Lista zamówień", null, scrollPaneZamowienia, null);
		
		tableZamowienia = new JTable();
		scrollPaneZamowienia.setViewportView(tableZamowienia);
		
		JScrollPane scrollPaneWykladowcy = new JScrollPane();
		tabbedPane.addTab("Lista wykładowców", null, scrollPaneWykladowcy, null);
		
		tableWykladowcy = new JTable();
		scrollPaneWykladowcy.setViewportView(tableWykladowcy);
		
		JScrollPane scrollPaneWyposazenia = new JScrollPane();
		tabbedPane.addTab("Lista wyposażenia", null, scrollPaneWyposazenia, null);
		
		tableWyposazenia = new JTable();
		scrollPaneWyposazenia.setViewportView(tableWyposazenia);
		
		JScrollPane scrollPaneSale = new JScrollPane();
		tabbedPane.addTab("Lista sal", null, scrollPaneSale, null);
		
		tableSale = new JTable();
		scrollPaneSale.setViewportView(tableSale);
		
		/*Uzupełnianie tabel*/
		tableZamowienia.setModel(new TableModelZamowienie(repositoryZamowienie));
		tableWykladowcy.setModel(new TableModelWykladowca(repositoryWykladowca));
		tableSale.setModel(new TableModelSala(repositorySala));
		tableWyposazenia.setModel(new TableModelSprzet(repositorySprzet));
		
		/*Kalendarz...*/
		JPanel paneKalendarz = new JPanel();
		tabbedPane.addTab("Kalendarz obłożenia", null, paneKalendarz, null);
		
		
		/*Menu prawe z przyciskami*/
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnZamowienie = new JButton("Dodaj zamówienie");
		btnZamowienie.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FrameZamowienieAdd().setVisible(true);
			}
			
		});
		panel.add(btnZamowienie);
		
		JButton btnWykladowca = new JButton("Dodaj wykładowcę");
		panel.add(btnWykladowca);
		
		JButton btnWyposazenie = new JButton("Dodaj wyposażenie");
		panel.add(btnWyposazenie);
		
		JButton btnSala = new JButton("Dodaj salę");
		panel.add(btnSala);
		
		
		//HibernateTest();
		
		/*Akcje dla przycisków tabelki zamówień*/
		Action deleteZamowienie = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelZamowienie)tableZamowienia.getModel()).removeRow((int)tableZamowienia.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		
		Action editZamowienie = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelZamowienie)tableZamowienia.getModel()).editRow((int)tableZamowienia.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		ButtonColumn btnDeleteZamowienie = new ButtonColumn(tableZamowienia, deleteZamowienie, 8);
		btnDeleteZamowienie.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditZamowienie = new ButtonColumn(tableZamowienia, editZamowienie, 7);
		btnEditZamowienie.setMnemonic(KeyEvent.VK_D);
		tableZamowienia.getColumnModel().getColumn(3).setCellRenderer(new HtmlTableCell());
		
		
		/*Akcje dla przycisków tabelki wykładowców*/
		Action deleteWykladowca = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelWykladowca)tableWykladowcy.getModel()).removeRow((int)tableWykladowcy.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		
		Action editWykladowca = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelWykladowca)tableWykladowcy.getModel()).editRow((int)tableWykladowcy.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		ButtonColumn btnDeleteWykladowca = new ButtonColumn(tableWykladowcy, deleteWykladowca, 3);
		btnDeleteWykladowca.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditWykladowca = new ButtonColumn(tableWykladowcy, editWykladowca, 4);
		btnEditWykladowca.setMnemonic(KeyEvent.VK_D);
		
		
		/*Akcje dla przycisków tabelki sal*/
		Action deleteSala = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelSala)tableSale.getModel()).removeRow((int)tableSale.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		
		Action editSala = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelSala)tableSale.getModel()).editRow((int)tableSale.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		ButtonColumn btnDeleteSala = new ButtonColumn(tableSale, deleteSala, 3);
		btnDeleteSala.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditSala = new ButtonColumn(tableSale, editSala, 4);
		btnEditSala.setMnemonic(KeyEvent.VK_D);
		
		
		/*Akcje dla przycisków tabelki sprzętów*/
		Action deleteSprzet = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelSprzet)tableWyposazenia.getModel()).removeRow((int)tableWyposazenia.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		
		Action editSprzet = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
		        ((TableModelSprzet)tableWyposazenia.getModel()).editRow((int)tableWyposazenia.getValueAt(Integer.valueOf(e.getActionCommand()), 0));
		    }
		};
		ButtonColumn btnDeleteSprzet = new ButtonColumn(tableWyposazenia, deleteSprzet, 4);
		btnDeleteSprzet.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditSprzet = new ButtonColumn(tableWyposazenia, editSprzet, 5);
		btnEditSprzet.setMnemonic(KeyEvent.VK_D);
	}

}
