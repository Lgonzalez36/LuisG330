package com.example.android.dessertclicker;

import java.lang.System;

/**
 * This is a class representing a timer that you can start or stop. The secondsCount outputs a count of
 * how many seconds since it started, every one second.
 *
 * -----
 *
 * Handler and Runnable are beyond the scope of this lesson. This is in part because they deal with
 * threading, which is a complex topic that will be covered in a later lesson.
 *
 * If you want to learn more now, you can take a look on the Android Developer documentation on
 * threading:
 *
 * https://developer.android.com/guide/components/processes-and-threads
 */
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0007J\b\u0010\u0011\u001a\u00020\u0010H\u0007R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, d2 = {"Lcom/example/android/dessertclicker/DessertTimer;", "Landroidx/lifecycle/LifecycleObserver;", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "(Landroidx/lifecycle/Lifecycle;)V", "handler", "Landroid/os/Handler;", "runnable", "Ljava/lang/Runnable;", "secondsCount", "", "getSecondsCount", "()I", "setSecondsCount", "(I)V", "startTimer", "", "stopTimer", "app_debug"})
public final class DessertTimer implements androidx.lifecycle.LifecycleObserver {
    private int secondsCount = 0;
    
    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
     * or actions (known as [Runnable]s)
     */
    private android.os.Handler handler;
    private java.lang.Runnable runnable;
    
    public DessertTimer(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.Lifecycle lifecycle) {
        super();
    }
    
    public final int getSecondsCount() {
        return 0;
    }
    
    public final void setSecondsCount(int p0) {
    }
    
    @androidx.lifecycle.OnLifecycleEvent(value = androidx.lifecycle.Lifecycle.Event.ON_START)
    public final void startTimer() {
    }
    
    @androidx.lifecycle.OnLifecycleEvent(value = androidx.lifecycle.Lifecycle.Event.ON_STOP)
    public final void stopTimer() {
    }
}