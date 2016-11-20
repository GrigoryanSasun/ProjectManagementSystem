package Core;

/**
 * Created by Grigoryan on 20.11.2016.
 */
public class Role {
    private int _id;
    private String _name;
    private boolean _isRequired;

    public int GetId()
    {
        return _id;
    }

    public void SetId(int newId)
    {
        _id = newId;
    }

    public String GetName()
    {
        return _name;
    }

    public void SetName(String newName)
    {
        _name = newName;
    }

    public boolean IsRequired()
    {
        return _isRequired;
    }

    public void SetIsRequired(boolean required)
    {
        _isRequired = required;
    }
}
