/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public interface Collider 
{
    FixtureDef getB2DFixture();
    
}
