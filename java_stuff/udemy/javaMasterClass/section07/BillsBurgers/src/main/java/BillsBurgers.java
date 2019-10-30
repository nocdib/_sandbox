public class BillsBurgers {

    public static void main(String[] args){
        Hamburger myOrder, herOrder, yourOrder;
        myOrder = new Hamburger("Buffalo Burger", "Sesame Seed", "Beef");
        herOrder = new HealthyBurger("Turkey");
        yourOrder = new DeluxeHamburger("Whole Wheat");

        System.out.println(myOrder);
        System.out.println(herOrder);
        System.out.println(yourOrder);

        Hamburger theirOrder = herOrder;
        myOrder.makeAddition("pickles");
        herOrder.makeAddition("onions");
        theirOrder.makeAddition("cheese");

        System.out.println("-------------------");
        System.out.println(myOrder);
        System.out.println(herOrder);
        System.out.println(yourOrder);
        System.out.println("********************");
        System.out.println(herOrder);
        System.out.println(theirOrder);

    }
}
