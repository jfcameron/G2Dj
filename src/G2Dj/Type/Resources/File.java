/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
