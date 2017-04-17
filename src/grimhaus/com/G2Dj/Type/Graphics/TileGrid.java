/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Graphics.GraphicsObject;
import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.ModelType;
import grimhaus.com.G2Dj.Imp.Graphics.VertexFormat;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class TileGrid extends GraphicsObject
{
    //
    //
    //
    private final Vector2 m_TileSize = Vector2.Zero();
    private float[]       m_VertexData;
    private final Model   m_ModelData;
    
    //
    //
    //
    public IntVector2 getTextureSize(){return getTexture("_Texture").get().getSize();}
    public final void setTileSizeByUV(final float aU, final float aV)
    {
        m_TileSize.setInPlace(aU, aV);
        setVector2("_UVScale",m_TileSize);
    
    }
    public final void setTileSizeByPixel(final int aWidthInPixels, final int aHeightInPixels)
    {
        IntVector2 texSize = getTextureSize();
        
        m_TileSize.x = (float)aWidthInPixels/(texSize.x);
        m_TileSize.y = (float)aHeightInPixels/(texSize.y);
        
        Debug.log("TEXTURESIZE: "+texSize,aWidthInPixels,m_TileSize.x);
        
        setTileSizeByUV(m_TileSize.x,m_TileSize.y);
        
    
    }
    
    //
    // Implementation
    //
    private static final float size = 1.0f;
    private static final int quadTotalAttributeCount = 30;
    private float[] generateCharQuad(final float aOffsetX, final float aOffsetY, final float aOffsetU, final float aOffsetV)
    {
        float 
        offsetU = aOffsetU,
        offsetV = aOffsetV,
        tileSizeX = (16/256f),
        tileSizeY = (16/256f);
                
        return new float[]
        {
            //                      x,                         y,    z,                         u,    v,
            aOffsetX+(size -(size/2)), aOffsetY+(size -(size/2)), 0.0f, offsetU+tileSizeX, offsetV+   (0.0f), // 1--0
            aOffsetX+(0.0f -(size/2)), aOffsetY+(size -(size/2)), 0.0f, offsetU+   (0.0f), offsetV+   (0.0f), // | /
            aOffsetX+(0.0f -(size/2)), aOffsetY+(0.0f -(size/2)), 0.0f, offsetU+   (0.0f), offsetV+tileSizeY, // 2

            aOffsetX+(size -(size/2)), aOffsetY+(size -(size/2)), 0.0f, offsetU+tileSizeX, offsetV+   (0.0f), //    0
            aOffsetX+(0.0f -(size/2)), aOffsetY+(0.0f -(size/2)), 0.0f, offsetU+   (0.0f), offsetV+tileSizeY, //  / |
            aOffsetX+(size -(size/2)), aOffsetY+(0.0f -(size/2)), 0.0f, offsetU+tileSizeX, offsetV+tileSizeY, // 1--2
                
        };
        
    }
    
    private void updateVertexData()
    { 
        /*m_VertexData = new float[quadTotalAttributeCount*m_Text.length()];//quad for each string        
        
        m_TextOffset.zero();
        
        float maxX = 0, maxY = 0, x = 0,y = 0;
        for(int i=0,s=m_Text.length();i<s;i++) 
        {
            IntVector2 pos = calculateUnicodePlanePosition(m_Text.charAt(i));             
            System.arraycopy(generateCharQuad(x,y,pos),0,m_VertexData,i*quadTotalAttributeCount,quadTotalAttributeCount);

        }*/
        
        int 
        dataW = 10,
        dataH = 10;
        
        
        m_VertexData = new float[quadTotalAttributeCount*(dataW*dataH)];

        for(int y=0;y<dataH;y++)
            for(int x=0;x<dataW;x++)
                System.arraycopy(generateCharQuad(x,y,0,0),0,m_VertexData,((y*dataW)+x)*quadTotalAttributeCount,quadTotalAttributeCount);
        
        m_ModelData.updateVertexData(m_VertexData);
            
    }
    
    public TileGrid()
    {
        setShader("AlphaCutOff");
        setTexture("_Texture","default.png");
        m_ModelData = new Model
        (
            "TileGrid"
            , 
            new float[0]//m_VertexData
            , 
            VertexFormat.pos3uv2
            ,
            ModelType.Dynamic
            
        );
        
        m_Model = new WeakReference<>(m_ModelData);
        
        
        
        /////////////TSET
        setTexture("_Texture", "bloo.png");
        //setTileSizeByPixel(16, 16);
        setTileSizeByUV(0.0625f*4,0.0625f*4);
        updateVertexData();//this is a test
        
    }
    
    
}
