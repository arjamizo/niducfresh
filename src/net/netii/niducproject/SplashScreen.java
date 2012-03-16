package net.netii.niducproject;

import java.util.ArrayList;
import java.util.List;

import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SplashScreen extends Activity {
//http://www.droidnova.com/how-to-create-a-splash-screen,561.html
	protected boolean _active = true;
	protected int _splashTime = 700; // time to display the splash screen in ms
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	 
	    // thread for displaying the SplashScreen
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                int waited = 0;
	                while(_active && (waited < _splashTime)) {
	                    sleep(100);
	                    if(_active) {
	                        waited += 100;
	                    }
	                }
	            } catch(InterruptedException e) {
	                // do nothing
	            } finally {
	                finish();
	                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
					startActivity(intent);
	                stop();
	            }
	        }
	    };
	    splashTread.start();
	}
}
