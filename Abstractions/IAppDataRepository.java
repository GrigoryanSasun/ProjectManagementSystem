package Abstractions;

import Core.AppPhase;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public interface IAppDataRepository {
    AppPhase GetApplicationPhase();
    void SetApplicationPhase(AppPhase newPhase);
}
