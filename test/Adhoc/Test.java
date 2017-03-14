package Adhoc;

import G2Dj.Engine;
import G2Dj.Imp.Input.KeyCode;
import G2Dj.Input;
import G2Dj.Type.Engine.Scene;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Test 
{
    private static void test()
    {
        WeakReference<Scene> mainScene = Engine.createScene("Main");
        
    }
    
    public static void main(String[] args) 
    {
        Engine.init();
        
        test();
        
        while(!Input.getKeyDown(KeyCode.Escape))
        {
            Engine.update();
            
        }
        
        while(!Input.getKeyDown(KeyCode.Escape)){}
        //for(;;){}
        //System.exit(0);
        
        
    }
    
}
