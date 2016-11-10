/**
 * Created by Grigoryan on 10.11.2016.
 */
public interface IAppDataProvider {
    AppPhase GetApplicationPhase();
    void SetApplicationPhase(AppPhase newPhase);
}
