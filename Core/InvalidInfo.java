/**
 * Created by Grigoryan on 18.12.2016.
 */
package Core;

import java.util.Vector;

public class InvalidInfo<T> {
    private T _invalidInfo;
    private Vector<Integer> _requiredRoleIds;

    public T GetInvalidInfo()
    {
        return _invalidInfo;
    }

    public void SetInvalidInfo(T invalidInfo)
    {
        _invalidInfo = invalidInfo;
    }

    public Integer[] GetRequiredRoleIds()
    {
        return _requiredRoleIds.toArray(new Integer[0]);
    }

    public void addRequiredRoleId(int roleId)
    {
        _requiredRoleIds.add(roleId);
    }

    public InvalidInfo()
    {
        _requiredRoleIds = new Vector<Integer>();
    }

}
