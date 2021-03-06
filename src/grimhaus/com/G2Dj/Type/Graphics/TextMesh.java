/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.GraphicsObject;
import grimhaus.com.G2Dj.Imp.Graphics.HorizontalTextAlignment;
import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.ModelType;
import grimhaus.com.G2Dj.Imp.Graphics.VertexFormat;
import grimhaus.com.G2Dj.Imp.Graphics.VerticalTextAlignment;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class TextMesh extends GraphicsObject//GraphicsComponent implements Drawable
{
    //**********
    // Constants
    //**********
    private static final float size = 1.0f;
    private static final int quadTotalAttributeCount = 30;
    private static final float characterUVSize = 0.00390625f;//16px/1char
    
    //*************
    // Data members
    //*************
    private final Model m_ModelData;
    private final Vector2           m_TextOffset = Vector2.Zero();
    private String                  m_Text = "";
    private float[]                 m_VertexData;
    private HorizontalTextAlignment m_HorizontalTextAlignment = HorizontalTextAlignment.Left;
    private VerticalTextAlignment   m_VerticalTextAlignment   = VerticalTextAlignment.Top;
    //buffers
    private final Vector3    b_PositionBuffer    = new Vector3();
    private final IntVector2 b_IntVector2        = new IntVector2();
    
    //**********
    // Accessors
    //**********
    public String getText(){return m_Text;}
    
    @Override 
    public Mat4x4 getModelMatrix()
    {
        Vector3 position = b_PositionBuffer.setInPlace(getTransform().get().getPosition());
        Vector3 scale    = getTransform().get().getScale   ();
        Vector3 eulers   = getTransform().get().getEulers  ();
        
        //Apply offset for anchoring etc.
        position.x += m_TextOffset.x;
        position.z += m_TextOffset.y;
        
        b_ModelMatrixBuffer.identityInPlace();
        
        //T
        b_ModelMatrixBuffer.translate(position.x,position.y,position.z);
        //R
        b_ModelMatrixBuffer.rotateX(eulers.x);
        b_ModelMatrixBuffer.rotateY(eulers.y);
        b_ModelMatrixBuffer.rotateZ(eulers.z);
        //S
        b_ModelMatrixBuffer.scale(scale);
        
        return b_ModelMatrixBuffer;
        
    }
    
    public void setHorizontalTextAlignment(final HorizontalTextAlignment aHorizontalTextAlignment){m_HorizontalTextAlignment=aHorizontalTextAlignment;}
    public void setVerticalTextAlignment(final VerticalTextAlignment aVerticalTextAlignment){m_VerticalTextAlignment=aVerticalTextAlignment;}
    public void setText(final String aText)
    {
        if (!m_Text.equals(aText))
        {
            m_Text=aText;
            updateVertexData();
    
        }
        
    }
    
    //***************
    // Implementation
    //***************
    private float[] generateCharQuad(final float aOffsetX, final float aOffsetY, final IntVector2 aBasicMultilingualPlaneCoord)
    {
        float 
        offsetU = aBasicMultilingualPlaneCoord.x*characterUVSize,
        offsetV = aBasicMultilingualPlaneCoord.y*characterUVSize;
        
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
    
    private IntVector2 calculateUnicodePlanePosition(final char aWideChar)
    {
        int codePoint = (int)aWideChar; //get the unicode codepoint (one dimensional)
        
        //I treat the BML plane as a 256*256 character sheet so the 1d code must be converted to 2d
        int lower = codePoint/256; //extract y component
        int upper = codePoint - (lower*256); //extract x component
        
        return b_IntVector2.setInPlace(upper, lower);
        
    }
    
    private void updateVertexData()
    { 
        m_VertexData = new float[quadTotalAttributeCount*m_Text.length()];//quad for each string        
        
        m_TextOffset.zero();
        
        float maxX = 0, maxY = 0, x = 0,y = 0;
        for(int i=0,s=m_Text.length();i<s;i++) 
        {
            if (m_Text.charAt(i) == '\n' || m_Text.charAt(i) == '\r')
            {
                x=0;
                y--;
                
                if (y<maxY)
                    maxY=y;
                
            }
            else
            {
                IntVector2 pos = calculateUnicodePlanePosition(m_Text.charAt(i));             
                System.arraycopy(generateCharQuad(x,y,pos),0,m_VertexData,i*quadTotalAttributeCount,quadTotalAttributeCount);
                
                x++;
                
                if (x>maxX)
                    maxX=x;
                
            }
            
        }
        
        switch(m_HorizontalTextAlignment)
        {
            case Center: 
            m_TextOffset.x = maxX*0.25f; 
            break;
            
            case Right:  
            m_TextOffset.x = maxX; 
            break;
            
            default:
            case Left:    
            break;
            
        }
        
        switch(m_VerticalTextAlignment)
        {
            case Center: 
            m_TextOffset.y = maxY*0.25f; 
            break;
            
            case Bottom:  
            m_TextOffset.y = maxY; 
            break;
            
            default:
            case Top:    
            break;
            
        }
                
        m_ModelData.updateVertexData(m_VertexData);//m_Model.updateVertexData(m_VertexData);
            
    }
    
    //
    // Constructor
    //
    public TextMesh()
    {
        m_Textures.put("_Texture",Graphics.getTexture("FullUnicodePlaneTest.png"));
        m_ShaderProgram = Graphics.getShaderProgram("AlphaCutOff");
        m_ModelData = new Model
        (
            "TextMesh"
            , 
            new float[0]//m_VertexData
            , 
            VertexFormat.pos3uv2
            ,
            ModelType.Dynamic
            
        );
        
        m_Model = new WeakReference<>(m_ModelData);
        
    }
    
}
