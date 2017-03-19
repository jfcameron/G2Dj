/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glf;

import glm.Constants;
import glm.vec._2.Vec2;
import glm.vec._4.d.Vec4d;
import java.nio.ByteBuffer;

/**
 *
 * @author elect
 */
public class Vertex_v2fc4d {

    public static final int SIZE = Vec2.SIZE + Vec4d.SIZE;

    public Vec2 position;
    public Vec4d color;

    public Vertex_v2fc4d(Vec2 position, Vec4d color) {
        this.position = position;
        this.color = color;
    }

    public ByteBuffer toBB(ByteBuffer bb, int index) {
        return bb
                .putFloat(index * SIZE + 0 * glm.Constants.FloatBYTES, position.x)
                .putFloat(index * SIZE + 1 * glm.Constants.FloatBYTES, position.y)
                .putDouble(index * SIZE + 2 * glm.Constants.FloatBYTES + 0 * Constants.DoubleBYTES, color.x)
                .putDouble(index * SIZE + 2 * glm.Constants.FloatBYTES + 1 * Constants.DoubleBYTES, color.y)
                .putDouble(index * SIZE + 2 * glm.Constants.FloatBYTES + 2 * Constants.DoubleBYTES, color.z)
                .putDouble(index * SIZE + 2 * glm.Constants.FloatBYTES + 3 * Constants.DoubleBYTES, color.w);
    }
}
