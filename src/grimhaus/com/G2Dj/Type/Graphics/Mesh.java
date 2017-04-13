/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.FloatUniformCollection;
import grimhaus.com.G2Dj.Imp.Graphics.GL;
import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.ShaderProgram;
import grimhaus.com.G2Dj.Imp.Graphics.Texture;
import grimhaus.com.G2Dj.Imp.Graphics.TextureUniformCollection;
import grimhaus.com.G2Dj.Imp.Graphics.Uniforms;
import grimhaus.com.G2Dj.Imp.Graphics.Vector2UniformCollection;
import grimhaus.com.G2Dj.Imp.Graphics.Vector3UniformCollection;
import grimhaus.com.G2Dj.Imp.Graphics.Vector4UniformCollection;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector4;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Mesh extends Component implements Drawable
{
    //*************
    // Data members
    //*************
    //Uniform collections
    private final TextureUniformCollection m_Textures = new TextureUniformCollection();
    private final FloatUniformCollection   m_Floats   = new FloatUniformCollection();
    private final Vector2UniformCollection m_Vector2s = new Vector2UniformCollection();
    private final Vector3UniformCollection m_Vector3s = new Vector3UniformCollection();
    private final Vector4UniformCollection m_Vector4s = new Vector4UniformCollection();
    
    //GFX objects
    private WeakReference<Model>           m_Model;
    private WeakReference<ShaderProgram>   m_ShaderProgram;
    //buffers & pools
    private final Mat4x4 b_ModelMatrixBuffer = new Mat4x4();
    private final Mat4x4 b_MVPMatrixBuffer   = new Mat4x4();
        
    //**********
    // Accessors
    //**********
    public WeakReference<Texture> getTexture(final String aTextureName, final WeakReference<Texture> aTexture){return m_Textures.get(aTextureName);}
    public WeakReference<Model> getModel(){return m_Model;}
    public WeakReference<ShaderProgram> getShaderProgram(){return m_ShaderProgram;}
    public Mat4x4 getModelMatrix()
    {
        //Marshall data
        Vector3 position = getTransform().get().getPosition();
        Vector3 scale    = getTransform().get().getScale   ();
        Vector3 eulers   = getTransform().get().getEulers  ();
        //Zero the buffer
        b_ModelMatrixBuffer.identityInPlace(); 
        //Transform
        b_ModelMatrixBuffer.translate(position.x,position.y,position.z);
        //Rotation
        b_ModelMatrixBuffer.rotateX(eulers.x);
        b_ModelMatrixBuffer.rotateY(eulers.y);
        b_ModelMatrixBuffer.rotateZ(eulers.z);
        //Scale
        b_ModelMatrixBuffer.scale(scale);
        //return
        return b_ModelMatrixBuffer;
        
    }
    
    public final void setTexture(final String aUniformName, final String aTextureResourceName){m_Textures.put(aUniformName, Graphics.getTexture(aTextureResourceName));}
    public final void setFloats (final String aUniformname, final Float aFloat){m_Floats.put(aUniformname, aFloat);}
    public final void setVector2(final String aUniformname, final Vector2 aVector2){m_Vector2s.put(aUniformname, aVector2);}
    public final void setVector3(final String aUniformname, final Vector3 aVector3){m_Vector3s.put(aUniformname, aVector3);}
    public final void setVector4(final String aUniformname, final Vector4 aVector4){m_Vector4s.put(aUniformname, aVector4);}
    public final void setModel  (final String aModelName){m_Model = Graphics.getModel(aModelName);}
    public final void setShader (final String aShaderName){m_ShaderProgram = Graphics.getShaderProgram(aShaderName);}
    
    //
    // Drawable interface
    //
    @Override
    public void draw(final WeakReference<Camera> aCamera)
    {
        m_ShaderProgram.get().draw();
        
        //Bind uniforms
        m_Textures.bind(m_ShaderProgram.get().getProgramHandle());
        m_Floats  .bind(m_ShaderProgram.get().getProgramHandle());
        m_Vector2s.bind(m_ShaderProgram.get().getProgramHandle());
        m_Vector3s.bind(m_ShaderProgram.get().getProgramHandle());
        m_Vector4s.bind(m_ShaderProgram.get().getProgramHandle());
        
        //mvp
        Mat4x4 p = aCamera.get().getProjectionMatrix();
        Mat4x4 v = aCamera.get().getViewMatrix();
        Mat4x4 m = getModelMatrix();
        b_MVPMatrixBuffer.set(p.mul(v).mul(m));
        
        Uniforms.loadMatrix4x4(m_ShaderProgram.get().getProgramHandle(), "_MVP", b_MVPMatrixBuffer.toFloatBuffer());
                
        m_Model.get().draw(m_ShaderProgram.get().getProgramHandle());
        
        GL.glDrawArrays( GL.GL_TRIANGLES, 0, m_Model.get().getVertexCount() );
        
        //Unbind uniforms
        m_Textures.unbind(m_ShaderProgram.get().getProgramHandle());
        m_Floats  .unbind(m_ShaderProgram.get().getProgramHandle());
        m_Vector2s.unbind(m_ShaderProgram.get().getProgramHandle());
        m_Vector3s.unbind(m_ShaderProgram.get().getProgramHandle());
        m_Vector4s.unbind(m_ShaderProgram.get().getProgramHandle());
        
    }
    
    //*******************
    // Component Inteface
    //*******************
    @Override protected void initialize() {}
    @Override public void update(){}
    @Override public void fixedUpdate() {}
    
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject){}
    @Override protected void OnRemovedFromGameObject(){}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}

    //
    // Constructors 
    //
    public Mesh()
    {
        setModel("Quad");
        setShader("AlphaCutOff");
        setTexture("_Texture","default.png");
        
    }
   
}
