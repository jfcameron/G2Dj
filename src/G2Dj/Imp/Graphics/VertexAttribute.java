/*


*/
package G2Dj.Imp.Graphics;

/**
 *
 * @author Joe
 */
public class VertexAttribute 
{
    private final String  m_Name;
    private final Integer m_Size;
    
    public String getName(){return m_Name != null ? m_Name : "" ;}
    public int    getSize(){return m_Size;}
    
    public VertexAttribute(final String aName, final Integer aSize)
    {
        m_Name=aName;
        m_Size=aSize;
    
    }
    
}
