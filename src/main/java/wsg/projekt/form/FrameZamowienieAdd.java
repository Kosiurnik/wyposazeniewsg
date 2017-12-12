package wsg.projekt.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

public class FrameZamowienieAdd extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameZamowienieAdd frame = new FrameZamowienieAdd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public FrameZamowienieAdd() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		toFront();
		requestFocus();
		setTitle("Nowe Zamówienie");
		setResizable(false);
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWykadowcaZlecajcy = new JLabel("Wykładowca zlecający:");
		lblWykadowcaZlecajcy.setBounds(12, 13, 133, 16);
		contentPane.add(lblWykadowcaZlecajcy);
		
		JList<?> listWykladowca = new JList<Object>();
		listWykladowca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listWykladowca.setBounds(54, 67, 1, 1);
		contentPane.add(listWykladowca);
	}
}
