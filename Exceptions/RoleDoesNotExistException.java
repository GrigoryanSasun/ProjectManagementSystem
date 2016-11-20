package Exceptions;

/**
 * Created by Grigoryan on 20.11.2016.
 */
public class RoleDoesNotExistException extends Exception {
    public RoleDoesNotExistException()
    {
        super("The role with the specified id does not exist");
    }

}
