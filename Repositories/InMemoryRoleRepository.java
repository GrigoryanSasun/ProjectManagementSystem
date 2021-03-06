/**
 * Created by Grigoryan on 20.11.2016.
 */
package Repositories;

import Abstractions.IRoleRepository;
import Core.Role;
import Exceptions.RoleAlreadyExistsException;
import Exceptions.RoleDoesNotExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class InMemoryRoleRepository implements IRoleRepository {
    private HashMap<Integer, Role> _roleIdRoleMapping;
    private HashMap<String, Boolean> _roleNameSet;

    public int GetRoleCount() {
        return _roleIdRoleMapping.size();
    }

    public Role[] GetAllRoles() {
        return _roleIdRoleMapping.values().toArray(new Role[0]);
    }

    public Role[] GetRequiredRoles() {
        List<Role> requiredRoles = new Vector<Role>();
        Role[] allRoles = GetAllRoles();
        for (int i=0;i<allRoles.length;i++)
        {
            Role role = allRoles[i];
            if (role.IsRequired()) {
                requiredRoles.add(role);
            }
        }
        return requiredRoles.toArray(new Role[0]);
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

    public void UpdateRole(Role updatedRole) throws RoleDoesNotExistException,RoleAlreadyExistsException {
        int updatedRoleId = updatedRole.GetId();
        Role foundRole = _roleIdRoleMapping.get(updatedRoleId);
        if (foundRole != null) {
            String updatedName = updatedRole.GetName();
            if (updatedName.equals(foundRole.GetName())) {
                _roleIdRoleMapping.put(updatedRoleId, updatedRole);
            }
            else if (!_roleNameSet.containsKey(updatedName)) {
                _roleNameSet.put(updatedName, true);
                _roleIdRoleMapping.put(updatedRoleId, updatedRole);
            }
            else {
                throw new RoleAlreadyExistsException();
            }
        }
        else {
            throw new RoleDoesNotExistException();
        }
    }

    public void RemoveRole(int roleId) throws RoleDoesNotExistException {
        Role foundRole = _roleIdRoleMapping.get(roleId);
        if (foundRole != null) {
            _roleIdRoleMapping.remove(roleId);
            _roleNameSet.remove(foundRole.GetName());
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
