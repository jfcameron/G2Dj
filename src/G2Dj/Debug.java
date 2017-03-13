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
public class Debug 
{
    public static void log(final Object... aObjects)
    {
        if (aObjects == null)
            return;
        
        String output = aObjects[0].toString(); //aObjects.toString();
        
        if (aObjects.length > 1)
            for(int i = 1, s = aObjects.length; i < s; i++)
                output += ", " + aObjects[i].toString();
        
        System.out.print(output+"\n");
        
    }
    
}
