package notification;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class InvalidOperandDialog extends JDialog {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5054168382626967792L;
	private final JPanel contentPanel = new JPanel();
	private JLabel enterLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InvalidSearchDialog dialog = new InvalidSearchDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InvalidOperandDialog() {
		setTitle("Search Error");
		setBounds(100, 100, 248, 171);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		{
			enterLabel = new JLabel("Invalid Search");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, enterLabel, 36, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, enterLabel, 75, SpringLayout.WEST, contentPanel);
			contentPanel.add(enterLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
		}
	}

}
