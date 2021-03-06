/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Math;

import java.nio.FloatBuffer;

/**
 *
 * @author Joe
 */
public class Mat4x4 
{
    //*************
    // Data members
    //*************
    //private final glm.mat._4.Mat4 m_Mat4;
    
    public float
    m00, m01, m02, m03,
    m10, m11, m12, m13,
    m20, m21, m22, m23,
    m30, m31, m32, m33;
    
    
    //**********
    // Accessors
    //**********
    //protected glm.mat._4.Mat4 getMat(){return m_Mat4;}
    
    //*****************
    // Public interface
    //*****************
    //public void rotateX(final float aX){m_Mat4.rotateX(aX);}
    //public void rotateY(final float aY){m_Mat4.rotateY(aY);}
    //public void rotateZ(final float aZ){m_Mat4.rotateZ(aZ);}
        
    //public void translate(final float aX, final float aY, final float aZ){m_Mat4.translate(aX,aY,aZ);}
    //public void translate(final Vector3 aVector){m_Mat4.translate(aVector.x,aVector.y,aVector.z);}
    
    //public void scale(final float aX, final float aY, final float aZ){m_Mat4.scale(aX,aY,aZ);}
    //public void scale(final Vector3 aVector){m_Mat4.scale(aVector.x,aVector.y,aVector.z);}
        
    //public Mat4x4 mul(final Mat4x4 aMat){m_Mat4.mul(aMat.getMat());return this;}
    
    //public FloatBuffer toFloatBuffer(){return m_Mat4.toDfb_();}
    
    //*************
    // Constructors
    //*************
    /*public Mat4x4(){this(new glm.mat._4.Mat4().identity());}
    
    private Mat4x4(glm.mat._4.Mat4 aMat4)
    {
        m_Mat4 = aMat4;
        
    }*/
    
    public Mat4x4()
    {
        this.set(1.0f);
        
    }
    
    /*private Mat4x4(glm.mat._4.Mat4 aMat4)
    {
        m_Mat4 = aMat4;
        
    }*/
    
    //*******
    // Static
    //*******
    /*public static Mat4x4 identity()
    {
        return new Mat4x4();
    
    }*/
    
    /*public static Mat4x4 perspective(final float aFOV, final float aAspectRatio, final float aNearClippingDistance, final float aFarClippingDistance)
    {
        return new Mat4x4(new glm.mat._4.Mat4().perspective(aFOV, aAspectRatio, aNearClippingDistance, aFarClippingDistance));
        
    }*/
    
    /*public static Mat4x4 orthographic(final float aLeft, final float aRight, final float aBottom, final float aTop, final float aNearClippingDistance, final float aFarClipppingDistance)
    {
        return new Mat4x4(new glm.mat._4.Mat4().ortho(aLeft, aRight, aBottom, aTop, aNearClippingDistance, aFarClipppingDistance));
        
    }*////////////////////////////////FIX THIS
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public Mat4x4 rotateX(final float ang){return rotateX(ang, this);}
    public Mat4x4 rotateY(final float ang){return rotateY(ang, this);}
    public Mat4x4 rotateZ(final float ang){return rotateZ(ang, this);}
    
    public void translate(final float aX, final float aY, final float aZ){translate(this,       aX,       aY,       aZ);}
    public void translate(final Vector3 aVector)                         {translate(this,aVector.x,aVector.y,aVector.z);}
    
    public void scale(final float x, final float y, final float z) {/*return*/ scale(x, y, z, this);}
    public void scale(final Vector3 aVector){scale(aVector.x,aVector.y,aVector.z,this);}
    
    public Mat4x4 mul(Mat4x4 right){return mul(right, this);}
    
    public static Mat4x4 perspective(final float aFOV, final float aAspectRatio, final float aNearClippingDistance, final float aFarClippingDistance)
    {
        return perspectiveRH(aFOV, aAspectRatio, aNearClippingDistance, aFarClippingDistance,new Mat4x4());
        
    }
    
    public static Mat4x4 identity() {
        return new Mat4x4().set(1.0f);
    }
    
    public void identityInPlace()
    {
        set(1.0f);
        
    }
    
    public void perspectiveInPlace(final float aFOV, final float aAspectRatio, final float aNearClippingDistance, final float aFarClippingDistance)
    {
        perspectiveRH(aFOV, aAspectRatio, aNearClippingDistance, aFarClippingDistance,this);
        
    }
    
