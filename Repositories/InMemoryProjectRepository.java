/**
 * Created by Grigoryan on 18.12.2016.
 */
package Repositories;

import Abstractions.IProjectRepository;
import Core.Project;
import Exceptions.ProjectAlreadyExistsException;
import Exceptions.ProjectDoesNotExistException;

import java.util.HashMap;

public class InMemoryProjectRepository implements IProjectRepository {
    private HashMap<Integer, Project> _projectIdProjectMapping;
    private HashMap<String, Boolean> _projectNameSet;

    public int GetProjectCount() {
        return _projectIdProjectMapping.size();
    }

    public Project[] GetAllProjects() {
        return _projectIdProjectMapping.values().toArray(new Project[0]);
    }

    public Project GetProjectById(int projectId) {
        return _projectIdProjectMapping.get(projectId);
    }

    public void InsertProject(Project newProject) throws ProjectAlreadyExistsException {
        String name = newProject.GetName();
        if (!_projectNameSet.containsKey(name)) {
            _projectNameSet.put(name, true);
            int currentProjectCount = _projectIdProjectMapping.size();
            int newProjectId = currentProjectCount + 1;
            newProject.SetId(newProjectId);
            _projectIdProjectMapping.put(newProjectId, newProject);
        }
        else {
            throw new ProjectAlreadyExistsException();
        }
    }

    public void UpdateProject(Project updatedProject) throws ProjectDoesNotExistException,ProjectAlreadyExistsException {
        int updatedProjectId = updatedProject.GetId();
        Project foundProject = _projectIdProjectMapping.get(updatedProjectId);
        if (foundProject != null) {
            String updatedName = updatedProject.GetName();
            if (updatedName.equals(foundProject.GetName())) {
                _projectIdProjectMapping.put(updatedProjectId, updatedProject);
            }
            else if (!_projectNameSet.containsKey(updatedName)) {
                _projectNameSet.put(updatedName, true);
                _projectIdProjectMapping.put(updatedProjectId, updatedProject);
            }
            else {
                throw new ProjectAlreadyExistsException();
            }
        }
        else {
            throw new ProjectDoesNotExistException();
        }
    }

    public void RemoveProject(int projectId) throws ProjectDoesNotExistException {
        Project foundProject = _projectIdProjectMapping.get(projectId);
        if (foundProject != null) {
            _projectIdProjectMapping.remove(projectId);
            _projectNameSet.remove(foundProject.GetName());
        }
        else {
            throw new ProjectDoesNotExistException();
        }
    }

    public void SaveChanges() {
        //Do nothing for in memory repository
    }

    public InMemoryProjectRepository()
    {
        _projectIdProjectMapping = new HashMap<Integer, Project>();
        _projectNameSet = new HashMap<String, Boolean>();
    }

}
