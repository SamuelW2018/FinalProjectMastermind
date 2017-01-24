import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.net.URL;

import javax.swing.*;

/**
 * GameDisplay class. This is the class that the user sees when playing the game.
 * @author swynsma18
 *
 */
public class GameDisplay extends JComponent implements ActionListener{
	public static JButton submit;
	static Image BackImage;
	public Row[] rows = new Row[10];
	public Row solution;
	public String Difficulty;
	boolean PlayerDone = true;
	boolean Multiplayer = false;
	public int turns = 0;
	public int peginfo = 0;
	public int rowinfo = 0;
	public int[] data = new int[3];
	JFrame frame;
	boolean GameOver = false;
	
	/**
	 * This is the big function where all the major activities happen in the Mastermind game.
	 * First, it sets the frame. Then, it asks the player(s) how many they are, and what difficulty they want to play on.
	 * Then, it gives action listeners to all of the pegs.
	 * Finally, it creates the board with all of the buttons and pegs at the start.
	 * The program withholds future rows from the player until they have guessed a certain amount.
	 */
	public GameDisplay()
	{
		Object[] options = {"2 players", "1 player"};
		int playerMode = JOptionPane.showOptionDialog(null, "How many people are playing.", "Number of players", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		frame = new JFrame("Mastermind");
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(11, 2));
		
		if(playerMode == JOptionPane.NO_OPTION) {
			Object[] options2 = {"Easy", "Medium", "Impossible?"};
			int DiffInt = JOptionPane.showOptionDialog(null, "What difficulty do you want?\n Easy has 5 colors.\n Medium has 6 colors.\n Impossible has 8 colors.", 
					"Choose Your Difficulty", JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, null, options2, options[1]);
			DifficultyChooser(DiffInt);
		}
		else
		{
			PlayerDone = false;
			Multiplayer = true;
		}
		
		for(int i = 0; i < 10; i++)
		{
			rowinfo = i;
			rows[i] = new Row(Difficulty);
			for(int j = 0; j < 4; j++)
			{
				peginfo = j;
				rows[i].getPeg(j).addActionListener(new ActionListener(){
					/**
					 * For every peg, clicking on them will change their color to the next in line.
					 */
					public void actionPerformed(ActionEvent e)
					{
						rows[rowinfo].getPeg(peginfo).changeColor();
					}
				});
			}
		}
		if(PlayerDone == true)
		{
			frame.add(rows[0]);
		}
		
		JPanel p = new JPanel();
		JButton Sub = new JButton("Submit");
		JButton NewGame = new JButton("New Game");
		NewGame.addActionListener(new ActionListener(){
			/**
			 * The new game button only appears after the game is finished. It resets the game.
			 */
			public void actionPerformed(ActionEvent e)
			{
				frame.removeAll();
				frame.dispose();
				new GameDisplay();
			}
		});
		
		if(PlayerDone == true)
		{
			p.add(Sub);
		}
		else
		{
			solution = new Row("Medium");
			frame.add(solution);
			JButton submitSolution = new JButton("Submit Solution");
			submitSolution.addActionListener(new ActionListener() {
				/**
				 * In a two player game, the second player makes their own solution (presumably while the other player is not watching).
				 * Then he submits the solution, and it disappears from view.
				 */
				public void actionPerformed(ActionEvent E)
				{
					frame.remove(solution);
					frame.remove(submitSolution);
					frame.add(rows[0]);
					p.add(Sub);
					frame.add(p);
					PlayerDone = true;
					frame.setVisible(true);
				}
			});
			frame.add(submitSolution);
		}
		
		Sub.addActionListener(new ActionListener(){
			/**
			 * This action listener is quite substantial, because a lot happens every time the player hits submit.
			 * First, the program checks to make sure the game has not ended. If it has ended, then nothing happens.
			 * If the game has not ended, it checks the turn counter and compares the rows between the current row and the solution.
			 * If the amount of correct pegs equals 4, the game ends and the player wins.
			 * If the player has not guessed the correct answer, the turn counter increases.
			 * The game board then displays the next row and the previous results.
			 * If the player has guessed ten times without success, the game ends with them losing.
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(GameOver == false)
				{
					if(turns <= 9)
					{
						frame.remove(p);
						data = rows[turns].compareRows(solution);
						String InfoString = "Correct: " + data[0] +", Missplaced: " + data[1] + ", Wrong: " + data[2];
						frame.add(new JLabel(InfoString));
					}
					if(data[0] == 4)
					{
						String winString;
						if(Multiplayer == true)
							winString = "Guessing player wins in " + (turns+1) + " tries";
						else
							winString =  "You have won the game. You got the correct solution in " + (turns+1) + " tries";
						JOptionPane.showMessageDialog(null, winString, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
						GameOver = true;
						p.add(NewGame);
					}
					solution.ResetSolution();
					turns++;
					if(turns < 10 && GameOver == false)
					{
						frame.add(rows[turns]);
					}
					else if(turns >= 10 && GameOver == false)
					{
						String loseString;
						if(Multiplayer == true)
						{
							loseString = "Solution maker wins the game. The correct solution is " + solution.getPeg(0).getColor() + " " 
									+ solution.getPeg(1).getColor() + " " + solution.getPeg(2).getColor() + " " + solution.getPeg(3).getColor() + ".";
						}
						else
						{
							loseString = "You have lost the game.\n The correct solution is " + solution.getPeg(0).getColor() + " " 
									+ solution.getPeg(1).getColor() + " " + solution.getPeg(2).getColor() + " " + solution.getPeg(3).getColor() + ".";
						}
						JOptionPane.showMessageDialog(null, loseString, "Game Over...", JOptionPane.ERROR_MESSAGE);
						GameOver = true;
						p.add(NewGame);
					}

					for(int i = 0; i < 4; i++)
					{
						rows[turns-1].getPeg(i).removeActionListener(rows[turns-1].getPeg(i).getActionListener());
					}
					frame.add(p);
					
					frame.setVisible(true);
					
				}
			}
		});
		frame.add(p);
		frame.setVisible(true);
			
	}
	
	/**
	 * Main function for the Mastermind game. Initializes GameDisplay, which handles the rest.
	 * @param args
	 */
	public static void main(String[] args)
	{
		new GameDisplay();
	}

	/**
	 * This is the actionPerformed function of the gameDisplay interface. It checks to see if the
	 * player has clicked any of the buttons. It will only change buttons on specific turns.
	 * Due to changes in the gameDisplay function, this function is for testing purposes only.
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "0"+turns)
		{
			rows[turns].getPeg(0).changeColor();
		}
		else if(action == "1"+turns)
		{
			rows[turns].getPeg(1).changeColor();
		}
		else if(action == "2"+turns)
		{
			rows[turns].getPeg(2).changeColor();
		}
		else if(action == "3"+turns)
		{
			rows[turns].getPeg(3).changeColor();
		}
		
		
	}
	
	/**
	 * This function is called after the game mode has been selected as "1 player" and 
	 * the player has selected the difficulty from the option pane with the difficulties.
	 * Because it was easier and better for me to treat the option pane as a yes/no/cancel box,
	 * thats how I treat it in here. If the player selected easy, the game sets the rules to easy mode rules.
	 * If the player selects impossible, they play on hard difficulty.
	 * If the player either selects medium or closes the window, the game defaults to medium difficulty.
	 * @param Diff is the integer corresponding to the answer in the JOptionPane.
	 */
	public void DifficultyChooser(int Diff)
	{
		if (Diff == JOptionPane.YES_OPTION)
		{
			Difficulty = "Easy";
			solution = new Row("Random", "Easy");
		}
		else if(Diff == JOptionPane.CANCEL_OPTION)
		{
			JOptionPane.showMessageDialog(null, "You are brave...");
			Difficulty = "Hard";
			solution = new Row("Random", "Hard");
		}
		else
		{
			Difficulty = "Medium";
			solution = new Row("Random", "Medium");
		}
	}
	
	
	
}
