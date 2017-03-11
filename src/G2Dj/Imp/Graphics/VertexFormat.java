/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import java.util.LinkedHashMap;

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
    
    //
    // Accessors
    //
    public int getNumberOfAttributes(){return m_NumberOfAttributes;}
    //implement foreach?
    
    //
    //
    //
    public VertexFormat(VertexAttribute... aAttributes)
    {
        m_NumberOfAttributes = aAttributes.length;
        
        //Process attribute data
        VertexAttribute attribute = null;
        for(int i = 0, s = m_NumberOfAttributes; i < s; i++)
            m_Format.put(attribute.getName(), attribute.getSize());
        
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