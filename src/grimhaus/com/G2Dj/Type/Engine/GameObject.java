/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Engine;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Engine.GameObject.Transform;
import grimhaus.com.G2Dj.Type.Math.Quaternion;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import static java.lang.Math.PI;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joe
 */
public class GameObject 
{
    //*************
    // Data members
    //*************
    private String  m_Name;
    private final WeakReference<Scene> m_MyScene;
    
    private final ArrayList<Component> m_Components = new ArrayList<>();
    private final Transform m_Transform = new Transform(new WeakReference<>(this));

    //
    //
    //
    public String                   getName     (){return m_Name;}
    public WeakReference<Scene>     getScene    (){return m_MyScene;}
    public WeakReference<Transform> getTransform(){return new WeakReference<>(m_Transform);}
    
    public void setName(final String aName){m_Name = aName;}
    
    //
    //
    //
    protected void OnScaleChanged()
    {
        for(int i=0,s=m_Components.size();i<s;i++)
            m_Components.get(i).OnScaleChanged();
        
        Debug.log("asdfafasfd");
        
    }
    
    public Component addComponent(Class<? extends Component> aComponentType)
    {
        Component rValue = null;
        
        try 
        {
            rValue = aComponentType.newInstance();
            
            //if (!m_Components.contains(rValue))
            {
                m_Components.add(rValue);
                rValue.OnAddedToGameObjectSuper(new WeakReference<>(this));
                rValue.OnAddedToGameObject(new WeakReference<>(this));
                
            }
            //else
            //    Debug.log("GameObject "+m_Name+" already has a "+rValue.getClass().getSimpleName());
            
            m_MyScene.get().OnComponentAdded(rValue);
            
            for(int i=0,s=m_Components.size();i<s;i++)
                m_Components.get(i).OnComponentAdded(rValue);
        
        } 
        
        catch (InstantiationException | IllegalAccessException ex) {Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);}
        
        return rValue;
        
    }
    
    public void removeComponent(Class<? /*extends Component*/> aComponentType)
    {
        for (int i=0,s=m_Components.size();i<s;i++)
            if (m_Components.get(i).getClass() == aComponentType)
            {
                m_MyScene.get().OnComponentRemoved(m_Components.get(i));
                
                for(int j=0,t=m_Components.size();j<t;j++)
                    m_Components.get(j).OnComponentRemoved(m_Components.get(i));
                
                m_Components.get(i).OnRemovedFromGameObjectSuper();
                m_Components.get(i).OnRemovedFromGameObject();
                m_Components.remove(i);
        
            }
        
    }
    
    public Component getComponent(Class<? /*extends Component*/> aComponentType)
    {
        Component rValue = null;
        
        Component currentComponent;
        for(int i = 0, s = m_Components.size(); i<s; i++)//m_Components
        {
            currentComponent = m_Components.get(i);
            
            Debug.log(m_Name+": "+aComponentType,currentComponent.getClass());
            
            if(aComponentType.isAssignableFrom(currentComponent.getClass()))
            {
                rValue = currentComponent;//Debug.log("SUCCESS");
                
            }
            
        }
        
        return rValue; //aComponentType.cast(rValue)
        
    }
    
    public ArrayList<Component> getComponents(Class<?> aComponentType)
    {
        ArrayList<Component> rValue = new ArrayList<>();
        
        Component currentComponent;
        for(int i = 0, s = m_Components.size(); i<s; i++)//m_Components
        {
            currentComponent = m_Components.get(i);
            
            if(aComponentType.isAssignableFrom(currentComponent.getClass()))
                rValue.add(currentComponent);
                
        }
        
        return rValue;
        
    }
    
    //
    //
    //
    public void update()
    {
        for(int i = 0, s = m_Components.size(); i<s; i++ )//m_Components.forEach(currentComponent->currentComponent.update());
            m_Components.get(i).update();

    }
    
    //
    //
    //
    public GameObject(WeakReference<Scene> aScene)
    {
        m_Name = "Unnamed GameObject";
        m_MyScene = aScene;
                
    }
    
    //
    // Transform
    //
    public class Transform 
    {
    //
    //
    //
    private final WeakReference<GameObject> m_GameObject;
    private Vector3 m_Position, m_Scale, m_Rotation;
    //private Quaternion m_Rotation;

    //
    //
    //
    public Vector3 getPosition(){return m_Position;}
    public Vector3 getScale   (){return m_Scale;   }
    public Vector3 getRotation(){return m_Rotation;}
    public Vector3 getEulers  (){return m_Rotation;}
    
    public void setPosition(final Vector3 aPosition){m_Position = aPosition;                           }
    public void setScale   (final Vector3 aScale   ){m_Scale    = aScale;}
    public void setRotation(final Vector3 aEulers  ){m_Rotation = aEulers.multiply((float)PI/180);     }
    
    public void setPosition(final float aX, final float aY, final float aZ){m_Position.x=aX;m_Position.y=aY;m_Position.z=aZ;}
    public void setScale   (final float aX, final float aY, final float aZ){m_Scale   .x=aX;m_Scale   .y=aY;m_Scale   .z=aZ;}
    public void setRotation(final float aX, final float aY, final float aZ){setRotation(new Vector3(aX,aY,aZ));}
    
    //public void setRotation(final Quaternion aRotation){m_Rotation = aRotation;}
    //public void setRotation(final Vector3    aRotation){m_Rotation = aRotation;}
    
    //public void setRadians(final Vector3 aRadians){m_Rotation = aRadians;}
    
    //
    //
    //    
    public void translate(final Vector3    aTranslation){m_Position.addInPlace(aTranslation);}
    public void scale    (final Vector3    aScale      ){m_Scale.addInPlace(aScale);}
    public void scale    (final float      aScalar     ){m_Scale.multiply(aScalar);}
    public void rotate   (final Vector3    aEulers     ){m_Rotation.addInPlace(aEulers.multiply((float)PI/180));}
    public void rotate   (final Quaternion aRotation   ){throw new java.lang.UnsupportedOperationException("Not supported yet.");}
    
    //
    //
    //
    public Transform(WeakReference<GameObject> aMyGameObject) 
    {
        m_GameObject = aMyGameObject;
        m_Position = new Vector3();
        m_Scale    = new Vector3(1.f);
        m_Rotation = new Vector3();
        
    }
    
        @Override public String toString(){return "Transform: { Position: "+m_Position+", m_Scale: "+m_Scale+", m_Rotation: "+m_Rotation+" }";}
    
    }
    
}
