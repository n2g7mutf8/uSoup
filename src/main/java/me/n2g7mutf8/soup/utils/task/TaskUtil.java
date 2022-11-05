package me.n2g7mutf8.soup.utils.task;

import me.n2g7mutf8.soup.SoupPvP;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtil {

    //BukkitAsync start
    public static void runTaskAsync(BukkitRunnable runnable) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskAsynchronously(SoupPvP.getInstance(), runnable);
    }

    public static void runTaskLaterAsync(BukkitRunnable runnable, long delay) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(SoupPvP.getInstance(), runnable, delay);
    }

    public static void runTaskTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(SoupPvP.getInstance(), runnable, delay, timer);
    }

    //RunnableAsync Start
    public static void runTaskAsync(Runnable runnable) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskAsynchronously(SoupPvP.getInstance(), runnable);
    }

    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(SoupPvP.getInstance(), runnable, delay);
    }

    public static void runTaskTimerAsync(Runnable runnable, long delay, long timer) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(SoupPvP.getInstance(), runnable, delay, timer);
    }

    //BukkitRunnable Start
    public static void runTaskLater(BukkitRunnable runnable, long delay) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskLater(SoupPvP.getInstance(), runnable, delay);
    }

    public static void runTaskTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(SoupPvP.getInstance(), delay, timer);
    }

    public static void runTask(BukkitRunnable runnable) {
        SoupPvP.getInstance().getServer().getScheduler().runTask(SoupPvP.getInstance(), runnable);
    }

    //Runnable Start
    public static void runTaskTimer(Runnable runnable, long delay, long timer) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskTimer(SoupPvP.getInstance(), runnable, delay, timer);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        SoupPvP.getInstance().getServer().getScheduler().runTaskLater(SoupPvP.getInstance(), runnable, delay);
    }

    public static void runTask(Runnable runnable) {
        SoupPvP.getInstance().getServer().getScheduler().runTask(SoupPvP.getInstance(), runnable);
    }
}