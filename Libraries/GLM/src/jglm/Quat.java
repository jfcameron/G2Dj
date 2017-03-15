/**
 * Copyright 2010 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY JogAmp Community ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL JogAmp Community OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of JogAmp Community.
 *
 */
package jglm;

/**
 * @deprecated
 * @author GBarbieri
 */
public class Quat {

    public float x, y, z, w;

    public Quat() {
    }

    public Quat(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quat(Vec3 vec, float w) {

        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        this.w = w;
    }

    /**
     * Constructor to create a rotation based quaternion from two vectors
     *
     * @param vector1
     * @param vector2
     */
    public Quat(float[] vector1, float[] vector2) {
        float theta = (float) Math.acos(dot(vector1, vector2));
        float[] cross = cross(vector1, vector2);
        cross = normalizeVec(cross);

        this.x = (float) Math.sin(theta / 2) * cross[0];
        this.y = (float) Math.sin(theta / 2) * cross[1];
        this.z = (float) Math.sin(theta / 2) * cross[2];
        this.w = (float) Math.cos(theta / 2);
        this.normalize();
    }

    /**
     * Transform the rotational quaternion to axis based rotation angles
     *
     * @return new float[4] with ,theta,Rx,Ry,Rz
     */
    public float[] toAxis() {
        float[] vec = new float[4];
        float scale = (float) Math.sqrt(x * x + y * y + z * z);
        vec[0] = (float) Math.acos(w) * 2.0f;
        vec[1] = x / scale;
        vec[2] = y / scale;
        vec[3] = z / scale;
        return vec;
    }

    public void printEulerAngles() {

        double heading, attitude, bank;

        double test = x * y + z * w;

        if (test > 0.499) { // singularity at north pole
            heading = 2 * Math.atan2(x, w);
            attitude = Math.PI / 2;
            bank = 0;
        }
        if (test < -0.499) { // singularity at south pole
            heading = -2 * Math.atan2(x, w);
            attitude = -Math.PI / 2;
            bank = 0;
        }
        double sqx = x * x;
        double sqy = y * y;
        double sqz = z * z;
        heading = Math.atan2(2 * y * w - 2 * x * z, 1 - 2 * sqy - 2 * sqz);
        attitude = Math.asin(2 * test);
        bank = Math.atan2(2 * x * w - 2 * y * z, 1 - 2 * sqx - 2 * sqz);

        System.out.println("heading: " + heading + " attitude: " + attitude + " bank: " + bank + " ");
    }

    /**
     * Normalize a vector
     *
     * @param vector input vector
     * @return normalized vector
     */
    private float[] normalizeVec(float[] vector) {
        float[] newVector = new float[3];

        float d = (float) Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
        if (d > 0.0f) {
            newVector[0] = vector[0] / d;
            newVector[1] = vector[1] / d;
            newVector[2] = vector[2] / d;
        }
        return newVector;
    }

    /**
     * compute the dot product of two points
     *
     * @param vec1 vector 1
     * @param vec2 vector 2
     * @return the dot product as float
     */
    private float dot(float[] vec1, float[] vec2) {
        return (vec1[0] * vec2[0] + vec1[1] * vec2[1] + vec1[2] * vec2[2]);
    }

    /**
     * cross product vec1 x vec2
     *
     * @param vec1 vector 1
     * @param vec2 vecttor 2
     * @return the resulting vector
     */
    private float[] cross(float[] vec1, float[] vec2) {
        float[] out = new float[3];

        out[0] = vec2[2] * vec1[1] - vec2[1] * vec1[2];
        out[1] = vec2[0] * vec1[2] - vec2[2] * vec1[0];
        out[2] = vec2[1] * vec1[0] - vec2[0] * vec1[1];

        return out;
    }

    public static Quat getQuatBetweenVecs(Vec3 v1, Vec3 v2) {

        Vec3 cross = v1.crossProduct(v2);

        Quat quat = new Quat();

        quat.x = cross.x;
        quat.y = cross.y;
        quat.z = cross.z;

//        quat.w = (float) (Math.sqrt(Math.pow(v1.length(), 2) * Math.pow(v2.length(), 2)) + v1.dot(v2));
        quat.w = (float) (v1.length() * v2.length() + v1.dot(v2));

        return quat;
    }

    public static Quat getQuatBetweenVecs1(Vec3 a, Vec3 b) {

        Vec3 tmp;
        Vec3 xUnit = new Vec3(1f, 0f, 0f);
        Vec3 yUnit = new Vec3(0f, 1f, 0f);
        Quat quat;

        float dot = a.dot(b);

        if (dot < -0.999999) {
//            System.out.println("1");
            tmp = xUnit.crossProduct(a);

            if (tmp.length() < 0.000001) {

                tmp = yUnit.crossProduct(a);
            }
            tmp.normalize();

            quat = Jglm.angleAxis(180, tmp);

        } else if (dot > 0.999999) {
//            System.out.println("2");
            quat = new Quat(0f, 0f, 0f, 1f);

        } else {
//            System.out.println("3");
            tmp = a.crossProduct(b);

            quat = new Quat(tmp.x, tmp.y, tmp.z, 1 + dot);

            quat.normalize();
        }
        return quat;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Add a quaternion
     *
     * @param q quaternion
     */
    public void add(Quat q) {
        x += q.x;
        y += q.y;
        z += q.z;
    }

    /**
     * Subtract a quaternion
     *
     * @param q quaternion
     */
    public void subtract(Quat q) {
        x -= q.x;
        y -= q.y;
        z -= q.z;
    }

    /**
     * Divide a quaternion by a constant
     *
     * @param n a float to divide by
     */
    public void divide(float n) {
        x /= n;
        y /= n;
        z /= n;
    }

    /**
     * Multiply this quaternion by the param quaternion
     *
     * @param q a quaternion to multiply with
     * @return
     */
    public Quat mult(Quat q) {

        Quat result = new Quat();

//        result.w = w * q.w - (x * q.x + y * q.y + z * q.z);
//
//        result.x = w * q.z + q.w * z + y * q.z - z * q.y;
//        result.y = w * q.x + q.w * x + z * q.x - x * q.z;
//        result.z = w * q.y + q.w * y + x * q.y - y * q.x;
        result.x = w * q.x + x * q.w + y * q.z - z * q.y;

        result.y = w * q.y + y * q.w + z * q.x - x * q.z;

//        result.y = w*q.y;
//        System.out.println("result.y = "+result.y);
//        result.y+=(-x*q.z);
//        System.out.println("result.y = "+result.y);
        result.z = w * q.z + z * q.w + x * q.y - y * q.x;

        result.w = w * q.w - (x * q.x + y * q.y + z * q.z);

        return result;
    }

    /**
     * Multiply a quaternion by a constant
     *
     * @param n a float constant
     * @return
     */
    public Quat mult(float n) {

        Quat result = new Quat(x, y, z, w);

        result.x *= n;
        result.y *= n;
        result.z *= n;

        return result;
    }

    public Vec3 mult(Vec3 v) {

        Vec3 quatVector = new Vec3(x, y, z);

        Vec3 uv = quatVector.crossProduct(v);

        Vec3 uuv = quatVector.crossProduct(uv);

        uv = uv.times(2 * w);

        uuv = uuv.times(2);

        return v.plus(uv).plus(uuv);
    }

    /**
     * Normalize a quaternion required if to be used as a rotational quaternion
     */
    public final void normalize() {
        float norme = (float) Math.sqrt(w * w + x * x + y * y + z * z);
        if (norme == 0.0f) {
            w = 1.0f;
            x = y = z = 0.0f;
        } else {
            float recip = 1.0f / norme;

            w *= recip;
            x *= recip;
            y *= recip;
            z *= recip;
        }
    }

    /**
     * Invert the quaternion If rotational, will produce a the inverse rotation
     *
     * @return
     */
    public Quat conjugate() {

        float norm = w * w + x * x + y * y + z * z;

//        w /= norm;
//        x = -x / norm;
//        y = -y / norm;
//        z = -z / norm;
        return new Quat(-x / norm, -y / norm, -z / norm, w / norm);
    }

    /**
     * Transform this quaternion to a 4x4 column matrix representing the
     * rotation
     *
     * @return new float[16] column matrix 4x4
     */
    public Mat4 toMatrix() {

        float[] matrix = new float[16];

        matrix[0] = 1.0f - 2 * y * y - 2 * z * z;
        matrix[1] = 2 * x * y + 2 * w * z;
        matrix[2] = 2 * x * z - 2 * w * y;
        matrix[3] = 0;

        matrix[4] = 2 * x * y - 2 * w * z;
        matrix[5] = 1.0f - 2 * x * x - 2 * z * z;
        matrix[6] = 2 * y * z + 2 * w * x;
        matrix[7] = 0;

        matrix[8] = 2 * x * z + 2 * w * y;
        matrix[9] = 2 * y * z - 2 * w * x;
        matrix[10] = 1.0f - 2 * x * x - 2 * y * y;
        matrix[11] = 0;

        matrix[12] = 0;
        matrix[13] = 0;
        matrix[14] = 0;
        matrix[15] = 1;

        return new Mat4(matrix);
    }

    /**
     * Set this quaternion from a Sphereical interpolation of two param
     * quaternion, used mostly for rotational animation
     *
     * @param a initial quaternion
     * @param b target quaternion
     * @param t float between 0 and 1 representing interp.
     */
    public void slerp(Quat a, Quat b, float t) {
        float omega, cosom, sinom, sclp, sclq;
        cosom = a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
        if ((1.0f + cosom) > Math.E) {
            if ((1.0f - cosom) > Math.E) {
                omega = (float) Math.acos(cosom);
                sinom = (float) Math.sin(omega);
                sclp = (float) Math.sin((1.0f - t) * omega) / sinom;
                sclq = (float) Math.sin(t * omega) / sinom;
            } else {
                sclp = 1.0f - t;
                sclq = t;
            }
            x = sclp * a.x + sclq * b.x;
            y = sclp * a.y + sclq * b.y;
            z = sclp * a.z + sclq * b.z;
            w = sclp * a.w + sclq * b.w;
        } else {
            x = -a.y;
            y = a.x;
            z = -a.w;
            w = a.z;
            sclp = (float) Math.sin((1.0f - t) * Math.PI * 0.5f);
            sclq = (float) Math.sin(t * Math.PI * 0.5f);
            x = sclp * a.x + sclq * b.x;
            y = sclp * a.y + sclq * b.y;
            z = sclp * a.z + sclq * b.z;
        }
    }

    /**
     * Check if this quaternion is empty, ie (0,0,0,1)
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (w == 1 && x == 0 && y == 0 && z == 0) {
            return true;
        }
        return false;
    }

    /**
     * Check if this quaternion represents an identity matrix, for rotation.
     *
     * @return true if it is an identity rep., false otherwise
     */
    public boolean isIdentity() {
        if (w == 0 && x == 0 && y == 0 && z == 0) {
            return true;
        }
        return false;
    }

    /**
     * compute the quaternion from a 3x3 column matrix
     *
     * @param m 3x3 column matrix
     */
    public void setFromMatrix(float[] m) {
        float T = m[0] + m[4] + m[8] + 1;
        if (T > 0) {
            float S = 0.5f / (float) Math.sqrt(T);
            w = 0.25f / S;
            x = (m[5] - m[7]) * S;
            y = (m[6] - m[2]) * S;
            z = (m[1] - m[3]) * S;
        } else if ((m[0] > m[4]) & (m[0] > m[8])) {
            float S = (float) (Math.sqrt(1.0f + m[0] - m[4] - m[8]) * 2f); // S=4*qx
            w = (m[7] - m[5]) / S;
            x = 0.25f * S;
            y = (m[3] + m[1]) / S;
            z = (m[6] + m[2]) / S;
        } else if (m[4] > m[8]) {
            float S = (float) (Math.sqrt(1.0f + m[4] - m[0] - m[8]) * 2f); // S=4*qy
            w = (m[6] - m[2]) / S;
            x = (m[3] + m[1]) / S;
            y = 0.25f * S;
            z = (m[7] + m[5]) / S;
        } else {
            float S = (float) (Math.sqrt(1.0f + m[8] - m[0] - m[4]) * 2f); // S=4*qz
            w = (m[3] - m[1]) / S;
            x = (m[6] + m[2]) / S;
            y = (m[7] + m[5]) / S;
            z = 0.25f * S;
        }
    }

    /**
     * Check if the the 3x3 matrix (param) is in fact an affine rotational
     * matrix
     *
     * @param m 3x3 column matrix
     * @return true if representing a rotational matrix, false otherwise
     */
    public boolean isRotationMatrix(float[] m) {
        double epsilon = 0.01; // margin to allow for rounding errors
        if (Math.abs(m[0] * m[3] + m[3] * m[4] + m[6] * m[7]) > epsilon) {
            return false;
        }
        if (Math.abs(m[0] * m[2] + m[3] * m[5] + m[6] * m[8]) > epsilon) {
            return false;
        }
        if (Math.abs(m[1] * m[2] + m[4] * m[5] + m[7] * m[8]) > epsilon) {
            return false;
        }
        if (Math.abs(m[0] * m[0] + m[3] * m[3] + m[6] * m[6] - 1) > epsilon) {
            return false;
        }
        if (Math.abs(m[1] * m[1] + m[4] * m[4] + m[7] * m[7] - 1) > epsilon) {
            return false;
        }
        if (Math.abs(m[2] * m[2] + m[5] * m[5] + m[8] * m[8] - 1) > epsilon) {
            return false;
        }
        return (Math.abs(determinant(m) - 1) < epsilon);
    }

    private float determinant(float[] m) {
        return m[0] * m[4] * m[8] + m[3] * m[7] * m[2] + m[6] * m[1] * m[5] - m[0] * m[7] * m[5] - m[3] * m[1] * m[8] - m[6] * m[4] * m[2];
    }

    public void print(String title) {
        System.out.println(title + " (" + x + ", " + y + ", " + z + ", " + w + ")");
    }
}
