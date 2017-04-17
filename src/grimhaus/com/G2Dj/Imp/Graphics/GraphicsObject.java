/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Drawable;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import grimhaus.com.G2Dj.Type.Math.Vector4;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public abstract class GraphicsObject extends GraphicsComponent implements Drawable
{
    //*************
    // Data members
    //*************
    //Uniform collections
    protected final TextureUniformCollection m_Textures = new TextureUniformCollection();
    protected final FloatUniformCollection   m_Floats   = new FloatUniformCollection();
    protected final Vector2UniformCollection m_Vector2s = new Vector2UniformCollection();
    protected final Vector3UniformCollection m_Vector3s = new Vector3UniformCollection();
    protected final Vector4UniformCollection m_Vector4s = new Vector4UniformCollection();
    //GFX objects
    protected WeakReference<Model>           m_Model;
    protected WeakReference<ShaderProgram>   m_ShaderProgram;
    //buffers & pools
    protected final Mat4x4 b_ModelMatrixBuffer = new Mat4x4();
    protected final Mat4x4 b_MVPMatrixBuffer   = new Mat4x4();
    
    //**********
    // Accessors
    //**********
    public final WeakReference<Texture> getTexture(final String aTextureName){return m_Textures.get(aTextureName);}
    public final float getFloat  (final String aUniformname){return m_Floats  .get(aUniformname);}
    public final Vector2 getVector2(final String aUniformname){return m_Vector2s.get(aUniformname);}
    public final Vector3 getVector3(final String aUniformname){return m_Vector3s.get(aUniformname);}
    public final Vector4 getVector4(final String aUniformname){return m_Vector4s.get(aUniformname);}
    public final WeakReference<ShaderProgram> getShaderProgram(){return m_ShaderProgram;}
    public final WeakReference<Model> getModel(){return m_Model;}
    
    public void setTexture(final String aUniformName, final String aTextureResourceName){m_Textures.put(aUniformName, Graphics.getTexture(aTextureResourceName));}
    public final void setFloats (final String aUniformname, final float aFloat){m_Floats.put(aUniformname, aFloat);}
    public final void setVector2(final String aUniformname, final Vector2 aVector2){m_Vector2s.put(aUniformname, aVector2);}
    public final void setVector3(final String aUniformname, final Vector3 aVector3){m_Vector3s.put(aUniformname, aVector3);}
    public final void setVector4(final String aUniformname, final Vector4 aVector4){m_Vector4s.put(aUniformname, aVector4);}
    public final void setShader (final String aShaderName){m_ShaderProgram = Graphics.getShaderProgram(aShaderName);}
    protected void setModel  (final String aModelName){m_Model = Graphics.getModel(aModelName);}
    
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
    
    //*******************
    // Drawable interface
    //*******************
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
    @Override protected void update(){}
    @Override protected void fixedUpdate() {}
    
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject){}
    @Override protected void OnRemovedFromGameObject(){}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}

    //*************
    // Constructors
    //*************
    protected GraphicsObject()
    {
        m_Model = Graphics.getModel("Quad");//setModel("Quad");
        setShader("AlphaCutOff");
        setTexture("_Texture","default.png");
        
    }
    
}
