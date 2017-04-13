/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public abstract class Collider extends Physics2DComponent 
{
    //*************
    // Data members
    //*************
    protected final Vector2 m_Offset = new Vector2();
    private boolean m_RebuildShape = false;
    private boolean m_DrawDebugLines = false;
    //buffers
    protected Vector3 b_ScaleBuffer;
    protected final Vec2 b_Vec2Buffer = new Vec2();
    
    //**********
    // Accessors
    //**********
    public void setDrawDebugLines(final boolean aBoolean){m_DrawDebugLines=aBoolean;}
    public void setOffset(final float aX, final float aY){m_Offset.setInPlace(aX, aY);requestShapeRebuildOnNextTick();}
    protected void requestShapeRebuildOnNextTick(){m_RebuildShape = true;}
    
    public boolean getDrawDebugLines(){return m_DrawDebugLines;}
    
    //*******************
    // Collider interface
    //*******************
    public abstract void setType(final ColliderType aColliderType);
    public abstract void setDensity(final float aDensity);
    public abstract void setFriction(final float aFriction);
    public abstract void setRestitution(final float aRestitution);
    
    public abstract ColliderType getType();
    public abstract float getDensity();
    public abstract float getFriction();
    public abstract float getRestitution();
    
    public abstract FixtureDef[] getB2DFixtures();
    protected abstract void buildShape();
    
    //***************
    // Implementation
    //***************
    private void checkForTransformScaleChange()
    {
        Vector3 scale = getTransform().get().getScale();
        
        if (b_ScaleBuffer == null)
        {
            b_ScaleBuffer = new Vector3();
            b_ScaleBuffer.copy(scale);
        
        }
                    
        if (!b_ScaleBuffer.equals(scale))
            requestShapeRebuildOnNextTick();
        
        b_ScaleBuffer.copy(scale);
        
    }
    //********************
    // Component interface
    //********************
    @Override protected void initialize()
    {
        requestShapeRebuildOnNextTick();
    
    }
    
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
    
    @Override public void fixedUpdate() {}
    
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject){requestShapeRebuildOnNextTick();}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
