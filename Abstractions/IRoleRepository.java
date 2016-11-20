/**
 * Created by Grigoryan on 20.11.2016.
 */

package Abstractions;

import Core.Role;
import Exceptions.RoleAlreadyExistsException;
import Exceptions.RoleDoesNotExistException;

public interface IRoleRepository {
    int GetRoleCount();
    Role[] GetAllRoles();
    Role GetRoleById(int roleId);
    void InsertRole(Role newRole) throws RoleAlreadyExistsException;
    void UpdateRole(Role updatedRole) throws RoleDoesNotExistException;
    void RemoveRole(int roleId) throws RoleDoesNotExistException;
    void SaveChanges();
}
