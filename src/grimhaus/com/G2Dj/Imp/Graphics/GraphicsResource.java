/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

/**
 *
 * @author Joe
 */
public abstract class GraphicsResource extends Object
{
    //
    // Data members
    //
    protected String m_Name;// = "Unnamed GraphicsObject";
    
    //
    // Accessors
    //
    public String getName() {return m_Name;}
    
    //
    // Ctor
    //
    //protected GraphicsObject(final String aName){m_Name = aName;}
    
    @Override public String toString(){return "GraphicsResource: { m_Name: "+m_Name+" }";}
    
}
