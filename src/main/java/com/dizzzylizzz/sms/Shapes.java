package com.dizzzylizzz.sms;

import net.minecraft.core.BlockPos;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.jpenilla.squaremap.api.Point;
import xyz.jpenilla.squaremap.api.marker.Ellipse;

import static com.dizzzylizzz.sms.Config.radius1;
import static com.dizzzylizzz.sms.Config.shape1Center;

public class Shapes {



    public static class circleMarker{
        int markerID = 0;
        BlockPos markerCenter = new BlockPos(0,0,0);
        int radius = 0;

        void setCenter(circleMarker obj,BlockPos newCenter){
            obj.markerCenter = newCenter;
        }

        void setRadius(circleMarker obj, int radius){
            obj.radius = radius;
        }
        Point shapeCenter = Point.of(markerCenter.getX(), markerCenter.getZ());


        @NonNull
        Ellipse circleMarker = Ellipse.ellipse(shapeCenter, radius, radius);


    }

    public static class squareMarker{



    }

}
