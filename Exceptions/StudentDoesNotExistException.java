package Exceptions;

/**
 * Created by Grigoryan on 18.12.2016.
 */
public class StudentDoesNotExistException extends Exception {
    public StudentDoesNotExistException()
    {
        super("The student with the specified id does not exist");
    }
}