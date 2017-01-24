
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This is the peg class. It contains the basic information on the pegs, which are the meat of a mastermind game.
 * They extend JButton. Originally, they were going to be circular, but I decided against it later.
 * @author swynsma18
 *
 */
public class Peg extends JButton{
	public enum AvailColor{Red, Blue, Green, Orange, Purple, Black, Cyan, Yellow};
	AvailColor AC;
	String Difficulty;
	Color Purple = new Color(127, 0, 255);
	Color DYellow = new Color(153, 153, 0);
	Color DOrange = new Color(255, 178, 102);
	boolean isCounted = false;
	ActionListener Q = new ActionListener(){
		/**
		 * This creates the basic action listener for the peg. It is called by both row and gameDisplay.
		 */
		public void actionPerformed(ActionEvent e)
		{
			changeColor();
		}
	};
	
	/**
	 * Standard constructor for peg. Takes a string representing the difficulty and sets the peg to the color red.
	 * This is used for non-solution rows, the ones that appear in the interface.
	 * @param D is the difficulty, which decides which colors the peg can change between.
	 */
	public Peg(String D)
	{
		AC = AvailColor.Red;
		Difficulty = D;
		this.setText("");
		this.setBackground(Color.RED);
		this.addActionListener(Q);
	}
	
	/**
	 * This is the peg function generally used for solutions. It sets the color based on the string passed.
	 * @param A = the color that the peg will be. Is set based on a constructor in row.
	 * @param D = the difficulty. Passed into here from row.
	 */
	public Peg(String A, String D)
	{
		Difficulty = D;
		if (A == "Red")
		{
			AC = AvailColor.Red;
			this.setBackground(Color.RED);
		}
		else if(A == "Blue")
		{
			AC = AvailColor.Blue;
			this.setBackground(Color.BLUE);
		}
		else if(A == "Green")
		{
			AC = AvailColor.Green;
			this.setBackground(Color.GREEN);
		}
		else if(A == "Orange")
		{
			AC = AvailColor.Orange;
			this.setBackground(DOrange);
		}
		else if(A == "Purple")
		{
			AC = AvailColor.Purple;
			this.setBackground(Purple);
		}
		else if(A == "Black")
		{
			AC = AvailColor.Black;
			this.setBackground(Color.BLACK);
		}
		else if(A == "Cyan")
		{
			AC = AvailColor.Cyan;
			this.setBackground(Color.CYAN);
		}
		else
		{
			AC = AvailColor.Yellow;
			this.setBackground(DYellow);
		}
		
	}
	
	/**
	 * This function changes the color of the pegs in a sequence. When a peg is clicked, this is called.
	 * The sequence is red-blue-green-orange-purple-(black)-(cyan)-(yellow).
	 * Black does not appear on easy difficulty.
	 * Cyan and yellow only appear on impossible difficulty.
	 * Note that this function changes both the background color of the peg and the AvailColor enum of the peg.
	 */
	public void changeColor()
	{
		if (AC == AvailColor.Red)
		{
			AC = AvailColor.Blue;
			this.setBackground(Color.BLUE);
		}
		else if (AC == AvailColor.Blue)
		{
			AC = AvailColor.Green;
			this.setBackground(Color.GREEN);
		}
		else if (AC == AvailColor.Green)
		{
			AC = AvailColor.Orange;
			this.setBackground(DOrange);
		}
		else if (AC == AvailColor.Orange)
		{
			AC = AvailColor.Purple;
			this.setBackground(Purple);
		}
		else if (AC == AvailColor.Purple && Difficulty != "Easy")
		{
			AC = AvailColor.Black;
			this.setBackground(Color.BLACK);
		}
		else if(AC == AvailColor.Black && Difficulty == "Hard")
		{
			AC = AvailColor.Cyan;
			this.setBackground(Color.CYAN);
		}
		else if(AC == AvailColor.Cyan)
		{
			AC = AvailColor.Yellow;
			this.setBackground(DYellow);
		}
		else
		{
			AC = AvailColor.Red;
			this.setBackground(Color.RED);
		}
	}
	
	/**
	 * This function checks to see if a peg has the same color as another peg.
	 * @param P the peg that the current peg is being compared to.
	 * @return true if the pegs match color (also sets Counted to true, which prevents this from being called again in Row.java). false if the pegs do not match
	 */
	public boolean compareColor(Peg P)
	{
		if (AC == P.getColor())
		{
			SetCounted(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the current AvailColor variable.
	 * @return AvailColor.
	 */
	public AvailColor getColor()
	{
		return AC;
	}
	
	/**
	 * Gets the action listener for this peg
	 * @return the action listener for this peg.
	 */
	public ActionListener getActionListener()
	{
		return Q;
	}
	
	/**
	 * Checks to see if the peg has been counted in the compareRows function in Row.
	 * @return true/false, depending on the value of isCounted.
	 */
	public boolean IsCounted()
	{
		return isCounted;
	}
	
	/**
	 * Sets isCounted to true/false depending on the situation.
	 * @param B true if in compare color, false if in reset solution.
	 */
	public void SetCounted(boolean B)
	{
		isCounted = B;
	}
	
	
}
