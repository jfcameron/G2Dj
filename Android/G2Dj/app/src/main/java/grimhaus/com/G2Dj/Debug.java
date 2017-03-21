/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

//.if ANDROID

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    /*public static String getFormattedInspectionString(Class<?> type) 
    {
        String rValue =type.getSimpleName()+"{ ";
        Field[] fields = type.getDeclaredFields();
        
        Field currentField = null;
        for (int i=0,s=fields.length;i<s;i++) 
        {
            currentField = fields[i];
            
            try 
            {
                rValue+=currentField.getName()+": "+currentField.get(type);
            
            } 
            catch (IllegalArgumentException ex){Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);} 
            catch (IllegalAccessException ex){Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);}
        
            if (i < s-1)
                rValue+=", ";
            
        }
        
        rValue +=" }";
        
        return rValue;
        
    }*/
    
}
