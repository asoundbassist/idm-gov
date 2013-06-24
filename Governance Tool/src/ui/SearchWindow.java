/**
 * @author David Garner
 * @version 0.1
 * 
 * Default search window for governance tool. Contains options for search type and object type for looking through a STEP database.
 */

//TODO Figure out options for export button (specifically file extensions and how to represent the choice

package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import notification.ExportDialog;
import notification.InvalidLogin;
import notification.InvalidSearchDialog;
import notification.SearchTypeDialog;
import function.ContextMenuMouseListener;
import function.Query;
import function.Util;
//import notification.ConnectionDialog; //TODO Utilize connection dialog after database stuff is set up





public class SearchWindow extends JFrame{

	
	/**
	 *Generated serial version UID 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField searchBox;
	private ArrayList<JCheckBox> checkList = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> searchTypeList = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> objectTypeList = new ArrayList<JCheckBox>();
	private Query q;
	
    // Declare the JDBC objects.
    static Connection con = null;
    static Statement stmt = null;
    
    /*
     * This variable determines whether a search has been performed.
     * If no search has been performed, the export button will be disabled.
     */
    //TODO The export button is temporarily disabled. This will be resolved in a future update
    @SuppressWarnings("unused")
	private boolean searchPerformed = false;
	
	//Passed into search function; determines the nature of the search (exact/partial)
	public boolean isPartial = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String connectionUrl="jdbc:sqlserver://CPPCPWCV62W7C:1433;integratedSecurity=true;";
				
		        try {
		            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            con = DriverManager.getConnection(connectionUrl);
					
		            SearchWindow frame = new SearchWindow();
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					frame.setVisible(true);
					
					frame.addWindowListener(new WindowListener(){

						public void windowClosed(WindowEvent arg0) {
							Query.closeConnection(con, stmt);
						}

						public void windowActivated(WindowEvent arg0) {}
						public void windowClosing(WindowEvent arg0) {}
						public void windowDeactivated(WindowEvent arg0) {}
						public void windowDeiconified(WindowEvent arg0) {}
						public void windowIconified(WindowEvent arg0) {}
						public void windowOpened(WindowEvent arg0) {}
						
					});
		        }

