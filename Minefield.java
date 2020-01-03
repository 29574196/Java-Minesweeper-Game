import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sun.audio.*;
import java.applet.*;
import java.awt.Color;
import javax.sound.sampled.*;


public class Minefield extends JFrame implements Serializable
{
	ScoreSorted sort = new ScoreSorted();
	Scores highScores = new Scores();
	
	final int ROWS = 7;
	final int COLS = 7;
	final int BOMBS = 10;
	int count = 0;
	int cellsRevealed = 0;
	int bombNum = 10;
	int timeCount = 0;
	
	String name;
	
	
	private MSCell field[][];
	// GUI components
	private JButton[][] grid = new JButton[ROWS+2][COLS+2];
	JPanel gridPanel;
	JPanel timerPanel;
	JLabel tempLabel;
	JLabel bombcountLabel;
	JButton restart;
	JLabel counterTimer;
	
	private Timer timer;
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private GridBagLayout layer;
	private GridBagConstraints c;
	private GridBagConstraints c2;
	
	WriteStuff savetime = new WriteStuff();
	public Minefield()
	{
		super("Tesing Buttons");
		name =  (String) JOptionPane.showInputDialog(null,"Enter name: ");
		newgame();
	}
		public void initialiseField()
	{
		for (int i=0; i<ROWS+2;i++)
		{
			for (int j=0; j<COLS+2; j++)
			{
				field[i][j]=new MSCell();
			}
		}
	}
	
	public void plantBombs()
	{
		Random randomNumbers = new Random();
		//plant BOMBS number of bombs randomly in grid
		int bombsPlanted=0;
		int bombRow = 0;
		int bombCol = 0;
		while (bombsPlanted < BOMBS)
		{
			bombRow = randomNumbers.nextInt(ROWS)+1; //nextInt(n) produce random number from 0-(n-1) we want a number from 1-(ROWS)
			bombCol = randomNumbers.nextInt(COLS)+1;
			System.out.println( bombRow +" " + bombCol);
			if (!field[bombRow][bombCol].isBomb()) // if not yet bomb
			{
				 field[bombRow][bombCol].setBomb();
				 bombsPlanted++;
			}
			
		}
		
	}
	public void computeNumbers()
	{
		int countBombs= 0;
		for (int i=1; i<=ROWS;i++)
		{
			for (int j=1; j<=COLS; j++)
			{	
	            countBombs=0;
				if (!field[i][j].isBomb())
				{
					for (int a=-1; a<=1; a++)
					{
						for (int b=-1; b<=1; b++)
						{
							if (field[i+a][j+b].isBomb() )
							{
								countBombs++;
							}
						}
							
					}	
					field[i][j].setValue(countBombs);
				}
			}
			
		}
	}
	public void displayField()
	{
		ImageIcon starterButton = new ImageIcon("E:/New folder/Icons/default.png");
		for (int i=0; i<=ROWS+1;i++)
		{
			for (int j=0; j<=COLS+1; j++)
			{  
				grid[i][j].setIcon(starterButton);
			}
			repaint();
			revalidate();
		}
	}
	
	public void newgame()
	{
		// GUI components
		name =  (String) JOptionPane.showInputDialog(null,"Enter name: ");
		timer = new Timer();
		MouseHandler mh = new MouseHandler();
		RestartAction ra = new RestartAction();
		//Panel holding Timer and restart button
		timerPanel = new JPanel();
		timerPanel.setSize(500, 200);
		timerPanel.setBackground(Color.blue);
		ImageIcon btnStartFace = new ImageIcon("E:/New folder/Stuff/smile.jpg");
		bombcountLabel = new JLabel("0" + String.valueOf(bombNum));
		//Timer panel grid
		layer = new GridBagLayout();
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(30, 0, 0, 250);
		timerPanel.setLayout(layer);
		timerPanel.add(timer, c);
		
		restart = new JButton();
		restart.setPreferredSize(new Dimension(48, 48));
		restart.addActionListener(ra);
		c2 = new GridBagConstraints();
		restart.setIcon(btnStartFace);
		c2.anchor = GridBagConstraints.NORTH;
		c2.gridx = 3;
		c2.gridy = 1;
		c2.insets = new Insets(30, 0, 0, 50);
		timerPanel.setLayout(layer);
		timerPanel.add(restart, c2);
		
		counterTimer = new JLabel();
		//end of grid
		
		
		
		
		//Field and button design
		//ImageIcon defaultButton = new ImageIcon();
		
		// create grid for bottons
		gridPanel = new JPanel();
		layout = new GridBagLayout();
		gridPanel.setLayout(layout);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		int row, col;		
		for (row=0; row<ROWS+2; row++)
			for (col = 0; col<COLS+2; col++)
			{
				ImageIcon starterButton = new ImageIcon("E:/New folder/Icons/defaultButton.png");
				gbc.gridx = row;
				gbc.gridy = col;
				grid[row][col] = new JButton();
				grid[row][col].setIcon(starterButton);
				grid[row][col].setPreferredSize(new Dimension(48, 48));
				gridPanel.add(grid[row][col],gbc);
				grid[row][col].addMouseListener (mh);			
			}
		setLayout(new BorderLayout());
		add(timerPanel,BorderLayout.NORTH);
		add(gridPanel,BorderLayout.CENTER);
		
		// data
		field= new MSCell[ROWS+2][COLS+2];
		initialiseField();
		plantBombs();
		computeNumbers();
		displayField();
	}
	
