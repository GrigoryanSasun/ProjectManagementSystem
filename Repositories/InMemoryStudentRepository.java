/**
 * Created by Grigoryan on 18.12.2016.
 */
package Repositories;

import Abstractions.IStudentRepository;
import Core.Student;
import Exceptions.StudentAlreadyExistsException;
import Exceptions.StudentDoesNotExistException;

import java.util.HashMap;

public class InMemoryStudentRepository implements IStudentRepository {
    private HashMap<String, Student> _studentIdStudentMapping;

    public int GetStudentCount() {
        return _studentIdStudentMapping.size();
    }

    public Student[] GetAllStudents() {
        return _studentIdStudentMapping.values().toArray(new Student[0]);
    }


    public Student GetStudentById(String studentId) {
        return _studentIdStudentMapping.get(studentId);
    }


    public void InsertStudent(Student newStudent) throws StudentAlreadyExistsException {
        String id = newStudent.GetId();
        if (!_studentIdStudentMapping.containsKey(id)) {
            _studentIdStudentMapping.put(id, newStudent);
        }
        else {
            throw new StudentAlreadyExistsException();
        }
    }


    public void UpdateStudent(Student updatedStudent) throws StudentDoesNotExistException {
        String updatedStudentId = updatedStudent.GetId();
        Student foundStudent = _studentIdStudentMapping.get(updatedStudentId);
        if (foundStudent != null) {
            _studentIdStudentMapping.put(updatedStudentId, foundStudent);
        }
        else {
            throw new StudentDoesNotExistException();
        }
    }


    public void RemoveStudent(String studentId) throws StudentDoesNotExistException {
        Student foundStudent = _studentIdStudentMapping.get(studentId);
        if (foundStudent != null) {
            _studentIdStudentMapping.remove(studentId);
        }
        else {
            throw new StudentDoesNotExistException();
        }
    }


    public void SaveChanges() {
        //in memory student repository
    }

    public InMemoryStudentRepository()
    {
        _studentIdStudentMapping = new HashMap<String, Student>();
    }

}
