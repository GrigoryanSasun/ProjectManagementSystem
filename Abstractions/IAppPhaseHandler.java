package Abstractions;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public interface IAppPhaseHandler {
    // return true if should proceed to the next phase
    // return false if the phase was not complete
    boolean Handle();
}
