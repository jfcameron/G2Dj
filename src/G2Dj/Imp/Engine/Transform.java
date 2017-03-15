/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Engine;

import G2Dj.Type.Math.Quaternion;
import G2Dj.Type.Math.Vector3;

/**
 *
 * @author Joe
 */
public class Transform 
{
    //
    //
    //
    private Vector3    m_Position, m_Scale;
    private Quaternion m_Rotation;

    //
    //
    //
    public Vector3    getPosition(){return m_Position;}
    public Vector3    getScale   (){return m_Scale;   }
    public Quaternion getRotation(){return m_Rotation;}
    public Vector3    getEulers  (){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    
    public void setPosition(final Vector3    aPosition){m_Position = aPosition;}
    public void setScale   (final Vector3    aScale   ){m_Scale    = aScale;   }
    public void setRotation(final Vector3    aRotation){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    public void setRotation(final Quaternion aRotation){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    
    //
    //
    //    
    public void translate(final Vector3    aTranslation){m_Position.addInPlace(aTranslation);}
    public void scale    (final Vector3    aScale      ){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    public void scale    (final float      aScalar     ){m_Scale.multiply(aScalar);}
    public void rotate   (final Vector3    aRotation   ){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    public void rotate   (final Quaternion aRotation   ){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    
    //
    //
    //
    public Transform() 
    {
        m_Position = new Vector3();
        m_Scale    = new Vector3(1.f);
        m_Rotation = new Quaternion();
        
    }
    
}
