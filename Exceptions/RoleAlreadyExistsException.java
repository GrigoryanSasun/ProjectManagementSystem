package Exceptions;

/**
 * Created by Grigoryan on 20.11.2016.
 */
public class RoleAlreadyExistsException extends Exception {
    public RoleAlreadyExistsException()
    {
        super("The role with the specified name already exists");
    }
}
