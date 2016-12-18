package AppPhaseHandlers;

import Abstractions.IAppPhaseHandler;
import Abstractions.IInputReader;
import Abstractions.IStudentRepository;
import Core.Student;
import Exceptions.StudentAlreadyExistsException;
import Exceptions.StudentDoesNotExistException;

import java.util.Scanner;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public class StudentInputPhaseHandler implements IAppPhaseHandler {
    private IInputReader _inputReader;

    private IStudentRepository _studentRepository;

    private void PrintStudents(Student[] students) {
        System.out.println("ID\tFirstname\tLastname");
        for (Student student: students)
        {
            String id = student.GetId();
            String firstName = student.GetFirstName();
            String lastName = student.GetLastName();
            System.out.println(id + "\t" + firstName+ "\t" + lastName);
        }
    }

    private void ViewAllStudents()
    {
        Student[] allStudent = _studentRepository.GetAllStudents();
        if (allStudent.length == 0) {
            System.out.println("No students found");
        }
        else {
            PrintStudents(allStudent);
        }
    }

    private void GetStudentById() {
        System.out.println("Specify the student id: ");
        String studentId = _inputReader.GetString();
        Student foundStudent = _studentRepository.GetStudentById(studentId);
        if (foundStudent == null) {
            System.out.println("No student found with specified id");
        }
        else {
            PrintStudents(new Student[]{foundStudent});
        }
    }

    private void InsertStudent()
    {
        Student student = new Student();
        System.out.println("Specify the new student id: ");
        String id = _inputReader.GetString();
        System.out.println("Specify the new student firstname: ");
        String firstName = _inputReader.GetString();
        System.out.println("Specify the new student lastname: ");
        String lastName = _inputReader.GetString();
        student.SetId(id);
        student.SetFirstName(firstName);
        student.SetLastName(lastName);
        try {
            _studentRepository.InsertStudent(student);
            System.out.println("The student successfully inserted");
        }
        catch(StudentAlreadyExistsException exc)
        {
            System.out.println("A student with specified id already exists!");
        }
    }

    private void EditStudentById()
    {
        System.out.println("Specify the student id: ");
        String studentId = _inputReader.GetString();
        Student foundStudent = _studentRepository.GetStudentById(studentId);
        if (foundStudent == null) {
            System.out.println("No student found with specified id");
        }
        else {
            Student editedStudent = new Student();
            editedStudent.SetId(foundStudent.GetId());
            System.out.println("Specify the new student firstname");
            String newFirstName = _inputReader.GetString();
            System.out.println("Specify the new student lastname");
            String newLastName = _inputReader.GetString();
            editedStudent.SetFirstName(newFirstName);
            editedStudent.SetLastName(newLastName);
            try {
                _studentRepository.UpdateStudent(editedStudent);
                System.out.println("The student successfully updated");
            }
            catch(StudentDoesNotExistException exc) {
                System.out.println("The student with specified id does not exist");
            }
        }
    }

    private void RemoveStudentById()
    {
        System.out.println("Specify the student id: ");
        String studentId = _inputReader.GetString();
        try {
            _studentRepository.RemoveStudent(studentId);
            System.out.println("The student successfully removed");
        }
        catch(StudentDoesNotExistException exc) {
            System.out.println("No student with specified id exists");
        }
    }

    private boolean IsThePhaseComplete()
    {
        return _studentRepository.GetStudentCount() > 0;
    }

    public boolean Handle() {
        Scanner scanner = new Scanner(System.in);
        boolean wasHandled = false;
        boolean shouldContinue = true;
        while(shouldContinue)
        {
            System.out.println("*** Student INPUT PHASE ***");
            System.out.println("(1) View all students");
            System.out.println("(2) Get student by id");
            System.out.println("(3) Insert student");
            System.out.println("(4) Edit student by id");
            System.out.println("(5) Remove student by id");
            System.out.println("(6) Complete the phase");
            System.out.println("(7) Exit");
            System.out.println("Your choice: ");
            int choice = scanner.nextInt();
            switch(choice)
            {
                case 1:
                    ViewAllStudents();
                    break;
                case 2:
                    GetStudentById();
                    break;
                case 3:
                    InsertStudent();
                    break;
                case 4:
                    EditStudentById();
                    break;
                case 5:
                    RemoveStudentById();
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
                        System.out.println("There should be at least one student.");
                    }
                    break;
                case 7:
                    System.out.println("Exiting from student input phase.");
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again");
            }
        }
        return wasHandled;
    }

    public StudentInputPhaseHandler(IInputReader inputReader, IStudentRepository studentRepository)
    {
        _inputReader = inputReader;
        _studentRepository = studentRepository;
    }
}
