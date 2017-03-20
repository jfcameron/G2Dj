package grimhaus.com.G2Dj.Imp.Input;

//Project
import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Type.Input.Touch;
import grimhaus.com.G2Dj.Type.Math.Vector2;

//Implementation
import java.util.ArrayList;

//.if ANDROID
//|import android.content.Context;
//|import android.opengl.GLSurfaceView;
//|import android.view.MotionEvent;
//.endif


/**
 * Created by Joe on 3/19/2017.
 */

public class TouchHandler
{
    //
    // Data members
    //
    private static int s_MAX_TOUCHES = 10;
    private final Touch[] m_Touches = new Touch[s_MAX_TOUCHES];
    private int m_TouchCount = 0;

    //
    // Accessors
    //
    public final Touch[] getTouches(){return m_Touches;}

    public final int getTouchCount(){return m_TouchCount;}

    //
    //
    //
    //.if ANDROID
    //|public void update(final MotionEvent aAndroidTouchEvent)
    //|{
    //|    m_TouchCount = aAndroidTouchEvent.getPointerCount();
    //|    
    //|    for (int i=0,s=aAndroidTouchEvent.getPointerCount();i<s;i++)
    //|        if (i > m_Touches.length)
    //|            break;
    //|        else
    //|            processAndroidTouchEvent(aAndroidTouchEvent, i);
    //|    
    //|}
    //|
    //|private Vector2 screenPositionBuffer = new Vector2();
    //|
    //|private void processAndroidTouchEvent(final MotionEvent aAndroidTouchEvent, final int aIndex)
    //|{
    //|    Touch currentTouch = m_Touches[aIndex];
    //|
    //|    screenPositionBuffer.setInPlace(aAndroidTouchEvent.getX(),aAndroidTouchEvent.getY());
    //|
    //|    //Set ID
    //|    currentTouch.id = aIndex;
    //|    //Set delta
    //|    if (aAndroidTouchEvent.getAction() == MotionEvent.ACTION_MOVE)
    //|    {
    //|        currentTouch.deltaPosition.setInPlace(screenPositionBuffer.x - currentTouch.position.x, screenPositionBuffer.y - currentTouch.position.y);
    //|        Debug.log("HELLO: "+currentTouch.deltaPosition);
    //|
    //|    }
    //|    //Set position
    //|    currentTouch.position.setInPlace(aAndroidTouchEvent.getX(),aAndroidTouchEvent.getY());
    //|    //Set time
    //|    currentTouch.time = (int)aAndroidTouchEvent.getDownTime();
    //|    //...
    //|
    //|
    //|    //Set state
    //|    switch (aAndroidTouchEvent.getAction())
    //|    {
    //|        case (MotionEvent.ACTION_DOWN) :
    //|        {
    //|            currentTouch.state = TouchState.Began;
    //|
    //|        } break;
    //|
    //|        case (MotionEvent.ACTION_MOVE) :
    //|        {
    //|            currentTouch.state = TouchState.Moved;
    //|
    //|
    //|
    //|        } break;
    //|
    //|        case (MotionEvent.ACTION_UP) :
    //|        {
    //|            currentTouch.state = TouchState.Ended;//Debug.log("**ACTION_UP**");
    //|            //m_TouchCount--;
    //|
    //|        } break;
    //|
    //|        case (MotionEvent.ACTION_CANCEL) :
    //|        {
    //|            currentTouch.state = TouchState.Canceled;//Debug.log("**ACTION_CANCEL**");
    //|            //m_TouchCount--;
    //|        } break;
    //|
    //|        case (MotionEvent.ACTION_OUTSIDE) : //This shouldnt happen
    //|        {
    //|            currentTouch.state = TouchState.NULL;//Debug.log("**ACTION_OUTSIDE**");
    //|            //m_TouchCount--;
    //|        } break;
    //|
    //|    }
    //|
    //|
    //|
    //|}
    //.endif
    
    public TouchHandler()
    {
        for(int i=0,s=m_Touches.length;i<s;i++)
            m_Touches[i] = new Touch();

    }

}
