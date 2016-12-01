package in.ac.iifm.devadevan.myservices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    BoundedAudioPlayerServices myService;
    private boolean isBound;
    ImageButton btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (ImageButton) findViewById(R.id.button1);
        isBound = false;

        //This code is for Unbounded Service
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ub=new Intent(getBaseContext(), UnboundedAudioPlayerService.class);
                startService(ub);
            }
        });
    }

    public void playAudio(View view) {
        Intent objIntent = new Intent(this, BoundedAudioPlayerServices.class);
        if (!isBound) {
            bindService(objIntent, myConnection, Context.BIND_AUTO_CREATE);
            isBound = true;
            btn.setBackgroundResource(R.drawable.pause);//setText("Pause");
            startService(objIntent);
        } else {
            myService.pauseAudio();
            btn.setBackgroundResource(R.drawable.play);
            isBound = false;
            unbindService(myConnection);
        }
    }

    public void stopAudio(View view) {
        Intent objIntent = new Intent(this, BoundedAudioPlayerServices.class);
        if (isBound) {
            isBound = false;
            unbindService(myConnection);
            stopService(objIntent);
        } else
            stopService(objIntent);
        btn.setBackgroundResource(R.drawable.play);
    }

    private ServiceConnection myConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            myService = ((BoundedAudioPlayerServices.MyLocalBinder) service).getService();
            isBound = true;
        }
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            // Disconnect from an application service. You will no longer receive calls as
            // the service is restarted, and the service is now allowed to stop at any time.
            unbindService(myConnection);
            isBound = false;
        }
    }
}