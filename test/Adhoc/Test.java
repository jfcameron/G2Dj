package Adhoc;

import G2Dj.Engine;
import G2Dj.Imp.Input.KeyCode;
import G2Dj.Input;

/**
 *
 * @author Joe
 */
public class Test 
{
    private static void test()
    {
        Engine.createScene("Main");
        
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
