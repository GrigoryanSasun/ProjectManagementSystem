/**
 * Created by Grigoryan on 18.12.2016.
 */
package Abstractions;

import Core.Project;
import Exceptions.ProjectAlreadyExistsException;
import Exceptions.ProjectDoesNotExistException;

public interface IProjectRepository {
    int GetProjectCount();
    Project[] GetAllProjects();
    Project GetProjectById(int projectId);
    void InsertProject(Project newProject) throws ProjectAlreadyExistsException;
    void UpdateProject(Project updatedProject) throws ProjectDoesNotExistException,ProjectAlreadyExistsException;
    void RemoveProject(int projectId) throws ProjectDoesNotExistException;
    void SaveChanges();
}
