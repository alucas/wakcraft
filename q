[1mdiff --git a/src/main/java/heero/mc/mod/wakcraft/entity/creature/EntityWCreature.java b/src/main/java/heero/mc/mod/wakcraft/entity/creature/EntityWCreature.java[m
[1mindex 9b12919..0aed240 100644[m
[1m--- a/src/main/java/heero/mc/mod/wakcraft/entity/creature/EntityWCreature.java[m
[1m+++ b/src/main/java/heero/mc/mod/wakcraft/entity/creature/EntityWCreature.java[m
[36m@@ -85,9 +85,6 @@[m [mpublic abstract class EntityWCreature extends EntityCreature implements IWMob, I[m
 //        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {[m
 //            System.out.println(element);[m
 //        }[m
[31m-[m
[31m-[m
[31m-        System.out.println("Set health " + health);[m
         super.setHealth(health);[m
     }[m
 [m
