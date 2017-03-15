/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import G2Dj.Type.Math.Quaternion;
import glm.quat.Quat;
import glm.vec._3.Vec3;
import static java.lang.Math.cos;
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
    
    private WeakReference<Model>         m_Model;
    private WeakReference<ShaderProgram> m_ShaderProgram;
        
    //**********
    // Accessors
    //**********
    //getters
    public WeakReference<Texture> getTexture(final String aTextureName, final WeakReference<Texture> aTexture){return m_Textures.get(aTextureName);}
    public WeakReference<Model> getModel(){return m_Model;}
    public WeakReference<ShaderProgram> getShaderProgram(){return m_ShaderProgram;}
    //setters
    public final void setTexture(final String aUniformName, final String aTextureResourceName)
    {
               
        m_Textures.put(aUniformName, Graphics.getTexture(aTextureResourceName));
    
    }
    public final void setModel(final String aModelName){m_Model = Graphics.getModel(aModelName);}
    public final void setShader(final String aShaderName){m_ShaderProgram = Graphics.getShaderProgram(aShaderName);}
    
    //
    // Graphics Scene interface
    //
    float counter = 0;
    public void draw(final WeakReference<Camera> aCamera)
    {
        counter+= 0.0001f;
        
        m_ShaderProgram.get().draw();
        {
            m_Textures.bind(m_ShaderProgram.get().getProgramHandle());
            
            //CAMERA
            Vector3 cameraPosition      = aCamera.get().getTransform().get().getPosition();
            Vector3 cameraRotation      = aCamera.get().getTransform().get().getEulers();
            float   viewportAspectRatio = aCamera.get().getViewportAspectRatio();
            
            //Debug.log(viewportAspectRatio);
            
            //ME
            Vector3 position = getTransform().get().getPosition();
            Vector3 scale    = getTransform().get().getScale   ();
            Vector3 eulers   = getTransform().get().getEulers  ();
            
            //Debug.log(scale);
                        
            //WORK
            glm.mat._4.Mat4 p = new glm.mat._4.Mat4().identity();
            {
                p.perspective(90f, viewportAspectRatio, 0.1f, 20f); //= new glm.mat._4.Mat4().perspective(90f, viewportAspectRatio, 0.1f, 20f);
                
            }
            
            glm.mat._4.Mat4 v = new glm.mat._4.Mat4().identity();
            {
                //R
                v.rotateX(cameraRotation.x);
                v.rotateY(cameraRotation.y);
                v.rotateZ(cameraRotation.z);
                //T
                v.translate(-cameraPosition.x,-cameraPosition.y,-cameraPosition.z);
                
            }
            
            glm.mat._4.Mat4 m = new glm.mat._4.Mat4().identity();
            {
                //T
                m.translate(position.x,position.y,position.z);
                //R
                m.rotateX(eulers.x);
                m.rotateY(eulers.y);
                m.rotateZ(eulers.z);
                //S
                m.scale(scale.x,scale.y,scale.z);
                
            }
            
            //OUTPUT
            glm.mat._4.Mat4 mvp = p.mul(v).mul(m);
            
            Uniforms.loadMatrix4x4(m_ShaderProgram.get().getProgramHandle(), "_MVP", mvp.toDfb_());
        
        }
        
        m_Model.get().draw(m_ShaderProgram.get().getProgramHandle());
        
    }
    
    //
    // Component Inteface
    //
    @Override
    public void update() {}
    
    //
    // Constructors 
    //
    public Mesh(/*final String aModelName, final String aShaderName*/)
    {
        setModel("Quad");
        setShader("AlphaCutOff");
        setTexture("_Texture","default.png");
        
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject)
    {
        Debug.log("Mesh added to "+aGameObject.get().getName());
        
    }

    @Override
    protected void OnRemovedFromGameObject(){}
    
}
