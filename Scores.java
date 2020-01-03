import java.awt.Color;
import javax.swing.*;

public class Scores extends JFrame
{
	JTextArea highScores;
	String bTime;
	String player;
	final String nextLine = "\n";
	
	public Scores()
	{
		super("Best Time List");
		addScore(bTime, player);
	}
	public void addScore(String bestTime, String name)
	{
		highScores = new JTextArea("All Highscores\n\nTime\tPlayer\n");
		highScores.setBackground(Color.white);
		highScores.setEditable(false);
		
		highScores.append(bestTime+ "\t" +name + nextLine);
		highScores.setVisible(true);
		add(highScores);
	}
}