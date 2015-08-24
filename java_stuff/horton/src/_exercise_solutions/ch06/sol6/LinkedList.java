// Chapter 6 Exercise 5

public class LinkedList {
  // Default constructor - creates an empty list
  public LinkedList() {}

  // Constructor to create a list containing one object
  public LinkedList(Object item) {
    if(item != null) {
      current = end = start = new ListItem(item);                      // item is the start and end
      ++count;                                                         // Increment count of list items  ***
    }
  }

  // Construct a linked list from an array of objects
  public LinkedList(Object[] items) {
    if(items != null) {
      // Add the items to the list
      for(int i = 0 ; i < items.length ; ++i) {
        addItem(items[i]);
      }
      current = start;
      count = items.length;                                            // Increment count of list items  ***
    }
  }

  // Add an item object to the list
  public void addItem(Object item) {
    ListItem newEnd = new ListItem(item);                              // Create a new ListItem
    if(start == null) {                                                // Is the list empty?
      start = end = newEnd;                                            // Yes, so new element is start and end
    } else {                                                           // No, so append new element
      end.next = newEnd;                                               // Set next variable for old end
      newEnd.previous = end;                                           // Set previous as old end item  ***
      end = newEnd;                                                    // Store new item as end
    }
    ++count;                                                           // Increment count of list items  ***
  }

  // Get the first object in the list
  public Object getFirst() {
    forwards = true;                                                   // Going forwards then  ***
    current = start;
    return start == null ? null : start.item;
  }

  // Get the next object in the list
  public Object getNext() {
    forwards = true;                                                   // Going forwards  ***
    if(current != null) {
      current = current.next;                                          // Get the reference to the next item
    }
    return current == null ? null : current.item;
  }

  // Method to get the last object in the list
  public Object getLast() {
    forwards = false;                                                  // Going backwards then ***
    current = end;
    return end == null ? null : end.item;
  }

  // Method to get the previous object in the list
  public Object getPrevious() {
    forwards = false;                                                  // Going backwards  ***
    if(current != null) {
      current = current.previous;                                      // Get the reference to the next item
    }
    return current == null ? null : current.item;
  }

  // New method to delete an item at the current position  ***
  public Object deleteItem() {
    if(current == null) {                                              // If no object is defined by current to delete:
      if(start == null) {
        return null;                                                   // If the list is empty: can't delete non-existent objects.
      } else {
        current = end;                                                 // Otherwise: delete the end object if none specified.
      }
    }

    if(current.previous != null) {
      current.previous.next = current.next;
    }
    if(current.next != null) {
      current.next.previous = current.previous;
    }
    Object item = current.item;
    current = forwards ? current.previous : current.next;
    --count;                                                          // Decrement count of list items
    return item;
  }

  // New method to insert new item preceding the current item  ***
  public void insertItem(Object item) {
    ListItem newItem = new ListItem(item);
    if(start == null) {                                              // If list is empty...
      start = end = current = newItem;                               // ...make new item the start and end.
      ++count;
      return;
    }
    if(current == null) {                                            // If no current item...
      current = end;                                                 // ...make the end current.
    }
    if(current.previous == null) {
      start = current.previous = newItem;
      newItem.next = current;
      current.previous = newItem;
    } else {
      current.previous.next = newItem;
      newItem.previous = current.previous;
      newItem.next = current;
      current.previous = newItem;
    }
    current = newItem;
    ++count;
  }

  // Method to insert the object specified by the first argument
  // after the object specified by the second argument.
  // If the first argument is not found the second argument is not inserted
  // The method returns true for success, and false for failure
  public boolean insertItem(Object inserted, Object position) {
    if(start == null) {                                                // If list is empty, start will be null
      return false;
    }

    // Search for position
    ListItem theItem = start;
    while(theItem != null) {
      if(theItem.item.equals(position)) {
        current = theItem;
        insertItem(inserted);
        return true;
      } else {
        theItem = theItem.next;
      }
    }

    // If we reach here we didn't find position
    return false;
  }

  // New method to return the number of items in the list  ***
  public int  size() {
    return count;
  }

  // Convert the entire list to a string     ***
  @Override
  public String toString() {
    StringBuffer str = new StringBuffer();                             // Use StringBuffer as list is of variable length.
    current = start;                                                   // Set current to first.
    while(current != null) {                                           // While there are still elements in the list,
      // append their string representations to  str separated by tabs.
      str.append(current.item.toString() + "\t");
      current = current.next;
    }
    return str.toString();                                             // Return the StringBuffer as a String
  }

  private boolean forwards = true;                                     // Indicator for list scan direction  ***
  private ListItem start = null;                                       // First ListItem in the list
  private ListItem end = null;                                         // Last ListItem in the list
  private ListItem current = null;                                     // The current item for iterating
  private int count = 0;                                               // Number of items in the list        ***

  private class ListItem {
    // Constructor
    public ListItem(Object item) {
      this.item = item;                                                // Store the item
      previous = next = null;                                          // Set next as end point              ***
    }

    @Override
    public String toString() {
      return "ListItem " + item ;
    }

    ListItem next;                                                    // Refers to next item in the list
    ListItem previous;                                                // Refers to previous item in the list ***
    Object item;                                                      // The item for this ListItem
  }
}
