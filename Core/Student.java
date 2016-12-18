package Core;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public class Student {
    private String _id;
    private String _firstName;
    private String _lastName;
    private HashMap<Integer, Project> _roleIdProjectMap;

    public String GetId() {
        return  _id;
    }

    public void SetId(String newId) {
        _id = newId;
    }

    public String GetFirstName()
    {
        return _firstName;
    }

    public void SetFirstName(String newFirstName)
    {
        _firstName = newFirstName;
    }

    public String GetLastName()
    {
        return _lastName;
    }

    public void SetLastName(String newLastName)
    {
        _lastName = newLastName;
    }

    public Integer[] GetAssignedRoleIds()
    {
        Set<Integer> keySet = _roleIdProjectMap.keySet();
        return keySet.toArray(new Integer[keySet.size()]);
    }

    public Project GetProjectByRoleId(int roleId)
    {
        return _roleIdProjectMap.get(roleId);
    }

    public void SetProjectByRoleId(int roleId, Project project)
    {
        Integer[] roleIds = GetAssignedRoleIds();
        for (Integer assignedRoleId:roleIds)
        {
            Project foundProject = GetProjectByRoleId(assignedRoleId);
            if (foundProject.GetId() == project.GetId()) {
                RemoveFromRole(assignedRoleId);
                break;
            }
        }
        _roleIdProjectMap.put(roleId, project);
    }

    public void RemoveFromRole(int roleId)
    {
        _roleIdProjectMap.remove(roleId);
    }

    public Student()
    {
        _roleIdProjectMap = new HashMap<Integer, Project>();
    }

}
