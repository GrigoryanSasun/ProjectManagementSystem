/**
 * Created by Grigoryan on 10.11.2016.
 */
public class DummyAppDataProvider implements IAppDataProvider {

    private AppPhase _appPhase;

    public AppPhase GetApplicationPhase() {
        return _appPhase;
    }

    public void SetApplicationPhase(AppPhase newPhase) {
        _appPhase = newPhase;
    }

    public DummyAppDataProvider()
    {
        _appPhase = AppPhase.STUDENT_INPUT;
    }

}
