package in.ac.iifm.devadevan.myservices;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by Devadevan on 01-12-2016.
 */

public class UnboundedAudioPlayerService extends Service {
    MediaPlayer AudioPlayer;

    public void onCreate(){
        super.onCreate();
        AudioPlayer = MediaPlayer.create(this,R.raw.krtheme);
        AudioPlayer.setLooping(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        AudioPlayer.start();
        return START_STICKY;
    }
    public void onDestroy(){
        AudioPlayer.stop();
        AudioPlayer.release();
    }

    @Override
    public IBinder onBind(Intent Audioindent) {
        return null;
    }
}