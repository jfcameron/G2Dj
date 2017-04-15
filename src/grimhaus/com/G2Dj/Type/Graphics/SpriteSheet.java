/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Imp.Graphics.GraphicsObject;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Vector2;

/**
 *
 * @author Joseph Cameron
 */
public class SpriteSheet extends GraphicsObject
{
    //private int m_CellNum
    private final IntVector2 m_CellSize = new IntVector2(0,0);
    private final IntVector2 m_CurrentCell = new IntVector2(0,0);
    //private WeakReference<Texture> m_SpriteSheetTexture;
    
    
    //public 
    
    public SpriteSheet()
    {
        setShader("AlphaCutOff");
        setTexture("_Texture","default.png");
        //m_SpriteSheetTexture = getTexture("_Texture");
        
        ///THIS IS JUST A TEST
        setVector2("_UVScale",new Vector2(0.25f,0.265625f));
        setVector2("_UVOffset",new Vector2(0,0));
    
    }
    
}
