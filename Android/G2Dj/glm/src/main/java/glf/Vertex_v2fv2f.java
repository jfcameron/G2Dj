/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glf;

import glm.vec._2.Vec2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v2fv2f {

    public static final int SIZE = 2 * Vec2.SIZE;

    public Vec2 position;
    public Vec2 texCoord;

    public Vertex_v2fv2f(Vec2 position, Vec2 texCoord) {
        this.position = position;
        this.texCoord = texCoord;
    }

    public void toBb(ByteBuffer bb, int index) {
        bb
                .putFloat(index * SIZE + 0 * glm.Constants.FloatBYTES, position.x)
                .putFloat(index * SIZE + 1 * glm.Constants.FloatBYTES, position.y)
                .putFloat(index * SIZE + 2 * glm.Constants.FloatBYTES, texCoord.x)
                .putFloat(index * SIZE + 3 * glm.Constants.FloatBYTES, texCoord.y);
    }
}
