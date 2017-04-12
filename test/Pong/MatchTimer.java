/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

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
public class MatchTimer extends Component 
{
    //
    //
    //
    private TextMesh m_TextMesh = null;
    
    private int m_Timer = 0;
    private float s_incrementSize = 1f;
    private float m_incrementTimer = 0;
    
    //
    //
    //
    @Override protected void initialize() 
    {
        m_TextMesh = (TextMesh)getGameObject().get().getComponent(TextMesh.class);
        m_TextMesh.setText("0");
    
    }

    @Override protected void update() 
    {
        if ((m_incrementTimer += Time.getDeltaTime()) > s_incrementSize)
        {
            m_TextMesh.setText(Integer.toString(++m_Timer));        
            m_incrementTimer = 0;
            
        }
        
    }

    @Override protected void fixedUpdate() {}
    
    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
