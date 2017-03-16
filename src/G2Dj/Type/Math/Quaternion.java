/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Type.Math;

/**
 *
 * @author Joe
 */
public class Quaternion 
{
    //
    //
    //
    public float x, y, z, w;
    
    //
    //
    //
    public Vector3 getEuler()
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    public void setFromEuler(final Vector3 aEulerAngles){}
    
    //
    //
    //
    
    
    //
    //
    //
    public Quaternion(){this(0,0,0,1);}
    public Quaternion(final float aX, final float aY, final float aZ, final float aW){x=aX;y=aY;z=aZ;w=aW;}
    
    @Override public String toString(){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    
}
