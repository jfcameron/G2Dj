/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glf;

import dev.Vec4u8;
import glm.vec._3.Vec3;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v3fv4u8 {

    public static final int SIZE = Vec3.SIZE + Vec4u8.SIZE;

    public Vec3 position;
    public Vec4u8 color;

    public Vertex_v3fv4u8(Vec3 position, Vec4u8 color) {
        this.position = position;
        this.color = color;
    }

    public void toBb(ByteBuffer bb, int index) {
        bb
                .putFloat(index * SIZE + 0 * glm.Constants.FloatBYTES, position.x)
                .putFloat(index * SIZE + 1 * glm.Constants.FloatBYTES, position.y)
                .putFloat(index * SIZE + 2 * glm.Constants.FloatBYTES, position.z)
                .put(index * SIZE + 3 * glm.Constants.FloatBYTES + 0 * glm.Constants.ByteBYTES, color.x)
                .put(index * SIZE + 3 * glm.Constants.FloatBYTES + 1 * glm.Constants.ByteBYTES, color.y)
                .put(index * SIZE + 3 * glm.Constants.FloatBYTES + 2 * glm.Constants.ByteBYTES, color.z)
                .put(index * SIZE + 3 * glm.Constants.FloatBYTES + 3 * glm.Constants.ByteBYTES, color.w);
    }
}
