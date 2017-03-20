/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

//Shared imports
import grimhaus.com.G2Dj.Type.Resources.Image;
import grimhaus.com.G2Dj.Type.Resources.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//.if DESKTOP
//|import javax.imageio.ImageIO;
//|import java.nio.file.Files;
//|import java.nio.file.Paths;
//|import java.awt.image.BufferedImage;
//|import java.net.URISyntaxException;
//|import java.util.logging.Level;
//|import java.util.logging.Logger;
//.endif

//.if ANDROID
import android.graphics.BitmapFactory;
//.endif

/**
 *
 * @author Joe
 */
public final class Resources
{
    public static final Text loadTextFile(final String aFileName)
    {
        String name = aFileName.substring(aFileName.lastIndexOf('/') + 1);
        String path = sanitizeFilePath(aFileName);
        String data = null;

        //.if DESKTOP
        //|try {data = new String(Files.readAllBytes(Paths.get(Resources.class.getResource(path).toURI())));}
        //|catch (URISyntaxException | IOException ex) {Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);}
        //.endif

        //.if ANDROID
        //getResources
        Debug.log("Resources.loadTextFile*********************************");
        Debug.log(path);

        InputStream dataStream = Mobile.loadAsset(path);

        BufferedReader r = new BufferedReader(new InputStreamReader(dataStream));
        StringBuilder total = new StringBuilder();
        String line;
        try
        {
            while ((line = r.readLine()) != null)
                total.append(line).append('\n');
        }
        catch (IOException e) {e.printStackTrace();}

        data = total.toString();

        //Debug.log(name,data);

        //.endif

        return new Text
        (
            name,
            data
                
        );
        
    }
    
    public static final Image loadImage(final String aFileName)
    {
        Debug.log(aFileName);
        String name = aFileName.substring(aFileName.lastIndexOf('/') + 1);
        String path = sanitizeFilePath(aFileName);
        
        //.if DESKTOP
        //|BufferedImage data = null;
        //|try{data=ImageIO.read(Resources.class.getResource(path));}
        //|catch (IOException ex) {Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);}
        //.elseif ANDROID
        android.graphics.Bitmap data = null;
        Debug.log("Resources.loadImage*********************************");
        Debug.log(path);

        InputStream dataStream = Mobile.loadAsset(path);

        data = BitmapFactory.decodeStream(dataStream);

        Debug.log(data.getByteCount());

        //.endif

        return new Image(name,data);
        
    }
    
    private static String sanitizeFilePath(final String aFileName)
    {
        //.if DESKTOP
        //|return aFileName.startsWith("/") ? aFileName : "/"+aFileName;
        //.elseif ANDROID
        return aFileName.startsWith("/") ? "/"+aFileName : aFileName;
        //.endif
        
    }
    
}
