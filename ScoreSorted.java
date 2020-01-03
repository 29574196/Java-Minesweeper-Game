import javax.swing.*;

public class ScoreSorted
{   
	private Element head;  // list header
	private Element tail;
    public ScoreSorted()
    {  head = null;  
	   tail = null;        
    }
   
   public boolean prepend(Integer newElement) //insert at head
    {  
		Element temp = new Element(newElement);
        if(temp == null) // out of memory
           return false;
        else
        {  
	       if (head==null)
		   {
			head = temp;
			tail = temp;
		   }
           else
           {
			 temp.next = head;
			 head = temp;
		   }
		}
        return true;
    }
  
   public boolean append(Integer newElement) //insert at head
    {  
		Element temp = new Element(newElement);
        if(temp == null) // out of memory
           return false;
        else
        {  
	       if (head==null)
		   {
			head = temp;
			tail = temp;
		   }
           else
           {
			 tail.next = temp;
			 tail = temp;
		   }
		}
        return true;
        
   }
  
  public boolean insertSorted(Integer item)
	{
		Element ptr = head;// start with second element
		Element prevPtr = null;
		Element newItem;
		boolean inserted = false;
		if (head == null)
		{
			append (item);
			return true;
		}
		if (item.compareTo(head.data)<0)// first element is special case
		{
			prepend(item);
			return true;
		}
		if (item.compareTo(tail.data)>=0)
		{
			append(item);
			return true;
		}
		newItem= new Element(item);
		ptr= head.next;// start with second element
		prevPtr= head;
		while (ptr!= null && !inserted)
		{
			if (newItem.data.compareTo(prevPtr.data)>=0 && newItem.data.compareTo(ptr.data)<=0)
			{
				newItem.next = ptr;
				prevPtr.next = newItem;
				inserted = true;
			}
			prevPtr=ptr;
			ptr= ptr.next;
		}
		return inserted;
	}

  public String showAll()
  { Element ptr = head;
  String scores = "";
     while (ptr != null) //continue to traverse the list
     {   
		scores += ptr.data.toString( ) + "\n";
	 
	// JOptionPane.showMessageDialog(null,ptr.data.toString( ) + "\n");
         ptr = ptr.next;
     }
	 return scores;
   }
   
   
   
  public class Element
  {   
      private Integer data;
      private Element next;
      public Element(Integer param)
     {
		 data = param;
     }
   }// end of inner class Node
}//end SinglyLinkedList outer class
