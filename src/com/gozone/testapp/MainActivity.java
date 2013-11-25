package com.gozone.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RemoteViews;
import android.content.Intent;
import android.content.Context;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;

public class MainActivity extends Activity 
        implements Button.OnClickListener {

    private Button mNotificationButton;
    private Button mHideNavButton;
    private Button mShowNavButton;
    private Button mRootButton;
    private Button mOpenGLButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationButton = ( Button ) findViewById(R.id.btn_notification);
        mHideNavButton = ( Button ) findViewById(R.id.btn_nav_hide);
        mShowNavButton = ( Button ) findViewById(R.id.btn_nav_show);
        mRootButton = ( Button ) findViewById(R.id.btn_root);
        mOpenGLButton = ( Button ) findViewById(R.id.btn_opengl_es);
        mNotificationButton.setOnClickListener(this);
        mHideNavButton.setOnClickListener(this);
        mShowNavButton.setOnClickListener(this);
        mRootButton.setOnClickListener(this);
        mOpenGLButton.setOnClickListener(this);
    }
    
        /*
     * Handle the buttons.
     */
    public void onClick(View button) {

        switch (button.getId()) {
            case R.id.btn_notification:
                showCustomizeNotification();
                break;
            case R.id.btn_nav_hide:
                sendBroadcast(new Intent("com.android.action.hide_navigationbar"));
                break;
            case R.id.btn_nav_show:
                sendBroadcast(new Intent("com.android.action.display_navigationbar"));
                break;
            case R.id.btn_root:
                if(upgradeRootPermission(getPackageCodePath()) == false){
                    Toast.makeText(this, "Run root process failed!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Run as root process!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_list_group:
                Intent intent1 = new Intent(this, GroupListActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_opengl_es:
                Intent intent2 = new Intent(this, OpenGLActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private boolean upgradeRootPermission(String pkgCodePath) {  
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd="chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }  
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    private void showCustomizeNotification() {
        CharSequence title = "I am title";
        int icon = R.drawable.icon;
        long when = System.currentTimeMillis();
        Notification noti = new Notification(icon, title, when + 10000);
        noti.flags = Notification.FLAG_INSISTENT;
        RemoteViews remoteView = new RemoteViews(this.getPackageName(),R.layout.notification);
        remoteView.setImageViewResource(R.id.image, R.drawable.icon);
        remoteView.setTextViewText(R.id.text , "This is Customize Notification");
        noti.contentView = remoteView;
        PendingIntent contentIntent = PendingIntent.getActivity
                         (MainActivity.this, 0,new Intent("android.settings.SETTINGS"), 0);
        noti.contentIntent = contentIntent;
        NotificationManager mnotiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mnotiManager.notify(0, noti);
    }


    private void showDefaultNotification() {
        CharSequence title = "I am title";
        int icon = R.drawable.icon;
        long when = System.currentTimeMillis();
        Notification noti = new Notification(icon, title, when + 10000);
        noti.flags = Notification.FLAG_INSISTENT;

        Notification mNotification = new Notification();

        mNotification.icon = R.drawable.icon;  
        mNotification.tickerText = "NotificationTest";
        mNotification.when = System.currentTimeMillis();

        // Notification mNotification = = new Notification(R.drawable.icon,"NotificationTest", System.currentTimeMillis()));

        mNotification.defaults |= Notification.DEFAULT_SOUND;
        //mNotification.defaults |= Notification.DEFAULT_VIBRATE ;

        mNotification.flags = Notification.FLAG_INSISTENT ;
        PendingIntent contentIntent = PendingIntent.getActivity
                           (MainActivity.this, 0,new Intent("android.settings.SETTINGS"), 0);
        mNotification.setLatestEventInfo(MainActivity.this, "Default Notification", "----Normal----",contentIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mNotification);

    }

    private void removeNotification()
    {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
