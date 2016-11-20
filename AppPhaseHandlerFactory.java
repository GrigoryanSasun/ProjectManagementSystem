import Abstractions.IAppPhaseHandler;
import Abstractions.IInputReader;
import Abstractions.IRoleRepository;
import AppPhaseHandlers.ProjectInputPhaseHandler;
import AppPhaseHandlers.ProjectManagementPhaseHandler;
import AppPhaseHandlers.RoleInputPhaseHandler;
import AppPhaseHandlers.StudentInputPhaseHandler;
import Core.AppPhase;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public class AppPhaseHandlerFactory {
    private IRoleRepository _roleRepository;
    private IInputReader _inputReader;

    public IAppPhaseHandler GetAppPhaseHandler(AppPhase appPhase)
    {
        if (appPhase == null) {
            return null;
        }
        IAppPhaseHandler appPhaseHandler = null;
        switch (appPhase)
        {
            case ROLE_INPUT:
                appPhaseHandler = new RoleInputPhaseHandler(_inputReader, _roleRepository);
                break;
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

    AppPhaseHandlerFactory(IInputReader reader, IRoleRepository roleRepository)
    {
        _inputReader = reader;
        _roleRepository = roleRepository;
    }
}
