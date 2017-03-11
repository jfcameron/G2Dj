/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Joe
 */
public class VertexFormat 
{
    //
    // Data members
    //
    private final LinkedHashMap<String,Integer>  m_Format = new LinkedHashMap<>();
    private final int m_Stride = 0;
    
    //
    // Accessors
    //
    int getStride(){return m_Stride;}
    
    //
    // private Implementation
    //
    private void calculateStride(LinkedHashMap<String,Integer> aFormat)
    {
        int stride = 0;
        
        for (Map.Entry<String,Integer> entry : aFormat.entrySet())
        {
           System.out.println(entry.getKey() + "/" + entry.getValue());
           
        }
        
    }
    
    //
    //
    //
    public VertexFormat(VertexAttribute... aAttributes)
    {
        //Process attribute data
        VertexAttribute attribute = null;
        for(int i = 0, s = aAttributes.length; i < s; i++)
            m_Format.put(attribute.Name, attribute.Size);
        
        
        
                
    }
    
    //Special formats
    /*public abstract VertexFormat v2uv2()
    {
        return 
        
    }*/
    
}