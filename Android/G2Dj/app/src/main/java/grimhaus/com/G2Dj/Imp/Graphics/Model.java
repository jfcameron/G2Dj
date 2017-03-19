/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Debug;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Joe
 */
public class Model extends GraphicsResource
{
    //*************
    // Data members
    //*************
    private final int          m_VertexBufferHandle;
    private final int          m_VertexCount;
    private final VertexFormat m_VertexFormat;
        
    //**********
    // Accessors
    //**********
    //public int          getVertexCount (){return m_VertexCount; }
    //public VertexFormat getVertexFormat(){return m_VertexFormat;}
    public int getHandle(){return m_VertexBufferHandle;}
    
    //*****************
    // Public interface
    //*****************
    public void draw(final int programHandle)
    {   
        enableAttributes(programHandle);

        GL.glDrawArrays( GL.GL_TRIANGLES, 0, m_VertexCount );
        
    }
    
    //****************
    // Private methods
    //****************
    private void enableAttribute(final String aAttributeName, final int aProgramHandle, final int attributeSize, final int attributeOffset)
    {
        int attribute = GL.glGetAttribLocation(aProgramHandle, aAttributeName);
        
        if (attribute ==-1)
        {
            //Debug.log("unused attribute: "+aAttributeName);
            return;
        }
            
        GL.glEnableVertexAttribArray(attribute);
    
        //Create vertex attribute pointers..
        //Position attribute pointer
        GL.glVertexAttribPointer
        (
            attribute, //Position attribute index
            attributeSize, //Pos size
            GL.GL_FLOAT, //data type of each member of the format (must be uniform, look at glbindbufferdata, it takes an array or ptr to an array, so no suprise)
            false/*gl.GL_FALSE*/, 
            Constants.FloatSize*m_VertexFormat.getSumOfAttributeComponents(),
            Constants.FloatSize*attributeOffset
            
        );
        
    }
    
    private void enableAttributes(final int aProgramHandle)
    {
        GL.glBindBuffer( GL.GL_ARRAY_BUFFER, m_VertexBufferHandle);
        
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
        //super(aName);
        m_Name = aName;
        
        Debug.log("***************MODELTEST******************************");
        
        //m_Name         = aName;
        m_VertexCount  = aVertexData.length;
        m_VertexFormat = aVertexFormat;
        
        ////////////////////////////////////////DEBUG
        String[] asdf = m_VertexFormat.getNames();
        String debugdata="{";
        for(int i = 0; i < asdf.length; i++)
            debugdata+=asdf[i]+", ";
        debugdata+="} {";
        for(int i = 0; i < aVertexData.length; i++)
            debugdata+=aVertexData[i]+", ";
        debugdata+="}";
        Debug.log(getName(),m_VertexCount,debugdata);
        /////////////////////////////////////////////////
        
        //Request a vertex buffer from the GL
        IntBuffer vbo = IntBuffer.allocate(1);
        GL.glGenBuffers(1, vbo);
        m_VertexBufferHandle = vbo.get(0);
        
        //Pass data to the vertex buffer
        GL.glBindBuffer (GL.GL_ARRAY_BUFFER, vbo.get(0));
        GL.glBufferData (GL.GL_ARRAY_BUFFER, Constants.FloatSize * aVertexData.length, FloatBuffer.wrap(aVertexData), GL.GL_STATIC_DRAW);
        GL.glBindBuffer (GL.GL_ARRAY_BUFFER,0);
        
    }
    
}
