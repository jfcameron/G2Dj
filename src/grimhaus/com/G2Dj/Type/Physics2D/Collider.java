/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Math.Vector2;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public interface Collider 
{
    FixtureDef getB2DFixture();
    
    void setOffset(final float aX, final float aY);
    
}
