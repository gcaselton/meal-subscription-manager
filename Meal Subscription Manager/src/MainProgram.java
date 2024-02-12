import java.util.*;
import java.io.*;

/**
 * The driver class for the program, containing the main method and multiple auxiliary methods which help the overall
 * functionality of the program. Some methods are modularised bits of code to improve readability.
 */
public class MainProgram
{
    /**
     * Declaring the SortedLinkedList of subscribers
     */
    private static SortedLinkedList<Subscriber> subscribers;
    /**
     * Declaring the SortedLinkedList of meals
     */
    private static SortedLinkedList<Meal> meals;
    /**
     * Scanner for reading keyboard input from user
     */
    private static Scanner k;
    /**
     * PrintWriter to write letters to a file
     */
    private static PrintWriter f;
    /**
     * This boolean helps to decide when the program needs to finish running
     */
    private static boolean done;

    public static void main(String[] args) throws IOException
    {

        k= new Scanner(System.in); //keyboard input
        f = new PrintWriter("letters.txt"); //prints to 'letters.txt'
        done = false;

        readFile();
        printWelcome();

        while (!done)
        {
            printMenu();
            String response = k.nextLine();

            switch (response) // decides how to proceed depending on which option the user selects
            {
                case "f": finish();
                break;
                case "m": displayMeals();
                nextOption();
                break;
                case "s": displaySubscribers();
                nextOption();
                break;
                case "a": addMeals();
                nextOption();
                break;
                case "r": removeMeals();
                nextOption();
                break;
                default: // error message if the user enters something other than one of the menu options
                    blankLine();
                    System.out.println("'" + response + "' is not valid. Please enter either m,s,a,r, or f");
                    blankLine();
            }
        }
        f.close();
    }

