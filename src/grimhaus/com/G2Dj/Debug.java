/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

//.if ANDROID
//|
//|import java.lang.reflect.Field;
//|import java.lang.reflect.Modifier;
//|import java.util.logging.Level;
//|import java.util.logging.Logger;
//|
//|import android.app.Application;
//|import android.util.Log;
//|
//|import java.lang.reflect.Field;
//|import java.lang.reflect.Type;
//.endif

/**
 *
 * @author Joe
 */
public class Debug 
{
    private static final StringBuilder s_StringBuilder = new StringBuilder();
    
    public static void log(final Object... aObjects)
    {
        if (aObjects == null || aObjects.length <= 0)
            return;
        
        s_StringBuilder.append(aObjects[0].toString());
            
        for(int i = 1, s = aObjects.length; i < s; i++)
        {
            s_StringBuilder.append(", ");
            s_StringBuilder.append(aObjects[i].toString());
        
        }
        
        s_StringBuilder.append("\n");
        
        //.if DESKTOP
        System.out.print(s_StringBuilder.toString());
        //.elseif ANDROID
        //|Log.d("G2Dj",s_StringBuilder.toString());
        //.endif
        
        s_StringBuilder.setLength(0);
        
    }
    
}
