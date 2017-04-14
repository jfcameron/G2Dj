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
    protected final Vector2 m_Offset = Vector2.Zero();
    private boolean m_RebuildShape = false;
    private boolean m_DrawDebugLines = true;
    
    //fixture def data
    protected float        m_Friction = 0.2f;
    protected float        m_Restitution = 0.0f;
    protected float        m_Density = 1.0f;
    protected ColliderType m_ColliderType = ColliderType.Collidable;
    
    //buffers
    protected Vector3 b_ScaleBuffer;
    protected final Vec2 b_Vec2Buffer = new Vec2();
    
    //**********
    // Accessors
    //**********
    public void setDrawDebugLines(final boolean aBoolean){m_DrawDebugLines=aBoolean;requestShapeRebuildOnNextTick();}
    public void setOffset(final float aX, final float aY){m_Offset.setInPlace(aX, aY);requestShapeRebuildOnNextTick();}
    public void setType(final ColliderType aColliderType){m_ColliderType=aColliderType;requestShapeRebuildOnNextTick();}
    public void setDensity(final float aDensity){m_Density=aDensity;requestShapeRebuildOnNextTick();}
    public void setFriction(final float aFriction){m_Friction=aFriction;requestShapeRebuildOnNextTick();}
    public void setRestitution(final float aRestitution){m_Restitution=aRestitution;requestShapeRebuildOnNextTick();}
    
    public boolean getDrawDebugLines(){return m_DrawDebugLines;}
    public ColliderType getType(){return m_ColliderType;}
    public float getDensity(){return m_Density;}
    public float getFriction(){return m_Friction;}
    public float getRestitution(){return m_Restitution;}
    
    
    //*******************
    // Collider interface
    //*******************    
    public abstract FixtureDef[] getB2DFixtures();
    protected abstract void buildShape();
    protected void requestShapeRebuildOnNextTick(){m_RebuildShape = true;}
    
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
    
    @Override public void fixedUpdate() 
    {
        
        
    }
    
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject){requestShapeRebuildOnNextTick();}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
