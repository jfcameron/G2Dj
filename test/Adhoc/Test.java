package Adhoc;

import G2Dj.Debug;
import G2Dj.Engine;
import G2Dj.Imp.Input.KeyCode;
import G2Dj.Input;
import G2Dj.Math.Vector2;

import Adhoc.TestShader;

/**
 *
 * @author Joe
 */
public class Test 
{
    private static void test()
    {
        G2Dj.Graphics.getWindow().setTitle("AdhocTest");
        
        G2Dj.Graphics.loadShader(new TestShader());
        
        
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
