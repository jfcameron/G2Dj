/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Engine;

import G2Dj.Debug;
import G2Dj.Imp.Engine.Transform;
import G2Dj.Math.Vector3;
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
    private final ArrayList<Component> m_Components = new ArrayList<>();
    private final WeakReference<Scene> m_MyScene;
    private String  m_Name;
    private Transform m_Transform;
        
    //
    //
    //
    public String               getName    (){return m_Name;    }
    public WeakReference<Scene> getScene   (){return m_MyScene; }
    
    public void setName    (final String  aName    ){m_Name     = aName;    }
    
    //
    //
    //
    public Component addComponent(Class<? extends Component> aComponentType)
    {
        Component rValue = null;
        
        try 
        {
            rValue = aComponentType.newInstance();
            
            if (!m_Components.contains(rValue))
            {
                m_Components.add(rValue);
                rValue.OnAddedToGameObjectSuper(this);
                rValue.OnAddedToGameObject(this);
                
            }
            else
                Debug.log("GameObject "+m_Name+" already has a "+rValue.getClass().getSimpleName());
            
            m_MyScene.get().OnComponentAdded(rValue);
        
        } 
        
        catch (InstantiationException | IllegalAccessException ex) {Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);}
        
        return rValue;
        
    }
    
    public void removeComponent(Class<? extends Component> aComponentType)
    {
        for (int i=0,s=m_Components.size();i<s;i++)
            if (m_Components.get(i).getClass() == aComponentType)
            {
                m_MyScene.get().OnComponentRemoved(m_Components.get(i));
                m_Components.get(i).OnRemovedFromGameObjectSuper();
                m_Components.get(i).OnRemovedFromGameObject();
                m_Components.remove(i);
        
            }
        
    }
    
    public Component getComponent(Class<? extends Component> aComponentType)
    {
        Component rValue = null;
        
        Component currentComponent;
        for(int i = 0, s = m_Components.size(); i<s; i++)//m_Components
        {
            currentComponent = m_Components.get(i);
            
            //Debug.log("agagasdg: ",aComponentType,currentComponent.getClass());
            
            if(aComponentType.equals(currentComponent.getClass()))
            {
                rValue = currentComponent;
                
            }
            
        }
        
        return rValue; //aComponentType.cast(rValue)
        
    }
    
    //
    //
    //
    public void update()
    {
        m_Components.forEach(currentComponent->currentComponent.update());
        
    }
    
    //
    //
    //
    public GameObject(WeakReference<Scene> aScene)
    {
        m_Name = "Unnamed GameObject";
        m_MyScene = aScene;
                
    }
    
}
