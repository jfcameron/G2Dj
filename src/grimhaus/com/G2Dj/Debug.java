/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

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