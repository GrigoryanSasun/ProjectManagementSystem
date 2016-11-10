public class Main {
    private static AppPhase GetNextPhase(AppPhase phase)
    {
        AppPhase nextPhase = null;
        switch (phase)
        {
            case STUDENT_INPUT:
                nextPhase =  AppPhase.PROJECT_INPUT;
                break;
            case PROJECT_INPUT:
                nextPhase =  AppPhase.PROJECT_MANAGEMENT;
                break;
        }
        return nextPhase;
    }

    public static void main(String[] args) {
	    IAppDataProvider appDataProvider = new DummyAppDataProvider();
        AppPhase appPhase = appDataProvider.GetApplicationPhase();
        AppPhaseHandlerFactory factory = new AppPhaseHandlerFactory();

        IAppPhaseHandler appPhaseHandler = factory.GetAppPhaseHandler(appPhase);

        while (true)
        {
            boolean shouldContinue = appPhaseHandler.Handle();
            if (shouldContinue) {
                appPhase = GetNextPhase(appPhase);
                appDataProvider.SetApplicationPhase(appPhase);
                appPhaseHandler = factory.GetAppPhaseHandler(appPhase);
            }
            else {
                break;
            }
        }
        System.out.println("BYE");
    }
}
