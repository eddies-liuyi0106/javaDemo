package net.canway.hotel_message.common;

import javax.xml.crypto.Data;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CommonTimer {
    public static ScheduledExecutorService service = Executors.newScheduledThreadPool(100);
}
