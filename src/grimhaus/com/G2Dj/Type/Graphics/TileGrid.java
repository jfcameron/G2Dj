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
    
    private int[]      m_TileData;
    private final IntVector2 m_TileDataSize = new IntVector2(0,0);
    
    private boolean m_RebuildVertsNextTick = false;
    
    //
    //
    //
    public final void setTileData(final int aDataWidth, final int aDataHeight,final int[] aTileData){m_TileDataSize.setInPlace(aDataWidth, aDataHeight);m_TileData=aTileData;m_RebuildVertsNextTick=true;}
    public IntVector2 getTextureSize(){return getTexture("_Texture").get().getSize();}
    private void setTileSizeByUV(final float aU, final float aV){m_TileSize.setInPlace(aU, aV);}
    private void setTileSizeByPixel(final int aWidthInPixels, final int aHeightInPixels)
    {
        IntVector2 texSize = getTextureSize();
        
        m_TileSize.x = (float)aWidthInPixels/(texSize.x);
        m_TileSize.y = (float)aHeightInPixels/(texSize.y);
        
        setTileSizeByUV(m_TileSize.x,m_TileSize.y);
    
    }
    
    public final void setTileSet(final String aTileSetTextureName, final int aTilePixelWidth, final int aTilePixelHeight)
    {
        setTexture("_Texture", aTileSetTextureName);
        setTileSizeByPixel(aTilePixelWidth,aTilePixelHeight);
                
    }
    
    //
    // Implementation
    //
    private static final float size = 1.0f;
    private static final int quadTotalAttributeCount = 30;
    private float[] generateTileQuad(final int aOffsetX, final int aOffsetY, final int aOffsetU, final int aOffsetV)
    {
        float 
        offsetU = aOffsetU*m_TileSize.x,
        offsetV = aOffsetV*m_TileSize.y,
        tileSizeX = m_TileSize.x,
        tileSizeY = m_TileSize.y;
                
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
        int 
        dataW = m_TileDataSize.x,
        dataH = m_TileDataSize.y;
        
        m_VertexData = new float[quadTotalAttributeCount*(dataW*dataH)];

        for(int y=0;y<dataH;y++)
            for(int x=0;x<dataW;x++)
            {                
                int dataIndex = (dataH*dataW*2)-((y+1)*2*dataW);
                dataIndex += x*2;
                
                System.arraycopy(generateTileQuad(x,y,m_TileData[dataIndex],m_TileData[dataIndex+1]),0,m_VertexData,((y*dataW)+x)*quadTotalAttributeCount,quadTotalAttributeCount);
        
            }
                
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
        
        
    }
    
    @Override protected void update()
    {
        if (m_RebuildVertsNextTick)
        {
            m_RebuildVertsNextTick = false;
            updateVertexData();
            
        }
        
    }
    
    
}
