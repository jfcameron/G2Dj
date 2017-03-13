package G2Dj;


import G2Dj.Resource.Graphics.AlphaCutOff;
import G2Dj.Type.Resources.Image;
import G2Dj.Type.Resources.Text;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        
        try 
        {
            data = new String(Files.readAllBytes(Paths.get(Resources.class.getResource(aFileName.startsWith("/")? aFileName : "/"+aFileName).toURI())));
        
        } 
        catch (URISyntaxException | IOException ex) {Logger.getLogger(AlphaCutOff.class.getName()).log(Level.SEVERE, null, ex);}
                
        return new Text
        (
            name,
            data
                
        );
        
    }
    
    public static Image loadImage(final String aFileName)
    {
        String name = aFileName.substring(aFileName.lastIndexOf('/') + 1);
        BufferedImage data = null;
        
        try 
        {
             data=ImageIO.read(Resources.class.getResource(aFileName));
        
        } 
        catch (IOException ex) {Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);}
        
        return new Image
        (
            name,
            data
                
        );
        
    }
    
}