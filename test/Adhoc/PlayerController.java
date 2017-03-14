/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adhoc;

import G2Dj.Debug;
import G2Dj.Type.Engine.GameObject;

/**
 *
 * @author Joe
 */
public class PlayerController extends G2Dj.Type.Engine.Component
{
    //
    //
    //
    
    
    //
    //
    //
    @Override
    public void update() 
    {
        Debug.log(getGameObject().get().getName());
        
    }

    @Override
    protected void OnAddedToGameObject(GameObject aGameObject){}

    @Override
    protected void OnRemovedFromGameObject(){}
    
}
