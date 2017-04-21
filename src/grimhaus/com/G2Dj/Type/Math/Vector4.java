/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Math;

/**
 *
 * @author Joseph Cameron
 */
public class Vector4 
{
    //*************
    // Data members
    //*************
    public float x,y,z,w;
    
    //
    // state mul op
    //
    public Vector4 multiply(final float aScalar)
    {
        x*=aScalar;
        y*=aScalar;
        z*=aScalar;
        w*=aScalar;
        
        return this;
        
    }
    
    //************
    //Constructors
    //************
    public Vector4(){this(0);}
    public Vector4(final Vector4 aVector4){this(aVector4.x,aVector4.y,aVector4.z,aVector4.w);}
    public Vector4(final float aScalar){this(aScalar,aScalar,aScalar,aScalar);}
    public Vector4(final float aX, final float aY, final float aZ, final float aW){x=aX;y=aY;z=aZ;w=aW;}
    //public Vector3(const btVector3 &aBulletVector);
    
    @Override public String toString(){return "{"+x+", "+y+", "+z+", "+w+"}";}
    
}
