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
    
    /*public final Button[] getButtons()
    {
        
        
    }*/
    
    public Button getButton(final String aButtonName){return m_Buttons.get(aButtonName);}
    public Hat    getHat   (final String aHatName   ){return m_Hats   .get(aHatName)   ;}
    public Axis   getAxis  (final String aAxisName  ){return m_Axes   .get(aAxisName)  ;}
    
    //public boolean getButton(final String aButtonName){return m_Buttons.get(aButtonName).get();}
    //public boolean getButtonDown(final String aButtonName){return m_Buttons.get(aButtonName).getDown();}
    //public float   getAxis(final String aAxisName){return m_Axes.get(aAxisName).get();}
    
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
        protected final net.java.games.input.Component m_Component;
        protected abstract void update();
        protected Component(final net.java.games.input.Component aComponent){m_Component = aComponent;}
        
    }
    
    public static class Button extends Component
    {
        private ButtonState m_Ready = ButtonState.Up;
        private ButtonState m_Raw   = ButtonState.Up;
        
        public boolean getDown() 
        {
            return (m_Ready == ButtonState.JustPressed)? true : false;
        
        }

        public boolean get() 
        {
            return (m_Ready == ButtonState.JustPressed || m_Ready == ButtonState.Down)? true : false;
        
        }
        
        @Override 
        protected void update() 
        {
            m_Raw = ButtonState.FromJInputComponentPollData(m_Component.getPollData());
            
            if (m_Ready == ButtonState.Down && m_Raw == ButtonState.Up)
                m_Ready = ButtonState.JustReleased;  
            
            else if (m_Ready == ButtonState.Up && m_Raw == ButtonState.Down)
                m_Ready = ButtonState.JustPressed;  
            else
                m_Ready = m_Raw;
        
        }
        
        protected Button(net.java.games.input.Component aComponent) {super(aComponent);}
        
        public static enum ButtonState
        {
            Up,
            Down,
            JustPressed,
            JustReleased;
            
            public static ButtonState FromJInputComponentPollData(final float aJInputComponentPollData)
            {
                return (aJInputComponentPollData==1.0f)?Down:Up;
                
            }
            
        }

    }
    
    public static class Axis extends Component
    {        
        public float get() 
        {
            return m_Component.getPollData();
            
        }
        
        protected Axis(net.java.games.input.Component aComponent) {super(aComponent);}

        @Override protected void update(){}
        
    }
    
    public static class Hat extends Component
    {
        private HatState m_Ready = HatState.Up;
        private HatState m_Raw   = HatState.Up; 

        public boolean get(final Direction aDirection) 
        {
            switch(aDirection)
            {
                case Up       : return (m_Ready == HatState.Up   )?true:false;
                case Down     : return (m_Ready == HatState.Down )?true:false;
                case Left     : return (m_Ready == HatState.Left )?true:false;
                case Right    : return (m_Ready == HatState.Right)?true:false;
                
                case UpLeft   : return (m_Ready == HatState.UpLeft)?true:false;
                case DownLeft : return (m_Ready == HatState.DownLeft)?true:false;
                case UpRight  : return (m_Ready == HatState.UpRight)?true:false;
                case DownRight: return (m_Ready == HatState.DownRight)?true:false;
                
            }
            
            return false;
            
        }
        
        public boolean getDown(final Direction aDirection) 
        {
            switch(aDirection)
            {
                case Up       : return (m_Ready == HatState.JustUp   )?true:false;
                case Down     : return (m_Ready == HatState.JustDown )?true:false;
                case Left     : return (m_Ready == HatState.JustLeft )?true:false;
                case Right    : return (m_Ready == HatState.JustRight)?true:false;
                
                case UpLeft   : return (m_Ready == HatState.JustUpLeft)?true:false;
                case DownLeft : return (m_Ready == HatState.JustDownLeft)?true:false;
                case UpRight  : return (m_Ready == HatState.JustUpRight)?true:false;
                case DownRight: return (m_Ready == HatState.JustDownRight)?true:false;
                
            }
            
            return false;
            
        }
        
        protected Hat(net.java.games.input.Component aComponent) {super(aComponent);}
        
        @Override protected void update() 
        {
            m_Raw = Hat.HatState.FromJInputComponentPollData(m_Component.getPollData());
            
            if (m_Raw == HatState.Up && m_Ready != HatState.Up && m_Ready != HatState.JustUp)
                m_Ready = HatState.JustUp;
            
            else if (m_Raw == HatState.Down && m_Ready != HatState.Down && m_Ready != HatState.JustDown)
                m_Ready = HatState.JustDown;
            
            else if (m_Raw == HatState.Left && m_Ready != HatState.Left && m_Ready != HatState.JustLeft)
                m_Ready = HatState.JustLeft;
            
            else if (m_Raw == HatState.Right && m_Ready != HatState.Right && m_Ready != HatState.JustRight)
                m_Ready = HatState.JustRight;
            
            //45
            else if (m_Raw == HatState.UpLeft && m_Ready != HatState.UpLeft && m_Ready != HatState.JustUpLeft)
                m_Ready = HatState.JustUpLeft;
            
            else if (m_Raw == HatState.DownLeft && m_Ready != HatState.DownLeft && m_Ready != HatState.JustDownLeft)
                m_Ready = HatState.JustDownLeft;
            
            else if (m_Raw == HatState.UpRight && m_Ready != HatState.UpRight && m_Ready != HatState.JustUpRight)
                m_Ready = HatState.JustUpRight;
            
            else if (m_Raw == HatState.DownRight && m_Ready != HatState.DownRight && m_Ready != HatState.JustDownRight)
                m_Ready = HatState.JustDownRight;
            
            //Neutral
            else if (m_Raw == HatState.Neutral && m_Ready != HatState.Neutral && m_Ready != HatState.JustReleased)
                m_Ready = HatState.JustReleased;
            
            else
                m_Ready = m_Raw;
            
        }
        
        public static enum Direction
        {
            Up,
            Down,
            Left,
            Right,
            
            UpLeft,
            DownLeft,
            UpRight,
            DownRight;
            
        }
        
        private static enum HatState
        {
            Up,
            Down,
            Left,
            Right,
            
            UpLeft,
            DownLeft,
            UpRight,
            DownRight,
            
            //
            JustUp,
            JustDown,
            JustLeft,
            JustRight,
            
            JustUpLeft,
            JustDownLeft,
            JustUpRight,
            JustDownRight,
            
            JustReleased,
            Neutral;
            
            public static HatState FromJInputComponentPollData(final float aJInputComponentPollData)
            {
                if (aJInputComponentPollData == net.java.games.input.Component.POV.UP)
                    return HatState.Up;
                else if (aJInputComponentPollData == net.java.games.input.Component.POV.DOWN)
                    return HatState.Down;
                else if (aJInputComponentPollData == net.java.games.input.Component.POV.LEFT)
                    return HatState.Left;
                else if (aJInputComponentPollData == net.java.games.input.Component.POV.RIGHT)
                    return HatState.Right;
                //cardinal+45
                else if (aJInputComponentPollData == net.java.games.input.Component.POV.UP_LEFT)
                    return HatState.UpLeft;
                else if (aJInputComponentPollData == net.java.games.input.Component.POV.UP_RIGHT)
                    return HatState.UpRight;
                else if (aJInputComponentPollData == net.java.games.input.Component.POV.DOWN_LEFT)
                    return HatState.DownLeft;
                else if (aJInputComponentPollData == net.java.games.input.Component.POV.DOWN_RIGHT)
                    return HatState.DownRight;
                
                return HatState.Neutral;
                
            }
            
        }
        
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
