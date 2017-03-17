package grimhaus.com.g2dj;

import java.util.ArrayList;

/**
 * Created by Joe on 3/17/2017.
 */

public class TestEngine
{
    static ArrayList<TestGameObject> s_GameObjects = new ArrayList<>();

    //
    //
    //
    static protected void init()
    {
        s_GameObjects.add(new TestGameObject());

    }

    static protected void update()
    {
        for(int i=0,s=s_GameObjects.size();i<s;i++)
            s_GameObjects.get(i).update();

    }

    static protected void draw()
    {
        for(int i=0,s=s_GameObjects.size();i<s;i++)
            s_GameObjects.get(i).draw();

    }

    //
    //
    //
    private TestEngine(){}

}
