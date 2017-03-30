/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import java.lang.ref.WeakReference;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 * @param <T>
 */
public abstract class SimpleCollider<T extends Shape> extends Collider
{
    //
    // Data members
    //
    protected final FixtureDef m_FixtureDefinition = new FixtureDef();
    protected final T          m_Shape;
    protected LineVisualizer m_LineVisualizer;    
    
    //
    // Accessors
    //
    @Override public FixtureDef[] getB2DFixtures(){return new FixtureDef[]{m_FixtureDefinition};}
    @Override public void setType(final ColliderType aColliderType){m_FixtureDefinition.setSensor(aColliderType.toB2TriggerBool());}
    @Override public void setDensity(final float aDensity){m_FixtureDefinition.setDensity(aDensity);}
    @Override public void setFriction(final float aFriction){m_FixtureDefinition.setFriction(aFriction);}
    @Override public void setRestitution(final float aRestitution){m_FixtureDefinition.setRestitution(aRestitution);}
    
    @Override public ColliderType getType(){return ColliderType.fromB2TriggerBool(m_FixtureDefinition.isSensor);}
    @Override public float getDensity(){return m_FixtureDefinition.getDensity();}
    @Override public float getFriction(){return m_FixtureDefinition.getFriction();}
    @Override public float getRestitution(){return m_FixtureDefinition.getRestitution();}
    
    //
    // Collider interface
    //
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        super.OnAddedToGameObject(aGameObject);
        
        m_LineVisualizer = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
        
    }
    public SimpleCollider(final T aShape)
    {
        m_Shape = aShape;
        m_FixtureDefinition.shape = aShape;
    
    }
    
}
