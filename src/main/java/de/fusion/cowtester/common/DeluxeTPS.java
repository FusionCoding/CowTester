package de.fusion.cowtester.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DeluxeTPS {

   private static final Class<?> minecraftServerClass = Reflection.getNmsClass("MinecraftServer");
   private static final Method getServerMethod = minecraftServerClass != null ? Reflection.makeMethod(minecraftServerClass, "getServer") : null;
   private static final Field recentTpsField = minecraftServerClass != null ? Reflection.makeField(minecraftServerClass, "recentTps") : null;

   private static double[] getNMSRecentTps() {
       if (getServerMethod == null || recentTpsField == null) return new double[]{0.0};
       Object server = Reflection.callMethod(getServerMethod, null); // Call static MinecraftServer.getServer()
       double[] recent = Reflection.getField(recentTpsField, server);
       return recent;
   }


   public double getTPS() {
       return getNMSRecentTps()[0];
   }


}
