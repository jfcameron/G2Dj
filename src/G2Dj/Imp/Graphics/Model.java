/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Graphics;
import com.jogamp.opengl.GL2;
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
    private int m_VertexBufferHandle;
    private int m_VertexCount;
    
    //**********
    // Accessors
    //**********
    public int  getVertexCount(){return m_VertexCount;}      
    
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
    public Model(final String aName/*, Vertex::Data aVertexData*/)
    {
        //VertexBufferData test = VertexBufferData();
        
        GL2 gl = Graphics.getGL().getGL2();
        
        m_Name = aName;
        
        //Create buffer
        IntBuffer vbo = IntBuffer.allocate(1);
        gl.glGenBuffers(1, vbo);
        m_VertexBufferHandle = vbo.get(0);
        
        /*m_VertexCount = aVertexData.size();
    
        glBindBuffer (GL_ARRAY_BUFFER, vbo);
        glBufferData (GL_ARRAY_BUFFER, sizeof (Vertex::Format)*aVertexData.size(), aVertexData.toArray(), GL_STATIC_DRAW);
        glBindBuffer( GL_ARRAY_BUFFER,0);
        */
        
    }
    
}
