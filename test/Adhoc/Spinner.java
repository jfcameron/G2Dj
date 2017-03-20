package Adhoc;

import java.lang.ref.WeakReference;

import grimhaus.com.G2Dj.Imp.Engine.Transform;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector3;

/**
 * Created by Joe on 3/19/2017.
 */

public class Spinner extends Component
{
    private WeakReference<Transform> m_Transform;

    @Override
    public void update()
    {
        m_Transform.get().rotate(new Vector3(0,0,10));



    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject)
    {
        m_Transform = aGameObject.get().getTransform();

    }

    @Override
    protected void OnRemovedFromGameObject() {}

}
