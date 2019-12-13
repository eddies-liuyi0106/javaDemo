package net.canway.meeting_message.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CommonTimer {
    public static ScheduledExecutorService service = Executors.newScheduledThreadPool(100);
}
