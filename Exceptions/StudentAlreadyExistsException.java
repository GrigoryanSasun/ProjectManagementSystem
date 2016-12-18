package Exceptions;

/**
 * Created by Grigoryan on 18.12.2016.
 */
public class StudentAlreadyExistsException extends Exception {
    public StudentAlreadyExistsException()
    {
        super("The student with the specified id already exists");
    }
}
