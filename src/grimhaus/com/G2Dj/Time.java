/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

/**
 *
 * @author Joseph Cameron
 */
public class Time 
{
    //
    // Data members
    //
    private static double s_LastTime;
    private static double s_DeltaTime;
    
    //
    // Accessors
    //
    public static double getCurrentTime(){return ((double)System.nanoTime())/1E9f;}
    public static double getDeltaTime(){return s_DeltaTime;}
    
    //
    // Engine interface
    //
    protected static void update()
    {
        double currentTime = getCurrentTime();
        s_DeltaTime =  currentTime - s_LastTime;
        s_LastTime = currentTime;
        
    }
    
    //
    // Static initializer
    //
    static
    {
        s_LastTime = getCurrentTime();
        
    }
    
}
