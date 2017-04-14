/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TextMeshTest;

import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.TextMesh;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({TextMesh.class})
public class Incrementer extends Component
{
    //
    //
    //
    private TextMesh m_TextMesh = null;
    private int counter = 0;
    
    //
    //
    //
    @Override protected void initialize() 
    {
        m_TextMesh = (TextMesh)getGameObject().get().getComponent(TextMesh.class);
        
    }

    @Override protected void update() 
    {
        
        
    }

    @Override protected void fixedUpdate() 
    {
        counter++;
        
        m_TextMesh.setText(Integer.toString(counter));
    
    }

    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
