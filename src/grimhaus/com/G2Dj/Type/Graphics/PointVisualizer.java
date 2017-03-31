/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.GL;
import grimhaus.com.G2Dj.Imp.Graphics.GraphicsComponent;
import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.ModelType;
import grimhaus.com.G2Dj.Imp.Graphics.ShaderProgram;
import grimhaus.com.G2Dj.Imp.Graphics.Uniforms;
import grimhaus.com.G2Dj.Imp.Graphics.VertexFormat;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class PointVisualizer extends GraphicsComponent implements Drawable
{
    private static final float h = 0.5f;
    private static final float y = -0.1f;
    
    public static final float[] pointData(final float aOffsetX, final float aOffsetY, final float aOffsetScale){return new float[]
    {
        (+aOffsetX)*aOffsetScale,y,(+aOffsetY)*aOffsetScale,
                
    };}
    
    private float[] m_VertexData = pointData(0,0,1);
    
    private final Model                         m_Model;
    private final WeakReference<ShaderProgram>  m_ShaderProgram;
    
    
    //buffers & pools
    private final Mat4x4 b_ModelMatrixBuffer = new Mat4x4();//reduce heap abuse in getModelMatrix()
    private final Mat4x4 b_MVPMatrixBuffer   = new Mat4x4();
    
    public void setVertexData(final float[] aVertexData)
    {
        m_VertexData = aVertexData;
        m_Model.updateVertexData(m_VertexData);
    
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
        //b_ModelMatrixBuffer.rotateX(eulers.x);//m.rotateX(eulers.x);
        b_ModelMatrixBuffer.rotateY(eulers.y);//m.rotateY(eulers.y);
        //b_ModelMatrixBuffer.rotateZ(eulers.z);//m.rotateZ(eulers.z);
        //S
        b_ModelMatrixBuffer.scale(scale);//m.scale(scale.x,scale.y,scale.z);
        
        return b_ModelMatrixBuffer;//m;
        
    }
    
    //
    // Drawable interface
    //
    @Override
    public void draw(WeakReference<Camera> aCamera) 
    {
        m_ShaderProgram.get().draw();
        
        //Bind uniforms
        //m_Textures.bind(m_ShaderProgram.get().getProgramHandle());
        
        //mvp
        Mat4x4 p = aCamera.get().getProjectionMatrix();
        Mat4x4 v = aCamera.get().getViewMatrix();
        Mat4x4 m = getModelMatrix();
        b_MVPMatrixBuffer.set(p.mul(v).mul(m));
        
        Uniforms.loadMatrix4x4(m_ShaderProgram.get().getProgramHandle(), "_MVP", b_MVPMatrixBuffer.toFloatBuffer());
        
        m_Model.draw(m_ShaderProgram.get().getProgramHandle());
        
        GL.glDrawArrays( GL.GL_POINTS, 0, m_Model.getVertexCount() );
    
    }
    
    public PointVisualizer()
    {
        m_ShaderProgram = Graphics.getShaderProgram();
        m_Model = new Model
        (
            "PointVisualizer"
            , 
            m_VertexData
            , 
            VertexFormat.pos3
            ,
            ModelType.Dynamic
                
                
        );
                
    }

    //
    // Component interface
    //
    @Override
    public void update() 
    {
        
    }
    
    @Override
    public void fixedUpdate() {}

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        
    }

    @Override
    protected void OnRemovedFromGameObject() {}

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
