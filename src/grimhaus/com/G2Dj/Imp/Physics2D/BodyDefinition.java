/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

import grimhaus.com.G2Dj.Type.Math.Vector2;
import org.jbox2d.dynamics.BodyDef;

/**
 *
 * @author Joseph Cameron
 */
public class BodyDefinition 
{
    private final BodyDef m_Box2DBodyDef = new BodyDef();
    
    //buffers
    private final Vector2 b_LinearVelocity = new Vector2(); 
    private final Vector2 b_Position       = new Vector2();
    
    
    public float getAngle(){return m_Box2DBodyDef.getAngle();}             
    public float getAngularDamping(){return m_Box2DBodyDef.getAngularDamping();}         
    public float getAngularVelocity(){return m_Box2DBodyDef.getAngularVelocity();}
    public float getGravityScale(){return m_Box2DBodyDef.getGravityScale();}
    public float getLinearDamping(){return m_Box2DBodyDef.getLinearDamping();}
    public Vector2 getLinearVelocity(){return b_LinearVelocity.setFromB2DVec2(m_Box2DBodyDef.getLinearVelocity());}
    public Vector2 getPosition(){return b_Position.setFromB2DVec2(m_Box2DBodyDef.getPosition());}
    
    public BodyType getType()
    {
        switch(m_Box2DBodyDef.getType())
        {
            case DYNAMIC   : return BodyType.Dynamic;
            case KINEMATIC : return BodyType.Kinematic;
            case STATIC    : default: return BodyType.Static;
            
        }
        
    }
    
    
    public BodyDefinition()
    {
        org.jbox2d.dynamics.BodyType asdf = m_Box2DBodyDef.getType();
        
    }
    
}
