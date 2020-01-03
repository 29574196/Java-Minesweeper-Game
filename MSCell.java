import javax.swing.ImageIcon;

public class MSCell
{
		private boolean revealed;
		private boolean bomb;
		private int value;
		private ImageIcon ico;
		private boolean flagged;
		
		// constructor to initialise variables
		public MSCell ()
		{
			revealed=false;
			flagged = false;
			bomb = false;
			value=-1;
			ico = new ImageIcon("E:/Data Structure/Project/Icons/0.png");
		}
		// accessor and mutators
		public  void setRevealed ()
		{
			revealed = true;
		}
		public  void setFlagged (boolean b)
		{
			flagged = b;
		}
		public boolean isRevealed()
		{
			return revealed;
		}
		public void setBomb()
		{
			bomb=true;
		}
		public boolean isBomb()
		{
			return bomb;
		}
		public boolean isFlagged()
		{
			return flagged;
		}
		public void setValue(int v)
		{
			value=v;
		}
		public int getValue()
		{
			return value;
		}
		public void setIcon(ImageIcon v)
		{
			ico=v;
		}
		public ImageIcon getIcon()
		{
			return ico;
		}
		
		public String toString() // mark BOMB!
		{
			String s="   ";
			String s2;
			if (bomb)
				s=" ";
			else
				s=" ";
			if (isRevealed())
			{
			   if (bomb)
				 s = " X ";
			   else
			   {
				  s = "  "+new Integer(value).toString()+ " ";
			   }
			}
			if (isFlagged())
				s = "!";
			s2 = String.format("%s",s);
			return s2;
		}
		

}