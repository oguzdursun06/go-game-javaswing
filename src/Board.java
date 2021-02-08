import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


public class Board extends JPanel {
	
	static JTextField text;
	static JButton[][] goBoard;

	
	protected static final Color BOARD_COLOR = Color.ORANGE;
	

	
	public Board(){
		goBoard = new JButton[9][9];
		
		
		for(int row=0;row<9;row++){
			for(int col=0;col<9;col++){
				goBoard[row][col] = new JButton();
				goBoard[row][col].setBorder(new LineBorder(Color.getHSBColor(256, 0,0)));
				goBoard[row][col].setBackground(BOARD_COLOR);
				add(goBoard[row][col]);//add to the panel
			}
		}
		
		setLayout(new GridLayout(9,9));
	    
	}
	

	
	
	


}