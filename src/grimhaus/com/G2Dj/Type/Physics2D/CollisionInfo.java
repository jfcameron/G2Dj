/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.ColliderType;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class CollisionInfo 
{
    public final WeakReference<GameObject> other;
    public final Vector2 collisionNormal;
    public final Vector2 collisionPoint;
    
    CollisionInfo
    (
        final WeakReference<GameObject> aOther,
        final Vector2 aCollisionNormal,
        final Vector2 aCollisionPoint
            
    )
    {
        other           = aOther;
        collisionNormal = aCollisionNormal;
        collisionPoint  = aCollisionPoint;
                
    }
    
}
