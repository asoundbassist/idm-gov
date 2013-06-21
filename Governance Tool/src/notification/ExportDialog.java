/**
 * @author David Garner
 * @version 0.1
 * 
 * A dialog window prompting the user to enter the desired file format to which the search data should be exported
 */

package notification;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class ExportDialog extends JDialog {

	private static final long serialVersionUID = -8043793522679855315L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ExportDialog dialog = new ExportDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ExportDialog() {
		
		this.setAlwaysOnTop(true);
		
		setTitle("Export");
		setBounds(100, 100, 337, 169);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{43, 188, 45, 0};
		gbl_contentPanel.rowHeights = new int[]{46, 23, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		
		contentPanel.setLayout(gbl_contentPanel);
			JLabel exportLabel = new JLabel("How would you like to export the data?");
			GridBagConstraints gbc_exportLabel = new GridBagConstraints();
			gbc_exportLabel.fill = GridBagConstraints.BOTH;
			gbc_exportLabel.insets = new Insets(0, 0, 5, 5);
			gbc_exportLabel.gridx = 1;
			gbc_exportLabel.gridy = 0;
			contentPanel.add(exportLabel, gbc_exportLabel);

			final JRadioButton xlsButton = new JRadioButton(".xls");
			GridBagConstraints gbc_xlsButton = new GridBagConstraints();
			gbc_xlsButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_xlsButton.insets = new Insets(0, 0, 0, 5);
			gbc_xlsButton.gridx = 0;
			gbc_xlsButton.gridy = 1;
			contentPanel.add(xlsButton, gbc_xlsButton);
			
			//Set xls as default extension
			xlsButton.setSelected(true);
			
			final JRadioButton xlsxButton = new JRadioButton(".xlsx");
			GridBagConstraints gbc_xlsxButton = new GridBagConstraints();
			gbc_xlsxButton.anchor = GridBagConstraints.NORTH;
			gbc_xlsxButton.insets = new Insets(0, 0, 0, 5);
			gbc_xlsxButton.gridx = 1;
			gbc_xlsxButton.gridy = 1;
			contentPanel.add(xlsxButton, gbc_xlsxButton);

			final JRadioButton csvButton = new JRadioButton(".csv");
			GridBagConstraints gbc_csvButton = new GridBagConstraints();
			gbc_csvButton.anchor = GridBagConstraints.NORTHWEST;
			gbc_csvButton.gridx = 2;
			gbc_csvButton.gridy = 1;
			contentPanel.add(csvButton, gbc_csvButton);
			
			
			/**
			 * The following three action listeners ensure that the
			 * user cannot select more than one file format at a time
			 * for exportation
			 */
			xlsButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) { 
					if(xlsButton.isSelected()){
						xlsxButton.setSelected(false);
						csvButton.setSelected(false);
					}
				}
			});
			
			xlsxButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) { 
					if(xlsxButton.isSelected()){
						xlsButton.setSelected(false);
						csvButton.setSelected(false);
					}
				}
			});
			
			csvButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) { 
					if(csvButton.isSelected()){
						xlsxButton.setSelected(false);
						xlsButton.setSelected(false);
					}
				}
			});
			
			/**
			 * End selection methods
			 */

			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//TODO Export
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}


