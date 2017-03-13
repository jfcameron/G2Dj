package G2Dj;


import G2Dj.Resource.Graphics.AlphaCutOff;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static String s_ResourceDirectoryRoot = "/";//"/G2Dj/Resource/";
    
    public static String readFileToString(final String aFileName)
    {
        String data = null;
        
        try {
            data = new String(Files.readAllBytes(Paths.get(AlphaCutOff.class.getResource(s_ResourceDirectoryRoot+aFileName).toURI())));
        } catch (URISyntaxException ex) {
            Logger.getLogger(AlphaCutOff.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlphaCutOff.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
        
    }
    
}