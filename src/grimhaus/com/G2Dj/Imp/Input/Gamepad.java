/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Input;

import grimhaus.com.G2Dj.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

/**
 *
 * @author Joseph Cameron
 */
public class Gamepad 
{    
    //
    //
    //
    private final String m_Name;
    private final net.java.games.input.Controller m_ControllerHandle;
    
    private final HashMap<String,Axis>   m_Axes    = new HashMap<>();
    private final HashMap<String,Hat>    m_Hats    = new HashMap<>();
    private final HashMap<String,Button> m_Buttons = new HashMap<>();
    
    //
    //
    //
    public String getName(){return m_Name;}
    
    public final String[] getAxisNames()
    {
        String[] rvalue = new String[m_Axes.size()];
        
        int i =0;
        for(Map.Entry<String,Axis> entry : m_Axes.entrySet())
            rvalue[i++] = entry.getKey();
        
        return rvalue;
        
    }
    
    public final String[] getHatNames()
    {
        String[] rvalue = new String[m_Hats.size()];
        
        int i =0;
        for(Map.Entry<String,Hat> entry : m_Hats.entrySet())
            rvalue[i++] = entry.getKey();
        
        return rvalue;
        
    }
    
    public final String[] getButtonNames()
    {
        String[] rvalue = new String[m_Buttons.size()];
        
        int i =0;
        for(Map.Entry<String,Button> entry : m_Buttons.entrySet())
            rvalue[i++] = entry.getKey();
        
        return rvalue;
        
    }
    
    public boolean getButton(final String aButtonName){return m_Buttons.get(aButtonName).get();}
    public boolean getButtonDown(final String aButtonName){return m_Buttons.get(aButtonName).getDown();}
    public float   getAxis(final String aAxisName){return m_Axes.get(aAxisName).get();}
    
    public void update()
    {
        m_ControllerHandle.poll();
        
        for(Map.Entry<String,Axis> entry : m_Axes.entrySet())//for (int i=0,s=m_Axes.size();i<s;i++)
            entry.getValue().update();
        
        for(Map.Entry<String,Hat> entry : m_Hats.entrySet())//for (int i=0,s=m_Axes.size();i<s;i++)
            entry.getValue().update();
        
        for(Map.Entry<String,Button> entry : m_Buttons.entrySet())//for (int i=0,s=m_Axes.size();i<s;i++)
            entry.getValue().update();
        
    }
    
    //
    //
    //
    public Gamepad(final net.java.games.input.Controller aJInputControllerHandle)
    {
        m_Name = aJInputControllerHandle.getName();
        m_ControllerHandle = aJInputControllerHandle;
        
        net.java.games.input.Component[] components = aJInputControllerHandle.getComponents();        
        net.java.games.input.Component currentComponent;
        
        for(int i=0,s=components.length;i<s;i++)
        {
            currentComponent = components[i];
            
            if (currentComponent.getIdentifier() instanceof Identifier.Button)
                m_Buttons.put(currentComponent.getName(),new Button(currentComponent));
            else if (currentComponent.getIdentifier() instanceof Identifier.Axis)
            {
                if (currentComponent.getIdentifier() == Identifier.Axis.POV)
                    m_Hats.put(currentComponent.getName(),new Hat(currentComponent));
                else
                    m_Axes.put(currentComponent.getName(),new Axis(currentComponent));
                                
            }
                
        }
        
    }
    
    //
    // inner classes
    //
    private abstract class Component
    {
        protected abstract void update();
        protected final net.java.games.input.Component m_Component;
        protected Component(final net.java.games.input.Component aComponent){m_Component = aComponent;}
        
    }
    
    private class Button extends Component
    {
        public boolean getDown() 
        {
            return true;
        
        }

        public boolean get() 
        {
            return true;
        
        }
        
        public Button(net.java.games.input.Component aComponent) {super(aComponent);}

        @Override protected void update() {}
        
    }
    
    private class Axis extends Component
    {
        public float get() 
        {
            return 1.0f;
            
        }
        
        public Axis(net.java.games.input.Component aComponent) {super(aComponent);}

        @Override protected void update() {}
        
    }
    
    private class Hat extends Component
    {
        
        public boolean getDown() 
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        }

        public boolean get() 
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        }
        
        public Hat(net.java.games.input.Component aComponent) {super(aComponent);}
        
        @Override protected void update() {}
        
    }
    
    @Override public String toString()
    {
        StringBuilder output = new StringBuilder();
        String[] buttonNames = getButtonNames();
        String[] hatNames    = getHatNames();
        String[] axisNames   = getAxisNames();
        
        output.append(m_Name).append(": {");
                
        for(int i=0;i<buttonNames.length;i++)
        {
            output.append(buttonNames[i]);
            if (i < -1+buttonNames.length)
                output.append(", ");
        
        }
        
        output.append("},{");
        
        for(int i=0;i<hatNames.length;i++)
        {
            output.append(hatNames[i]);
            if (i < -1+hatNames.length)
                output.append(", ");
        
        }
        
        output.append("},{");
        
        for(int i=0;i<axisNames.length;i++)
        {
            output.append(axisNames[i]);
            if (i < -1+axisNames.length)
                output.append(", ");
        
        }
        
        output.append("}");
                    
        return "{"+output.toString()+"}";
        
    }
    
}
