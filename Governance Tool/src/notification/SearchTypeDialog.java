package notification;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class SearchTypeDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SearchTypeDialog dialog = new SearchTypeDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SearchTypeDialog() {
		
		this.setAlwaysOnTop(true);
		
		setAlwaysOnTop(true);
		setTitle("Search Error");
		setBounds(100, 100, 258, 172);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblYouMustSelect = new JLabel("You must select at least one");
		lblYouMustSelect.setBounds(57, 51, 200, 14);
		contentPanel.add(lblYouMustSelect);
		
		JLabel lblSearchType = new JLabel("search type!");
		lblSearchType.setBounds(93, 75, 135, 14);
		contentPanel.add(lblSearchType);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			GridBagLayout gbl_buttonPane = new GridBagLayout();
			gbl_buttonPane.columnWidths = new int[]{112, 49, 0};
			gbl_buttonPane.rowHeights = new int[]{23, 0};
			gbl_buttonPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_buttonPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			buttonPane.setLayout(gbl_buttonPane);
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) { 
					dispose();
				}

				
			});
			
			okButton.setActionCommand("OK");
			GridBagConstraints gbc_okButton = new GridBagConstraints();
			gbc_okButton.fill = GridBagConstraints.BOTH;
			gbc_okButton.gridx = 1;
			gbc_okButton.gridy = 0;
			buttonPane.add(okButton, gbc_okButton);
			//close on "enter"
			getRootPane().setDefaultButton(okButton);
		}
	}
}
