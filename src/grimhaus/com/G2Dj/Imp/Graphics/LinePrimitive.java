/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

/**
 *
 * @author Joseph Cameron
 */
public enum LinePrimitive 
{
    Lines,
    LineStrip,
    LineLoop;
    
    public int toOpenGLLinePrimitive()
    {
        switch (this) 
        {
            case Lines:
            default:
            return GL.GL_LINES;
            
            case LineStrip:
            return GL.GL_LINE_STRIP;

            case LineLoop:
            return GL.GL_LINE_LOOP;
    
        }
    
    }
    
}
