package AppPhaseHandlers;

import Abstractions.*;
import Core.*;
import Exceptions.InvalidProjectPhaseException;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Grigoryan on 11.11.2016.
 */
public class ProjectManagementPhaseHandler implements IAppPhaseHandler {
    private IStudentRepository _studentRepository;
    private IProjectRepository _projectRepository;
    private IRoleRepository _roleRepository;
    private IInputReader _inputReader;

    private void ViewAllStudents() {
        Student[] allStudents = _studentRepository.GetAllStudents();
        System.out.println("ID\tFirstname\tLastname");
        for (Student student : allStudents) {
            String id = student.GetId();
            String firstName = student.GetFirstName();
            String lastName = student.GetLastName();
            System.out.println(id + "\t" + firstName + "\t" + lastName);
        }
    }

    private void ViewAllProjects() {
        Project[] allProjects = _projectRepository.GetAllProjects();
        System.out.println("ID\tName\tPhase");
        for (Project project : allProjects) {
            int id = project.GetId();
            String name = project.GetName();
            ProjectPhase phase = project.GetProjectPhase();
            System.out.println(id + "\t" + name + "\t" + "\t" + phase.toString());
        }
    }

    private void GetStudentById() {
        System.out.println("Specify the student id: ");
        String studentId = _inputReader.GetString();
        Student foundStudent = _studentRepository.GetStudentById(studentId);
        if (foundStudent == null) {
            System.out.println("No student found with specified id");
        } else {
            System.out.println("ID\tFirstname\tLastname");
            String id = foundStudent.GetId();
            String firstName = foundStudent.GetFirstName();
            String lastName = foundStudent.GetLastName();
            System.out.println(id + "\t" + firstName + "\t" + lastName);
            Integer[] assignedRoleIds = foundStudent.GetAssignedRoleIds();
            for (Integer roleId : assignedRoleIds) {
                Role foundRole = _roleRepository.GetRoleById(roleId);
                Project foundProject = foundStudent.GetProjectByRoleId(roleId);
                System.out.println(foundRole.GetName() + " for " + foundProject.GetName());
            }
        }
    }

    private void GetProjectById() {
        System.out.println("Specify the project id: ");
        int projectId = _inputReader.GetInteger();
        Project foundProject = _projectRepository.GetProjectById(projectId);
        if (foundProject == null) {
            System.out.println("No project found with specified id");
        } else {
            System.out.println("ID\tName\tPhase");
            int id = foundProject.GetId();
            String name = foundProject.GetName();
            ProjectPhase phase = foundProject.GetProjectPhase();
            System.out.println(id + "\t" + name + "\t" + phase);
            System.out.println("Description:");
            System.out.println(foundProject.GetDescription());
            System.out.println("Assigned students:");
            Integer[] assignedRoleIds = foundProject.GetAssignedRoleIds();
            for (Integer roleId : assignedRoleIds) {
                Role foundRole = _roleRepository.GetRoleById(roleId);
                Student foundStudent = foundProject.GetStudentByRoleId(roleId);
                System.out.println(foundStudent.GetFirstName() + " as " + foundRole.GetName());
            }
        }
    }

    private void SetProjectPhase()
    {
        System.out.println("Specify the project id: ");
        int projectId = _inputReader.GetInteger();
        Project foundProject = _projectRepository.GetProjectById(projectId);
        if (foundProject == null) {
            System.out.println("No project found with specified id");
        }
        else {
            System.out.println("Specify the phase (0 - dev, 1 - staging, 2 - production)");
            int phaseValue = _inputReader.GetInteger();
            ProjectPhase newPhase = null;
            switch(phaseValue)
            {
                case 0:
                    newPhase = ProjectPhase.DEVELOPMENT;
                    break;
                case 1:
                    newPhase = ProjectPhase.STAGING;
                    break;
                case 2:
                    newPhase = ProjectPhase.PRODUCTION;
                    break;
            }
            try {
                foundProject.SetProjectPhase(newPhase);
                _projectRepository.UpdateProject(foundProject);
                System.out.println("The project successfully updated");
            }
            catch(InvalidProjectPhaseException exc)
            {
                System.out.println(exc.getMessage());
            }
            catch(Exception exc)
            {}
        }
    }


    public boolean Handle() {
        Scanner scanner = new Scanner(System.in);
        boolean wasHandled = false;
        boolean shouldContinue = true;
        while (shouldContinue) {
            System.out.println("*** PROJECT MANAGEMENT PHASE ***");
            System.out.println("(1) View all students");
            System.out.println("(2) View all projects");
            System.out.println("(3) Get student by id");
            System.out.println("(4) Get project by id");
            System.out.println("(5) Set project phase");
            System.out.println("(6) Exit");
            System.out.println("Your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    ViewAllStudents();
                    break;
                case 2:
                    ViewAllProjects();
                    break;
                case 3:
                    GetStudentById();
                    break;
                case 4:
                    GetProjectById();
                    break;
                case 5:
                    SetProjectPhase();
                    break;
                case 6:
                    shouldContinue = false;
                    wasHandled = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again");
            }
        }
        return wasHandled;
    }

    public ProjectManagementPhaseHandler(IInputReader inputReader,
                                         IRoleRepository roleRepository,
                                         IStudentRepository studentRepository,
                                         IProjectRepository projectRepository) {
        _roleRepository = roleRepository;
        _inputReader = inputReader;
        _studentRepository = studentRepository;
        _projectRepository = projectRepository;
    }
}
