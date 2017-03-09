package Adhoc;

import G2Dj.Debug;
import G2Dj.Engine;
import G2Dj.Imp.Input.KeyCode;
import G2Dj.Input;
import G2Dj.Math.Vector2;

/**
 *
 * @author Joe
 */
public class Test 
{
    private static void test()
    {
        
        
    }
    
    public static void main(String[] args) 
    {
        Engine.init();
        
        test();
        
        while(!Input.getKeyDown(KeyCode.Escape))
        {
            Engine.update();
            
        }
        
        System.exit(0);
        
        
    }
    
}
