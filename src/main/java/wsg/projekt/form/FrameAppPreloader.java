package wsg.projekt.form;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

public class FrameAppPreloader extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea txLogger;
	
	public FrameAppPreloader() {
		setTitle("System zarządzania wyposażeniem WSG");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbladowanieAplikacji = new JLabel("Ładowanie aplikacji...");
		lbladowanieAplikacji.setBounds(12, 13, 185, 16);
		contentPane.add(lbladowanieAplikacji);
		
		txLogger = new JTextArea();
		txLogger.setText("Ładowanie aplikacji...");
		txLogger.setEnabled(false);
		txLogger.setLineWrap(true);
		txLogger.setBounds(12, 42, 420, 146);
		contentPane.add(txLogger);
		
		JScrollPane sp = new JScrollPane(txLogger);
		sp.setBounds(12, 42, 420, 146);
		contentPane.add(sp);

	}
	
	public void appendMessage(String text){
		txLogger.append("\n"+text);
	}
}
