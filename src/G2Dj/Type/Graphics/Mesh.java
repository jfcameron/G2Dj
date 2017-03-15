/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import G2Dj.Imp.Engine.Transform;
import G2Dj.Imp.Graphics.GraphicsObject;
import G2Dj.Imp.Graphics.Model;
import G2Dj.Imp.Graphics.ShaderProgram;
import G2Dj.Imp.Graphics.Texture;
import G2Dj.Imp.Graphics.TextureUniformCollection;
import G2Dj.Type.Engine.Component;
import G2Dj.Type.Engine.GameObject;
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
    public void draw()
    {
        m_ShaderProgram.get().draw();
        {
            m_Textures.bind(m_ShaderProgram.get().getProgramHandle());
            
        
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
