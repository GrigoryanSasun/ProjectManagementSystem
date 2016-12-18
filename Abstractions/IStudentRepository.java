package Abstractions;
import Core.Student;
import Exceptions.StudentAlreadyExistsException;
import Exceptions.StudentDoesNotExistException;

/**
 * Created by Grigoryan on 18.12.2016.
 */
public interface IStudentRepository {
    int GetStudentCount();
    Student[] GetAllStudents();
    Student GetStudentById(String studentId);
    void InsertStudent(Student newStudent) throws StudentAlreadyExistsException;
    void UpdateStudent(Student updatedStudent) throws StudentDoesNotExistException;
    void RemoveStudent(String studentId) throws StudentDoesNotExistException;
    void SaveChanges();
}
