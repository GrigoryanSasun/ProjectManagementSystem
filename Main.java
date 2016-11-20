import Abstractions.IAppDataRepository;
import Abstractions.IAppPhaseHandler;
import Abstractions.IInputReader;
import Abstractions.IRoleRepository;
import Core.AppPhase;
import Repositories.InMemoryAppDataProvider;
import Repositories.InMemoryRoleRepository;

public class Main {
    private static AppPhase GetNextPhase(AppPhase phase)
    {
        AppPhase nextPhase = null;
        switch (phase)
        {
            case ROLE_INPUT:
                nextPhase = AppPhase.STUDENT_INPUT;
                break;
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
	    IAppDataRepository appDataProvider = new InMemoryAppDataProvider();
        AppPhase appPhase = appDataProvider.GetApplicationPhase();

        IInputReader inputReader = new ConsoleInputReader();
        IRoleRepository roleRepository = new InMemoryRoleRepository();
        AppPhaseHandlerFactory factory = new AppPhaseHandlerFactory(inputReader, roleRepository);

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
