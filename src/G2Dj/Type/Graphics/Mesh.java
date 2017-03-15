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
import G2Dj.Math.Vector3;
import G2Dj.Type.Engine.Component;
import G2Dj.Type.Engine.GameObject;
import static java.lang.Math.sin;
import java.lang.ref.WeakReference;
import java.nio.FloatBuffer;

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
    public void draw()
    {
        counter+= 0.0001f;
        
        m_ShaderProgram.get().draw();
        {
            m_Textures.bind(m_ShaderProgram.get().getProgramHandle());
            
            //CAMERA
            Vector3 cameraPosition    = new Vector3((float)sin(counter)*2f,1,+3 + (float)sin(counter));
            float viewportWidth       = 1;
            float viewportHeight      = 1;
            float viewportAspectRatio = viewportWidth/viewportHeight;
            
            //ME
            Vector3 position = getGameObject().get().getTransform().getPosition();
            Vector3 scale    = getGameObject().get().getTransform().getScale();
            Vector3 rotation = Vector3.Zero(); //getGameObject().get().getTransform().getEulers();
            
            
            //WORK
            glm.mat._4.Mat4 p = new glm.mat._4.Mat4().identity();
            {
                p = new glm.mat._4.Mat4().perspective(Graphics.getWindow().getHeight(), viewportAspectRatio, 0.1f, 20f);
                
            }
            
            glm.mat._4.Mat4 v = new glm.mat._4.Mat4().identity();
            {
                v.translate(-cameraPosition.x,-cameraPosition.y,-cameraPosition.z);
                
            }
            
            glm.mat._4.Mat4 m = new glm.mat._4.Mat4().identity();
            {
                
                
            }
            
            //OUTPUT
            glm.mat._4.Mat4 mvp = p.mul(v).mul(m);
            
            
                   
            FloatBuffer mvpDataBuffer = mvp.toDfb_();
            
            Uniforms.loadMatrix4x4(m_ShaderProgram.get().getProgramHandle(), "_MVP", mvpDataBuffer);
        
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
    protected void OnAddedToGameObject(GameObject aGameObject) 
    {
        Debug.log("Mesh added to "+aGameObject.getName());
        
    }

    @Override
    protected void OnRemovedFromGameObject(){}
    
}
