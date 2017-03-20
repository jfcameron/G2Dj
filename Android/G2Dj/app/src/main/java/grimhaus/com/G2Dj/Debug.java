/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

//.if ANDROID
import android.app.Application;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
//.endif

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

        //.if DESKTOP
        //|System.out.print(output+"\n");
        //.elseif ANDROID
        Log.d("G2Dj",output+"\n");
        //.endif

    }

}
