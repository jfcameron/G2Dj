/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.TextMesh;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({TextMesh.class})
public class ScoreCounter extends Component 
{
    //
    //
    //
    private TextMesh m_TextMesh = null;
    
    private int m_Value = 0;
    
    //
    //
    //
    public int getScore(){return m_Value;}
    public void incrementScore(){m_TextMesh.setText("Score: "+Integer.toString(++m_Value));}
    
    //
    //
    //
    @Override protected void initialize() 
    {
        m_TextMesh = (TextMesh)getGameObject().get().getComponent(TextMesh.class);
        m_TextMesh.setText("Score: 0");
    
    }

    @Override protected void update(){}
    
    @Override protected void fixedUpdate() {}
    
    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}