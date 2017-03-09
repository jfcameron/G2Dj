/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj;

/**
 *
 * @author Joe
 */
public class Engine 
{    
    public static void init()
    {
        Input.init();
        Graphics.init();
        
    }
    
    public static void update()
    {
        Input.update();
        
        Graphics.draw();
        
    }
    
}
