/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

//.if ANDROID

import com.sun.management.GarbageCollectionNotificationInfo;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

//|
//|import java.lang.reflect.Field;
//|import java.lang.reflect.Modifier;
//|import java.util.logging.Level;
//|import java.util.logging.Logger;
//|
//|import android.app.Application;
//|import android.util.Log;
//|
//|import java.lang.reflect.Field;
//|import java.lang.reflect.Type;
//.endif

/**
 *
 * @author Joe
 */
public class Debug 
{
    private static final StringBuilder s_StringBuilder = new StringBuilder();
    
    public static void log(final Object... aObjects)
    {
        if (aObjects == null || aObjects.length <= 0)
            return;
        
        if (aObjects[0] != null)
            s_StringBuilder.append(aObjects[0].toString());
        else
            s_StringBuilder.append("NULL");
            
        for(int i = 1, s = aObjects.length; i < s; i++)
        {
            s_StringBuilder.append(", ");
            s_StringBuilder.append(aObjects[i].toString());
        
        }
        
        s_StringBuilder.append("\n");
        
        //.if DESKTOP
        System.out.print(s_StringBuilder.toString());
        //.elseif ANDROID
        //|Log.d("G2Dj",s_StringBuilder.toString());
        //.endif
        
        s_StringBuilder.setLength(0);
        
    }
    
    public static void enableGarbageCollectionLogging()
    {
        //get all the GarbageCollectorMXBeans - there's one for each heap generation
        //so probably two - the old generation and young generation
        List<java.lang.management.GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        //Install a notifcation handler for each bean
        for (GarbageCollectorMXBean gcbean : gcbeans) 
        {
            System.out.println(gcbean);
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            //use an anonymously generated listener for this example
            // - proper code should really use a named class
            NotificationListener listener = new NotificationListener() 
            {
                //keep a count of the total time spent in GCs
                long totalGcDuration = 0;

                //implement the notifier callback handler
                @Override
                public void handleNotification(Notification notification, Object handback) 
                {
                    //we only handle GARBAGE_COLLECTION_NOTIFICATION notifications here
                    if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) 
                    {
                        //get the information associated with this notification
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                        
                        String gctype = info.getGcAction();
                        if ("end of minor GC".equals(gctype))
                            gctype = "Young Gen GC";
                        else if ("end of major GC".equals(gctype))
                            gctype = "Old Gen GC";
                        
                        StringBuilder sb = new StringBuilder();
                        sb.append("**************************\nGarbage Collector invoked!\n**************************\n");
                        sb.append("GC Type: ").append(gctype).append("\n");
                        //sb.append("ID: ").append(info.getGcInfo().getId()).append("\n");
                        sb.append("Cause: ").append(info.getGcCause()).append("\n");
                        sb.append("Duration: ").append(info.getGcInfo().getDuration()).append(" ms\n");
                        
                        /*//Get the information about each memory space, and pretty print it
                        Map<String, MemoryUsage> membefore = info.getGcInfo().getMemoryUsageBeforeGc();
                        Map<String, MemoryUsage> mem = info.getGcInfo().getMemoryUsageAfterGc();
                        for (Entry<String, MemoryUsage> entry : mem.entrySet()) 
                        {
                            String name = entry.getKey();
                            MemoryUsage memdetail = entry.getValue();
                            long memInit = memdetail.getInit();
                            long memCommitted = memdetail.getCommitted();
                            long memMax = memdetail.getMax();
                            long memUsed = memdetail.getUsed();
                            MemoryUsage before = membefore.get(name);
                            long beforepercent = ((before.getUsed()*1000L)/before.getCommitted());
                            long percent = ((memUsed*1000L)/before.getCommitted()); //>100% when it gets expanded
                            
                            sb.append(name + (memCommitted==memMax?"(fully expanded)":"(still expandable)") +"used: "+(beforepercent/10)+"."+(beforepercent%10)+"%->"+(percent/10)+"."+(percent%10)+"%("+((memUsed/1048576)+1)+"MB) / \n");
                            
                        }*/
            
                        totalGcDuration += info.getGcInfo().getDuration();
                        long percent = totalGcDuration*1000L/info.getGcInfo().getEndTime();
                        sb.append("GC cumulated overhead "+(percent/10)+"."+(percent%10)+"%");
                        Debug.log(sb.append("\n").toString());
          
                    }
        
                }
                
            };
            
            //Add the listener
            emitter.addNotificationListener(listener, null, null);

        }
        
    }
    
}
