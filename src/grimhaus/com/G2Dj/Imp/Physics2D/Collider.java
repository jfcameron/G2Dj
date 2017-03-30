/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public abstract class Collider extends Component 
{
    //
    // Data members
    //
    protected final Vector2 m_Offset = new Vector2();
    protected boolean m_RebuildShape = false;
    
    //buffers
    protected Vector3 b_ScaleBuffer;
    protected final Vec2 b_Vec2Buffer = new Vec2();
    
    public void setOffset(final float aX, final float aY)
    {
        m_Offset.setInPlace(aX, aY);
        m_RebuildShape = true;
        
    }
    
    public abstract FixtureDef[] getB2DFixtures();
    
    //
    //
    //
    protected abstract void buildShape();
    
    private void checkForTransformScaleChange()
    {
        Vector3 scale = getTransform().get().getScale();
        
        if (b_ScaleBuffer == null)
        {
            b_ScaleBuffer = new Vector3();
            b_ScaleBuffer.copy(scale);
        
        }
                    
        if (!b_ScaleBuffer.equals(scale))
            m_RebuildShape = true;
        
        b_ScaleBuffer.copy(scale);
        
    }
    
    //
    // Component interface
    //
    @Override
    public void update() 
    {
        checkForTransformScaleChange();
        
        if (m_RebuildShape)
        {
            buildShape();
            m_RebuildShape = false;
            
        }
            
    }
    
    @Override
    public void fixedUpdate() {}
    
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_RebuildShape = true;
        
    }

    @Override 
    protected void OnRemovedFromGameObject() {}

    @Override 
    protected void OnComponentAdded(Component aComponent) {}

    @Override 
    protected void OnComponentRemoved(Component aComponent) {}
    
    @Override
    protected void initialize() 
    {
        m_RebuildShape = true;
        
    }
    
}
