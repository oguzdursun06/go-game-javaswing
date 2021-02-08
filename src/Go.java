import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

public class Go implements ActionListener {
	private int counter = 0;
	private Board gBoard;
	static boolean marked[][];
	private JFrame window = new JFrame("Go Board Game");
	private static Color stoneColors[] = {Color.BLACK, Color.WHITE};
	Player playerBlack;
	Player playerWhite;
	Label blackScore,whiteScore,turn;
	JButton passButton;
	
	public Go(){
		window.setSize(1000, 1000);
		gBoard = new Board();
		marked = new boolean[9][9];
		
		playerBlack = new Player("black");
		playerWhite = new Player("white");
		window.add(gBoard);
		window.setVisible(true);
		
		for(int row=0;row<9;row++){
			for(int col=0;col<9;col++){
				Board.goBoard[row][col].addActionListener(this);
			}
		}
		Panel infoPanel = new Panel();
        infoPanel.setLayout(new GridLayout(1,3));
        blackScore = new Label("black: "+playerBlack.getGoStones());
        whiteScore = new Label("white: "+playerWhite.getGoStones());
        turn = new Label("current turn: "+"black");
        turn.setBackground(Color.GREEN);
        blackScore.setBackground(Color.BLACK);
        blackScore.setForeground(Color.WHITE);
        blackScore.setFont(new Font("serif", Font.PLAIN, 18));
        whiteScore.setBackground(Color.WHITE);
        whiteScore.setFont(new Font("serif", Font.PLAIN, 18));
        turn.setFont(new Font("serif", Font.PLAIN, 18));	
        passButton = new JButton("Pas geç");
        infoPanel.add(blackScore);
        infoPanel.add(whiteScore);
        infoPanel.add(turn);
        window.add(infoPanel, BorderLayout.NORTH);
        
        Panel pass = new Panel();
        pass.setLayout(new GridLayout(1,1));
        passButton.setBackground(Color.RED);
        passButton.setFont(new Font("serif", Font.PLAIN, 32));
        pass.add(passButton);
        
        window.add(pass, BorderLayout.SOUTH);
        passButton.addActionListener(this);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            { 
                String ObjButtons[] = {"Evet","Hayir"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Çýkmak istediðinizden emin misiniz?","ÇIKIÞ",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
		window.setVisible(true);
		
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		
		if(button.getBackground()==Board.BOARD_COLOR){
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
					if(button == Board.goBoard[i][j] && !(Board.goBoard[i][j].getText().equals("white")|| Board.goBoard[i][j].getText().equals("black"))) {
						Board.goBoard[i][j].setBorder(new Stones(100,stoneColors[counter%2]));
						if(stoneColors[counter%2] == Color.BLACK) {
							Board.goBoard[i][j].setText("black");
							playerBlack.setGoStones(playerBlack.getGoStones()+1);
							blackScore.setText("Black: "+playerBlack.getGoStones());
							turn.setText("current turn: "+"white");
							Player.passCount = 0;
						}
						else {
							Board.goBoard[i][j].setText("white");
							playerWhite.setGoStones(playerWhite.getGoStones()+1);
							whiteScore.setText("White: "+playerWhite.getGoStones());
							turn.setText("current turn: "+"black");
							Player.passCount = 0;
						}
						counter++;
					}
				}
			}
		}
		if(button == passButton) {
			if(turn.getText().equals("current turn: "+"white"))
				turn.setText("current turn: "+"black");
			else
				turn.setText("current turn: "+"white");
			Player.passCount++;
			if(Player.passCount == 2) {
				if(playerBlack.getGoStones() > playerWhite.getGoStones())
					JOptionPane.showMessageDialog(gBoard, "Oyun bitti, siyahýn taþý daha fazla olduðu için kazanan SÝYAH!");
				else if(playerBlack.getGoStones() < playerWhite.getGoStones())
					JOptionPane.showMessageDialog(gBoard, "Oyun bitti, beyazýn taþý daha fazla olduðu için kazanan BEYAZ!");
				else
					JOptionPane.showMessageDialog(gBoard, "Oyun bitti, taþ sayýsý eþit olduðu için BERABERLÝK!");
				System.exit(0);
			}
			counter++; 
		}
		
		if(stoneColors[counter%2] == Color.BLACK)
			replaceSurround("black",playerBlack,playerWhite,blackScore,whiteScore);
		else {
			replaceSurround("white",playerBlack,playerWhite,blackScore,whiteScore);
		}
	}
	
	static void replaceSurround(String color,Player playerBlack, Player playerWhite,Label blackScore, Label whiteScore){
		int[][] surroundTemp = new int [9][9];
		for (int[] row: surroundTemp)
    	    Arrays.fill(row,0);
		for (int y = 0; y < 9; y++)for (int x = 0; x < 9; x++)
		{
			if (!Board.goBoard[y][x].getText().equals(color)) continue;
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++) 
					marked[i][j] = false;
			if (isSurround(color, x, y) == false)
				surroundTemp[y][x] = 1;
		}
		for (int y = 0; y < 9; y++)for (int x = 0; x < 9; x++)
		{
			if (surroundTemp[y][x]==1)
				if(color.equals("white")) {
					Board.goBoard[y][x].setText("black");
					Board.goBoard[y][x].setBorder(new Stones(100,Color.BLACK));
					playerWhite.setGoStones(playerWhite.getGoStones()-1);
					playerBlack.setGoStones(playerBlack.getGoStones()+1);
					whiteScore.setText("White: "+playerWhite.getGoStones());
					blackScore.setText("Black: "+playerBlack.getGoStones());
				
				}
				else if(color.equals("black")) {
					Board.goBoard[y][x].setText("white");
					Board.goBoard[y][x].setBorder(new Stones(100,Color.WHITE));
					playerBlack.setGoStones(playerBlack.getGoStones()-1);
					playerWhite.setGoStones(playerWhite.getGoStones()+1);
					blackScore.setText("Black: "+playerBlack.getGoStones());
					whiteScore.setText("White: "+playerWhite.getGoStones());
				}	
				
		}
	}
	static boolean isSurround(String color, int x, int y)
	{
		if (marked[y][x]) return false;  
		marked[y][x] = true;

		if (!(Board.goBoard[y][x].getText().equals("white") || Board.goBoard[y][x].getText().equals("black") ))
		{
			return true; 
		}
		if (!Board.goBoard[y][x].getText().equals(color))return false;  

		//recursive search
		boolean r = x > 0 && isSurround(color, x - 1, y);
		r |= x < 9 - 1 && isSurround(color, x + 1, y);
		r |= y > 0 && isSurround(color, x, y - 1);
		r |= y < 9 - 1 && isSurround(color, x, y + 1);
		return r;
	}
  
	public static void main(String args[]){
		Go start = new Go();
	}

	
	
}
