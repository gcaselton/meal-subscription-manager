import java.util.*;
/**
 * The Subscriber class stores a Subscriber's first name and surname, as well as an ArrayList of their chosen subscriptions.
 * On top of getters and setters, it contains auxiliary methods and an overridden CompareTo method from the Comparable interface.
 * I made the choice to store Subscriptions in an ArrayList rather than a SortedLinkedList, as the functionality of each data structure is
 * largely the same, however each Subscriber can only have a maximum of three subscriptions,
 * eliminating the need for scalable searching or sorting algorithms.
 */
public class Subscriber implements Comparable<Subscriber>
{
    /**
     * First name field
     */
    private String firstName;
    /**
     * Surname field
     */
    private String surname;
    /**
     * The subscriber's list of subscriptions
     */
    private ArrayList<Subscription> subscriptions;

    /**
     * This constructor is not used in the program, but I left it in case a future system needs to add Subscriptions
     * at the same time a new Subscriber is created.
     * @param firstName First name
     * @param surname Surname
     * @param subscriptions List of subscriptions
     */
    public Subscriber(String firstName, String surname, ArrayList<Subscription> subscriptions)
    {
        this.firstName = firstName;
        this.surname = surname;
        this.subscriptions = subscriptions;
    }

    /**
     * This constructor is used to create a new Subscriber with first and last name, as well as an empty ArrayList of Subscriptions.
     * @param firstName First name
     * @param surname Surname
     */
    public Subscriber(String firstName, String surname)
    {
        this.firstName = firstName;
        this.surname = surname;
        this.subscriptions = new ArrayList<>();
    }

    /**
     * First name getter
     * @return First name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Surname getter
     * @return Surname
     */
    public String getSurname()
    {
        return surname;
    }

    /**
     * Full name getter
     * @return First name followed by surname
     */
    public String getName()
    {
        return firstName + " " + surname;
    }

    /**
     * Full name setter.
     * This method is unused in the current system but may be useful in a later version.
     * @param firstName First name
     * @param surname Surname
     */
    public void setName(String firstName, String surname)
    {
        this.firstName = firstName;
        this.surname = surname;
    }

    /**
     * Subscriptions getter
     * @return Subscriptions
     */
    public ArrayList<Subscription> getSubscriptions()
    {
        return subscriptions;
    }

    /**
     * This method checks if a meal exists inside a subscriber's subscriptions.
     * If it exists, it will add the desired quantity. If not, it will create a new Subscription and add it.
     * @param meal The chosen meal
     * @param quantity The desired amount
     */
    public void setSubscriptions(Meal meal, int quantity)
    {
        boolean subscriptionExists = false;

        for (Subscription s : subscriptions) // Checks if meal is present within subscriptions
        {
            if(s.getMeal().equals(meal))
            {
                s.setQuantity(s.getQuantity() + quantity); // if so, adds quantity
                subscriptionExists = true;
            }
        }

        if (!subscriptionExists) // if not, creates new subscription and adds
        {
            Subscription s = new Subscription(meal, quantity);
            this.subscriptions.add(s);
        }

    }

    /**
     * toString override
     * @return The full name and the subscriptions list of a subscriber as a String.
     */
    public String toString()
    {
        return firstName + " " + surname +  " " + subscriptions;
    }

    /**
     * compareTo override which first compares by surname, then by first name.
     * It first converts the names to lowercase to ensure that a Subscriber will be accurately compared to another
     * whether the user enters the name with capitals or not.
     * @param s the other subscription to compare to
     * @return The difference between surnames, or the difference between first names if surnames are identical
     */
    public int compareTo(Subscriber s)
    {
        String formattedSurname = surname.toLowerCase();
        String formattedFirstName = firstName.toLowerCase();

        int surnameComp = formattedSurname.compareTo(s.surname.toLowerCase());
        int firstNameComp = formattedFirstName.compareTo(s.firstName.toLowerCase());

        if (surnameComp != 0) // If two subscribers have the same surname, compare and order by first name
            return surnameComp;
        else
            return firstNameComp;
    }

    /**
     * Equals override which equates one subscriber to another if they have the same first and last name.
     * @param other The other subscriber to equality check against
     * @return boolean depending on the result of the equality check
     */
    public boolean equals(Subscriber other)
    {
        return (firstName.equals(other.firstName) && surname.equals(other.surname));
    }

    /**
     * Searches for a given Meal within a subscriber's subscriptions.
     * Returns the Subscription if found, null if not.
     * @param m The meal to search for
     * @return the Subscription if found, null if not.
     */
    public Subscription searchSubscriptions(Meal m)
    {
        for (Subscription s : subscriptions)
        {
            if (s.getMeal().equals(m))
                return s;
        }
        return null;
    }

    /**
     * This method prints a subscriber's name, followed by their subscriptions.
     */
    public void printInfo()
    {
        System.out.println(getName() + ":");

        if (getSubscriptions().isEmpty()) // checks if subscriber has no meals added
            System.out.println("No meals!");
        else
        {
            for (Subscription s : getSubscriptions()) // prints the subscriptions line by line
                System.out.println(s.toString());
        }
    }
}