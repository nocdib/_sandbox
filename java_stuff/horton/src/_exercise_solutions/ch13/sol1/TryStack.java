// Chapter 13 Exercise 1

/*
 A stack is a container that stores objects in a manner indicated by its name —
 in a vertical stack where only the object at the top of the stack is accessible.
 It works rather like a sprung stack of plates in a cafeteria.
 Only the top plate is at counter level and, therefore, is the only one you can access.
 When you add a plate to the stack, the existing plates are pushed down so the new plate
 is now the one that you can access. Define a generic Stack<> type with a method push()
 that adds the object that is passed as an argument to the top of the stack, and with
 a method pop() that removes and returns the object that is currently at the top of the stack.
 The pop() method should return null when the stack is empty. Demonstrate the operation of
 your Stack<> implementation by storing and retrieving 10 strings and 10 Double objects
 in stacks of a suitable type.
 */

 /*
  We are back with an easy exercise. No big challenge here.
  */

public class TryStack {
  public static void main(String[] args) {
    // Values to be stored in stack and then retrieved
    double[] values = { 1.0, 1.4142 , 1.732 , 2.0, 2.236, 2.449, 2.646, 2.828, 3.0, 3.162 };

    Stack<Double> data = new Stack<>();

    // Store the values on the stack
    System.out.println("Data pushed onto the stack in sequence is:");
    for(double v : values) {
      System.out.println(v);
      data.push(v);
    }

    double back = 0;
    System.out.println("\nData retrieved from the stack in sequence is:");
    while(!data.isEmpty()) {
      System.out.println(data.pop());
    }

    System.out.println("The stack should now be empty. Popping an item produces: " + data.pop());
  }
}