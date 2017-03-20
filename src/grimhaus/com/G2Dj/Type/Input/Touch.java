package grimhaus.com.G2Dj.Type.Input;

import grimhaus.com.G2Dj.Imp.Input.TouchState;
import grimhaus.com.G2Dj.Type.Math.Vector2;

/**
 * Created by Joe on 3/19/2017.
 */

public class Touch
{
    public int           id;
    public final Vector2 position      = new Vector2();
    public int           time;
    public final Vector2 deltaPosition = new Vector2();//TODO:IMPLEMENT
    public float         deltaTime;//TODO:IMPLEMENT
    public TouchState    state;

    public final void clear()
    {
        id            = -1;
        time          = 0;
        deltaTime     = 0;
        state         = TouchState.NULL;

        position.setInPlace(0,0);
        deltaPosition.setInPlace(0,0);

    }

    public boolean isActive()
    {
        switch(state)
        {
            case Stationary:
            case Moved:
            case Began:
            return true;

        }

        return false;

    }

    public Touch(){clear();}

    @Override public String toString(){return "Touch: { ID: "+id+", Position: "+position+", Time: "+time+", DeltaPosition: "+deltaPosition+", DeltaTime: "+deltaTime+", TouchState: "+state+" }";}

}
