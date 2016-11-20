/**
 * Created by Grigoryan on 20.11.2016.
 */
package Repositories;

import Abstractions.IRoleRepository;
import Core.Role;
import Exceptions.RoleAlreadyExistsException;
import Exceptions.RoleDoesNotExistException;

import java.util.HashMap;

public class InMemoryRoleRepository implements IRoleRepository {
    private HashMap<Integer, Role> _roleIdRoleMapping;
    private HashMap<String, Boolean> _roleNameSet;

    public int GetRoleCount() {
        return _roleIdRoleMapping.size();
    }

    public Role[] GetAllRoles() {
        return _roleIdRoleMapping.values().toArray(new Role[0]);
    }

    public Role GetRoleById(int roleId) {
        return _roleIdRoleMapping.get(roleId);
    }

    public void InsertRole(Role newRole) throws RoleAlreadyExistsException {
        String name = newRole.GetName();
        if (!_roleNameSet.containsKey(name)) {
            _roleNameSet.put(name, true);
            int currentRoleCount = _roleIdRoleMapping.size();
            int newRoleId = currentRoleCount + 1;
            newRole.SetId(newRoleId);
            _roleIdRoleMapping.put(newRoleId, newRole);
        }
        else {
            throw new RoleAlreadyExistsException();
        }
    }

    public void UpdateRole(Role updatedRole) throws RoleDoesNotExistException {
        int updatedRoleId = updatedRole.GetId();
        if (_roleIdRoleMapping.containsKey(updatedRoleId)) {
            _roleIdRoleMapping.put(updatedRoleId, updatedRole);
        }
        else {
            throw new RoleDoesNotExistException();
        }
    }

    public void RemoveRole(int roleId) throws RoleDoesNotExistException {
        if (_roleIdRoleMapping.containsKey(roleId)) {
            _roleIdRoleMapping.remove(roleId);
        }
        else {
            throw new RoleDoesNotExistException();
        }
    }

    public void SaveChanges() {
        //Do nothing for in memory repository
    }

    public InMemoryRoleRepository()
    {
        _roleIdRoleMapping = new HashMap<Integer, Role>();
        _roleNameSet = new HashMap<String, Boolean>();
    }

}
