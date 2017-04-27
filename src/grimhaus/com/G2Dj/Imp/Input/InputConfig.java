/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Input;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.Gamepad.Axis;
import grimhaus.com.G2Dj.Imp.Input.Gamepad.Button;
import grimhaus.com.G2Dj.Imp.Input.Gamepad.Hat;
import grimhaus.com.G2Dj.Input;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joseph Cameron
 */
public class InputConfig 
{
    static int s_GamepadIndex = 0;
    
    //
    private final HashMap<String,InputEvent> m_InputEvents = new HashMap<>();
        
    //
    public final boolean get(final String aEventName)
    {
        InputEvent temp = m_InputEvents.get(aEventName);
        
        if (temp!=null)
            return temp.get();
        
        return false;
    
    }
    
    public final boolean getDown(final String aEventName)
    {
        InputEvent temp = m_InputEvents.get(aEventName);
        
        if (temp!=null)
            return temp.getDown();
        
        return false;
    
    }
    
    public final void addKeysToAEvent(final String aEventName, KeyCode... aKeyCodes)
    {
        if (aKeyCodes!=null)
            m_InputEvents.get(aEventName).addKeyCodes(aKeyCodes);
        
    }
    
    //
    // Gamepad functions
    //
    public final void addGamepadHatToAEvent(final String aEventName,final String aHatName,final Hat.Direction... aDirection)
    {
        if (aDirection != null)
            m_InputEvents.get(aEventName).addGamepadHat(m_Gamepad,aHatName,aDirection);
        
    }
    
    public final void addGamepadButtonsToAEvent(final String aEventName, String... aButtonNames)
    {
        if (aButtonNames!=null)
            m_InputEvents.get(aEventName).addGamepadButtons(m_Gamepad,aButtonNames);
        
    }
    
    //
    public InputConfig(final InputEvent... aInputEvents)
    {
        if (aInputEvents != null)
            for(int i=0,s=aInputEvents.length;i<s;i++)
                m_InputEvents.put(aInputEvents[i].getName(),aInputEvents[i]);
        
        requestGamepad();
        
    }
    
    private Gamepad m_Gamepad = null;
    private boolean requestGamepad()
        {
            Debug.log(m_Gamepad == null?true:false);
            
            if (m_Gamepad == null)
            {                
                Gamepad[] gamepads = Input.getGamepads();
                
                //if (gamepads==null || gamepads.length <= 0)
                //    return false;
            
                //Debug.log("GAMEPAD IS NULL");
            
                m_Gamepad = gamepads[s_GamepadIndex];
                s_GamepadIndex++;
                
            }
            
            return true;
            
        }
    
    private void releaseGamepad()
        {
            s_GamepadIndex--;
            
            if (s_GamepadIndex<0)
                s_GamepadIndex = 0;
            
        }
        
        @Override
        protected void finalize() throws Throwable
        {
            releaseGamepad();
            
            super.finalize();
        
        }
    
    //
    public static class InputEvent
    {
        private final String m_Name;
        
        
        private final ArrayList<KeyCode>             m_Keys    = new ArrayList<>();
        private final ArrayList<Button>              m_Buttons = new ArrayList<>();
        private final ArrayList<HatAndDirectionPair> m_Hats    = new ArrayList<>();
        
        public final String getName(){return m_Name;}
        
        public final boolean get()
        {
            for(int i=0,s=m_Keys.size();i<s;i++)
                if (Input.getKey(m_Keys.get(i)))
                    return true;
            
            for(int i=0,s=m_Buttons.size();i<s;i++)
                if (m_Buttons.get(i).get())
                    return true;
            
            for(int i=0,s=m_Hats.size();i<s;i++)
                if (m_Hats.get(i).get())
                    return true;
            
            //todo: more
            
            return false;
                        
        }
        
        public final boolean getDown()
        {
            for(int i=0,s=m_Keys.size();i<s;i++)
                if (Input.getKeyDown(m_Keys.get(i)))
                    return true;
            
            for(int i=0,s=m_Buttons.size();i<s;i++)
                if (m_Buttons.get(i).getDown())
                    return true;
            
            for(int i=0,s=m_Hats.size();i<s;i++)
                if (m_Hats.get(i).getDown())
                    return true;
            
            //todo: more
            
            return false;
            
        }
        
        public void addKeyCodes(final KeyCode... aKeyCodes)
        {
            if (aKeyCodes != null)
                for(int i=0,s=aKeyCodes.length;i<s;i++)
                    m_Keys.add(aKeyCodes[i]);
            
        }
        
        public final void addGamepadHat(final Gamepad aGamepad, final String aHatName,final Hat.Direction... aDirections)
        {
            //if (requestGamepad() == false)
            //    return;
            
            m_Hats.add(new HatAndDirectionPair(aGamepad.getHat(aHatName),aDirections));
            
        }
        
        public final void addGamepadButtons(final Gamepad aGamepad, final String... aButtonNames)
        {
            //if (requestGamepad() == false)
             //   return;
            
            if (aButtonNames != null)
                for(int i=0,s=aButtonNames.length;i<s;i++)
                {
                    m_Buttons.add(aGamepad.getButton(aButtonNames[i]));
                
                }
            
        }
        
        public InputEvent(final String aInputEventName)
        {
            m_Name = aInputEventName;
            
        }
        
        
        
        private class HatAndDirectionPair
        {
            public final Hat hat;
            public final Hat.Direction[] directions;
            
            public final boolean get()
            {
                for(int i=0,s=directions.length;i<s;i++)
                    if(hat.get(directions[i]))
                        return true;
                
                return false;
            
            }
            
            public final boolean getDown()
            {
                for(int i=0,s=directions.length;i<s;i++)
                    if(hat.getDown(directions[i]))
                        return true;
                
                return false;
            
            }
            
            public HatAndDirectionPair(final Hat aHat, final Hat.Direction[] aDirections)
            {
                hat = aHat;
                directions = aDirections;
                
            }
            
        }
        
    }
    
}
