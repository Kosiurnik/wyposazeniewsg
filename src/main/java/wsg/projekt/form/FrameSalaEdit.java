package wsg.projekt.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntitySala;

public class FrameSalaEdit extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfSalaNumer;
	private JTextArea tfSalaOpis;
	
	public FrameSalaEdit(final EntitySala sala) {
		setModal(true);
		setResizable(false);
		setTitle("Edytuj salę");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 310, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImi = new JLabel("Numer sali:");
		lblImi.setBounds(12, 13, 82, 16);
		contentPane.add(lblImi);
		
		tfSalaNumer = new JTextField();
		tfSalaNumer.setText(sala.getNumerSali());
		tfSalaNumer.setToolTipText("Numer sali");
		tfSalaNumer.setBounds(93, 10, 201, 22);
		contentPane.add(tfSalaNumer);
		tfSalaNumer.setColumns(10);
		
		JLabel lblNazwisko = new JLabel("Opis:");
		lblNazwisko.setBounds(12, 42, 82, 16);
		contentPane.add(lblNazwisko);
		
		tfSalaOpis = new JTextArea();
		tfSalaOpis.setText(sala.getOpis());
		tfSalaOpis.setLineWrap(true);
		tfSalaOpis.setToolTipText("Opis sali");
		tfSalaOpis.setBounds(14, 15, 201, 55);
		contentPane.add(tfSalaOpis);
		JScrollPane sp = new JScrollPane(tfSalaOpis);
		sp.setBounds(93, 42, 201, 55);
		contentPane.add(sp);
		tfSalaOpis.setColumns(10);
		
		JButton btnAccept = new JButton("Zmień");
		btnAccept.setBounds(12, 107, 97, 25);
		contentPane.add(btnAccept);
		
		JButton btnCancel = new JButton("Anuluj");
		btnCancel.setBounds(197, 107, 97, 25);
		contentPane.add(btnCancel);
		
		btnAccept.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try(AppEntityManager em = new AppEntityManager()){
					if(formValidate()){
						sala.setNumerSali(tfSalaNumer.getText());
						sala.setOpis(tfSalaOpis.getText());
						em.getEntityManager().getTransaction().begin();
						em.getEntityManager().merge(sala);
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
		if(tfSalaNumer.getText().equals(null)||tfSalaNumer.getText().equals("")){
			JOptionPane.showMessageDialog(this,
				    "Numer sali nie może być puste!",
				    "Błąd formularza",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(tfSalaOpis.getText().equals(null)||tfSalaOpis.getText().equals("")){
			JOptionPane.showMessageDialog(this,
				    "Opis sali nie może być puste!",
				    "Błąd formularza",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
