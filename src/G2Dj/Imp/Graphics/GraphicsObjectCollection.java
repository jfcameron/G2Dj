/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import java.util.ArrayList;
import java.lang.RuntimeException;

public abstract class GraphicsObjectCollection<T extends GraphicsObject>
{
    //*************
    // Data members
    //*************
    protected ArrayList<T> m_Vector;
    protected ArrayList<String> m_FileTypes;
    
    //*****************
    // Public interface
    //*****************
    //public abstract void init();
    
    public T getDefault()
    {
        if (m_Vector.isEmpty())
            throw new RuntimeException("No default exists.");
        
        return m_Vector.get(0);
        
    }
    
    public T find(final String aItemName)
    {
        for(int i =0, s = m_Vector.size(); i < s; i++)
            if (m_Vector.get(i).getName() == aItemName)
                return m_Vector.get(i);
            
        return getDefault();
        
    }
    
    public T get(final int i)
    {
        if (i >= m_Vector.size())
            return getDefault();
    
        return m_Vector.get(i);
        
    }
    
    /*Boolean listOfAllDynamicTypeFilesInDir(final String dirname, std::vector<std::string>& ioListOfShaderFiles)
    {
        DIR *dir = opendir(dirname);
    
        if (dir == NULL)
        {
            printf("Could not open directory %s (Does it exist?)\n", dirname);
            return false;
    
        }
    
        struct dirent *ent;
    
        while ((ent = readdir(dir)) != NULL)
            if (fileNameHasFileType(ent->d_name))
                ioListOfShaderFiles.push_back(ent->d_name);
    
        closedir(dir);
        return true;
    
    }*/
    
    /*bool fileNameHasFileType(char *string)
    {
        if (m_FileTypes.size() != 0)
        {
            string = strrchr(string, '.');
    
            if (string != NULL)
                for (int i = 0; i < m_FileTypes.size(); i++)
                    if (strcmp(string, m_FileTypes[i].c_str()) == 0)//if (strcmp(string, ".shader") == 0)
                        return 1;
                
        }
                
        return 0;
    
    }*/
    
    /*void loadDirectory(final String aDirectoryName)
    {
        //if (*aDirectoryName.rbegin() != '/') //adds '/' to end of dirname if none exist
        //    aDirectoryName += "/";
        
        //std::vector<std::string> fileNames;
        ArrayList<String> fileNames;
        
        if (listOfAllDynamicTypeFilesInDir(aDirectoryName.c_str(), fileNames))
        {
            for (unsigned int i = 0; i < fileNames.size(); i++)
            {
                std::string fullFileName = aDirectoryName + fileNames[i];
                
                try
                {
                    m_Vector.push_back(std::shared_ptr<BaseType>(new DynamicType(fullFileName.c_str())));
    
                }
                catch (const std::runtime_error &e)
                {
                    std::cout << e.what();
    
                }
    
            }
            
            //std::cout << "number of shaders in list is: " << m_Vector.size() << "\n";
            //std::cout << "Loaded shaders are:\n";
            
            for (int i = 0; i < m_Vector.size(); i++)
                std::cout << m_Vector[i]._Get()->getName() << "\n";
            
        }
    
    }*/
        
}
