/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Math;

/**
 *
 * @author Joe
 */
public class Mat4x4 
{
    //
    //
    //
    private final glm.mat._4.Mat4 m_Mat4;
    
    //
    //
    //
    //KILL IT
    public glm.mat._4.Mat4 getMat(){return m_Mat4;}
    
    //
    //
    //
    public void perspective(final float aFOV, final float aAspectRatio, final float aNearClippingDistance, final float aFarClippingDistance)
    {
        m_Mat4.perspective(aFOV, aAspectRatio, aNearClippingDistance, aFarClippingDistance);
        
    }
    
    //
    //
    //
    public Mat4x4(){this(new glm.mat._4.Mat4().identity());}
    public Mat4x4(glm.mat._4.Mat4 aMat4)
    {
        m_Mat4 = aMat4;
        
    }
    
    //
    // Static
    //
    public static Mat4x4 identity()
    {
        return new Mat4x4();
        
    }
    
}
