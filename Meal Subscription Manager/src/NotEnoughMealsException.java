/**
 * A new exception for the case that there aren't enough meals to fulfil a request.
 */
public class NotEnoughMealsException extends Exception
{
    public NotEnoughMealsException(String message)
    {
        super(message);
    }
}
