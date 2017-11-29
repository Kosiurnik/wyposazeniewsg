package wsg.projekt;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wsg.projekt.data.HibernateInit;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.repository.RepositoryZamowienie;
import wsg.projekt.data.tablemodel.TableModelZamowienie;

public class MainJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public EntityManagerFactory emf;
	public EntityManager em;
	private JTable table;

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("wyposazeniewsg-context.xml");
	private RepositoryZamowienie repository = context.getBean("RepositoryZamowienie",RepositoryZamowienie.class);
	
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

	private void initHibernate(){
		//List<EntityZamowienie> zamowienia = repository.getAllZamowienie();
		//System.out.println(zamowienia.get(0).getWykladowca().getImie());
		
		table.setModel(new TableModelZamowienie());
	}
	
	public MainJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 497);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		initHibernate();
	}

}
