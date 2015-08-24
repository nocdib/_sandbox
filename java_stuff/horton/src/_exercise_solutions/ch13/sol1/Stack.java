// Class implementing a push down stack
public class Stack<T> {
  public Stack() {
    items = new Object[initialCapacity];
  }

  // Push an item onto the stack
  public void push(T item) {
    if(itemCount==items.length) {
      Object[] newItems = new Object[items.length + increment];
      for(int i = 0 ; i < items.length ; ++i) {
        newItems[i] = items[i];
      }
      items = newItems;
    }
    items[itemCount++] = item;
  }

  // Pop an item off the top of the stack
  @SuppressWarnings("unchecked")
  public T pop() {
    if(itemCount == 0) {
      return null;
    }
    // Note: the annotation ensures this cast to type T will not result
    // in a warning from the compiler.
    T item = (T)items[--itemCount];
    items[itemCount] = null;                                           // Erase the object from the stack
    return item;
  }

  // Test for empty stack
  public boolean isEmpty() {
    return itemCount == 0;
  }

  // You cannot define an array of a parametric type T.
  // To store items in the stack of an arbitrary you can use type Object[]
  private Object[] items;                                              // Array to store items in the stack
  private int itemCount;                                               // Number of items in the stack

  private final static int initialCapacity = 10;                       // Initial stack capacity
  private final static int increment = 5;                              // Capacity increment when full
}