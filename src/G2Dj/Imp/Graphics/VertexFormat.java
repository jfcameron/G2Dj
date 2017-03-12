/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Joe
 */
public class VertexFormat 
{
    //
    // Data members
    //
    private final LinkedHashMap<String,Integer> m_Format = new LinkedHashMap<>();
    private final int m_NumberOfAttributes;
    private final int m_TotalNumberOfAttributeComponents;
    
    //
    // Accessors
    //
    public int getTotalNumberOfAttributeComponents(){return m_TotalNumberOfAttributeComponents;}
    public int getNumberOfAttributes(){return m_NumberOfAttributes;}
    public int getAttributeSize(final String aAttributeName){return m_Format.get(aAttributeName);}
    public String[] getNames()
    {
        String[] keys = new String[m_NumberOfAttributes];
        
        int i = 0;
        for (Map.Entry<String, Integer> entry : m_Format.entrySet())
            keys[i++] = entry.getKey();
            
        return keys;
        
    }
    
    //implement foreach?
    
    //
    //
    //
    public VertexFormat(VertexAttribute... aAttributes)
    {
        m_NumberOfAttributes = aAttributes.length;
        int attributeComponentCount = 0;
        
        
        //Process attribute data
        VertexAttribute attribute = null;
        for(int i = 0, s = m_NumberOfAttributes; i < s; i++)
        {
            attribute = aAttributes[i];
            m_Format.put(attribute.getName(), attribute.getSize());
            
            attributeComponentCount+= attribute.getSize();
                    
        }
        
        m_TotalNumberOfAttributeComponents = attributeComponentCount;
            
    }
    
    //
    // Special formats
    //
    public static VertexFormat pos3uv2 = new VertexFormat
    (
        new VertexAttribute("a_Position",3),
        new VertexAttribute("a_UV"      ,2)
                                
    );
    
}