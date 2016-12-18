package Core;

import Exceptions.InvalidProjectPhaseException;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Grigoryan on 18.12.2016.
 */
public class Project {
    private int _id;
    private String _name;
    private String _description;
    private HashMap<Integer,Student> _roleIdStudentMap;
    private ProjectPhase _phase;

    public int GetId() {
        return  _id;
    }

    public void SetId(int id) {
        _id = id;
    }

    public String GetName() {
        return  _name;
    }

    public void SetName(String name) {
        _name = name;
    }

    public String GetDescription() {
        return  _description;
    }

    public void SetDescription(String description) {
        _description = description;
    }

    public Student GetStudentByRoleId(int roleId)
    {
        return _roleIdStudentMap.get(roleId);
    }

    private int GetIntegerForPhase(ProjectPhase phase)
    {
        int value = 0;
        switch(phase) {
            case DEVELOPMENT:
                value = 0;
                break;
            case STAGING:
                value = 1;
                break;
            case PRODUCTION:
                value = 2;
                break;
        }
        return value;
    }

    public ProjectPhase GetProjectPhase()
    {
        return _phase;
    }

    public void SetProjectPhase(ProjectPhase newPhase) throws InvalidProjectPhaseException
    {
        boolean isValid = false;
        if (newPhase != null) {
            int currentPhaseValue = GetIntegerForPhase(_phase);
            int newPhaseValue = GetIntegerForPhase(newPhase);
            if (newPhaseValue >= currentPhaseValue) {
                isValid = true;
            }
        }
        if (isValid) {
            _phase = newPhase;
        }
        else {
            throw new InvalidProjectPhaseException();
        }
    }

    public void SetStudentByRoleId(int roleId, Student student)
    {
        Integer[] roleIds = GetAssignedRoleIds();
        for (Integer assignedRoleId:roleIds)
        {
            Student foundStudent = GetStudentByRoleId(assignedRoleId);
            if (foundStudent.GetId().equals(student.GetId())) {
                RemoveFromRole(assignedRoleId);
                break;
            }
        }
        _roleIdStudentMap.put(roleId, student);
    }

    public void RemoveFromRole(int roleId)
    {
        _roleIdStudentMap.remove(roleId);
    }

    public Integer[] GetAssignedRoleIds()
    {
        Set<Integer> keySet = _roleIdStudentMap.keySet();
        return keySet.toArray(new Integer[keySet.size()]);
    }

    public Project()
    {
        _roleIdStudentMap = new HashMap<Integer, Student>();
        _phase = ProjectPhase.DEVELOPMENT;
    }

}
