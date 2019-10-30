public class DeluxeHamburger extends Hamburger{


    public DeluxeHamburger(String breadRollType) {
        super("Deluxe Burger", breadRollType, "Hamburger", 5.50);
        this.setAdditions(new String[]{"Chips", "Soda"});
        this.setAdditionIndex(2);
    }

    @Override
    public void makeAddition(String addition) {
        // Do nothing
    }

    @Override
    public void removeAddition() {
        // Do nothing
    }
}

/*
b) Deluxe hamburger - comes with chips and drinks as additions, but no extra additions are allowed.
hint:  You have to find a way to automatically add these new additions at the time the deluxe burger
object is created, and then prevent other additions being made.
All 3 classes should have a method that can be called anytime to show the base price of the hamburger
plus all additionals, each showing the addition name, and addition price, and a grand/final total for the
burger (base price + all additions)
For the two additional classes this may require you to be looking at the base class for pricing and then
adding totals to final price.
*/