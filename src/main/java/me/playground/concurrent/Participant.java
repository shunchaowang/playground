package me.playground.concurrent;

import java.util.concurrent.TimeUnit;

public class Participant implements Runnable {

    private String name;
    private VideoConference conference;

    public Participant(String name, VideoConference conference) {
        this.name = name;
        this.conference = conference;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conference.arrive(name);
    }

    public static void main(String[] args) {

    }
}
