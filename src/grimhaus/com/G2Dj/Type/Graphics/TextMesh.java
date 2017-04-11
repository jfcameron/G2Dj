/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Imp.Graphics.GraphicsComponent;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class TextMesh extends GraphicsComponent implements Drawable
{
    private String m_Text = "";
    
    //
    //
    //
    public void setText(final String aText){m_Text=aText;}
    
    public String getText(){return m_Text;}
    
    //
    //
    //
    @Override
    protected void initialize() 
    {
    
    
    }

    @Override
    protected void update() 
    {
    
    
    }

    @Override
    protected void fixedUpdate() 
    {
    
    
    }
    
    @Override
    public void draw(WeakReference<Camera> aCamera) 
    {
        
        
    }
    
    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
