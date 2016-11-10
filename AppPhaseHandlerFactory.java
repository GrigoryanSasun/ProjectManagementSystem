/**
 * Created by Grigoryan on 10.11.2016.
 */
public class AppPhaseHandlerFactory {
    public IAppPhaseHandler GetAppPhaseHandler(AppPhase appPhase)
    {
        if (appPhase == null) {
            return null;
        }
        IAppPhaseHandler appPhaseHandler = null;
        switch (appPhase)
        {
            case STUDENT_INPUT:
                appPhaseHandler = new StudentInputPhaseHandler();
                break;
            case PROJECT_INPUT:
                appPhaseHandler = new ProjectInputPhaseHandler();
                break;
            case PROJECT_MANAGEMENT:
                appPhaseHandler = new ProjectManagementPhaseHandler();
                break;
        }
        return appPhaseHandler;
    }
}
