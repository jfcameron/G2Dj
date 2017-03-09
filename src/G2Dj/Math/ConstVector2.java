/*
 * Const does not exist in Java. Final is not equivalent (final refers to the field aka the ref but not the instance being pointed to)
 * Const vec provides an immutable {x,y} for cases where I need immutable data pairs.
*/
package G2Dj.Math;

/**
 *
 * @author Joe
 */
public class ConstVector2 extends VectorBase
{
    public final float x,y;
    
    ConstVector2(){this(0,0);}
    ConstVector2(final float aX, final float aY) { x = aX; y = aY; }
    
    //************************************
    // VectorBase interface implementation
    //************************************
    @Override
    protected float getX() {return x;}

    @Override
    protected float getY() {return y;}
    
}
