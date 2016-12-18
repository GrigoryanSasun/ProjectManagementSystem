/**
 * Created by Grigoryan on 11.11.2016.
 */
package AppPhaseHandlers;

import Abstractions.IAppPhaseHandler;
import Abstractions.IInputReader;
import Abstractions.IProjectRepository;
import Core.Project;
import Exceptions.ProjectAlreadyExistsException;
import Exceptions.ProjectDoesNotExistException;

import java.util.Scanner;

public class ProjectInputPhaseHandler implements IAppPhaseHandler {
    private IInputReader _inputReader;

    private IProjectRepository _projectRepository;

    private void PrintProjects(Project[] projects) {
        System.out.println("ID\tName");
        for (Project project: projects)
        {
            int id = project.GetId();
            String name = project.GetName();
            System.out.println(id + "\t" + name + "\t");
        }
    }

    private void ViewAllProjects()
    {
        Project[] allProjects = _projectRepository.GetAllProjects();
        if (allProjects.length == 0) {
            System.out.println("No projects found");
        }
        else {
            PrintProjects(allProjects);
        }
    }

    private void GetProjectById() {
        System.out.println("Specify the project id: ");
        int projectId = _inputReader.GetInteger();
        Project foundProject = _projectRepository.GetProjectById(projectId);
        if (foundProject == null) {
            System.out.println("No project found with specified id");
        }
        else {
            int id = foundProject.GetId();
            String name = foundProject.GetName();
            String description = foundProject.GetDescription();
            System.out.println(id + "\t" + name);
            System.out.println(description);
        }
    }

    private void InsertProject()
    {
        Project project = new Project();
        System.out.println("Specify the new project name: ");
        String name = _inputReader.GetString();
        System.out.println("Specify the new project description: ");
        String description = _inputReader.GetString();
        project.SetName(name);
        project.SetDescription(description);
        try {
            _projectRepository.InsertProject(project);
            System.out.println("The project successfully inserted");
        }
        catch(ProjectAlreadyExistsException exc)
        {
            System.out.println("A project with specified name already exists!");
        }
    }

    private void EditProjectById()
    {
        System.out.println("Specify the project id: ");
        int projectId = _inputReader.GetInteger();
        Project foundProject = _projectRepository.GetProjectById(projectId);
        if (foundProject == null) {
            System.out.println("No project found with specified id");
        }
        else {
            Project editedProject = new Project();
            editedProject.SetId(foundProject.GetId());
            System.out.println("Specify the new project name");
            String newProjectName = _inputReader.GetString();
            editedProject.SetName(newProjectName);
            System.out.println("Specify the new project description");
            String newProjectDescription = _inputReader.GetString();
            editedProject.SetDescription(newProjectDescription);
            try {
                _projectRepository.UpdateProject(editedProject);
                System.out.println("The project successfully updated");
            }
            catch(ProjectDoesNotExistException noRoleException)
            {
                System.out.println("The project does not exist");
            }
            catch(ProjectAlreadyExistsException roleExistsException)
            {
                System.out.println("A project with specified name already exists");
            }
        }
    }

    private void RemoveProjectById()
    {
        System.out.println("Specify the project id: ");
        int projectId = _inputReader.GetInteger();
        try {
            _projectRepository.RemoveProject(projectId);
            System.out.println("The project successfully removed");
        }
        catch(ProjectDoesNotExistException exc) {
            System.out.println("No project with specified id exists");
        }
    }

    private boolean IsThePhaseComplete()
    {
        return _projectRepository.GetProjectCount() > 0;
    }

    public boolean Handle() {
        Scanner scanner = new Scanner(System.in);
        boolean wasHandled = false;
        boolean shouldContinue = true;
        while(shouldContinue)
        {
            System.out.println("*** PROJECT INPUT PHASE ***");
            System.out.println("(1) View all projects");
            System.out.println("(2) Get project by id");
            System.out.println("(3) Insert project");
            System.out.println("(4) Edit project by id");
            System.out.println("(5) Remove project by id");
            System.out.println("(6) Complete the phase");
            System.out.println("(7) Exit");
            System.out.println("Your choice: ");
            int choice = scanner.nextInt();
            switch(choice)
            {
                case 1:
                    ViewAllProjects();
                    break;
                case 2:
                    GetProjectById();
                    break;
                case 3:
                    InsertProject();
                    break;
                case 4:
                    EditProjectById();
                    break;
                case 5:
                    RemoveProjectById();
                    break;
                case 6:
                    if (IsThePhaseComplete())
                    {
                        System.out.println("The phase successfully completed");
                        wasHandled = true;
                        shouldContinue = false;
                    }
                    else
                    {
                        System.out.println("The phase cannot be complete.");
                        System.out.println("There should be at least one project.");
                    }
                    break;
                case 7:
                    System.out.println("Exiting from project input phase.");
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again");
            }
        }
        return wasHandled;
    }

    public ProjectInputPhaseHandler(IInputReader inputReader, IProjectRepository projectRepository)
    {
        _inputReader = inputReader;
        _projectRepository = projectRepository;
    }
}
