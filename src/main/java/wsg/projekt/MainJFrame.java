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
import wsg.projekt.form.FrameAppPreloader;
import wsg.projekt.form.FrameSalaAdd;
import wsg.projekt.form.FrameSalaDelete;
import wsg.projekt.form.FrameSalaEdit;
import wsg.projekt.form.FrameSprzetAdd;
import wsg.projekt.form.FrameSprzetDelete;
import wsg.projekt.form.FrameSprzetEdit;
import wsg.projekt.form.FrameWykladowcaAdd;
import wsg.projekt.form.FrameWykladowcaDelete;
import wsg.projekt.form.FrameWykladowcaEdit;
import wsg.projekt.form.FrameZamowienieAdd;

/*klasa startowa apki, tworzy pierwsze okienko i ładuje potrzebne rzeczy*/
public class MainJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public EntityManagerFactory emf;
	public EntityManager em;
	private JTable tableZamowienia, tableWykladowcy, tableWyposazenia, tableSale;
	static JFrame preloader;
	
	/*Spring - ładuję beany z repozytoriami danych do ładowania ich w listach do modelu wypełniającego tabelki*/
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("wyposazeniewsg-context.xml");
	private RepositoryZamowienie repositoryZamowienie = context.getBean("RepositoryZamowienie",RepositoryZamowienie.class);
	private RepositoryWykladowca repositoryWykladowca = context.getBean("RepositoryWykladowca",RepositoryWykladowca.class);
	private RepositorySala repositorySala = context.getBean("RepositorySala",RepositorySala.class);
	private RepositorySprzet repositorySprzet = context.getBean("RepositorySprzet",RepositorySprzet.class);
	
	public static void main(String[] args) {
		preloader = new FrameAppPreloader();
		preloader.setVisible(true);
		((FrameAppPreloader) preloader).appendMessage("Ładowanie Hibernate i łączenie z bazą danych...");
		try{
			new HibernateInit();
		}catch(Exception e){
			((FrameAppPreloader) preloader).appendMessage("Błąd ładowania Hibernate");
			((FrameAppPreloader) preloader).appendMessage(e.getMessage());
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame();
					frame.setVisible(true);
					preloader.dispose();
				} catch (Exception e) {
					((FrameAppPreloader) preloader).appendMessage("Coś poszło nie tak.");
					((FrameAppPreloader) preloader).appendMessage(e.getMessage());
				}
			}
		});
	}
	/* wpisuje jakieś dane z palca, żeby sprawdzić, czy Hibernate w ogóle działa*/
	private void HibernateTest(){

		/*EntityManagerFactory emf;
		EntityManager em;
		emf = Persistence.createEntityManagerFactory("wyposazeniewsgDB");
		em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		EntityZamowienie zamowienie = new EntityZamowienie();
		EntitySala sala = new EntitySala("E101","Jakaś sala");
		EntityWykladowca wykladowca = new EntityWykladowca("Hans","Kloss");
		zamowienie.setWykladowca(wykladowca);
		zamowienie.setSala(sala);
		zamowienie.setRodzajZajec("Zajęcia2");
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
		
		//em.persist(sala);
		//em.persist(wykladowca);
		em.persist(zamowienie);
		em.getTransaction().commit();
	
		em.close();
		emf.close();*/
	}
	
	public MainJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 583);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle("System zarządzania wyposażeniem WSG");
		
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
		
		/*Przeładowuję tabelki*/
		tableZamowieniaReload();
		tableWykladowcyReload();
		tableSaleReload();
		tableWyposazeniaReload();
		/*Kalendarz... Ta...*/
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
				tableZamowieniaReload();
			}
			
		});
		panel.add(btnZamowienie);
		
		JButton btnWykladowca = new JButton("Dodaj wykładowcę");
		btnWykladowca.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FrameWykladowcaAdd().setVisible(true);
				tableWykladowcyReload();
			}
			
		});
		panel.add(btnWykladowca);
		
		JButton btnWyposazenie = new JButton("Dodaj wyposażenie");
		btnWyposazenie.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FrameSprzetAdd().setVisible(true);
				tableWyposazeniaReload();
			}
			
		});
		panel.add(btnWyposazenie);
		
		JButton btnSala = new JButton("Dodaj salę");
		btnSala.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FrameSalaAdd().setVisible(true);
				tableSaleReload();
			}
			
		});
		panel.add(btnSala);
		//HibernateTest();
	}
	
	private void tableZamowieniaReload(){
		tableZamowienia.setModel(new TableModelZamowienie(repositoryZamowienie));
		/*Akcje dla przycisków tabelki zamówień*/
		Action deleteZamowienie = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				//new FrameZamowieniaDelete(repositoryZamowienie.getZamowienieByID((int)tableZamowienia.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				//tableZamowieniaReload();
		    }
		};
		
		Action editZamowienie = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				//new FrameZamowieniaEdit(repositoryZamowienie.getZamowienieByID((int)tableZamowienia.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				//tableZamowieniaReload();
		    }
		};
		ButtonColumn btnDeleteZamowienie = new ButtonColumn(tableZamowienia, deleteZamowienie, 8);
		btnDeleteZamowienie.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditZamowienie = new ButtonColumn(tableZamowienia, editZamowienie, 7);
		btnEditZamowienie.setMnemonic(KeyEvent.VK_D);
		tableZamowienia.getColumnModel().getColumn(3).setCellRenderer(new HtmlTableCell());
		tableZamowienia.getColumnModel().getColumn(4).setCellRenderer(new HtmlTableCell());
		tableZamowienia.getColumnModel().getColumn(0).setMaxWidth(90);
		tableZamowienia.getColumnModel().getColumn(7).setMaxWidth(90);
		tableZamowienia.getColumnModel().getColumn(8).setMaxWidth(90);
	}
	private void tableWykladowcyReload(){
		tableWykladowcy.setModel(new TableModelWykladowca(repositoryWykladowca));
		/*Akcje dla przycisków tabelki wykładowców*/
		Action deleteWykladowca = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				new FrameWykladowcaDelete(repositoryWykladowca.getWykladowcaByID((int)tableWykladowcy.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				tableWykladowcyReload();
		    }
		};
		
		Action editWykladowca = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				new FrameWykladowcaEdit(repositoryWykladowca.getWykladowcaByID((int)tableWykladowcy.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				tableWykladowcyReload();
		    }
		};
		ButtonColumn btnDeleteWykladowca = new ButtonColumn(tableWykladowcy, deleteWykladowca, 4);
		btnDeleteWykladowca.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditWykladowca = new ButtonColumn(tableWykladowcy, editWykladowca, 3);
		btnEditWykladowca.setMnemonic(KeyEvent.VK_D);
		tableWykladowcy.getColumnModel().getColumn(0).setMaxWidth(90);
		tableWykladowcy.getColumnModel().getColumn(3).setMaxWidth(90);
		tableWykladowcy.getColumnModel().getColumn(4).setMaxWidth(90);
		
	}
	private void tableSaleReload(){
		tableSale.setModel(new TableModelSala(repositorySala));
		/*Akcje dla przycisków tabelki sal*/
		Action deleteSala = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				new FrameSalaDelete(repositorySala.getSalaByID((int)tableSale.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				tableSaleReload();
		    }
		};
		
		Action editSala = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				new FrameSalaEdit(repositorySala.getSalaByID((int)tableSale.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				tableSaleReload();
		    }
		};
		ButtonColumn btnDeleteSala = new ButtonColumn(tableSale, deleteSala, 4);
		btnDeleteSala.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditSala = new ButtonColumn(tableSale, editSala, 3);
		btnEditSala.setMnemonic(KeyEvent.VK_D);
		tableSale.getColumnModel().getColumn(2).setCellRenderer(new HtmlTableCell());
		tableSale.getColumnModel().getColumn(0).setMaxWidth(90);
		tableSale.getColumnModel().getColumn(3).setMaxWidth(90);
		tableSale.getColumnModel().getColumn(4).setMaxWidth(90);
		
	}
	private void tableWyposazeniaReload(){
		tableWyposazenia.setModel(new TableModelSprzet(repositorySprzet));	
		/*Akcje dla przycisków tabelki sprzętów*/
		Action deleteSprzet = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				new FrameSprzetDelete(repositorySprzet.getSprzetByID((int)tableWyposazenia.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				tableWyposazeniaReload();
		    }
		};
		
		Action editSprzet = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e)
		    {
				new FrameSprzetEdit(repositorySprzet.getSprzetByID((int)tableWyposazenia.getValueAt(Integer.valueOf(e.getActionCommand()), 0))).setVisible(true);
				tableWyposazeniaReload();
		    }
		};
		ButtonColumn btnDeleteSprzet = new ButtonColumn(tableWyposazenia, deleteSprzet, 5);
		btnDeleteSprzet.setMnemonic(KeyEvent.VK_D);
		ButtonColumn btnEditSprzet = new ButtonColumn(tableWyposazenia, editSprzet, 4);
		btnEditSprzet.setMnemonic(KeyEvent.VK_D);
		tableWyposazenia.getColumnModel().getColumn(0).setMaxWidth(90);
		tableWyposazenia.getColumnModel().getColumn(4).setMaxWidth(90);
		tableWyposazenia.getColumnModel().getColumn(5).setMaxWidth(90);
		
	}

}
