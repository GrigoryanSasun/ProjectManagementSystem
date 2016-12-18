import Abstractions.*;
import AppPhaseHandlers.*;
import Core.AppPhase;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public class AppPhaseHandlerFactory {
    private IRoleRepository _roleRepository;
    private IStudentRepository _studentRepository;
    private IProjectRepository _projectRepository;

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
                appPhaseHandler = new StudentInputPhaseHandler(_inputReader, _studentRepository,_roleRepository);
                break;
            case PROJECT_INPUT:
                appPhaseHandler = new ProjectInputPhaseHandler(_inputReader, _projectRepository,_studentRepository);
                break;
            case STUDENT_PROJECT_MAP:
                appPhaseHandler = new StudentProjectMapPhaseHandler(_inputReader,_roleRepository,_studentRepository,_projectRepository);
                break;
            case PROJECT_MANAGEMENT:
                appPhaseHandler = new ProjectManagementPhaseHandler(_inputReader,_roleRepository,_studentRepository,_projectRepository);
                break;
        }
        return appPhaseHandler;
    }

    AppPhaseHandlerFactory(IInputReader reader,
                           IRoleRepository roleRepository,
                           IStudentRepository studentRepository,
                           IProjectRepository projectRepository)
    {
        _inputReader = reader;
        _roleRepository = roleRepository;
        _studentRepository = studentRepository;
        _projectRepository = projectRepository;
    }
}
