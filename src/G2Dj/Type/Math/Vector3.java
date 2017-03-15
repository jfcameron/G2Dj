/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Math;

import static com.jogamp.opengl.math.FloatUtil.sqrt;

/**
 *
 * @author Joe
 */
public class Vector3 
{
    //***************************
    //Special vectors definitions
    //***************************
    public static final Vector3 Up      (){return new Vector3( 0, 1, 0);}
    public static final Vector3 Down    (){return new Vector3( 0,-1, 0);}
    public static final Vector3 Left    (){return new Vector3(-1, 0, 0);}
    public static final Vector3 Right   (){return new Vector3( 1, 0, 0);}
    public static final Vector3 Forward (){return new Vector3( 0, 0, 1);}
    public static final Vector3 Backward(){return new Vector3( 0, 0,-1);}
    public static final Vector3 Zero    (){return new Vector3( 0, 0, 0);}
    
    //*************
    // Data members
    //*************
    public float x,y,z;
    
    //********************************
    //State mutating vector operations
    //********************************
    public void addInPlace     (final Vector3 aVector){x+=aVector.x;y+=aVector.y;z+=aVector.z;}
    public void addInPlace     (final float   aScalar){x+=aScalar  ;y+=aScalar  ;z+=aScalar  ;}
    public void subtractInPlace(final Vector3 aVector){x-=aVector.x;y-=aVector.y;z-=aVector.z;}
    public void multiplyInPlace(final float   aScalar){x*=aScalar  ;y*=aScalar  ;z*=aScalar;  }
    public void divideInPlace  (final float   aScalar){x/=aScalar  ;y/=aScalar  ;z/=aScalar;  }
    
    public void normalize(){float l=length(); x/=l; y/=l; z/=l;}
    //public void normalizeAndScale(const float &aScalar);

    //******************************
    //Non mutating vector operations
    //******************************
    public Vector3 add     (final Vector3 aVector){return new Vector3(x+aVector.x,y+aVector.y,z+aVector.z);}
    public Vector3 subtract(final Vector3 aVector){return new Vector3(x-aVector.x,y-aVector.y,z-aVector.z);}
    public Vector3 multiply(final float   aScalar){return new Vector3(x*aScalar  ,y*aScalar  ,z*aScalar  );}
    public Vector3 divide  (final float   aScalar){return new Vector3(x/aScalar  ,y/aScalar  ,z*aScalar  );}
    
    public Vector3 unit(){float l=length(); return new Vector3(x/l,y/l,z/l);}
    public float length(){return sqrt((x*x)+(y*y)+(z*z));}
        
    //************
    //Constructors
    //************
    public Vector3(){this(0);}
    public Vector3(final float aScalar){this(aScalar,aScalar,aScalar);}
    public Vector3(final float aX, final float aY, final float aZ){x=aX;y=aY;z=aZ;}
    //public Vector3(const btVector3 &aBulletVector);
    
    @Override public String toString(){return "{"+x+", "+y+", "+z+"}";}
    
}
