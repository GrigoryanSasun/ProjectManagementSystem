package Repositories;

import Abstractions.IAppDataRepository;
import Core.AppPhase;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public class InMemoryAppDataProvider implements IAppDataRepository {

    private AppPhase _appPhase;

    public AppPhase GetApplicationPhase() {
        return _appPhase;
    }

    public void SetApplicationPhase(AppPhase newPhase) {
        _appPhase = newPhase;
    }

    public InMemoryAppDataProvider()
    {
        _appPhase = AppPhase.ROLE_INPUT;
    }

}