	public void revealZero(int i,int j)
	{
		System.out.println( i +" " + j);
		for (int a=-1; a<=1; a++)
		{
			for (int b=-1; b<=1; b++)
			{
				if ((field[i+a][j+b].getValue() ==0) && (!field[i+a][j+b].isRevealed()))
				{
					field[i+a][j+b].setRevealed();
					if (field[i+a][j+b].getValue() !=-1) cellsRevealed++;
					gridPanel.remove(grid[i+a][j+b]);
					gbc.gridx = i+a;
					gbc.gridy = j+b;
					tempLabel = new JLabel(field[i+a][j+b].toString());
					
					//grid[i+a][j+b].setText(field[i+a][j+b].toString());
					System.out.println(tempLabel.getText());
					tempLabel.setPreferredSize(new Dimension(52, 52));
					gridPanel.add(tempLabel,gbc);
					tempLabel.setPreferredSize(new Dimension(52, 52));
					revalidate();
					repaint();
					
					revealZero(i+a,j+b); // clear more zeros!
				}
				else
				{
					if (!field[i+a][j+b].isRevealed())
					{
						field[i+a][j+b].setRevealed();
						if (field[i+a][j+b].getValue() !=-1) cellsRevealed++;
						gbc.gridx = i+a;
						gbc.gridy = j+b;
						tempLabel = new JLabel(field[i+a][j+b].toString());
						
						gridPanel.remove(grid[i+a][j+b]);
						gridPanel.add(tempLabel,gbc);
						tempLabel.setPreferredSize(new Dimension(52, 52));
						//grid[i+a][j+b].setText(field[i+a][j+b].toString());
						revalidate();
						repaint();
					}
				}
			}
							
		}	
	}
	
