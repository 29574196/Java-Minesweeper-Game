import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WriteStuff
{
	ObjectOutputStream output;
	
	public void openFile()
	{
		try
		{
			//output = new ObjectOutputStream(new FileOutputStream("stuff.txt"));
			File yourFile = new File("stuff.txt");
			yourFile.createNewFile(); // if file already exists will do nothing 
			FileOutputStream oFile = new FileOutputStream(yourFile, false); 
			output = new ObjectOutputStream(oFile);
		}
		catch(IOException e)
		{
			System.out.println("Could not open file" + e);
		}
	}
	
	public void closeFile()
	{
		try
		{
			output.close();
		}
		catch(IOException e)
		{
			System.out.println("Could not close file" + e);
		}
	}

	public void write(String name, String time)
	{
		
		try
		{
			
			/*System.out.print("\nGive Radius: ");
			radius = kInput.nextInt();
			System.out.print("\nGive Color: ");
			col = kInput.next();
			thing = new March14(radius, col);
			output.writeObject(thing);
			invalid = false;*/
			output.writeObject("\n"+name+"\t"+time+"\n");
		}
		catch(IOException e)
		{
			System.out.println("Problem writing file " +e);
		}
		catch(Exception e)
		{
			System.out.println("Big Problem...... " +e);
		}
	}
	/*public static void main(String[] args)
	{
		WriteStuff prob = new WriteStuff();
		prob.openFile();
		prob.write();
		prob.closeFile();
	}*/
}