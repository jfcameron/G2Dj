package Adhoc;

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
    public static void main(String[] args) 
    {
        Engine.init();
        
        System.out.print(Vector2.Down+"\n");
              
        Vector2 aVec = Vector2.Down();
        
        aVec.addInPlace(Vector2.Left);
        
        aVec.x += 10;
        
        System.out.print(aVec+"\n");
        System.out.print(Vector2.Down+"\n");
        
        
        while(!Input.getKeyDown(KeyCode.Escape))
        {
            Engine.update();
            
        }
        
        System.exit(0);
        
        
    }
    
}
