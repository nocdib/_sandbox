public class Hamburger {
    private String name;
    private String breadRollType;
    private String meat;
    private double price;
    private double total;

    private int additionSize = 4;
    private String [] additions;
    private int additionIndex;

    public void setAdditions(String[] additions) {
        this.additions = additions;
    }

    public void setAdditionIndex(int additionIndex) {
        this.additionIndex = additionIndex;
    }

    public String getName() {
        return name;
    }

    public String getBreadRollType() {
        return breadRollType;
    }

    public String getMeat() {
        return meat;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return total;
    }

    public int getAdditionSize() {
        return additionSize;
    }

    public String[] getAdditions() {
        return additions;
    }

    public int getAdditionIndex() {
        return additionIndex;
    }

    public double getAdditionPrice() {
        return additionPrice;
    }

    private double additionPrice = 1.00;

    public Hamburger(String name, String breadRollType, String meat, double price) {
        this.name = name;
        this.breadRollType = breadRollType;
        this.meat = meat;
        this.price = price;
        this.total = price;
        this.additions = new String[additionSize];
        this.additionIndex = 0;
    }

    public Hamburger(String name, String breadRollType, String meat) {
        this(name, breadRollType, meat, 3.30);
    }

    public void makeAddition(String addition){
        this.additions[additionIndex++] = addition;
        this.total += additionPrice;
    }

    public void removeAddition(){
        this.additions[additionIndex--] = null;
        this.total -= additionPrice;
    }

    public String toString() {
        String returnString = String.format("%s: $%.2f\n", this.name, this.price);
        if(additionIndex > 0) { // print additions, if there are any
            for(int index=0; index<additionIndex; index++){
                returnString += String.format("%s: $%.2f\n", additions[index], additionPrice);
            }
        }
        returnString += String.format("Total: $%.2f\n", this.total);
       return returnString;
    }

}


/*

Also create two extra varieties of Hamburgers (subclasses) to cater for
a) Healthy burger (on a brown rye bread roll), plus two addition items that can be added.
The healthy burger can have 6 items (Additions) in total.
hint:  you probably want to process the two additional items in this new class (subclass of Hamburger),
not the base class (Hamburger), since the two additions are only appropriate for this new class
(in other words new burger type).
b) Deluxe hamburger - comes with chips and drinks as additions, but no extra additions are allowed.
hint:  You have to find a way to automatically add these new additions at the time the deluxe burger
object is created, and then prevent other additions being made.
All 3 classes should have a method that can be called anytime to show the base price of the hamburger
plus all additionals, each showing the addition name, and addition price, and a grand/final total for the
burger (base price + all additions)
For the two additional classes this may require you to be looking at the base class for pricing and then
adding totals to final price.
*/