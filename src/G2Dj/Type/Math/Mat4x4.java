/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Type.Math;

import java.nio.FloatBuffer;

/**
 *
 * @author Joe
 */
public class Mat4x4 
{
    //*************
    // Data members
    //*************
    private final glm.mat._4.Mat4 m_Mat4;
    
    //**********
    // Accessors
    //**********
    protected glm.mat._4.Mat4 getMat(){return m_Mat4;}
    
    //*****************
    // Public interface
    //*****************
    public void rotateX(final float aX){m_Mat4.rotateX(aX);}
    public void rotateY(final float aY){m_Mat4.rotateY(aY);}
    public void rotateZ(final float aZ){m_Mat4.rotateZ(aZ);}
        
    public void translate(final float aX, final float aY, final float aZ){m_Mat4.translate(aX,aY,aZ);}
    public void translate(final Vector3 aVector){m_Mat4.translate(aVector.x,aVector.y,aVector.z);}
    
    public void scale(final float aX, final float aY, final float aZ){m_Mat4.scale(aX,aY,aZ);}
    public void scale(final Vector3 aVector){m_Mat4.scale(aVector.x,aVector.y,aVector.z);}
        
    public Mat4x4 mul(final Mat4x4 aMat)
    {
        m_Mat4.mul(aMat.getMat());
        return this;
        
    }
    
    public FloatBuffer toFloatBuffer(){return m_Mat4.toDfb_();}
    
    //*************
    // Constructors
    //*************
    public Mat4x4(){this(new glm.mat._4.Mat4().identity());}
    private Mat4x4(glm.mat._4.Mat4 aMat4)
    {
        m_Mat4 = aMat4;
        
    }
    
    //*******
    // Static
    //*******
    public static Mat4x4 identity()
    {
        return new Mat4x4();
    
    }
    
    public static Mat4x4 perspective(final float aFOV, final float aAspectRatio, final float aNearClippingDistance, final float aFarClippingDistance)
    {
        return new Mat4x4(new glm.mat._4.Mat4().perspective(aFOV, aAspectRatio, aNearClippingDistance, aFarClippingDistance));
        
    }
    
    public static Mat4x4 orthographic(final float aLeft, final float aRight, final float aBottom, final float aTop, final float aNearClippingDistance, final float aFarClipppingDistance)
    {
        return new Mat4x4(new glm.mat._4.Mat4().ortho(aLeft, aRight, aBottom, aTop, aNearClippingDistance, aFarClipppingDistance));
        
    }
    
}
