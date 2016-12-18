package Exceptions;

import Core.ProjectPhase;

/**
 * Created by Grigoryan on 19.12.2016.
 */
public class InvalidProjectPhaseException extends Exception {
    public InvalidProjectPhaseException()
    {
        super("Invalid phase specified");
    }
}