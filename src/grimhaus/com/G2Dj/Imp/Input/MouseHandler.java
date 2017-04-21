/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Math.IntVector2;

/**
 *
 * @author Joseph Cameron
 */
public class MouseHandler implements MouseListener
{
    //
    // Data members
    //
    private final IntVector2 m_MousePosition        = IntVector2.Zero();
    private MouseButtonState m_LeftButtonStateRaw   = MouseButtonState.Up;
    private MouseButtonState m_MiddleButtonStateRaw = MouseButtonState.Up;
    private MouseButtonState m_RightButtonStateRaw  = MouseButtonState.Up;
    
    private MouseButtonState m_LeftButtonStateReady   = MouseButtonState.Up;
    private MouseButtonState m_MiddleButtonStateReady = MouseButtonState.Up;
    private MouseButtonState m_RightButtonStateReady  = MouseButtonState.Up;
    
    //
    // Public interface
    //            
    public void update()
    {
        m_LeftButtonStateReady   = updateReadyButtonState(m_LeftButtonStateRaw,m_LeftButtonStateReady);
        m_MiddleButtonStateReady = updateReadyButtonState(m_MiddleButtonStateRaw,m_MiddleButtonStateReady);
        m_RightButtonStateReady  = updateReadyButtonState(m_RightButtonStateRaw,m_RightButtonStateReady);
        
    }
    
    public IntVector2 getPosition(){return m_MousePosition;}
    
    public boolean getButtonDown(final MouseButtonCode aButtonCode)
    {
        switch(aButtonCode)
        {
            case Left:
            return (m_LeftButtonStateReady == MouseButtonState.JustPressed)?true:false;
            
            case Middle:
            return (m_MiddleButtonStateReady == MouseButtonState.JustPressed)?true:false;
            
            case Right:
            return (m_RightButtonStateReady  == MouseButtonState.JustPressed)?true:false;
            
            default:
            return false;
            
        }
        
    }
    
    public boolean getButton(final MouseButtonCode aButtonCode)
    {
        switch(aButtonCode)
        {
            case Left:
            return (m_LeftButtonStateReady == MouseButtonState.Down)?true:false;
            
            case Middle:
            return (m_MiddleButtonStateReady == MouseButtonState.Down)?true:false;
            
            case Right:
            return (m_RightButtonStateReady  == MouseButtonState.Down)?true:false;
            
            default:
            return false;
            
        }
        
    }

    //
    // MouseListener implementation
    //
    @Override
    public void mousePressed(MouseEvent me) 
    {        
        if (me.isButtonDown(MouseButtonCode.Left.ToAWTMouseButtonIndex()))
            m_LeftButtonStateRaw = MouseButtonState.JustPressed;
        
        if (me.isButtonDown(MouseButtonCode.Middle.ToAWTMouseButtonIndex()))
            m_MiddleButtonStateRaw = MouseButtonState.JustPressed;
        
        if (me.isButtonDown(MouseButtonCode.Right.ToAWTMouseButtonIndex()))
            m_RightButtonStateRaw = MouseButtonState.JustPressed;

    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {
        if (me.isButtonDown(MouseButtonCode.Left.ToAWTMouseButtonIndex()))
            m_LeftButtonStateRaw = MouseButtonState.JustReleased;
        
        if (me.isButtonDown(MouseButtonCode.Middle.ToAWTMouseButtonIndex()))
            m_MiddleButtonStateRaw = MouseButtonState.JustReleased;
        
        if (me.isButtonDown(MouseButtonCode.Right.ToAWTMouseButtonIndex()))
            m_RightButtonStateRaw = MouseButtonState.JustReleased;
 
    }
    
    @Override
    public void mouseWheelMoved(MouseEvent me) 
    {
        //Debug.log("wheel");
        
    }
    
    @Override
    public void mouseMoved(MouseEvent me)
    {
        updateMousePosition(me.getX(),me.getY());
        
    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {
        updateMousePosition(me.getX(),me.getY());
        
    }
    
    @Override public void mouseClicked(MouseEvent me){}
    @Override public void mouseEntered(MouseEvent me) {}
    @Override public void mouseExited(MouseEvent me) {}
    
    //
    // Implementation
    //
    private void updateMousePosition(final int aX, final int aY)
    {
        m_MousePosition.setInPlace(aX,aY);
    
    }
    
    private MouseButtonState updateReadyButtonState(final MouseButtonState aRawState, MouseButtonState aReadyState)
    {
        if (aRawState == MouseButtonState.JustPressed)
        {
            if(aReadyState == MouseButtonState.Up)
                aReadyState = MouseButtonState.JustPressed;
            
            else if(aReadyState == MouseButtonState.JustPressed)
                aReadyState = MouseButtonState.Down;
                        
        }
        
        if (aRawState == MouseButtonState.JustReleased)
        {
            if(aReadyState == MouseButtonState.Down)
                aReadyState = MouseButtonState.JustReleased;
            
            else if(aReadyState == MouseButtonState.JustReleased)
                aReadyState = MouseButtonState.Up;
            
        }
        
        return aReadyState;
        
    }
    
}
