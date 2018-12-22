package top.koguma.tuner.audio;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import top.koguma.tuner.activity.ToningActivity;
import top.koguma.tuner.model.Sound;

public class ASoundThread extends Thread {
    /**
     * 线程通讯的handler
     */
    private Handler handler;
    /**
     * 声音的信息
     */
    private Sound sound = new Sound();
    /**
     * 当前的频率
     */
    private double currentFrequency = 0.0;

    public ASoundThread(Handler handler) {

        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        while (currentFrequency < 290 || currentFrequency > 300) {
            currentFrequency = (1 + Math.random() * 400);
            Log.e("TAG", "currentFrequency = " + currentFrequency);
            sound.mFrequency = currentFrequency;
            Message message = Message.obtain();
            message.obj = sound;
            message.what = ToningActivity.SOUND_MESSAGE;
            handler.sendMessage(message);

            if (currentFrequency > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void close() {

    }
}

