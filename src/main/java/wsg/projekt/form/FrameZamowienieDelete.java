package wsg.projekt.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntityWykladowca;
import wsg.projekt.data.entity.EntityZamowienie;

import javax.swing.JLabel;
import java.awt.Font;

public class FrameZamowienieDelete extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public FrameZamowienieDelete(final EntityZamowienie zamowienie) {
		setModal(true);
		setResizable(false);
		setTitle("Usuń zamówienie");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 310, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAccept = new JButton("Usuń");
		btnAccept.setBounds(12, 69, 97, 25);
		contentPane.add(btnAccept);
		
		JButton btnCancel = new JButton("Anuluj");
		btnCancel.setBounds(197, 69, 97, 25);
		contentPane.add(btnCancel);
		
		JLabel lblCzyNaPewno = new JLabel("Czy na pewno chcesz usunąć wpis:");
		lblCzyNaPewno.setBounds(12, 13, 282, 16);
		contentPane.add(lblCzyNaPewno);
		
		JLabel lblDasdad = new JLabel("Zamówienie: "+zamowienie.getRodzajZajec());
		lblDasdad.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDasdad.setBounds(12, 40, 282, 16);
		contentPane.add(lblDasdad);
		
		btnAccept.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try(AppEntityManager em = new AppEntityManager()){
						em.getEntityManager().getTransaction().begin();
						em.getEntityManager().remove(em.getEntityManager().find(EntityZamowienie.class, zamowienie.getZamowienieID()));
						em.getEntityManager().getTransaction().commit();
						dispose();
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

}
