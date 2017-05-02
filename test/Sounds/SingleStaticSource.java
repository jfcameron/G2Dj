/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Sounds;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Type.Engine.Game;

/**
 *
 * @author Joseph Cameron
 */
/*public class Main 
{
    //Entry point
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){soundTest();}});}
    
    public static void soundTest()
    {
        
        
    }
    
    //
    // Implementation
    //
    
    
}*/

/**
 * Adapted from <a href="http://www.devmaster.net/">DevMaster</a>
 * <a href="http://www.devmaster.net/articles/openal-tutorials/lesson1.php">SingleStaticSource Tutorial</a>
 * by Jesse Maurais.
 *
 * @author Athomas Goldberg
 * @author Kenneth Russell
 */
import java.io.*;
import java.nio.ByteBuffer;

import com.jogamp.openal.*;
import com.jogamp.openal.util.*;
import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Resources;

// For the GUI
import java.awt.*;
import java.awt.event.*;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class SingleStaticSource {

  public SingleStaticSource(boolean gui) {
    this(gui, null, true);
  }

  public SingleStaticSource(boolean gui, Container parent, boolean showQuitButton) {
    if (gui) {
      JFrame frame = null;

      if (parent == null) {
        frame = new JFrame("Single Static Source - DevMaster OpenAL Lesson 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent = frame.getContentPane();
      }

      JPanel container = new JPanel();
      container.setLayout(new GridLayout((showQuitButton ? 4 : 3), 1));

      JButton button = new JButton("Play sound");
      button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (!initialize())
              System.exit(1);
            al.alSourcePlay(source[0]);
          }
        });
      container.add(button);
      button = new JButton("Stop playing");
      button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (!initialize())
              System.exit(1);
            al.alSourceStop(source[0]);
          }
        });
      container.add(button);
      button = new JButton("Pause sound");
      button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (!initialize())
              System.exit(1);
            al.alSourcePause(source[0]);
          }
        });
      container.add(button);

      if (showQuitButton) {
        button = new JButton("Quit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if (!initialize())
                System.exit(1);
              killAllData();
              System.exit(0);
            }
          });
        container.add(button);
      }

      parent.add(container);

      if (frame != null) {
        frame.pack();
        frame.setVisible(true);
      }
    } else {
      // Initialize OpenAL and clear the error bit.
      if (!initialize()) {
        System.exit(1);
      }

      char[] c = new char[1];
      while (c[0] != 'q') {
        try {
          BufferedReader buf =
            new BufferedReader(new InputStreamReader(System.in));
          System.out.println(
                             "Press a key and hit ENTER: \n"
                             + "'p' to play, 's' to stop, " +
                             "'h' to pause and 'q' to quit");
          buf.read(c);
          switch (c[0]) {
          case 'p' :
            // Pressing 'p' will begin playing the sample.
            al.alSourcePlay(source[0]);
            break;
          case 's' :
            // Pressing 's' will stop the sample from playing.
            al.alSourceStop(source[0]);
            break;
          case 'h' :
            // Pressing 'n' will pause (hold) the sample.
            al.alSourcePause(source[0]);
            break;
          case 'q' :
            killAllData();
            break;
          }
        } catch (IOException e) {
          System.exit(1);
        }
      }
    }
  }

  private AL al;

  // Buffers hold sound data.
  private int[] buffer = new int[1];

  // Sources are points emitting sound.
  private int[] source = new int[]{0};

  // Position of the source sound.
  private float[] sourcePos = { 0.0f, 0.0f, 0.0f };

  // Velocity of the source sound.
  private float[] sourceVel = { 0.0f, 0.0f, 0.0f };

  // Position of the listener.
  private float[] listenerPos = { 0.0f, 0.0f, 0.0f };

  // Velocity of the listener.
  private float[] listenerVel = { 0.0f, 0.0f, 0.0f };

  // Orientation of the listener. (first 3 elems are "at", second 3 are "up")
  private float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };

  private boolean initialized = false;
  private boolean initialize() {
    if (initialized) {
      return true;
    }

    // Initialize OpenAL and clear the error bit.
    try {
      ALut.alutInit();
      al = ALFactory.getAL();
      al.alGetError();
    } catch (ALException e) {
      e.printStackTrace();
      return false;
    }
    // Load the wav data.
    try {
      if (loadALData() == AL.AL_FALSE)
        return false;
    } catch (ALException e) {
      e.printStackTrace();
      return false;
    }

    setListenerValues();

    initialized = true;
    return true;
  }

  private int loadALData() {

    // variables to load into

    int[] format = new int[1];
    int[] size = new int[1];
    int[] freq = new int[1];
    int[] loop = new int[1];
    ByteBuffer[] data = new ByteBuffer[1];

    // Load wav data into a buffer.
    al.alGenBuffers(1, buffer, 0);
    if (al.alGetError() != AL.AL_NO_ERROR)
      throw new ALException("Error generating OpenAL buffers");
    
    //InputStream wavData = Resources.class.getResourceAsStream("/Sounds/Test.wav");

    ALut.alutLoadWAVFile(
      Resources.class.getResourceAsStream("/Sounds/Test.wav"),
      format,
      data,
      size,
      freq,
      loop);
    if (data[0] == null) {
      throw new RuntimeException("Error loading WAV file");
    }
    System.out.println("sound size = "   + size[0]);
    System.out.println("sound freq = "   + freq[0]);
    System.out.println("sound loop = "   + loop[0]);
    System.out.println("sound format = " + format[0]);
    System.out.println("sound data = "   + data[0]);
    al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

    // Bind buffer with a source.
    //al.alGenSources(1, source, 0);
    
    //int[] sourceHandle = new int[]{0};
    al.alGenSources(1, source, 0);
    

    int error = al.alGetError();
    if (error != AL.AL_NO_ERROR)
      throw new ALException("Error generating OpenAL source: "+error);

    al.alSourcei(source[0], AL.AL_BUFFER, buffer[0]);
    al.alSourcef(source[0], AL.AL_PITCH, 1.0f);
    al.alSourcef(source[0], AL.AL_GAIN, 1.0f);
    al.alSourcei(source[0], AL.AL_LOOPING, loop[0]);

    // Do another error check
    //if (al.alGetError() != AL.AL_NO_ERROR)
    //  throw new ALException("Error setting up OpenAL source");

    // Note: for some reason the following two calls are producing an
    // error on one machine with NVidia's OpenAL implementation. This
    // appears to be harmless, so just continue past the error if one
    // occurs.
    al.alSourcefv(source[0], AL.AL_POSITION, sourcePos, 0);
    al.alSourcefv(source[0], AL.AL_VELOCITY, sourceVel, 0);

    return AL.AL_TRUE;
  }

  private void setListenerValues() 
  {
    al.alListenerfv(AL.AL_POSITION, listenerPos, 0);
    al.alListenerfv(AL.AL_VELOCITY, listenerVel, 0);
    al.alListenerfv(AL.AL_ORIENTATION, listenerOri, 0);
  }

  private void killAllData() {
    al.alDeleteBuffers(1, buffer, 0);
    al.alDeleteSources(1, source, 0);
  }

  public static void main(String[] args) {
    boolean gui = false;

    /*for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-gui"))
        gui = true;
    }*/

    //gui = true;
    
    new SingleStaticSource(gui);
    
            
  }
}