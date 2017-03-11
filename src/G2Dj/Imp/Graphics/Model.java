/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

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
        
        
    }
    
    //****************
    // Private methods
    //****************
    //private void loadMeshFromFile(final String aFileName, final String aMeshName){}
    
    //************
    // Constructor
    //************
    //Model(const std::string &aFileName, const std::string &aMeshName = "");
    public Model(final String aName, final float[] aVertexData, final VertexFormat aVertexFormat)
    {
        GL2 gl = Graphics.getGL().getGL2();
        int byteSizeOfFloat = 4;
        
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
