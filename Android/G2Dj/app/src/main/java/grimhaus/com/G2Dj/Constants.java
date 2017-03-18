/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

/**
 *
 * @author Joseph Cameron
 */
public class Constants 
{
    public static final int SizeOfFloat;
    
    static
    {
        //.if DESKTOP
        //|SizeOfFloat = Float.BYTES;
        //.elseif ANDROID
        SizeOfFloat = 4;
        //.endif
        
    }
    
}