		        // if we receive invalid login data, throw up an error window
		        catch (Exception e1) {
		        	
		            InvalidLogin invalid = new InvalidLogin();
		            invalid.setVisible(true);
		            
		        	e1.printStackTrace();
		        }
		        
		        
				try {
					//TODO The initialization of the search window was here.
					// Replace if needed
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public SearchWindow() {		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SearchWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		setTitle("Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		searchBox = new JTextField();

		panel.add(searchBox);
		searchBox.setColumns(35);
		
		 
		searchBox.addMouseListener(new ContextMenuMouseListener());
		
		//Open context menu when user right-clicks on the search box
				
				
////////////////Begin tedious addition of visual elements////////////////
		
		final JButton btnSearch = new JButton("Search");
		panel.add(btnSearch);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel label = new JLabel("");
		panel_2.add(label);
		
		JLabel label_1 = new JLabel("");
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("");
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel("");
		panel_2.add(label_3);
		
		JLabel searchTypeLabel = new JLabel("Search Type:");
		panel_2.add(searchTypeLabel);
		
		final JCheckBox lovValueBox = new JCheckBox("LOV Value");
		panel_2.add(lovValueBox);
		
		final JCheckBox guidBox = new JCheckBox("GUID");
		panel_2.add(guidBox);
		
		final JCheckBox nameBox = new JCheckBox("Name");
		panel_2.add(nameBox);
		
		JLabel label_4 = new JLabel("");
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("");
		panel_2.add(label_5);
		
		JLabel label_6 = new JLabel("");
		panel_2.add(label_6);
		
		JLabel label_7 = new JLabel("");
		panel_2.add(label_7);
		
		JLabel label_8 = new JLabel("");
		panel_2.add(label_8);
		
		JLabel label_9 = new JLabel("");
		panel_2.add(label_9);
		
		JLabel label_10 = new JLabel("");
		panel_2.add(label_10);
		
		JLabel label_11 = new JLabel("");
		panel_2.add(label_11);
		
		JLabel lblObjectType = new JLabel("Object Type:");
		panel_2.add(lblObjectType);
		
		final JCheckBox collectionNodeBox = new JCheckBox("Collection Node");
		panel_2.add(collectionNodeBox);
		
		final JCheckBox attributeBox = new JCheckBox("Attribute");
		panel_2.add(attributeBox);
		
		final JCheckBox lovBox = new JCheckBox("LOV");
		panel_2.add(lovBox);
		
		JLabel label_12 = new JLabel("");
		panel_2.add(label_12);
		
		final JCheckBox attributeGroupBox = new JCheckBox("Attribute Group");
		panel_2.add(attributeGroupBox);
		
		final JCheckBox viewGroupBox = new JCheckBox("View Group");
		panel_2.add(viewGroupBox);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{85, 129, 67, 149, 63, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

////////////////End tedious addition of visual elements////////////////
		
		
		
		
		/*
		 * If user has selected the LOV Value search type,
		 * uncheck the GUID and Name search types and 
		 * disable them until the box is unchecked
		 * 
		 * Otherwise, in the event that the user deselects
		 * the LOV Value search type, re-enable the check boxes
		 */
		
		lovValueBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					guidBox.setSelected(false);
					nameBox.setSelected(false);
					
					guidBox.setEnabled(false);
					nameBox.setEnabled(false);
					
					collectionNodeBox.setSelected(false);
					attributeBox.setSelected(false);
					attributeGroupBox.setSelected(false);
					viewGroupBox.setSelected(false);
					lovBox.setSelected(true);
					
					collectionNodeBox.setEnabled(false);
					attributeBox.setEnabled(false);
					attributeGroupBox.setEnabled(false);
					viewGroupBox.setEnabled(false);
					lovBox.setEnabled(false);
					
				}
				
				else{
					if(guidBox.isEnabled() == false){
						guidBox.setEnabled(true);
						nameBox.setEnabled(true);
						
						collectionNodeBox.setEnabled(true);
						attributeBox.setEnabled(true);
						attributeGroupBox.setEnabled(true);
						viewGroupBox.setEnabled(true);
						
						lovBox.setEnabled(true);
						lovBox.setSelected(false);
					}
					
				}
			}
			
		});
		
		guidBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED){
					lovValueBox.setSelected(false);
					lovValueBox.setEnabled(false);
				}
				
				else{
					if(lovValueBox.isEnabled() == false && nameBox.isSelected() == false){
						lovValueBox.setEnabled(true);
					}
				}
			}
		});
		
		nameBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED){
					lovValueBox.setSelected(false);
					lovValueBox.setEnabled(false);
				}
				
				else{
					if(lovValueBox.isEnabled() == false && guidBox.isSelected() == false){
						lovValueBox.setEnabled(true);
					}
				}
			}
		});
		
		/*
		 * Add all checkbox objects to arraylist for reset button
		 */
		checkList.add(lovValueBox);
		checkList.add(guidBox);
		checkList.add(nameBox);
		checkList.add(collectionNodeBox);
		checkList.add(attributeBox);
		checkList.add(lovBox);
		checkList.add(attributeGroupBox);
		checkList.add(viewGroupBox);
		
		JLabel label_13 = new JLabel("");
		panel_2.add(label_13);
		
		/*
		 * Sets all check boxes to blank and clears the search box.
		 */
		final JCheckBox matchBox = new JCheckBox("Exact Match");
		matchBox.setToolTipText("Shows only results that contain the exact text entered");
		matchBox.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_matchBox = new GridBagConstraints();
		gbc_matchBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_matchBox.insets = new Insets(0, 0, 0, 5);
		gbc_matchBox.gridx = 0;
		gbc_matchBox.gridy = 0;
		panel_1.add(matchBox, gbc_matchBox);
		checkList.add(matchBox);
		
		//TODO re-enable check box
		matchBox.setEnabled(false);
		
		/*
		 * If the exact match check box is selected, set the partial
		 * boolean to false
		 */
		matchBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(matchBox.isSelected()==true)
					isPartial=false;
			}
		});
		
		JButton btnReset = new JButton("Reset");
		
		/*
		 * When the user presses the reset button, clear all check
		 * boxes and set the search field back to its default value
		 */
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//reset all check boxes
				for(int i=0; i<checkList.size(); i++){
					JCheckBox check = checkList.get(i);
					if(check.isSelected())
						check.setSelected(false);
				}
				
				//clear search field
				searchBox.setText("");
			}
		});
		
		JButton btnExport = new JButton("Export");
		GridBagConstraints gbc_btnExport = new GridBagConstraints();
		gbc_btnExport.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnExport.insets = new Insets(0, 0, 0, 5);
		gbc_btnExport.gridx = 2;
		gbc_btnExport.gridy = 0;
		panel_1.add(btnExport, gbc_btnExport);
		
		//TODO Enable export button after search has been performed
		btnExport.setEnabled(false);
		
		/*
		 * Initialize prompt regarding whether the user wants to export
		 * the search results as .xls, .xlsx, or .csv.
		 */
		
		btnExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) { 
				ExportDialog export = new ExportDialog();
				export.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				export.setVisible(true);
			}
		});
		
		
		
		btnReset.setToolTipText("Clears all current search data");
		btnReset.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnReset.gridx = 4;
		gbc_btnReset.gridy = 0;
		panel_1.add(btnReset, gbc_btnReset);
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////HERE IS WHERE THE ACTUAL SEARCHING IS DONE///////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Add search types to array list
		searchTypeList.add(lovValueBox);
		searchTypeList.add(guidBox);
		searchTypeList.add(nameBox);
		
		//Add object types to array list
		objectTypeList.add(collectionNodeBox);
		objectTypeList.add(attributeBox);
		objectTypeList.add(lovBox);
		objectTypeList.add(attributeGroupBox);
		objectTypeList.add(viewGroupBox);
		

		/*
		 * The user can press the search button to search or press the
		 * 'enter' key while the text field is selected
		 */
		searchBox.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
			    int key = e.getKeyCode();
		        
			    // Only respond to the enter key
		        if (key == KeyEvent.VK_ENTER) {
		        	boolean checked = Util.isChecked(searchTypeList);
		        	
					/*
					 * If the user has not entered text into the search box,
					 * throw an error
					 */
		        
				if(searchBox.getText().equals("") || searchBox.getText().trim().toCharArray()[0]=='\"'
						|| searchBox.getText().trim().toCharArray()[0]=='\''){
					InvalidSearchDialog d = new InvalidSearchDialog();
					d.setVisible(true);
					}
				
				else if(!checked){
						SearchTypeDialog notChecked = new SearchTypeDialog();
						notChecked.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						notChecked.setVisible(true);
					}
				else{
					
					q = new Query(objectTypeList, searchTypeList, searchBox.getText().trim());
					ArrayList<ResultSet> resList = q.doQuery(con, stmt);
					
					JTable table;
					try {
						table = new JTable(Util.buildTableModel(resList));
						JOptionPane.showMessageDialog(null, new JScrollPane(table));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    
				}
		        }
			}

			//////////Unnecessary methods for this text field//////////
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
			//////////Unnecessary methods for this text field//////////
		});
		
		/*
		 * If the search type has not been selected, throw an error dialog
		 */
		
		btnSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) { 
				boolean checked = Util.isChecked(searchTypeList);

				if(searchBox.getText().equals("") || searchBox.getText().trim().toCharArray()[0]=='\"'
						|| searchBox.getText().trim().toCharArray()[0]=='\''){
					InvalidSearchDialog d = new InvalidSearchDialog();
					d.setVisible(true);
					}
				
				else if(!checked){
						SearchTypeDialog notChecked = new SearchTypeDialog();
						notChecked.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						notChecked.setVisible(true);
					}
				else{
					
					q = new Query(objectTypeList, searchTypeList, searchBox.getText().trim());
					ArrayList<ResultSet> resList = q.doQuery(con, stmt);
					
					JTable table;
					try {
						table = new JTable(Util.buildTableModel(resList));
						JScrollPane scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  
							    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						
						JOptionPane.showMessageDialog(null, scroll);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    
				}
		        }
		});

	}
}