/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Math;

/**
 *
 * @author Joseph Cameron
 */
public class Plane 
{
    public enum AxisAligned
    {
        XZ,
        XY,
        YZ;
        
    }
    
    public static Vector3 getPlaneInterceptPointFromALineInVectorForm(final Vector3 aPointInTheLine, final Vector3 aLineDirection, final float aPlaneOffset)
    {
        Vector3 cameraPos = aPointInTheLine;
        float aPlaneY = aPlaneOffset;
            
        Vector3 dir = aLineDirection;
        dir.normalize();
        
        if (dir.y == 0)//no intercept possible with this dir. project directly against xzplane
            return new Vector3(cameraPos.x,aPlaneY,cameraPos.z);
        
        float t = (aPlaneY-cameraPos.y)/dir.y;
        dir.multiplyInPlace(t);
        
        Vector3 projectedPoint = new Vector3(cameraPos.add(dir));//dir.setInPlace(cameraPos.add(dir));
        
        //Debug.log("getPlaneInterceptPointFromALineInVectorForm Intercept vec: "+projectedPoint);
        
        return projectedPoint;
        
    }
    
}
