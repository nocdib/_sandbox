public class HealthyBurger extends Hamburger {
    private int additionSize = 6;

    public HealthyBurger(String meat) {
        super("Healthy Burger", "Brown Rye", meat, 4.50);
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