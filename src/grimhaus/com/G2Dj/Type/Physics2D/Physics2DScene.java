/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Engine.SceneGraph;
import java.lang.ref.WeakReference;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 *
 * @author Joseph Cameron
 */
public class Physics2DScene extends SceneGraph 
{
    private final World m_B2DWorld = new World(new Vec2(0,0));
    
    public World getB2DWorld(){return m_B2DWorld;}
    
    
    @Override
    public void update() 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        m_B2DWorld.step(0.001f, 1, 1);
    }

    @Override
    public void draw() 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    protected void OnComponentAdded(Component aComponent) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //************
    // Constructor
    //************
    public Physics2DScene(WeakReference<Scene> aScene){super(aScene);}
    
}
