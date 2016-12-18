package Core;

/**
 * Created by Grigoryan on 18.12.2016.
 */
public class Project {
    private int _id;
    private String _name;
    private String _description;

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
}
