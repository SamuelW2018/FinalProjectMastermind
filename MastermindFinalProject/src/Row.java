import java.util.Random;

import javax.swing.*;

/**
 * Row class: This class extends JPanel and stores the pegs in the game. It has various small functions that connect the pegs with the gameDisplay.
 * @author swynsma18
 *
 */
public class Row extends JPanel {
	Peg[] pegs = new Peg[4];
	
	/**
	 * Basic constructor for row. Only takes a string D, which is the difficulty of the game.
	 * @param D the difficulty of the game.
	 */
	public Row(String D)
	{
		pegs[0] = new Peg(D);
		pegs[1] = new Peg(D);
		pegs[2] = new Peg(D);
		pegs[3] = new Peg(D);
		add(pegs[0]);
		add(pegs[1]);
		add(pegs[2]);
		add(pegs[3]);
	}
	
	/**
	 * This is the constructor for the randomized solution. The constructor does different actions depending on the difficulty.
	 * @param CS an unimportant variable that was originally going to be used for the mode name, but became just the indicator that this is a solution.
	 * @param D the difficulty of the game.
	 */
	public Row(String CS, String D)
	{
		for(int i = 0; i < 4; i++)
		{
			Random R = new Random();
			int Col;
			if(D == "Easy")
			{
				Col = R.nextInt(5);
			}
			else if(D == "Hard")
			{
				Col = R.nextInt(8);
			}
			else
			{
				Col = R.nextInt(6);
			}
			if (Col == 0)
			{
				pegs[i] = new Peg("Red", D);
			}
			else if(Col == 1)
			{
				pegs[i] = new Peg("Blue", D);
			}
			else if(Col == 2)
			{
				pegs[i] = new Peg("Green", D);
			}
			else if(Col == 3)
			{
				pegs[i] = new Peg("Orange", D);
			}
			else if(Col == 4)
			{
				pegs[i] = new Peg("Purple", D);
			}
			else if(Col == 5)
			{
				pegs[i] = new Peg("Black", D);
			}
			else if(Col == 6)
			{
				pegs[i] = new Peg("Cyan", D);
			}
			else
			{
				pegs[i] = new Peg("Yellow", D);
			}
		}
		add(pegs[0]);
		add(pegs[1]);
		add(pegs[2]);
		add(pegs[3]);
	}
	
	/**
	 * In a multiplayer game, this constructor was going to be called. This constructor sets a row to a particular value.
	 * This is used for testing purposes, and was originally going to be used for multiplayer.
	 * This constructor is still very useful for testing various colors in the game.
	 * @param GivenSolution = the solution that the player gives.
	 */
	public Row(String[] GivenSolution)
	{
		pegs[0] = new Peg(GivenSolution[0], "Medium");
		pegs[1] = new Peg(GivenSolution[1], "Medium");
		pegs[2] = new Peg(GivenSolution[2], "Medium");
		pegs[3] = new Peg(GivenSolution[3], "Medium");
		add(pegs[0]);
		add(pegs[1]);
		add(pegs[2]);
		add(pegs[3]);
	}
	
	/**
	 * This function compares two rows to each other. It takes another row, and then compares it to the main row in question.
	 * It returns an array of integers with 3 integers.
	 * The first integer is the amount of correct pegs.
	 * The second integer is the amount of misplaced pegs.
	 * The third integer is the amount of wrong pegs.
	 * @param R another row to compare this row to.
	 * @return a three integer array which is used to give the player information on how close they are to the correct answer.
	 */
	public int[] compareRows(Row R)
	{
		int[] Information = {0, 0, 0};
		for (int i = 0; i < 4; i++)
		{
			if(getPeg(i).compareColor(R.getPeg(i)))
			{
				R.getPeg(i).SetCounted(true);
				Information[0]++;
			}
		}
		
		for(int j = 0; j < 4; j++)
		{
			if(!(getPeg(j).IsCounted()))
			{
				for(int k = 0; k < 4; k++)
				{
					if(!(R.getPeg(k).IsCounted()))
					{
						if(getPeg(j).compareColor(R.getPeg(k)))
						{
							R.getPeg(k).SetCounted(true);
							Information[1]++;
						}
					}
				}
			}
		}
		
		Information[2] = 4 - Information[1] - Information[0];
		return Information;
	}
	
	/**
	 * This function sets the action commands of the pegs.
	 * @param k = the action command number being set.
	 */
	public void SetPegCommand(int k)
	{
		pegs[0].setActionCommand("0"+k);
		pegs[1].setActionCommand("1"+k);
		pegs[2].setActionCommand("2"+k);
		pegs[3].setActionCommand("3"+k);
	}
	
	/**
	 * This function gets a specific peg inside the row.
	 * @param k the index of the peg in the row
	 * @return the peg that fits the index.
	 */
	public Peg getPeg(int k)
	{
		return pegs[k];
	}
	
	/**
	 * This function's purpose is to reset all of the compare functions on a row to false.
	 * It is used for the correct solution, and it allows the compareRows function to behave better.
	 * It resets the checked nature of pegs in the correct solution.
	 */
	public void ResetSolution()
	{
		pegs[0].SetCounted(false);
		pegs[1].SetCounted(false);
		pegs[2].SetCounted(false);
		pegs[3].SetCounted(false);
	}
}
