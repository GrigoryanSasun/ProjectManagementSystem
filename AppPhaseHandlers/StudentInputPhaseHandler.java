package AppPhaseHandlers;

import Abstractions.IAppPhaseHandler;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public class StudentInputPhaseHandler implements IAppPhaseHandler {
    public boolean Handle() {
        System.out.println("STUDENT INPUT PHASE");
        return true;
    }
}
