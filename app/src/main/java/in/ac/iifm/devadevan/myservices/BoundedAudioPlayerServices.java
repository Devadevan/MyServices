package in.ac.iifm.devadevan.myservices;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Devadevan on 01-12-2016.
 */

public class BoundedAudioPlayerServices extends Service {

    MediaPlayer AudioPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        AudioPlayer = MediaPlayer.create(this,R.raw.krtheme);
        AudioPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AudioPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        AudioPlayer.stop();
        AudioPlayer.release();
        super.onDestroy();
    }


    public class MyLocalBinder extends Binder{
        BoundedAudioPlayerServices getService() {
            return BoundedAudioPlayerServices.this;
        }
    }

    private final IBinder myBinder = new MyLocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
        //return null;   This is default
    }

    public void pauseAudio(){
        if(AudioPlayer.isPlaying())
            AudioPlayer.pause();
    }
}
