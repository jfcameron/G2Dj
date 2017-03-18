/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glf;

import dev.Vec4u8;
import glm.vec._2.Vec2;
import java.nio.ByteBuffer;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v2fc4ub {

    public static final int SIZE = Vec2.SIZE + Vec4u8.SIZE;

    public Vec2 position;
    public Vec4u8 color;

    public Vertex_v2fc4ub(Vec2 position, Vec4u8 color) {
        this.position = position;
        this.color = color;
    }

    public void toBb(ByteBuffer bb, int index) {
        bb
                .putFloat(index * SIZE + 0 * glm.Constants.FloatBYTES, position.x)
                .putFloat(index * SIZE + 1 * glm.Constants.FloatBYTES, position.y)
                .put(index * SIZE + 2 * glm.Constants.FloatBYTES + 0 * Byte.BYTES, color.x)
                .put(index * SIZE + 2 * glm.Constants.FloatBYTES + 1 * Byte.BYTES, color.y)
                .put(index * SIZE + 2 * glm.Constants.FloatBYTES + 2 * Byte.BYTES, color.z)
                .put(index * SIZE + 2 * glm.Constants.FloatBYTES + 3 * Byte.BYTES, color.w);
    }
}
