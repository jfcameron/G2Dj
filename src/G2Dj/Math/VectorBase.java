/*
 * The existence of vectorBase and ConstVector2 is a work around for the non existence of const keyword (c++) from java language.
 * Final is not equivalent. Final marks the pointer as immutable but not the pointee (data members of pointee can be changed).
 * Vec2 is meant to be mutable. ConstVec2 is meant to be immutable. 
 * VectorBase provides a common interface for the two for the sake of code reusability.
 * Opted for abstract basetype with abstract methods rather than java interface to scoped the interface at the package level.
 * (a public interface would have polluted implementing classes with redundant accessor methods)
 */
package G2Dj.Math;

import static com.jogamp.opengl.math.FloatUtil.sqrt;

/*
 *
 * @author Joe
 */
public abstract class VectorBase 
{
    //package-wide interface
    protected abstract float getX();
    protected abstract float getY();
    
    //Non mutating vector operations
    @Override public String toString(){return "{"+getX()+", "+getY()+"}";}
    
    public float aspectRatio() {return getX() / getY(); }
    public float length     () {return sqrt( (getX()*getX()) + (getY()*getY()) );}
    
    public Vector2 add     (final VectorBase aVector){return new Vector2(getX()+aVector.getX(),getY()+aVector.getY());}
    public Vector2 subtract(final VectorBase aVector){return new Vector2(getX()-aVector.getX(),getY()-aVector.getY());}
    public Vector2 multiply(final float      aScalar){return new Vector2(getX()*aScalar       ,getY()*aScalar       );}
    public Vector2 divide  (final float      aScalar){return new Vector2(getX()/aScalar       ,getY()/aScalar       );}
    
    public Vector2 unit(){float l=length(); return new Vector2(getX()/l,getY()/l);}
    
}
