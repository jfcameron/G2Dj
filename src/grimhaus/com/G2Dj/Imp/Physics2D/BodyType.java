/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

/**
 *
 * @author Joseph Cameron
 */
public enum BodyType 
{
    Dynamic,
    Kinematic,
    Static;
    
    public org.jbox2d.dynamics.BodyType toB2BodyType()
    {
        switch (this) 
        {
            case Dynamic:
            return org.jbox2d.dynamics.BodyType.DYNAMIC;

            case Kinematic:
            return org.jbox2d.dynamics.BodyType.KINEMATIC;

            case Static:
            default:
            return org.jbox2d.dynamics.BodyType.STATIC;
                
        }
    
    }
    
}
