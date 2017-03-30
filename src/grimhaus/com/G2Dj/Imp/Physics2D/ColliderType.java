/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

/**
 *
 * @author Joseph Cameron
 */
public enum ColliderType
{
    Collidable,
    Trigger;
    
    public boolean toB2TriggerBool()
    {
        switch (this) 
        {
            case Trigger:
            return true;
            
            case Collidable:
            default:
            return false;
            
        }
    
    }
    
    public static ColliderType fromB2TriggerBool(boolean aB2TriggerBool)
    {
        if (aB2TriggerBool == true)
            return ColliderType.Trigger;
        
        return ColliderType.Collidable;
        
    }
    
}
