package wsg.projekt;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import wsg.projekt.data.entity.EntitySala;
import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.entity.EntityWykladowca;
import wsg.projekt.data.entity.EntityZamowienie;

public class MainJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public EntityManagerFactory emf;
	public EntityManager em;

	public static void main(String[] args) {
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

	private void initHibernate(){
		emf = Persistence.createEntityManagerFactory("wyposazeniewsgDB");
		em = emf.createEntityManager();
		
		EntitySprzet sprzet1 = new EntitySprzet();
			sprzet1.setNazwa("Tłuczek do mięsa");
			sprzet1.setIlosc(5);
		EntitySprzet sprzet2 = new EntitySprzet();
			sprzet2.setNazwa("Wałek do ciasta");
			sprzet2.setIlosc(2);

		EntityWykladowca wykladowca1 = new EntityWykladowca();
			wykladowca1.setImie("Jan");
			wykladowca1.setNazwisko("Kowalski");
			
		EntitySala sala1 = new EntitySala();
			sala1.setNumerSali("E-201");
			sala1.setOpis("Piwnica");
		
		List<EntitySprzet> sprzety = new ArrayList<EntitySprzet>();
			sprzety.add(sprzet1);
			sprzety.add(sprzet2);
		
		EntityZamowienie zamowienie = new EntityZamowienie();
			zamowienie.setWykladowca(wykladowca1);
			zamowienie.setSprzety(sprzety);
			zamowienie.setSala(sala1);
			zamowienie.setDataStart(new Date());
			zamowienie.setDataKoniec(new Date());
			zamowienie.setRodzajZajec("Metody Dyscyplinarne");
			zamowienie.setUwagi("Dostarczyć pod salę");
			
		em.getTransaction().begin();
			em.persist(sprzet1);
			em.persist(sprzet2);
			em.persist(wykladowca1);
			em.persist(sala1);
			em.persist(zamowienie);
		em.getTransaction().commit();
		
		
		em.close();
		emf.close();
	}
	
	public MainJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		initHibernate();
	}

}
