package function;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;



public class PopUp extends JPopupMenu{

	private static final long serialVersionUID = 8359745349206715146L;
	
	public JMenuItem copyOpt, pasteOpt;
	
	public PopUp(){
		copyOpt = new JMenuItem("Copy");
		pasteOpt = new JMenuItem("Paste");
		
		add(copyOpt);
		add(pasteOpt);
	}
	
	/**
	 * Copy selected text to clipboard
	 * 
	 * @param box
	 * 		The search field from which the text is to be copied
	 */
	public static void copy(JTextField box){
		Clipboard clippy = Toolkit.getDefaultToolkit().getSystemClipboard();
		String selection = box.getSelectedText();
		StringSelection data = new StringSelection(selection);
		
		clippy.setContents(data, data);
	}
	
	/**
	 * Paste most recent text from clipboard into the search
	 * box
	 * 
	 * @param box
	 * 		The search field into which the desired text will be
	 * 		pasted
	 */
	public static void paste(JTextField box){
		Clipboard clippy = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		Transferable clipData = clippy.getContents(clippy);
		   if (clipData != null) {
		     try {
		       if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
		         String s = (String)(clipData.getTransferData(
		           DataFlavor.stringFlavor));
		         box.replaceSelection(s);
		       }
		     } catch (UnsupportedFlavorException ufe) {
		       System.err.println("Flavor unsupported: " + ufe);
		     } catch (IOException ioe) {
		       System.err.println("Data not available: " + ioe);
		     }
		   }

	}
	
	public JMenuItem getCopyOpt(){
		return this.copyOpt;
	}
	
	public JMenuItem getPasteOpt(){
		return this.pasteOpt;
	}
}