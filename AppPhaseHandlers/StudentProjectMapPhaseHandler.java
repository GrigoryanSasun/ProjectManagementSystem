/**
 * Created by Grigoryan on 18.12.2016.
 */
package AppPhaseHandlers;

import Abstractions.*;
import Core.InvalidInfo;
import Core.Project;
import Core.Role;
import Core.Student;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class StudentProjectMapPhaseHandler implements IAppPhaseHandler {

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
        System.out.println("ID\tName");
        for (Project project : allProjects) {
            int id = project.GetId();
            String name = project.GetName();
            System.out.println(id + "\t" + name + "\t");
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
            System.out.println("ID\tName");
            int id = foundProject.GetId();
            String name = foundProject.GetName();
            System.out.println(id + "\t" + name);
            Integer[] assignedRoleIds = foundProject.GetAssignedRoleIds();
            for (Integer roleId : assignedRoleIds) {
                Role foundRole = _roleRepository.GetRoleById(roleId);
                Student foundStudent = foundProject.GetStudentByRoleId(roleId);
                System.out.println(foundStudent.GetFirstName() + " as " + foundRole.GetName());
            }
        }
    }

    private void MapProjectAndStudent() {
        System.out.println("Specify the project id: ");
        int projectId = _inputReader.GetInteger();
        Project foundProject = _projectRepository.GetProjectById(projectId);
        if (foundProject == null) {
            System.out.println("No project found with specified id");
            return;
        }
        System.out.println("Specify the student id: ");
        String studentId = _inputReader.GetString();
        Student foundStudent = _studentRepository.GetStudentById(studentId);
        if (foundStudent == null) {
            System.out.println("No student found with specified id");
            return;
        }
        System.out.println("Specify the role id: ");
        int roleId = _inputReader.GetInteger();
        Role foundRole = _roleRepository.GetRoleById(roleId);
        if (foundRole == null) {
            System.out.println("No role found with specified id");
            return;
        }
        Student oldStudent = foundProject.GetStudentByRoleId(roleId);
        Project oldProject = foundStudent.GetProjectByRoleId(roleId);
        if (oldStudent != null) {
            oldStudent.RemoveFromRole(roleId);
        }
        if (oldProject != null) {
            oldProject.RemoveFromRole(roleId);
        }
        foundStudent.SetProjectByRoleId(roleId, foundProject);
        foundProject.SetStudentByRoleId(roleId, foundStudent);
        System.out.println("Mapping succeeded");
    }

    private InvalidInfo<Student>[] GetInvalidStudents() {
        Role[] requiredRoles = _roleRepository.GetRequiredRoles();
        List<InvalidInfo<Student>> invalidStudentInfos = new Vector<InvalidInfo<Student>>();
        Student[] allStudents = _studentRepository.GetAllStudents();
        for (Student student : allStudents) {
            InvalidInfo<Student> invalidStudentInfo = null;
            for (Role requiredRole : requiredRoles) {
                if (student.GetProjectByRoleId(requiredRole.GetId()) == null) {
                    if (invalidStudentInfo == null) {
                        invalidStudentInfo = new InvalidInfo<Student>();
                        invalidStudentInfo.SetInvalidInfo(student);
                    }
                    invalidStudentInfo.addRequiredRoleId(requiredRole.GetId());
                }
            }
            if (invalidStudentInfo != null) {
                invalidStudentInfos.add(invalidStudentInfo);
            }
        }
        return invalidStudentInfos.toArray(new InvalidInfo[0]);
    }

    private InvalidInfo<Project>[] GetInvalidProjects() {
        Role[] requiredRoles = _roleRepository.GetRequiredRoles();
        List<InvalidInfo<Project>> invalidProjectInfos = new Vector<InvalidInfo<Project>>();
        Project[] allProjects = _projectRepository.GetAllProjects();
        for (Project project : allProjects) {
            InvalidInfo<Project> invalidProjectInfo = null;
            for (Role requiredRole : requiredRoles) {
                if (project.GetStudentByRoleId(requiredRole.GetId()) == null) {
                    if (invalidProjectInfo == null) {
                        invalidProjectInfo = new InvalidInfo<Project>();
                        invalidProjectInfo.SetInvalidInfo(project);
                    }
                    invalidProjectInfo.addRequiredRoleId(requiredRole.GetId());
                }
            }
            if (invalidProjectInfo != null) {
                invalidProjectInfos.add(invalidProjectInfo);
            }
        }
        return invalidProjectInfos.toArray(new InvalidInfo[0]);
    }

    private InvalidInfo<Student>[] ValidateStudents() {
        InvalidInfo<Student>[] invalidStudentInfos = GetInvalidStudents();
        if (invalidStudentInfos.length == 0) {
            System.out.println("All students are valid");
        } else {
            System.out.println("ID\tFirstname\tLastname");
            for (InvalidInfo<Student> info : invalidStudentInfos) {
                Student student = info.GetInvalidInfo();
                String id = student.GetId();
                String firstName = student.GetFirstName();
                String lastName = student.GetLastName();
                System.out.println(id + "\t" + firstName + "\t" + lastName);
                System.out.println("Required roles:");
                Integer[] roleIds = info.GetRequiredRoleIds();
                for (int roleId: roleIds) {
                    Role foundRole = _roleRepository.GetRoleById(roleId);
                    System.out.println(roleId + "\t" + foundRole.GetName());
                }
            }
        }
        return invalidStudentInfos;
    }

    private InvalidInfo<Project>[] ValidateProjects() {
        InvalidInfo<Project>[] invalidProjectInfos = GetInvalidProjects();
        if (invalidProjectInfos.length == 0) {
            System.out.println("All projects are valid");
        } else {
            System.out.println("ID\tName");
            for (InvalidInfo<Project> info : invalidProjectInfos) {
                Project project = info.GetInvalidInfo();
                int id = project.GetId();
                String name = project.GetName();
                System.out.println(id + "\t" + name);
                System.out.println("Required roles:");
                Integer[] roleIds = info.GetRequiredRoleIds();
                for (int roleId: roleIds) {
                    Role foundRole = _roleRepository.GetRoleById(roleId);
                    System.out.println(roleId + "\t" + foundRole.GetName());
                }
            }
        }
        return invalidProjectInfos;
    }

    private boolean IsThePhaseValid()
    {
        InvalidInfo<Student>[] invalidStudentInfos = ValidateStudents();
        InvalidInfo<Project>[] invalidProjectInfos = ValidateProjects();
        if (invalidStudentInfos.length == 0 && invalidProjectInfos.length == 0) {
            System.out.println("The phase is valid");
            return true;
        }
        return false;
    }



    public boolean Handle() {
        Scanner scanner = new Scanner(System.in);
        boolean wasHandled = false;
        boolean shouldContinue = true;
        while (shouldContinue) {
            System.out.println("*** STUDENT-PROJECT MAP PHASE ***");
            System.out.println("(1) View all students");
            System.out.println("(2) View all projects");
            System.out.println("(3) Get student by id");
            System.out.println("(4) Get project by id");
            System.out.println("(5) Map project and student");
            System.out.println("(6) Validate students");
            System.out.println("(7) Validate projects");
            System.out.println("(8) Complete the phase");
            System.out.println("(9) Exit");
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
                    MapProjectAndStudent();
                    break;
                case 6:
                    ValidateStudents();
                    break;
                case 7:
                    ValidateProjects();
                    break;
                case 8:
                    if (IsThePhaseValid()) {
                        wasHandled = true;
                        shouldContinue = false;
                    }
                    break;
                case 9:
                    System.out.println("Exiting from student-project mapping phase.");
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again");
            }
        }
        return wasHandled;
    }

    public StudentProjectMapPhaseHandler(IInputReader inputReader,
                                         IRoleRepository roleRepository,
                                         IStudentRepository studentRepository,
                                         IProjectRepository projectRepository) {
        _roleRepository = roleRepository;
        _inputReader = inputReader;
        _studentRepository = studentRepository;
        _projectRepository = projectRepository;
    }
}