    /**
     * Reads the input file and adds and sorts subscribers and meals to their respective SortedLinkedLists.
     */
    private static void readFile()
    {
        try
        {
            Scanner fileIn = new Scanner(new FileReader("src/input_data.txt"));

            subscribers = new SortedLinkedList<>();

            int noOfSubscribers = Integer.parseInt(fileIn.nextLine());
            for (int i = 0; i < noOfSubscribers; i++) // splits each line of input into first and last name fields, then adds and sorts.
            {
                String[] split = fileIn.nextLine().split(" ");

                Subscriber s = new Subscriber(split[0], split[1]);
                subscribers.addAndSort(s);
            }

            meals = new SortedLinkedList<>();

            int noOfMealTypes = Integer.parseInt(fileIn.nextLine());
            for (int i = 0; i < noOfMealTypes; i++) // similarly, reads the meals and quantities then adds and sorts
            {
                String mealName = fileIn.nextLine();
                int mealNumber = Integer.parseInt(fileIn.nextLine());

                Meal m = new Meal(mealName, mealNumber);
                meals.addAndSort(m);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
    }

    /**
     * Blank line generator to make the UI a bit more readable between processes
     */
    private static void blankLine()
    {
        System.out.println();
    }

    /**
     * The dashed line appears at the end of a process and makes things a bit easier to read
     */
    private static void dash()
    {
        System.out.println("---------------------------------------------");
    }

    /**
     * This prints the greeting, the artwork and the first instruction
     */
    private static void printWelcome()
    {
        blankLine();
        System.out.println(randomGreeting());
        System.out.println("Welcome to Fred's Frozen Food Factory!");
        blankLine();
        System.out.println("""
                         (
                          )
                     __..---..__
                 ,-='  /  |  \\  `=-.
                :--..___________..--;
                 \\.,_____________,./"""); // ASCII art by Riitta Rasimus: https://www.asciiart.eu/food-and-drinks/other
        blankLine();
        System.out.println("Please select an option from the menu below by entering a letter:");
        blankLine();
    }

    /**
     * Prints the menu of options
     */
    private static void printMenu()
    {
        System.out.println("m - Display information about all the meals");
        System.out.println("s - Display information about all the subscribers");
        System.out.println("a - Add meals to a subscription");
        System.out.println("r - Remove meals from a subscription");
        System.out.println("f - Finish the program");
    }

    /**
     * Prints a goodbye message and sets done to true, ending the program
     */
    private static void finish()
    {
        blankLine();
        System.out.println("Bye for now!");
        done = true;
    }

    /**
     * Loops through the SortedLinkedList of meals and prints each meal's info
     */
    private static void displayMeals()
    {
        blankLine();
        for (Meal m : meals)
        {
            m.printInfo();
        }
    }

    /**
     * Loops through the SortedLinkedList of subscribers and prints each subscriber's info
     */
    private static void displaySubscribers()
    {
        blankLine();
        for (Subscriber s : subscribers)
        {
            s.printInfo();
            blankLine();
        }
    }

    /**
     * Allows the user to add a chosen meal to a chosen subscriber's subscriptions
     */
    private static void addMeals()
    {

        Meal chosenMeal = null; // declaring the chosen meal and subscriber objects for later use
        Subscriber chosenSub = null;

        boolean foundSubscriber = false;
        int counter = 2; // keeps track of how many attempts the user has made

        while (!foundSubscriber) // loop which prompts the user for input until a valid choice is made
        {
            blankLine();
            chosenSub = chooseSubscriber(); // calls the chooseSubscriber() method which returns the user's chosen subscriber

            if (chosenSub == null) // handles any case in which an invalid subscriber was entered
            {
                counter--;

                blankLine();
                System.out.println("Subscriber not found!");

                if (counter <= 0) // prints a reminder of valid subscribers if the user enters incorrectly twice
                {
                    blankLine();
                    System.out.println("Reminder - here is a list of valid subscribers:");
                    displaySubscribers();
                }
            }
            else
                foundSubscriber = true;
        }

        blankLine();
        chosenSub.printInfo();

        boolean foundMeal = false; // the following block of code operates very similarly to the above section
        int counter2 = 2;

        while (!foundMeal)
        {
            blankLine();
            chosenMeal = chooseMeal();

            if (chosenMeal == null)
            {
                counter2--;

                blankLine();
                System.out.println("Meal not found!");

                if(counter2 <= 0)
                {
                    blankLine();
                    System.out.println("Reminder - here is a list of valid meals:");
                    displayMeals();
                }
            }
            else if (chosenMeal.getNumberAvailable() == 0) // checks if there are not enough meals and prints a letter if so
            {
                blankLine();
                System.out.println("No " + chosenMeal.getName() + " left!");
                writeLetter(chosenSub, chosenMeal);
                return;
            }
            else
                foundMeal = true;
        }

        if (chosenSub.getSubscriptions().size() == 3 && chosenSub.searchSubscriptions(chosenMeal) == null) // checks if the subscriber already has 3 subscriptions and if they are trying to add a new one or edit an existing one
        {
            blankLine();
            System.out.println(chosenSub.getFirstName() + " has reached the maximum number of meal subscriptions (3)!");
            System.out.println("To delete one, remove all meals of that type");
            return;
        }

        blankLine();
        System.out.println(chosenMeal);
        blankLine();
        System.out.println("How many " + chosenMeal.getName() + " would " + chosenSub.getFirstName() + " like to add?");

        boolean valid = false;
        int number = 0;

        while (!valid) // checks for a valid meal number request
        {
            try
            {
                String input = k.nextLine();
                number = Integer.parseInt(input);

                if (chosenMeal.getNumberAvailable() - number < 0)
                {
                    throw new NotEnoughMealsException
                            ("There is not enough " + chosenMeal.getName() + " left to add " + number +
                                    " to this subscription.");
                }
                else if (number < 0)
                {
                    System.out.println(number + " is a negative number!");
                    System.out.println("Please enter a number between 0 - " + chosenMeal.getNumberAvailable() + ":");
                }
                else // adds the meal
                {
                    chosenSub.setSubscriptions(chosenMeal, number);
                    chosenMeal.setNumberAvailable(chosenMeal.getNumberAvailable() - number);
                    valid = true;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid format, please enter an integer:");
            }
            catch (NotEnoughMealsException e) // prints a letter if there are not enough to fulfil the request
            {
                System.out.println(e.getMessage());
                writeLetter(chosenSub, chosenMeal);
                System.out.println("Please enter a number between 0 - " + chosenMeal.getNumberAvailable() + ":");
            }
            blankLine();
        }
        System.out.println(number + " " + chosenMeal.getName() + " successfully added to "
                + chosenSub.getFirstName() + "'s subscription!");
    }

    /**
     * Prints a letter to file informing the subscriber that the request was not processed
     * @param s Subscriber
     * @param m The meal they tried to add
     */
    private static void writeLetter(Subscriber s, Meal m)
    {
        f.println("Dear " + s.getName() + ",");
        f.println();
        f.println("We regret to inform you that there was not enough " + m.getName()
                + " left to fulfil your culinary cravings.");
        f.println("Please accept our sincerest apologies at this most troubling of times.");
        f.println();
        f.println("Yours gastronomically,");
        f.println("Fred's Frozen Food Factory");
        f.println();
    }

    /**
     * Searches for the chosen subscriber and returns it if valid.
     * @return Chosen subscriber, or null if not found
     */
    private static Subscriber chooseSubscriber()
    {
        System.out.println("Please select a subscriber by entering their full name:");
        String input = k.nextLine().toLowerCase();

        String[] names = input.split(" "); // splits the input into first and last name

        if (names.length != 2)
            return null;

        Subscriber searchItem = new Subscriber(names[0], names[1]);
        int result = binarySearch(subscribers, searchItem); // Binary search for the subscriber

        if (result == -1) // not found
            return null;
        else
            return subscribers.get(result);
    }

    /**
     * Searches for the chosen meal and returns it if valid.
     * @return Chosen meal, or null if not found
     */
    private static Meal chooseMeal()
    {
        System.out.println("Please select a meal by entering its name:");
        String input = k.nextLine().toLowerCase();

        Meal searchItem = new Meal(input);
        int result = binarySearch(meals, searchItem); // Binary search

        if (result == -1) // not found
            return null;
        else
            return meals.get(result);
    }

    /**
     * Similar to addMeals(), this allows the user to select a subscriber and meal then removes it from
     * the subscription if the request is valid.
     */
    private static void removeMeals()
    {
        Subscriber chosenSub = null;
        Meal chosenMeal = null;
        Subscription chosenSubscription = null;

        boolean foundSubscriber = false;

        while (!foundSubscriber) // loops through prompting the user to choose a subscriber
        {
            blankLine();
            chosenSub = chooseSubscriber();

            if (chosenSub == null)
            {
                blankLine();
                System.out.println("Subscriber not found!");
            }
            else
                foundSubscriber = true;
        }

        blankLine();

        if (chosenSub.getSubscriptions().isEmpty()) // returns back to the menu if the user chooses a subscriber with no meals
        {
            System.out.println(chosenSub.getFirstName() + " has no subscriptions to remove from!");
            return;
        }

        chosenSub.printInfo();

        boolean foundMeal = false;

        while (!foundMeal) // prompts the user to enter a valid meal
        {
            blankLine();
            chosenMeal = chooseMeal();

            if (chosenMeal == null)
            {
                blankLine();
                System.out.println("Meal not found!");
            }
            else if (chosenSub.searchSubscriptions(chosenMeal) == null)
            {
                blankLine();
                System.out.println(chosenSub.getFirstName() + " is not subscribed to " + chosenMeal.getName() + "!");
            }
            else
                foundMeal = true;
        }

        chosenSubscription = chosenSub.searchSubscriptions(chosenMeal);

        blankLine();
        System.out.println(chosenSubscription.toString());
        blankLine();

        System.out.println("How many " + chosenMeal.getName() + " would "
                + chosenSub.getFirstName() + " like to remove?");

        boolean valid = false;
        int number = 0;

        while (!valid) // prompts for a valid number to remove
        {
            try
            {
                String input = k.nextLine();
                number = Integer.parseInt(input);

                if (chosenSubscription.getQuantity() - number < 0)
                {
                    blankLine();
                    System.out.println("You are trying to remove too many meals!");
                    System.out.println("Please enter a number between 0 - " + chosenSubscription.getQuantity() + ":");
                }
                else if (number < 0)
                {
                    blankLine();
                    System.out.println(number + " is a negative number!");
                    System.out.println("Please enter a number between 0 - " + chosenSubscription.getQuantity() + ":");
                }
                else // removes the meal(s)
                {
                    chosenSubscription.setQuantity(chosenSubscription.getQuantity() - number);
                    chosenMeal.setNumberAvailable(chosenMeal.getNumberAvailable() + number);
                    valid = true;
                }
            }
            catch (NumberFormatException e)
            {
                blankLine();
                System.out.println("Invalid format, please enter an integer:");
            }
            blankLine();
        }
        blankLine();

        if (chosenSubscription.getQuantity() == 0) // removes a subscription from the ArrayList if quantity is set to 0
        {
            chosenSub.getSubscriptions().remove(chosenSubscription);
            System.out.println("Subscription successfully removed from " + chosenSub.getFirstName() + "'s account!");
        }
        else
        {
            System.out.println(number + " " + chosenMeal.getName() + " successfully removed from "
                    + chosenSub.getFirstName() + "'s subscription!");
        }
    }

    /**
     * Prompts to select another option from the menu
     */
    private static void nextOption()
    {
        blankLine();
        dash();
        System.out.println("Please select another option: ");
        blankLine();
    }

    /**
     * Binary Search algorithm I included for scalability. Checks through a SortedLinkedList for the Meal or Subscriber
     * @param a The list to search through
     * @param searchItem The item to find
     * @return The index of the found item, or -1 if not found
     * @param <E> An object which extends the Comparable interface
     */
    private static <E extends Comparable<E>> int binarySearch(SortedLinkedList<E> a, E searchItem)
    {
        int first = 0;
        int last = a.size() - 1;
        int mid = -1;
        boolean found = false;

        while (first <= last && !found)
        {
            mid = (first + last)/2;
            int result = a.get(mid).compareTo(searchItem);
            if (result == 0)
                found = true;
            else
                if (result > 0)
                    last = mid - 1;
                else
                    first = mid + 1;
        }

        if (found)
            return mid;
        else
            return -1;
    }

    /**
     * Generates a random greeting upon opening the program
     * @return Random greeting
     */
    private static String randomGreeting()
    {
        Random rand = new Random();
        String s = "";

        int i = rand.nextInt(5);

        s = switch (i) {
            case 0 -> "Hello there!";
            case 1 -> "Howdy, partner!";
            case 2 -> "Aloha!";
            case 3 -> "Ahoy there, Captain!";
            case 4 -> "Greetings, fellow sentient being!";
            default -> s;
        };
        return s;
    }
}