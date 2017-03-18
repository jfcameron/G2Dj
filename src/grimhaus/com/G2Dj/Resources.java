/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

//Shared imports
import grimhaus.com.G2Dj.Type.Resources.Image;
import grimhaus.com.G2Dj.Type.Resources.Text;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

//.if DESKTOP
import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
//.endif

//.if ANDROID
//|import android.graphics.Bitmap;
//.endif

/**
 *
 * @author Joe
 */
public class Resources
{
    public static Text loadTextFile(final String aFileName)
    {
        String name = aFileName.substring(aFileName.lastIndexOf('/') + 1);
        String data = null;

        //.if DESKTOP
        try {data = new String(Files.readAllBytes(Paths.get(Resources.class.getResource(aFileName.startsWith("/")? aFileName : "/"+aFileName).toURI())));}
        catch (URISyntaxException | IOException ex) {Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);}
        //.endif

        //.if ANDROID
        //|Debug.log("Resources.loadTextFile ANDROID UNIMPLEMENTED");
        //.endif

        return new Text
        (
            name,
            data
                
        );
        
    }
    
    public static Image loadImage(final String aFileName)
    {
        Debug.log(aFileName);
        String name = aFileName.substring(aFileName.lastIndexOf('/') + 1);

        //.if DESKTOP
        BufferedImage data = null;
        try{data=ImageIO.read(Resources.class.getResource(aFileName));}
        catch (IOException ex) {Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);}
        //.endif

        //.if ANDROID
        //|android.graphics.Bitmap data = null;
        //|Debug.log("Resources.loadTextFile ANDROID UNIMPLEMENTED");
        //.endif

        return new Image
        (
            name,
            data
                
        );
        
    }
    
}