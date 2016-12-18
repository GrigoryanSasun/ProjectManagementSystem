package Core;

/**
 * Created by Grigoryan on 10.11.2016.
 */
public class Student {
    private String _id;
    private String _firstName;
    private String _lastName;

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
}
