/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Engine;

import G2Dj.Type.Math.Quaternion;
import G2Dj.Type.Math.Vector3;
import static java.lang.Math.PI;

/**
 *
 * @author Joe
 */
public class Transform 
{
    //
    //
    //
    private Vector3 m_Position, m_Scale, m_Rotation;
    //private Quaternion m_Rotation;

    //
    //
    //
    public Vector3    getPosition(){return m_Position;}
    public Vector3    getScale   (){return m_Scale;   }
    public Quaternion getRotation(){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    public Vector3    getEulers  (){return m_Rotation;}
    
    public void setPosition(final Vector3    aPosition){m_Position = aPosition;}
    public void setScale   (final Vector3    aScale   ){m_Scale    = aScale;   }
    //public void setRotation(final Quaternion aRotation){m_Rotation = aRotation;}
    //public void setRotation(final Vector3    aRotation){m_Rotation = aRotation;}
    public void setRotation(final Vector3 aEulers)  {m_Rotation = aEulers.multiply((float)PI/180);}
    //public void setRadians(final Vector3 aRadians){m_Rotation = aRadians;}
    
    //
    //
    //    
    public void translate(final Vector3    aTranslation){m_Position.addInPlace(aTranslation);}
    public void scale    (final Vector3    aScale      ){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    public void scale    (final float      aScalar     ){m_Scale.multiply(aScalar);}
    public void rotate   (final Vector3    aEulers     ){m_Rotation.addInPlace(aEulers.multiply((float)PI/180));}
    public void rotate   (final Quaternion aRotation   ){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    
    //
    //
    //
    public Transform() 
    {
        m_Position = new Vector3();
        m_Scale    = new Vector3(1.f);
        m_Rotation = new Vector3();
        
    }
    
}
