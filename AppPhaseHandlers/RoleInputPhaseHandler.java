/**
 * Created by Grigoryan on 20.11.2016.
 */

package AppPhaseHandlers;
import Abstractions.IAppPhaseHandler;
import Abstractions.IInputReader;
import Abstractions.IRoleRepository;
import Core.Role;
import Exceptions.RoleAlreadyExistsException;
import Exceptions.RoleDoesNotExistException;

import java.util.Scanner;

public class RoleInputPhaseHandler implements IAppPhaseHandler {
    private IInputReader _inputReader;

    private IRoleRepository _roleRepository;

    private void PrintRoles(Role[] roles) {
        System.out.println("ID\tName\tRequired");
        for (Role role: roles)
        {
            int id = role.GetId();
            String name = role.GetName();
            boolean isRequired = role.IsRequired();
            System.out.println(id + "\t" + name + "\t" + isRequired);
        }
    }

    private void ViewAllRoles()
    {
        Role[] allRoles = _roleRepository.GetAllRoles();
        if (allRoles.length == 0) {
            System.out.println("No roles found");
        }
        else {
            PrintRoles(allRoles);
        }
    }

    private void GetRoleById() {
        System.out.println("Specify the role id: ");
        int roleId = _inputReader.GetInteger();
        Role foundRole = _roleRepository.GetRoleById(roleId);
        if (foundRole == null) {
            System.out.println("No role found with specified id");
        }
        else {
            PrintRoles(new Role[] {foundRole});
        }
    }

    private void InsertRole()
    {
        Role role = new Role();
        System.out.println("Specify the new role name: ");
        String name = _inputReader.GetString();
        System.out.println("Is the role required? (y/n)");
        boolean isRequired = _inputReader.GetBoolean();
        role.SetName(name);
        role.SetIsRequired(isRequired);
        try {
            _roleRepository.InsertRole(role);
            System.out.println("The role successfully inserted");
        }
        catch(RoleAlreadyExistsException exc)
        {
            System.out.println("A role with specified name already exists!");
        }
    }

    private void EditRoleById()
    {
        System.out.println("Specify the role id: ");
        int roleId = _inputReader.GetInteger();
        Role foundRole = _roleRepository.GetRoleById(roleId);
        if (foundRole == null) {
            System.out.println("No role found with specified id");
        }
        else {
            Role editedRole = new Role();
            editedRole.SetId(foundRole.GetId());
            System.out.println("Specify the new role name");
            String newRoleName = _inputReader.GetString();
            editedRole.SetName(newRoleName);
            System.out.println("Specify whether the role is required (y/n)");
            boolean isRequired = _inputReader.GetBoolean();
            editedRole.SetIsRequired(isRequired);
            try {
                _roleRepository.UpdateRole(editedRole);
                System.out.println("The role successfully updated");
            }
            catch(RoleDoesNotExistException noRoleException)
            {
                System.out.println("The role does not exist");
            }
            catch(RoleAlreadyExistsException roleExistsException)
            {
                System.out.println("A role with specified name already exists");
            }
        }
    }

    private void RemoveRoleById()
    {
        System.out.println("Specify the role id: ");
        int roleId = _inputReader.GetInteger();
        try {
            _roleRepository.RemoveRole(roleId);
            System.out.println("The role successfully removed");
        }
        catch(RoleDoesNotExistException exc) {
            System.out.println("No role with specified id exists");
        }
    }

    private boolean IsThePhaseComplete()
    {
        Role[] roles = _roleRepository.GetAllRoles();
        for (int i=0;i<roles.length;i++)
        {
            if (roles[i].IsRequired()) {
                return true;
            }
        }
        return false;
    }

    public boolean Handle() {
        Scanner scanner = new Scanner(System.in);
        boolean wasHandled = false;
        boolean shouldContinue = true;
        while(shouldContinue)
        {
            System.out.println("*** ROLE INPUT PHASE ***");
            System.out.println("(1) View all roles");
            System.out.println("(2) Get role by id");
            System.out.println("(3) Insert role");
            System.out.println("(4) Edit role by id");
            System.out.println("(5) Remove role by id");
            System.out.println("(6) Complete the phase");
            System.out.println("(7) Exit");
            System.out.println("Your choice: ");
            int choice = scanner.nextInt();
            switch(choice)
            {
                case 1:
                    ViewAllRoles();
                    break;
                case 2:
                    GetRoleById();
                    break;
                case 3:
                    InsertRole();
                    break;
                case 4:
                    EditRoleById();
                    break;
                case 5:
                    RemoveRoleById();
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
                        System.out.println("There should be at least one required role.");
                    }
                    break;
                case 7:
                    System.out.println("Exiting from role input phase.");
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again");
            }
        }
        return wasHandled;
    }

    public RoleInputPhaseHandler(IInputReader inputReader,IRoleRepository roleRepository)
    {
        _inputReader = inputReader;
        _roleRepository = roleRepository;
    }
}
