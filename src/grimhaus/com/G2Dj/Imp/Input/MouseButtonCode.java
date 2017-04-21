/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Input;

/**
 *
 * @author Joseph Cameron
 */
public enum MouseButtonCode 
{
    Left,
    Middle,
    Right;
    
    public int ToAWTMouseButtonIndex()
    {
        switch(this)
        {
            default:
            case Left:
            return 1;
            
            case Middle:
            return 2;
            
            case Right:
            return 3;
            
        }
        
    }
    
}
