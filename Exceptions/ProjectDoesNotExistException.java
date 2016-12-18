package Exceptions;

/**
 * Created by Grigoryan on 18.12.2016.
 */
public class ProjectDoesNotExistException extends Exception {
    public ProjectDoesNotExistException()
    {
        super("The project with the specified id does not exist");
    }
}
