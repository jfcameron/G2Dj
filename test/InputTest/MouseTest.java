/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.MouseButtonCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class MouseTest extends Component 
{
    //
    
    //
    @Override
    protected void initialize() 
    {
        
    
    }

    @Override
    protected void update() 
    {
        //buttonsTest();
        leftClickAtTest();

    }

    @Override
    protected void fixedUpdate() 
    {
    
    
    }

    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
    //
    //
    //
    private void leftClickAtTest()
    {
        if (Input.getMouseButtonDown(MouseButtonCode.Left))
            Debug.log("LeftClicked at: "+Input.getMousePosition());
        
    }
    
    private void buttonsTest()
    {
        if (Input.getMouseButton(MouseButtonCode.Left))
            Debug.log("Left button held");
        
        if (Input.getMouseButton(MouseButtonCode.Middle))
            Debug.log("Middle button held");
        
        if (Input.getMouseButton(MouseButtonCode.Right))
            Debug.log("Right button held");
        
        
        if (Input.getMouseButtonDown(MouseButtonCode.Left))
            Debug.log("Left button clicked");
        
        if (Input.getMouseButtonDown(MouseButtonCode.Middle))
            Debug.log("Middle button clicked");
        
        if (Input.getMouseButtonDown(MouseButtonCode.Right))
            Debug.log("Right button clicked");
        
    }
    
}
