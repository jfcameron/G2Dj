/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Imp.Graphics.GL;
import grimhaus.com.G2Dj.Imp.Graphics.GraphicsComponent;
import grimhaus.com.G2Dj.Imp.Graphics.LinePrimitive;
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
public class LineVisualizer extends GraphicsComponent implements Drawable
{
    //
    // Constants
    //
    private static final float h = 0.5f;
    private static final float y = -0.1f;
    
    public static final float[] lineStripBox(final float aOffsetX, final float aOffsetY, final float aOffsetScale){return new float[]
    {
        (+h+aOffsetX)*aOffsetScale,y,(+h+aOffsetY)*aOffsetScale,
        (-h+aOffsetX)*aOffsetScale,y,(+h+aOffsetY)*aOffsetScale,
        (-h+aOffsetX)*aOffsetScale,y,(-h+aOffsetY)*aOffsetScale,
        (+h+aOffsetX)*aOffsetScale,y,(-h+aOffsetY)*aOffsetScale,
        (+h+aOffsetX)*aOffsetScale,y,(+h+aOffsetY)*aOffsetScale,

                
    };}
    
    public static final float[] lineStripCircle(final float aOffsetX, final float aOffsetY, final float aScale){return new float[]
    {
        (-h/3+aOffsetX)*aScale,y,(+h  +aOffsetY)*aScale,    (+h/3+aOffsetX)*aScale,y,(+h  +aOffsetY)*aScale,
        (+h  +aOffsetX)*aScale,y,(+h/3+aOffsetY)*aScale,    (+h  +aOffsetX)*aScale,y,(-h/3+aOffsetY)*aScale,
        (+h/3+aOffsetX)*aScale,y,(-h  +aOffsetY)*aScale,    (-h/3+aOffsetX)*aScale,y,(-h  +aOffsetY)*aScale,
        (-h  +aOffsetX)*aScale,y,(-h/3+aOffsetY)*aScale,    (-h  +aOffsetX)*aScale,y,(+h/3+aOffsetY)*aScale,
        (-h/3+aOffsetX)*aScale,y,(+h  +aOffsetY)*aScale,    (+h/3+aOffsetX)*aScale,y,(+h  +aOffsetY)*aScale,
                
    };}
    
    //
    // Data members
    //
    private LinePrimitive m_LinePrimitive = LinePrimitive.LineStrip;
    private float[] m_VertexData = lineStripCircle(0,0,1);
    
    private final Model                         m_Model;
    private final WeakReference<ShaderProgram>  m_ShaderProgram;
    
    private float m_LineWidth = 7.5f;
    private Color m_Color = Color.DeathlyPink();
    
    //buffers & pools
    private final Mat4x4 b_ModelMatrixBuffer = new Mat4x4();//reduce heap abuse in getModelMatrix()
    private final Mat4x4 b_MVPMatrixBuffer   = new Mat4x4();
    
    public void setVertexData(final float[] aVertexData)
    {
        m_VertexData = aVertexData;
        m_Model.updateVertexData(m_VertexData);
    
    }
    
    public void setDrawPrimitiveMode(final LinePrimitive aLinePrimitive){m_LinePrimitive = aLinePrimitive;}
    public void setLineWidth(final float aLineWidth){m_LineWidth=aLineWidth;}
    public void setColor(final Color aColor){m_Color = aColor;}
    
    public float getLineWidth(){return m_LineWidth;}
    
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
        
        Uniforms.load4Float(m_ShaderProgram.get().getProgramHandle(), "_Color", m_Color.r, m_Color.g, m_Color.b, m_Color.a);
        
        GL.glLineWidth(m_LineWidth);
        
        GL.glDrawArrays( m_LinePrimitive.toOpenGLLinePrimitive(), 0, m_Model.getVertexCount() );
    
    }
    
    public LineVisualizer()
    {
        m_ShaderProgram = Graphics.getShaderProgram("SimpleColor");
        m_Model = new Model
        (
            "LineVisualizer"
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
