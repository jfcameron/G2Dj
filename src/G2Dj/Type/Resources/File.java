/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Type.Resources;

/**
 *
 * @author Joe
 */
public abstract class File<DataType> 
{
    private final String m_Name;
    private final DataType m_Data;
    
    public String   getName(){return m_Name;}
    public DataType getData(){return m_Data;}
    
    protected File(final String aName, final DataType aData)
    {
        m_Name = aName;
        m_Data = aData;
        
    }
    
}
