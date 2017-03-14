/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Engine;

import G2Dj.Debug;
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
    private final String               m_Name;
    private final ArrayList<Component> m_Components = new ArrayList<>(); 
    
    //
    //
    //
    public Component addComponent(Class<? extends Component> aComponentType)
    {
        Component rValue = null;
        
        try 
        {
            rValue = aComponentType.newInstance();
            m_Components.add(rValue);
        
        } 
        
        catch (InstantiationException | IllegalAccessException ex) {Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);}
        
        return rValue;
        
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
    public GameObject()
    {
        m_Name = "Unnamed GameObject";
        
    }
    
}
