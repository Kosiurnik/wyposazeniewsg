package wsg.projekt.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntityWykladowca;

public class FrameWykladowcaEdit extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField tfWykladowcaImie;
	private JTextField tfWykladowcaNazwisko;


	public FrameWykladowcaEdit(final EntityWykladowca wykladowca) {
		setModal(true);
		setResizable(false);
		setTitle("Zmień wykładowcę");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 310, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImi = new JLabel("Imię:");
		lblImi.setBounds(12, 13, 56, 16);
		contentPane.add(lblImi);
		
		tfWykladowcaImie = new JTextField();
		tfWykladowcaImie.setText(wykladowca.getImie());
		tfWykladowcaImie.setToolTipText("Imię wykładowcy");
		tfWykladowcaImie.setBounds(93, 10, 201, 22);
		contentPane.add(tfWykladowcaImie);
		tfWykladowcaImie.setColumns(10);
		
		JLabel lblNazwisko = new JLabel("Nazwisko:");
		lblNazwisko.setBounds(12, 42, 82, 16);
		contentPane.add(lblNazwisko);
		
		tfWykladowcaNazwisko = new JTextField();
		tfWykladowcaNazwisko.setText(wykladowca.getNazwisko());
		tfWykladowcaNazwisko.setToolTipText("Nazwisko wykładowcy");
		tfWykladowcaNazwisko.setBounds(93, 39, 201, 22);
		contentPane.add(tfWykladowcaNazwisko);
		tfWykladowcaNazwisko.setColumns(10);
		
		JButton btnAccept = new JButton("Zmień");
		btnAccept.setBounds(12, 74, 97, 25);
		contentPane.add(btnAccept);
		
		JButton btnCancel = new JButton("Anuluj");
		btnCancel.setBounds(197, 74, 97, 25);
		contentPane.add(btnCancel);
		
		btnAccept.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try(AppEntityManager em = new AppEntityManager()){
					if(formValidate()){
						wykladowca.setImie(tfWykladowcaImie.getText());
						wykladowca.setNazwisko(tfWykladowcaNazwisko.getText());
						em.getEntityManager().getTransaction().begin();
						em.getEntityManager().merge(wykladowca);
						em.getEntityManager().getTransaction().commit();
						dispose();
					}
					
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
	
	private boolean formValidate(){
		if(tfWykladowcaImie.getText().equals(null)||tfWykladowcaImie.getText().equals("")){
			JOptionPane.showMessageDialog(this,
				    "Imię nie może być puste!",
				    "Błąd formularza",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(tfWykladowcaNazwisko.getText().equals(null)||tfWykladowcaNazwisko.getText().equals("")){
			JOptionPane.showMessageDialog(this,
				    "Nazwisko nie może być puste!",
				    "Błąd formularza",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
