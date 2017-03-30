/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.GL;
import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.ShaderProgram;
import grimhaus.com.G2Dj.Imp.Graphics.Texture;
import grimhaus.com.G2Dj.Imp.Graphics.TextureUniformCollection;
import grimhaus.com.G2Dj.Imp.Graphics.Uniforms;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
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
    private final TextureUniformCollection m_Textures = new TextureUniformCollection();
    private WeakReference<Model>           m_Model;
    private WeakReference<ShaderProgram>   m_ShaderProgram;
    
    //buffers & pools
    private final Mat4x4 b_ModelMatrixBuffer = new Mat4x4();//reduce heap abuse in getModelMatrix()
    private final Mat4x4 b_MVPMatrixBuffer   = new Mat4x4();
        
    //**********
    // Accessors
    //**********
    public WeakReference<Texture> getTexture(final String aTextureName, final WeakReference<Texture> aTexture){return m_Textures.get(aTextureName);}
    public WeakReference<Model> getModel(){return m_Model;}
    public WeakReference<ShaderProgram> getShaderProgram(){return m_ShaderProgram;}
    public Mat4x4 getModelMatrix()
    {
        Vector3 position = getTransform().get().getPosition();
        Vector3 scale    = getTransform().get().getScale   ();
        Vector3 eulers   = getTransform().get().getEulers  ();
        
        b_ModelMatrixBuffer.identityInPlace();//Mat4x4 m = Mat4x4.identity();
        
        //T
        b_ModelMatrixBuffer.translate(position.x,position.y,position.z);//m.translate(position.x,position.y,position.z);
        //R
        b_ModelMatrixBuffer.rotateX(eulers.x);//m.rotateX(eulers.x);
        b_ModelMatrixBuffer.rotateY(eulers.y);//m.rotateY(eulers.y);
        b_ModelMatrixBuffer.rotateZ(eulers.z);//m.rotateZ(eulers.z);
        //S
        b_ModelMatrixBuffer.scale(scale);//m.scale(scale.x,scale.y,scale.z);
        
        return b_ModelMatrixBuffer;//m;
        
    }
    
    public final void setTexture(final String aUniformName, final String aTextureResourceName){m_Textures.put(aUniformName, Graphics.getTexture(aTextureResourceName));}
    public final void setModel(final String aModelName){m_Model = Graphics.getModel(aModelName);}
    public final void setShader(final String aShaderName){m_ShaderProgram = Graphics.getShaderProgram(aShaderName);}
    
    //
    // Drawable interface
    //
    @Override
    public void draw(final WeakReference<Camera> aCamera)
    {
        m_ShaderProgram.get().draw();
        
        //Bind uniforms
        m_Textures.bind(m_ShaderProgram.get().getProgramHandle());
        
        //mvp
        Mat4x4 p = aCamera.get().getProjectionMatrix();
        Mat4x4 v = aCamera.get().getViewMatrix();
        Mat4x4 m = getModelMatrix();
        b_MVPMatrixBuffer.set(p.mul(v).mul(m));
        
        Uniforms.loadMatrix4x4(m_ShaderProgram.get().getProgramHandle(), "_MVP", b_MVPMatrixBuffer.toFloatBuffer());
                
        m_Model.get().draw(m_ShaderProgram.get().getProgramHandle());
        
        GL.glDrawArrays( GL.GL_TRIANGLES, 0, m_Model.get().getVertexCount() );
        
    }
    
    //
    // Component Inteface
    //
    @Override
    public void update(){}
    
    @Override
    public void fixedUpdate() {}
    
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject)
    {
        Debug.log("Mesh added to "+aGameObject.get().getName());
        
    }

    @Override
    protected void OnRemovedFromGameObject(){}
    
    //
    // Constructors 
    //
    public Mesh()
    {
        setModel("Quad");
        setShader("AlphaCutOff");
        setTexture("_Texture","default.png");
        
    }

    //@Override public String toString(){return ;}

    @Override
    protected void OnComponentAdded(Component aComponent) {
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }

    @Override
    protected void initialize() {
    }

   
}
