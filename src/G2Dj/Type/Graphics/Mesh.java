/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Type.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import G2Dj.Imp.Graphics.Model;
import G2Dj.Imp.Graphics.ShaderProgram;
import G2Dj.Imp.Graphics.Texture;
import G2Dj.Imp.Graphics.TextureUniformCollection;
import G2Dj.Imp.Graphics.Uniforms;
import G2Dj.Type.Math.Vector3;
import G2Dj.Type.Engine.Component;
import G2Dj.Type.Engine.GameObject;
import G2Dj.Type.Math.Mat4x4;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Mesh extends Component
{
    //*************
    // Data members
    //*************
    private final TextureUniformCollection m_Textures = new TextureUniformCollection();
    private WeakReference<Model>           m_Model;
    private WeakReference<ShaderProgram>   m_ShaderProgram;
        
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
        
        Mat4x4 m = Mat4x4.identity();
        
        //T
        m.translate(position.x,position.y,position.z);
        //R
        m.rotateX(eulers.x);
        m.rotateY(eulers.y);
        m.rotateZ(eulers.z);
        //S
        m.scale(scale.x,scale.y,scale.z);
        
        return m;
        
    }
    
    public final void setTexture(final String aUniformName, final String aTextureResourceName){m_Textures.put(aUniformName, Graphics.getTexture(aTextureResourceName));}
    public final void setModel(final String aModelName){m_Model = Graphics.getModel(aModelName);}
    public final void setShader(final String aShaderName){m_ShaderProgram = Graphics.getShaderProgram(aShaderName);}
    
    //
    // Graphics Scene interface
    //
    public void draw(final WeakReference<Camera> aCamera)
    {
        m_ShaderProgram.get().draw();
        
        //Bind uniforms
        m_Textures.bind(m_ShaderProgram.get().getProgramHandle());
        
        //mvp
        Mat4x4 p = aCamera.get().getProjectionMatrix();
        Mat4x4 v = aCamera.get().getViewMatrix();
        Mat4x4 m = getModelMatrix();
        Mat4x4 mvp = p.mul(v).mul(m);
        
        Uniforms.loadMatrix4x4(m_ShaderProgram.get().getProgramHandle(), "_MVP", mvp.toFloatBuffer());
                
        m_Model.get().draw(m_ShaderProgram.get().getProgramHandle());
        
    }
    
    //
    // Component Inteface
    //
    @Override
    public void update(){}
    
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
   
}
