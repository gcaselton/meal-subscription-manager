import java.util.*;
/**
 * The Meal class. This stores the name of each meal as well as the number available.
 * Contains getters and setters and some overridden methods. Similarly to subscriber, Meal implements the Comparable interface.
 */
public class Meal implements Comparable<Meal>
{

    /**
     * Field for meal name
     */
    private String name;
    /**
     * Field for the number available
     */
    private int numberAvailable;

    /**
     * The constructor for a new meal
     * @param name Meal name
     * @param numberAvailable Number left
     */
    public Meal(String name, int numberAvailable)
    {
        this.name = name;
        this.numberAvailable = numberAvailable;
    }

    /**
     * This constructor only takes a name as a parameter and is used to create a new Meal for searching purposes.
     * @param name Meal name
     */
    public Meal(String name)
    {
        this.name = name;
        this.numberAvailable = 0;
    }

    /**
     * Meal name getter
     * @return Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Meal name setter
     * @param name Meal name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Number available getter
     * @return Number available
     */
    public int getNumberAvailable()
    {
        return numberAvailable;
    }

    /**
     * Number available setter
     * @param numberAvailable Number available
     */
    public void setNumberAvailable(int numberAvailable)
    {
        this.numberAvailable = numberAvailable;
    }

    /**
     * toString override which formats the fields nicely for printing purposes
     * @return name and number available
     */
    public String toString()
    {
        return name + ": " + numberAvailable + " available";
    }

    /**
     * compareTo override which compares by meal name.
     * @param m the meal to be compared.
     * @return the result of the comparison
     */
    public int compareTo(Meal m)
    {
        String formattedName = name.toLowerCase();
        return formattedName.compareTo(m.name.toLowerCase());
    }

    /**
     * Equals override
     * @param other The meal to check against
     * @return The result of the equality check
     */
    public boolean equals(Meal other)
    {
        return (this.name.equals(other.name));
    }

    /**
     * Similar to the printInfo method in Subscription, this one calculates whitespace and prints the meal along with
     * its number available spaced apart neatly.
     */
    public void printInfo()
    {
        int i = 20 - getName().length();
        String spaces = String.format("%1$"+i+"s", "");

        System.out.println(getName() + spaces + getNumberAvailable());
    }

}
