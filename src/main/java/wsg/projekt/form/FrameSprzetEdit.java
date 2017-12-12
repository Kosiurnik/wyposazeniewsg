package wsg.projekt.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import wsg.projekt.data.controller.AppEntityManager;
import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.entity.EntityZamowienieSprzetAlloc;

public class FrameSprzetEdit extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfSprzetNazwa;
	private JTextField tfSprzetIlosc;


	public FrameSprzetEdit(final EntitySprzet sprzet) {
		setModal(true);
		setResizable(false);
		setTitle("Zmień sprzęt");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 310, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImi = new JLabel("Nazwa:");
		lblImi.setBounds(12, 13, 56, 16);
		contentPane.add(lblImi);
		
		tfSprzetNazwa = new JTextField();
		tfSprzetNazwa.setText(sprzet.getNazwa());
		tfSprzetNazwa.setToolTipText("Nazwa sprzętu");
		tfSprzetNazwa.setBounds(93, 10, 201, 22);
		contentPane.add(tfSprzetNazwa);
		tfSprzetNazwa.setColumns(10);
		
		JLabel lblNazwisko = new JLabel("Ilość:");
		lblNazwisko.setBounds(12, 42, 82, 16);
		contentPane.add(lblNazwisko);
		
		tfSprzetIlosc = new JTextField();
		tfSprzetIlosc.setText(sprzet.getMaxIlosc()+"");
		tfSprzetIlosc.setToolTipText("Ilość dysponowanego sprzętu");
		tfSprzetIlosc.setBounds(93, 39, 97, 22);
		contentPane.add(tfSprzetIlosc);
		tfSprzetIlosc.setColumns(10);
		
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
						sprzet.setNazwa(tfSprzetNazwa.getText());
						sprzet.setMaxIlosc(Integer.parseInt(tfSprzetIlosc.getText()));
						sprzet.setDostepnaIlosc(Integer.parseInt(tfSprzetIlosc.getText()));
						sprzet.setZamowieniaSprzety(new ArrayList<EntityZamowienieSprzetAlloc>());
						em.getEntityManager().getTransaction().begin();
						em.getEntityManager().merge(sprzet);
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
		if(tfSprzetNazwa.getText().equals(null)||tfSprzetNazwa.getText().equals("")){
			JOptionPane.showMessageDialog(this,
				    "Nazwa nie może być pusta!",
				    "Błąd formularza",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(tfSprzetIlosc.getText().equals(null)||tfSprzetIlosc.getText().equals("")){
			JOptionPane.showMessageDialog(this,
				    "Ilość nie może być pusta!",
				    "Błąd formularza",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!StringUtils.isNumeric(tfSprzetIlosc.getText())){
			JOptionPane.showMessageDialog(this,
				    "Ilość musi być w formacie liczbowym!",
				    "Błąd formularza",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
