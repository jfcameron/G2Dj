/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import com.jogamp.opengl.GL2;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Joe
 */
public class Model extends GraphicsObject
{
    //*************
    // Data members
    //*************
    private final int          m_VertexBufferHandle;
    private final int          m_VertexCount;
    private final VertexFormat m_VertexFormat;
    
    int byteSizeOfFloat = 4;
    
    //**********
    // Accessors
    //**********
    //public int          getVertexCount (){return m_VertexCount; }
    //public VertexFormat getVertexFormat(){return m_VertexFormat;}
    
    //*****************
    // Public interface
    //*****************
    public void draw(final int programHandle)
    {   
        GL2 gl = Graphics.getGL().getGL2();
        
        //Vertex::EnableAttributes(programHandle, m_VertexBufferHandle);

        gl.glDrawArrays( GL2.GL_TRIANGLES, 0, m_VertexCount );
        
    }
    
    //****************
    // Private methods
    //****************
    private void enableAttribute(final String aAttributeName, final int aProgramHandle, final int attributeSize, final int attributeOffset)
    {
        GL2 gl = Graphics.getGL().getGL2();
        
        int attribute = gl.glGetAttribLocation(aProgramHandle, aAttributeName); //Graphics::shader_programme, "a_Pos" );
        
        if (attribute ==-1)
            return;
        
        gl.glEnableVertexAttribArray(attribute);
    
        //Create vertex attribute pointers..
        //Position attribute pointer
        gl.glVertexAttribPointer
        (
            attribute, //Position attribute index
            attributeSize, //Pos size
            gl.GL_FLOAT, //data type of each member of the format (must be uniform, look at glbindbufferdata, it takes an array or ptr to an array, so no suprise)
            false/*gl.GL_FALSE*/, 
            m_VertexFormat.getNumberOfAttributes()*byteSizeOfFloat, //stride is size of vertex format in bytes
            byteSizeOfFloat*attributeOffset  
            
        );
        
    }
    
    private void enableAttributes(final int aProgramHandle)
    {
        GL2 gl = Graphics.getGL().getGL2();
        
        gl.glBindBuffer( GL2.GL_ARRAY_BUFFER, m_VertexBufferHandle);
        
        
        //Map<String, String> map = ...
        //for (Map.Entry<String, String> entry : map.entrySet())
        //{
        //    System.out.println(entry.getKey() + "/" + entry.getValue());
        //}
        
        int attributeOffset = 0;//TODO CALCULATE
        String[] attributeNames = m_VertexFormat.getNames();
        
        for(int i = 0, s = attributeNames.length; i < s; i++)
        {
            String attributeName = attributeNames[i];
            int attributeSize = m_VertexFormat.getAttributeSize(attributeName);
        
            enableAttribute(attributeName, aProgramHandle, attributeSize, attributeOffset);
            
            attributeOffset+=attributeSize;
        
        }
        
    }
    
    
    //private void loadMeshFromFile(final String aFileName, final String aMeshName){}
    
    //************
    // Constructor
    //************
    //Model(const std::string &aFileName, const std::string &aMeshName = "");
    public Model(final String aName, final float[] aVertexData, final VertexFormat aVertexFormat)
    {
        Debug.log("***************MODELTEST******************************");
        
        GL2 gl = Graphics.getGL().getGL2();
        
        
        m_Name         = aName;
        m_VertexCount  = aVertexData.length;
        m_VertexFormat = aVertexFormat;
        
        //Request a vertex buffer from the GL
        IntBuffer vbo = IntBuffer.allocate(1);
        gl.glGenBuffers(1, vbo);
        m_VertexBufferHandle = vbo.get(0);
        
        //Pass data to the vertex buffer
        gl.glBindBuffer (GL2.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData (GL2.GL_ARRAY_BUFFER, byteSizeOfFloat * aVertexFormat.getNumberOfAttributes() * aVertexData.length, FloatBuffer.wrap(aVertexData), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer (GL2.GL_ARRAY_BUFFER,0);
        
    }
    
}
