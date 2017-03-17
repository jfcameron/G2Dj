/*
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Resources;

//.if DESKTOP
//|import java.awt.image.BufferedImage;
//|/**
//| *
//| * @author Joe
//| */
//|public class Image extends File<BufferedImage>
//|{
//|    public Image(String aName, BufferedImage aData)
//|    {
//|        super(aName, aData);
//|
//|    }
//|
//|
//|}
//.endif

//.if ANDROID
import android.graphics.Bitmap;

/**
 *
 * @author Joe
 */
public class Image extends File<Bitmap>
{
    public Image(String aName, Bitmap aData)
    {
        super(aName, aData);

    }


}

//.endif
