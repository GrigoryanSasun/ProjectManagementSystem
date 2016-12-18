import Abstractions.*;
import Core.AppPhase;
import Repositories.InMemoryAppDataProvider;
import Repositories.InMemoryProjectRepository;
import Repositories.InMemoryRoleRepository;
import Repositories.InMemoryStudentRepository;

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
                nextPhase = AppPhase.STUDENT_PROJECT_MAP;
                break;
            case STUDENT_PROJECT_MAP:
                nextPhase = AppPhase.PROJECT_MANAGEMENT;
                break;
        }
        return nextPhase;
    }

    public static void main(String[] args) {
	    IAppDataRepository appDataProvider = new InMemoryAppDataProvider();
        AppPhase appPhase = appDataProvider.GetApplicationPhase();

        IInputReader inputReader = new ConsoleInputReader();
        IRoleRepository roleRepository = new InMemoryRoleRepository();
        IStudentRepository studentRepository = new InMemoryStudentRepository();
        IProjectRepository projectRepository = new InMemoryProjectRepository();
        AppPhaseHandlerFactory factory = new AppPhaseHandlerFactory(inputReader, roleRepository, studentRepository, projectRepository);

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
