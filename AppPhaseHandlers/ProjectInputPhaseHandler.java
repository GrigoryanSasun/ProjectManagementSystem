/**
 * Created by Grigoryan on 11.11.2016.
 */
package AppPhaseHandlers;

import Abstractions.IAppPhaseHandler;

public class ProjectInputPhaseHandler implements IAppPhaseHandler {
    public boolean Handle() {
        System.out.println("Handling project input phase");
        return true;
    }
}
