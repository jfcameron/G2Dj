/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Engine;

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
    //
    //
    //
    private       String               m_Name;
    private final ArrayList<Component> m_Components = new ArrayList<>();
    private final WeakReference<Scene> m_MyScene;
    
    //private ArrayList<int> m_asdf = new ArrayList<int>();
    //private final ArrayList<Consumer<Component>> m_OnComponentAttached = new ArrayList<>();
    //private m_OnComponentRemoved;
    
    //
    //
    //
    public String getName(){return m_Name;}
    
    public void setName(final String aName){m_Name = aName;}
    
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
                m_Components.add(rValue);
            
            m_MyScene.get().OnComponentAdded(rValue);
        
        } 
        
        catch (InstantiationException | IllegalAccessException ex) {Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);}
        
        return rValue;
        
    }
    
    public void removeComponent(Class<? extends Component> aComponentType)
    {
        //aComponentType.
                
        /*for(int i = 0, s = m_Components.size(); i < s; i++ )
            if (m_Components.get(i).TypeRTTI() == aComponentType.TypeRTTI() )
            {
                
                
            }*/
        
        throw new UnsupportedOperationException("Not supported yet.");
       
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
