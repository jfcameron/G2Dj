/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.GL;
import grimhaus.com.G2Dj.Imp.Graphics.GraphicsComponent;
import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.ModelType;
import grimhaus.com.G2Dj.Imp.Graphics.ShaderProgram;
import grimhaus.com.G2Dj.Imp.Graphics.TextureUniformCollection;
import grimhaus.com.G2Dj.Imp.Graphics.Uniforms;
import grimhaus.com.G2Dj.Imp.Graphics.VertexFormat;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joseph Cameron
 */
public class TextMesh extends GraphicsComponent implements Drawable
{
    private static final float size = 1.0f;
    private static final int quadTotalAttributeCount = 30;
    private static final float characterUVSize = 0.00390625f;//16px/1char
    
    private float[] generateCharQuad(final float aOffsetX, final float aOffsetY, final IntVector2 aBasicMultilingualPlaneCoord)
    {
        float 
        offsetU = aBasicMultilingualPlaneCoord.x*characterUVSize,
        offsetV = aBasicMultilingualPlaneCoord.y*characterUVSize;
        
        //Debug.log("Pos: "+aBasicMultilingualPlaneCoord);
        
        return new float[]
        {
            //                      x,                         y,    z,                         u,    v,
            aOffsetX+(size -(size/2)), aOffsetY+(size -(size/2)), 0.0f, offsetU+(characterUVSize), offsetV+           (0.0f), // 1--0
            aOffsetX+(0.0f -(size/2)), aOffsetY+(size -(size/2)), 0.0f, offsetU+           (0.0f), offsetV+           (0.0f), // | /
            aOffsetX+(0.0f -(size/2)), aOffsetY+(0.0f -(size/2)), 0.0f, offsetU+           (0.0f), offsetV+(characterUVSize), // 2

            aOffsetX+(size -(size/2)), aOffsetY+(size -(size/2)), 0.0f, offsetU+(characterUVSize), offsetV+           (0.0f), //    0
            aOffsetX+(0.0f -(size/2)), aOffsetY+(0.0f -(size/2)), 0.0f, offsetU+           (0.0f), offsetV+(characterUVSize), //  / |
            aOffsetX+(size -(size/2)), aOffsetY+(0.0f -(size/2)), 0.0f, offsetU+(characterUVSize), offsetV+(characterUVSize), // 1--2
                
        };
        
    }
    
    //
    //
    //
    private String m_Text = "";
    private final Model                         m_Model;
    private final WeakReference<ShaderProgram>  m_ShaderProgram;
    private final TextureUniformCollection m_Textures = new TextureUniformCollection();
    private float[] m_VertexData;
    
    //buffers
    private final IntVector2 b_IntVector2 = new IntVector2();
    private final byte[] b_32bitBuffer = new byte[4];
    
    private final Mat4x4 b_ModelMatrixBuffer = new Mat4x4();//reduce heap abuse in getModelMatrix()
    private final Mat4x4 b_MVPMatrixBuffer   = new Mat4x4();
    
    //
    //
    //
    public String getText(){return m_Text;}
    
    public void setText(final String aText)
    {
        if (!m_Text.equals(aText))
        {
            m_Text=aText;
            updateVertexData();
    
        }
        
    }
    
    //
    //
    //
    private void updateVertexData()
    { 
        m_VertexData = new float[quadTotalAttributeCount*m_Text.length()];//quad for each string
        
        for(int i=0,s=m_Text.length();i<s;i++) 
        {
            IntVector2 pos = calculateUnicodePlanePosition(m_Text.charAt(i));          
            //Debug.log("Char: "+m_Text.charAt(i));
            System.arraycopy(generateCharQuad(i,0,pos),0,m_VertexData,i*quadTotalAttributeCount,quadTotalAttributeCount);  
            
        }
        
        m_Model.updateVertexData(m_VertexData);
            
    }
        
    private IntVector2 calculateUnicodePlanePosition(final char aWideChar)
    {
        int codePoint = (int)aWideChar; //get the unicode codepoint (one dimensional)
        int lower = codePoint/256; //y
        int upper = codePoint - (lower*256); //x
        
        return b_IntVector2.setInPlace(upper, lower);
        
    }
    
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
    
    //
    //
    //
    @Override protected void initialize(){}
    @Override protected void update(){}
    @Override protected void fixedUpdate(){}
    
    @Override
    public void draw(WeakReference<Camera> aCamera) 
    {
        m_ShaderProgram.get().draw();
        
        //Bind uniforms
        m_Textures.bind(m_ShaderProgram.get().getProgramHandle());/////////////////////////////////////////////////////////////////////
        
        //mvp
        Mat4x4 p = aCamera.get().getProjectionMatrix();
        Mat4x4 v = aCamera.get().getViewMatrix();
        Mat4x4 m = getModelMatrix();
        b_MVPMatrixBuffer.set(p.mul(v).mul(m));
        
        Uniforms.loadMatrix4x4(m_ShaderProgram.get().getProgramHandle(), "_MVP", b_MVPMatrixBuffer.toFloatBuffer());
                
        m_Model.draw(m_ShaderProgram.get().getProgramHandle());
        
        GL.glDrawArrays( GL.GL_TRIANGLES, 0, m_Model.getVertexCount() );
        
    }
    
    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
    //
    //
    //
    public TextMesh()
    {
        m_Textures.put("_Texture",Graphics.getTexture("FullUnicodePlaneTest.png"));
        
        m_ShaderProgram = Graphics.getShaderProgram("AlphaCutOff");
        m_Model = new Model
        (
            "LineVisualizer"
            , 
            new float[0]//m_VertexData
            , 
            VertexFormat.pos3uv2
            ,
            ModelType.Dynamic
            
        );
        
    }
    
}
