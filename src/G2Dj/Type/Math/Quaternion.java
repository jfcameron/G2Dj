/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Math;

import glm.quat.Quat;


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
