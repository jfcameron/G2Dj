/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Graphics.GraphicsObject;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Vector2;


/**
 *
 * @author Joseph Cameron
 */

public class SpriteSheet extends GraphicsObject
{
    //
    //
    //
    private final Vector2 m_CellSize = new Vector2(0,0);
    private final Vector2 m_CurrentCellInUVs = new Vector2(0,0);
    
    //
    //
    //
    public IntVector2 getTextureSize(){return getTexture("_Texture").get().getSize();}
    
    public final void setCellSizeByUV(final float aU, final float aV)
    {
        m_CellSize.setInPlace(aU, aV);
        setVector2("_UVScale",m_CellSize);
    
    }
    public final void setCellSizeByPixel(final int aWidthInPixels, final int aHeightInPixels)
    {
        IntVector2 size = getTextureSize();
        size.x = 64;
        size.y =64;
        
        setCellSizeByUV((float)aWidthInPixels/size.x,(float)aHeightInPixels/size.y);
    
    }
    
    public final void setCurrentCell(final int aX, final int aY)
    {
        m_CurrentCellInUVs.setInPlace(aX*m_CellSize.x, aY*m_CellSize.y);
        setVector2("_UVOffset",m_CurrentCellInUVs);
                
    }
    
    //
    //
    //
    public SpriteSheet()
    {
        setShader("AlphaCutOff");
        setTexture("_Texture","default.png");
            
    }
    
}