    public final Mat4x4 set(float f) {
        return set(
                f, 0, 0, 0,
                0, f, 0, 0,
                0, 0, f, 0,
                0, 0, 0, f);
    }
    
    public final Mat4x4 set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13,
            float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {

        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        return this;
    }
    
    public final Mat4x4 set(final Mat4x4 aMat) 
    {
        this.m00 = aMat.m00;
        this.m01 = aMat.m01;
        this.m02 = aMat.m02;
        this.m03 = aMat.m03;
        this.m10 = aMat.m10;
        this.m11 = aMat.m11;
        this.m12 = aMat.m12;
        this.m13 = aMat.m13;
        this.m20 = aMat.m20;
        this.m21 = aMat.m21;
        this.m22 = aMat.m22;
        this.m23 = aMat.m23;
        this.m30 = aMat.m30;
        this.m31 = aMat.m31;
        this.m32 = aMat.m32;
        this.m33 = aMat.m33;
        return this;
    }
    
    
    
    public FloatBuffer toFloatBuffer()
    {
        FloatBuffer rValue = FloatBuffer.allocate(16);
        rValue.put( 0, m00);
        rValue.put( 1, m01);
        rValue.put( 2, m02);
        rValue.put( 3, m03);
        rValue.put( 4, m10);
        rValue.put( 5, m11);
        rValue.put( 6, m12);
        rValue.put( 7, m13);
        rValue.put( 8, m20);
        rValue.put( 9, m21);
        rValue.put(10, m22);
        rValue.put(11, m23);
        rValue.put(12, m30);
        rValue.put(13, m31);
        rValue.put(14, m32);
        rValue.put(15, m33);
        return rValue;
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public Mat4x4 rotateX(float ang, Mat4x4 res) 
    {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rm11 = +cos;
        float rm12 = +sin;
        float rm21 = -sin;
        float rm22 = +cos;
        // add temporaries for dependent values
        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        float nm13 = m13 * rm11 + m23 * rm12;
        // set non-dependent values directly
        res.m20 = m10 * rm21 + m20 * rm22;
        res.m21 = m11 * rm21 + m21 * rm22;
        res.m22 = m12 * rm21 + m22 * rm22;
        res.m23 = m13 * rm21 + m23 * rm22;
        // set other values
        res.m10 = nm10;
        res.m11 = nm11;
        res.m12 = nm12;
        res.m13 = nm13;
        res.m00 = m00;
        res.m01 = m01;
        res.m02 = m02;
        res.m03 = m03;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }

    public Mat4x4 rotateY(float ang, Mat4x4 res) {
        float cos, sin;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rm00 = cos;
        float rm02 = -sin;
        float rm20 = sin;
        float rm22 = cos;
        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        float nm03 = m03 * rm00 + m23 * rm02;
        // set non-dependent values directly
        res.m20 = m00 * rm20 + m20 * rm22;
        res.m21 = m01 * rm20 + m21 * rm22;
        res.m22 = m02 * rm20 + m22 * rm22;
        res.m23 = m03 * rm20 + m23 * rm22;
        // set other values
        res.m00 = nm00;
        res.m01 = nm01;
        res.m02 = nm02;
        res.m03 = nm03;
        res.m10 = m10;
        res.m11 = m11;
        res.m12 = m12;
        res.m13 = m13;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }
    
    public Mat4x4 rotateZ(float ang, Mat4x4 res) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rm00 = cos;
        float rm01 = sin;
        float rm10 = -sin;
        float rm11 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        float nm03 = m03 * rm00 + m13 * rm01;
        // set non-dependent values directly
        res.m10 = m00 * rm10 + m10 * rm11;
        res.m11 = m01 * rm10 + m11 * rm11;
        res.m12 = m02 * rm10 + m12 * rm11;
        res.m13 = m03 * rm10 + m13 * rm11;
        // set other values
        res.m00 = nm00;
        res.m01 = nm01;
        res.m02 = nm02;
        res.m03 = nm03;
        res.m20 = m20;
        res.m21 = m21;
        res.m22 = m22;
        res.m23 = m23;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }
    
