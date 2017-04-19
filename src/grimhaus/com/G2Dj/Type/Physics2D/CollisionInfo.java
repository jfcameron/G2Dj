/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class CollisionInfo 
{
    public final WeakReference<Collider> mine;
    public final WeakReference<Collider> other;
    public final Vector2 collisionPoint;
    
    CollisionInfo
    (
        final WeakReference<Collider> aMine,
        final WeakReference<Collider> aOther,
        final Vector2 aCollisionPoint
            
    )
    {
        mine             = aMine;
        other            = aOther;
        collisionPoint   = aCollisionPoint;
                
    }
    
}
