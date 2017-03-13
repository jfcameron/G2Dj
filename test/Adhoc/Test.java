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
        
        //G2Dj.Graphics.getWindow().setTitle("AdhocTest");
        
        /*G2Dj.Graphics.loadShader(new TestShader());
        
        WeakReference<ShaderProgram> shader = Graphics.getShaderProgram("TestShader");
        
        
        */
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
