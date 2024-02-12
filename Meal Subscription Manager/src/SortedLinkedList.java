import java.util.*;
/**
 * This class extends LinkedList (of generic type E), and therefore has all the same methods you could invoke on a LinkedList,
 * with the addition of an Insertion Sort algorithm.
 *It is the class which is used to store Subscribers and Meals, as well as order them lexicographically.
 */
public class SortedLinkedList<E extends Comparable<E>> extends LinkedList<E>
{

    /**
     * This Insertion Sort algorithm first adds a generic element to a SortedLinkedList,
     * then compares elements based on the compareTo method it calls
     * (compareTo is overridden differently in the Subscriber and Meal classes).
     */
    public void addAndSort(E e)
    {
        this.add(e);

        for (int i = 1; i < this.size(); i++) // Outer loop
        {
            E value = this.get(i);
            int j;
            for (j = i; j > 0; j--) // Inner loop
            {
                if (this.get(j-1).compareTo(value) < 0)
                    break;
                else
                    this.set(j, this.get(j-1));
            }
            this.set(j, value);
        }
    }
}