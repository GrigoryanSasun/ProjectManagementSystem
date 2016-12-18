package Exceptions;

/**
 * Created by Grigoryan on 18.12.2016.
 */
public class ProjectAlreadyExistsException extends Exception {
    public ProjectAlreadyExistsException()
    {
        super("The project with the specified name already exists");
    }
}