	public class RestartAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent ye)
		{
			count = 0;
			remove(gridPanel);
			timerPanel.remove(timer);
			revalidate();
			repaint();
		
			
			timer.reset();
		
			newgame();	
		}
	}
	private class MouseHandler implements MouseListener
	{
		
		 int count =0;
		 
		 public void mouseClicked(MouseEvent me) 
		 {
			if(me.getClickCount()==1){
				count++;
			}
			if (count == 1)
				timer.start();
			//left button
			if (me.getButton()== MouseEvent.BUTTON1)
			{
				Object o= me.getSource();
				tempLabel= new JLabel("@");
				for (int r=1; r<=ROWS; r++)
					for (int c = 1; c<=COLS; c++)
					{
						if 	(grid[r][c] == (JButton) o)
						{
							field[r][c].setRevealed();
							soundEffect();
							
							if (field[r][c].isBomb())
							{
								LossSOUND();
								timer.stop();
								JOptionPane.showMessageDialog(gridPanel," You lose");
								String gameTime = timer.getText();
								
								int bestTime = Integer.parseInt(gameTime);
								boolean inserted = sort.insertSorted(new Integer(bestTime));
								if(inserted)
								{
									highScores.addScore(sort.showAll(), name);
									highScores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									highScores.setSize(500,500);
									highScores.setVisible(true);
								}
								try//adding of Bomb image
								{
									BufferedImage mineImage = ImageIO.read(new File("E:/New folder/EX/bambino.png"));//this is where we get the image form class BufferedImage
									Image newMine = mineImage.getScaledInstance( 48, 48,  java.awt.Image.SCALE_SMOOTH );//manipulates image size
									tempLabel.setIcon(new ImageIcon(newMine));//imageIcone is a class that adds image to a component (it turns EX into an object )
								}
								catch (FileNotFoundException ex)
								{
								}
								catch (IOException ex)
								{
								}
							}
							if (!field[r][c].isBomb())// remove if later when exit is used
								{
									if(field[r][c].getValue()==0)// set revealed done already
									{
										revealZero(r,c);
										gbc.gridx = r;
										gbc.gridy = c;
										gridPanel.remove(grid[r][c]);
										tempLabel.setPreferredSize(new Dimension(52, 52));
										gridPanel.add(tempLabel,gbc);
										cellsRevealed++;
									
									}
									else
									{
										cellsRevealed++;
									}
								}
								
								
								tempLabel.setText("  "+field[r][c].toString());
								gbc.gridx = r;
								gbc.gridy = c;
								gridPanel.remove(grid[r][c]);
								tempLabel.setPreferredSize(new Dimension(52, 52));
								tempLabel.setPreferredSize(new Dimension(52, 52));
								gridPanel.add(tempLabel,gbc);
							if (cellsRevealed==((ROWS*COLS)-BOMBS))
							{
								timer.stop();
								playCheerSound();
								JOptionPane.showMessageDialog(gridPanel," You win");
								String gameTime = timer.getText();
								//savetime.openFile();
								//savetime.write(name,gameTime);
								int bestTime = Integer.parseInt(gameTime);
								boolean inserted = sort.insertSorted(new Integer(bestTime));
								if(inserted)
									sort.showAll();
							}
							revalidate();
							repaint();
						}
					}
			}
					if (me.getButton()== MouseEvent.BUTTON3)
					{
						Object o= me.getSource();
						tempLabel= new JLabel("@");
						for (int r=1; r<=ROWS; r++)
							for (int c = 1; c<=COLS; c++)
							{
								if 	(grid[r][c] == (JButton) o)
								{
									gbc.gridx = r;
									gbc.gridy = c;
									if (field[r][c].isFlagged())
									{
										field[r][c].setFlagged(false);
										grid[r][c].setIcon(null);
									}

									else
									{
										field[r][c].setFlagged(true);
							
								
										try//adding of flagg image
										{
											BufferedImage flagImage = ImageIO.read(new File("E:/New folder//EX/Fg.png"));
											Image newFlag = flagImage.getScaledInstance( 48, 48,  java.awt.Image.SCALE_SMOOTH );
											grid[r][c].setIcon(new ImageIcon(newFlag));
										}
										catch (FileNotFoundException ex)
										{
										}
										catch (IOException ex)
										{
										}
									}		
								
							revalidate();
							repaint();
								}
					
							}
						}	
		
		}
		public void mousePressed(MouseEvent me) {}
		public void mouseReleased(MouseEvent me) {}
		public void mouseEntered(MouseEvent me) {}
		public void mouseExited(MouseEvent me) {}
	}
	
	public void soundEffect()
	{
	try {
		File backtrack = new File("E:/New folder/EX/button2.WAV");
		AudioInputStream soundTwo = AudioSystem.getAudioInputStream(backtrack);
 

		DataLine.Info soundwave = new DataLine.Info(Clip.class, soundTwo.getFormat());
		Clip clipTwo = (Clip) AudioSystem.getLine(soundwave);
		clipTwo.open(soundTwo);
 
   
		clipTwo.addLineListener(new LineListener() {
		public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
          event.getLine().close();
        }
      }
    });
 
    clipTwo.start();
    }
    catch (IOException e) {
    }
    catch (LineUnavailableException e) {
    }
    catch (UnsupportedAudioFileException e) {
    }

	}

	public void playCheerSound()
	{
		try 
		{
			File soundFileFour = new File("E:/New folder/EX/cheer2.WAV");
			AudioInputStream soundFour = AudioSystem.getAudioInputStream(soundFileFour);
 

			DataLine.Info infoFour = new DataLine.Info(Clip.class, soundFour.getFormat());
			Clip clipFour = (Clip) AudioSystem.getLine(infoFour);
			clipFour.open(soundFour);
 
   
			clipFour.addLineListener(new LineListener() 
			{
				public void update(LineEvent event) 
				{
					if (event.getType() == LineEvent.Type.STOP) 
					{
						event.getLine().close();
					}	
				}
			});
 
			clipFour.start();
		}
			catch (IOException e) 
			{
			}
			catch (LineUnavailableException e) 
			{
			}
			catch (UnsupportedAudioFileException e) 
			{
			}

	}
	
public void LossSOUND()
	{
		try 
		{
			File LossSOUND = new File("E:/New folder/EX/boo2.WAV");
			AudioInputStream soundFive = AudioSystem.getAudioInputStream(LossSOUND);
 

			DataLine.Info infoFive = new DataLine.Info(Clip.class, soundFive.getFormat());
			Clip clipFive = (Clip) AudioSystem.getLine(infoFive);
			clipFive.open(soundFive);
 
   
			clipFive.addLineListener(new LineListener() 
			{
				public void update(LineEvent event) 
				{
					if (event.getType() == LineEvent.Type.STOP) 
					{
						event.getLine().close();
					}
			
				} 
			});
 
				clipFive.start();
		}
		catch (IOException e) 
		{
		}
		catch (LineUnavailableException e) 
		{
		}
		catch (UnsupportedAudioFileException e) 
		{
		}

	}
	
	
	
	
}