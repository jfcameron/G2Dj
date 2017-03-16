/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Imp.Input;

import com.jogamp.newt.event.KeyListener;

import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyboardInputHandler implements KeyListener 
{
  private static final int KEY_COUNT = 256;

        

  /*private enum KeyState {

    RELEASED, // Not down

    PRESSED,  // Down, but not the first time

    ONCE      // Down for the first time

  }*/

        

  // Current state of the keyboard

  private boolean[] currentKeys = null;

        

  // Polled keyboard state

  private KeyState[] keys = null;

        

  public KeyboardInputHandler() 
  {

    currentKeys = new boolean[ KEY_COUNT ];

    keys = new KeyState[ KEY_COUNT ];

    for( int i = 0; i < KEY_COUNT; ++i ) {

      keys[ i ] = KeyState.Up;

    }

  }

  public synchronized void update() {

    for( int i = 0; i < KEY_COUNT; ++i ) {

      // Set the key state 

      if( currentKeys[ i ] ) {

        // If the key is down now, but was not

        // down last frame, set it to ONCE,

        // otherwise, set it to PRESSED

        if( keys[ i ] == KeyState.Up )

          keys[ i ] = KeyState.JustPressed;

        else

          keys[ i ] = KeyState.Down;

      } else {

        keys[ i ] = KeyState.Up;

      }

    }

  }

        

  public boolean getKey( KeyCode keyCode ) 
  {
      return keys[s_G2DjKeyMapToAWTKey.get(keyCode)] == KeyState.JustPressed ||
             keys[s_G2DjKeyMapToAWTKey.get(keyCode)] == KeyState.Down;

  }

        

  public boolean getKeyDown( KeyCode keyCode ) 
  {

    return keys[s_G2DjKeyMapToAWTKey.get(keyCode)] == KeyState.JustPressed;

  }

  
  @Override
  public void keyPressed(com.jogamp.newt.event.KeyEvent e) 
  {

    int keyCode = e.getKeyCode();

    if( keyCode >= 0 && keyCode < KEY_COUNT ) {

      currentKeys[ keyCode ] = true;

    }

  }

  @Override
  public void keyReleased(com.jogamp.newt.event.KeyEvent e) 
  {

    int keyCode = e.getKeyCode();

    if( keyCode >= 0 && keyCode < KEY_COUNT ) {

      currentKeys[ keyCode ] = false;

    }

  }
  
  private static final HashMap<KeyCode,Integer> s_G2DjKeyMapToAWTKey = new HashMap<>();
  static
    {
        //Top row
        s_G2DjKeyMapToAWTKey.put(KeyCode.Escape,KeyEvent.VK_ESCAPE);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F1,KeyEvent.VK_F1);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F2,KeyEvent.VK_F2);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F3,KeyEvent.VK_F3);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F4,KeyEvent.VK_F4);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F5,KeyEvent.VK_F5);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F6,KeyEvent.VK_F6);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F7,KeyEvent.VK_F7);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F8,KeyEvent.VK_F8);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F9,KeyEvent.VK_F9);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F10,KeyEvent.VK_F10);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F11,KeyEvent.VK_F11);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F12,KeyEvent.VK_F12);
        s_G2DjKeyMapToAWTKey.put(KeyCode.PrintScreen,KeyEvent.VK_PRINTSCREEN);
        s_G2DjKeyMapToAWTKey.put(KeyCode.ScrollLock,KeyEvent.VK_SCROLL_LOCK);
        s_G2DjKeyMapToAWTKey.put(KeyCode.PauseBreak,KeyEvent.VK_PAUSE);
        
        //Number row
        s_G2DjKeyMapToAWTKey.put(KeyCode.One       , KeyEvent.VK_1          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Two       , KeyEvent.VK_2          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Three     , KeyEvent.VK_3          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Four      , KeyEvent.VK_4          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Five      , KeyEvent.VK_5          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Six       , KeyEvent.VK_6          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Seven     , KeyEvent.VK_7          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Eight     , KeyEvent.VK_8          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Nine      , KeyEvent.VK_9          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Zero      , KeyEvent.VK_0          );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Tilda     , KeyEvent.VK_BACK_QUOTE );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Minus     , KeyEvent.VK_MINUS      );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Equals    , KeyEvent.VK_EQUALS     );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Backspace , KeyEvent.VK_BACK_SPACE );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Home      , KeyEvent.VK_HOME       );
        s_G2DjKeyMapToAWTKey.put(KeyCode.End       , KeyEvent.VK_END        );

        //Q Row
        s_G2DjKeyMapToAWTKey.put(KeyCode.Tab          , KeyEvent.VK_TAB           );
        s_G2DjKeyMapToAWTKey.put(KeyCode.OpenBracket  , KeyEvent.VK_OPEN_BRACKET  );
        s_G2DjKeyMapToAWTKey.put(KeyCode.CloseBracket , KeyEvent.VK_CLOSE_BRACKET );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Backslash    , KeyEvent.VK_BACK_SLASH    );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Insert       , KeyEvent.VK_INSERT        );
        s_G2DjKeyMapToAWTKey.put(KeyCode.PageUp       , KeyEvent.VK_PAGE_UP       );
        
        //A row
        s_G2DjKeyMapToAWTKey.put(KeyCode.Capslock ,KeyEvent.VK_CAPS_LOCK );
        s_G2DjKeyMapToAWTKey.put(KeyCode.SemiColon,KeyEvent.VK_SEMICOLON );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Quote    ,KeyEvent.VK_QUOTE     );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Enter    ,KeyEvent.VK_ENTER     );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Delete   ,KeyEvent.VK_DELETE    );
        s_G2DjKeyMapToAWTKey.put(KeyCode.PageDown ,KeyEvent.VK_PAGE_DOWN );
        
        //Z row
        s_G2DjKeyMapToAWTKey.put(KeyCode.LeftShift    , KeyEvent.VK_SHIFT  );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Comma        , KeyEvent.VK_COMMA  );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Period       , KeyEvent.VK_PERIOD );
        s_G2DjKeyMapToAWTKey.put(KeyCode.ForwardSlash , KeyEvent.VK_SLASH  );
        s_G2DjKeyMapToAWTKey.put(KeyCode.RightShift   , KeyEvent.VK_SHIFT  ); //NOTE: AWT offers no way to differentiate shift keys
        
        //Alphabet
        s_G2DjKeyMapToAWTKey.put(KeyCode.Q,KeyEvent.VK_Q);
        s_G2DjKeyMapToAWTKey.put(KeyCode.W,KeyEvent.VK_W);
        s_G2DjKeyMapToAWTKey.put(KeyCode.E,KeyEvent.VK_E);
        s_G2DjKeyMapToAWTKey.put(KeyCode.R,KeyEvent.VK_R);
        s_G2DjKeyMapToAWTKey.put(KeyCode.T,KeyEvent.VK_T);
        s_G2DjKeyMapToAWTKey.put(KeyCode.Y,KeyEvent.VK_Y);
        s_G2DjKeyMapToAWTKey.put(KeyCode.U,KeyEvent.VK_U);
        s_G2DjKeyMapToAWTKey.put(KeyCode.I,KeyEvent.VK_I);
        s_G2DjKeyMapToAWTKey.put(KeyCode.O,KeyEvent.VK_O);
        s_G2DjKeyMapToAWTKey.put(KeyCode.P,KeyEvent.VK_P);
        s_G2DjKeyMapToAWTKey.put(KeyCode.A,KeyEvent.VK_A);
        s_G2DjKeyMapToAWTKey.put(KeyCode.S,KeyEvent.VK_S);
        s_G2DjKeyMapToAWTKey.put(KeyCode.D,KeyEvent.VK_D);
        s_G2DjKeyMapToAWTKey.put(KeyCode.F,KeyEvent.VK_F);
        s_G2DjKeyMapToAWTKey.put(KeyCode.G,KeyEvent.VK_G);
        s_G2DjKeyMapToAWTKey.put(KeyCode.H,KeyEvent.VK_H);
        s_G2DjKeyMapToAWTKey.put(KeyCode.J,KeyEvent.VK_J);
        s_G2DjKeyMapToAWTKey.put(KeyCode.K,KeyEvent.VK_K);
        s_G2DjKeyMapToAWTKey.put(KeyCode.L,KeyEvent.VK_L);
        s_G2DjKeyMapToAWTKey.put(KeyCode.Z,KeyEvent.VK_Z);
        s_G2DjKeyMapToAWTKey.put(KeyCode.X,KeyEvent.VK_X);
        s_G2DjKeyMapToAWTKey.put(KeyCode.C,KeyEvent.VK_C);
        s_G2DjKeyMapToAWTKey.put(KeyCode.V,KeyEvent.VK_V);
        s_G2DjKeyMapToAWTKey.put(KeyCode.B,KeyEvent.VK_B);
        s_G2DjKeyMapToAWTKey.put(KeyCode.N,KeyEvent.VK_N);
        s_G2DjKeyMapToAWTKey.put(KeyCode.M,KeyEvent.VK_M);
        
        //Bottom row
        s_G2DjKeyMapToAWTKey.put(KeyCode.LeftControl ,KeyEvent.VK_CONTROL); //NOTE: AWT offers no way to differentiate ctrl keys
        s_G2DjKeyMapToAWTKey.put(KeyCode.LeftAlt     ,KeyEvent.VK_ALT    ); //NOTE: AWT offers no way to differentiate alt keys
        s_G2DjKeyMapToAWTKey.put(KeyCode.Space       ,KeyEvent.VK_SPACE  );
        s_G2DjKeyMapToAWTKey.put(KeyCode.RightAlt    ,KeyEvent.VK_ALT    );
        s_G2DjKeyMapToAWTKey.put(KeyCode.RightControl,KeyEvent.VK_CONTROL);
        
        //Arrow keys
        s_G2DjKeyMapToAWTKey.put(KeyCode.LeftArrow ,KeyEvent.VK_LEFT );
        s_G2DjKeyMapToAWTKey.put(KeyCode.RightArrow,KeyEvent.VK_RIGHT);
        s_G2DjKeyMapToAWTKey.put(KeyCode.UpArrow   ,KeyEvent.VK_UP   );
        s_G2DjKeyMapToAWTKey.put(KeyCode.DownArrow ,KeyEvent.VK_DOWN );
        
        //Numpad
        s_G2DjKeyMapToAWTKey.put(KeyCode.Numlock    ,KeyEvent.VK_NUM_LOCK);
        s_G2DjKeyMapToAWTKey.put(KeyCode.NumSlash   ,KeyEvent.VK_SLASH   ); //NOTE: AWT offers no way to differentiate slash keys
        s_G2DjKeyMapToAWTKey.put(KeyCode.NumAsterisk,KeyEvent.VK_ASTERISK); 
        s_G2DjKeyMapToAWTKey.put(KeyCode.NumMinus   ,KeyEvent.VK_MINUS   ); //NOTE: AWT offers no way to differentiate minus keys
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num7       ,KeyEvent.VK_NUMPAD7 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num8       ,KeyEvent.VK_NUMPAD8 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num9       ,KeyEvent.VK_NUMPAD9 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.NumPlus    ,KeyEvent.VK_PLUS    );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num4       ,KeyEvent.VK_NUMPAD4 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num5       ,KeyEvent.VK_NUMPAD5 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num6       ,KeyEvent.VK_NUMPAD6 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num1       ,KeyEvent.VK_NUMPAD1 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num2       ,KeyEvent.VK_NUMPAD2 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num3       ,KeyEvent.VK_NUMPAD3 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.NumEnter   ,KeyEvent.VK_ENTER   );
        s_G2DjKeyMapToAWTKey.put(KeyCode.Num0       ,KeyEvent.VK_NUMPAD0 );
        s_G2DjKeyMapToAWTKey.put(KeyCode.NumPeriod  ,KeyEvent.VK_PERIOD  );
        
    }

    

}