    public Mat4x4 translate(Mat4x4 res, float x, float y, float z) {
        // translation matrix elements: m00, m11, m22, m33 = 1
        // m30 = x, m31 = y, m32 = z, all others = 0
        res.m30 = res.m00 * x + res.m10 * y + res.m20 * z + res.m30;
        res.m31 = res.m01 * x + res.m11 * y + res.m21 * z + res.m31;
        res.m32 = res.m02 * x + res.m12 * y + res.m22 * z + res.m32;
        res.m33 = res.m03 * x + res.m13 * y + res.m23 * z + res.m33;
        return this;
    }
    
    public Mat4x4 scale(float x, float y, float z, Mat4x4 res) {
        res.m00 = m00 * x;
        res.m01 = m01 * x;
        res.m02 = m02 * x;
        res.m03 = m03 * x;
        res.m10 = m10 * y;
        res.m11 = m11 * y;
        res.m12 = m12 * y;
        res.m13 = m13 * y;
        res.m20 = m20 * z;
        res.m21 = m21 * z;
        res.m22 = m22 * z;
        res.m23 = m23 * z;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }    
    
    

    public Mat4x4 mul(Mat4x4 right, Mat4x4 dest) {
        dest.set(
                m00 * right.m00 + m10 * right.m01 + m20 * right.m02 + m30 * right.m03,
                m01 * right.m00 + m11 * right.m01 + m21 * right.m02 + m31 * right.m03,
                m02 * right.m00 + m12 * right.m01 + m22 * right.m02 + m32 * right.m03,
                m03 * right.m00 + m13 * right.m01 + m23 * right.m02 + m33 * right.m03,
                m00 * right.m10 + m10 * right.m11 + m20 * right.m12 + m30 * right.m13,
                m01 * right.m10 + m11 * right.m11 + m21 * right.m12 + m31 * right.m13,
                m02 * right.m10 + m12 * right.m11 + m22 * right.m12 + m32 * right.m13,
                m03 * right.m10 + m13 * right.m11 + m23 * right.m12 + m33 * right.m13,
                m00 * right.m20 + m10 * right.m21 + m20 * right.m22 + m30 * right.m23,
                m01 * right.m20 + m11 * right.m21 + m21 * right.m22 + m31 * right.m23,
                m02 * right.m20 + m12 * right.m21 + m22 * right.m22 + m32 * right.m23,
                m03 * right.m20 + m13 * right.m21 + m23 * right.m22 + m33 * right.m23,
                m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30 * right.m33,
                m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31 * right.m33,
                m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32 * right.m33,
                m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33 * right.m33);
        return dest;
    }
    
    /*private static Mat4x4 perspectiveLH(float fovy, float aspect, float zNear, float zFar, Mat4x4 res) 
    {
        float tanHalfFovy = (float) Math.tan(fovy * 0.5f);
        res.m00 = 1.0f / (aspect * tanHalfFovy);
        res.m01 = 0.0f;
        res.m02 = 0.0f;
        res.m03 = 0.0f;
        res.m10 = 0.0f;
        res.m11 = 1.0f / tanHalfFovy;
        res.m12 = 0.0f;
        res.m13 = 0.0f;
        res.m20 = 0.0f;
        res.m21 = 0.0f;
        res.m22 = (zFar + zNear) / (zFar - zNear);
        res.m23 = 1.0f;
        res.m30 = 0.0f;
        res.m31 = 0.0f;
        res.m32 = -2.0f * zFar * zNear / (zFar - zNear);
        res.m33 = 0.0f;
        return res;
    }*/
    
    private static Mat4x4 perspectiveRH(float fovy, float aspect, float zNear, float zFar, Mat4x4 res) {
        float tanHalfFovy = (float)Math.tan(fovy * 0.5f);
        res.m00 = 1.0f / (aspect * tanHalfFovy);
        res.m01 = 0.0f;
        res.m02 = 0.0f;
        res.m03 = 0.0f;
        res.m10 = 0.0f;
        res.m11 = 1.0f / tanHalfFovy;
        res.m12 = 0.0f;
        res.m13 = 0.0f;
        res.m20 = 0.0f;
        res.m21 = 0.0f;
        res.m22 = -(zFar + zNear) / (zFar - zNear);
        res.m23 = -1.0f;
        res.m30 = 0.0f;
        res.m31 = 0.0f;
        res.m32 = -2.0f * zFar * zNear / (zFar - zNear);
        res.m33 = 0.0f;
        return res;
    }
    
}
