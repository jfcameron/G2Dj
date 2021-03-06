/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Resources;

/**
 *
 * @author Joe
 * @param <DataType>
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
