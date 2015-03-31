package labs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProgressDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JProgressBar progressBar ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProgressDialog dialog = new ProgressDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ProgressDialog() {
		setBounds(100, 100, 450, 151);
		final int MAX = 1000;
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			 progressBar = new JProgressBar();
			progressBar.setBounds(120, 36, 260, 33);
			progressBar.setStringPainted(true);
		
	
		
			progressBar.setMinimum(0);
			progressBar.setMaximum(MAX);
			progressBar.setStringPainted(true);
			contentPanel.add(progressBar);
		}
		{
			JLabel label = new JLabel("image");
			label.setBounds(38, 63, 28, 14);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Copy ");
			label.setBounds(129, 11, 28, 14);
			contentPanel.add(label);
		}
		{
			JButton button = new JButton("Cancel");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					// güzel ornek
					for (int i = 0; i <= MAX; i++) {
						final int currentValue = i;
						if (i == MAX) {
							progressBar.setValue(currentValue);
						/*	JOptionPane.showMessageDialog(null, " oldu  ", "oldu",
									JOptionPane.WARNING_MESSAGE);*/
						
							
							

						}

						// java.lang.Thread.sleep(100);
					}
				}
			});
			button.setBounds(195, 80, 65, 23);
			contentPanel.add(button);
		}
	}

}
