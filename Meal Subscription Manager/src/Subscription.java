/**
 * I created this class as a means of storing information about specific meals that subscribers are subscribed to.
 * The fields and methods are very similar to the meal class, and I could well have chosen to use a list of Meal items
 * to store a subscriber's choices, but I decided a Meal and a Subscription were semantically separate,
 * so it would be simpler to create an extra class to represent subscriptions.
 */
public class Subscription
{
    /**
     * Field for Meal
     */
    private Meal meal;
    /**
     * Field for meal quantity
     */
    private int quantity;

    /**
     * Constructor for Subscription using Meal and Quantity as parameters
     * @param meal The meal
     * @param quantity The quantity of the meal
     */
    public Subscription(Meal meal, int quantity)
    {
        this.meal = meal;
        this.quantity = quantity;
    }

    /**
     * Meal getter
     * @return Meal
     */
    public Meal getMeal()
    {
        return meal;
    }

    /**
     * Quantity getter
     * @return Quantity
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * Meal setter
     * @param m Meal
     */
    public void setMeal(Meal m)
    {
        this.meal = m;
    }

    /**
     * Quantity setter
     * @param q Quantity
     */
    public void setQuantity(int q)
    {
        this.quantity = q;
    }

    /**
     * toString override which calculates a set amount of whitespace to add between the meal name and quantity.
     * This ensures the subscriptions align neatly when printed to the console.
     * @return Meal name, whitespace and meal quantity
     */
    public String toString()
    {
        int i = 20 - getMeal().getName().length(); // calculate the number of spaces based on the number of characters in meal name
        String spaces = String.format("%1$"+i+"s", ""); // format string accordingly

        return meal.getName() + spaces + quantity;
    }
}
