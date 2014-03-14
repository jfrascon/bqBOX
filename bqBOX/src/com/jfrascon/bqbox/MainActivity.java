package com.jfrascon.bqbox;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button acceso_dropbox = (Button) findViewById(R.id.acceso_dropbox);
		acceso_dropbox.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Ir a dropbox", Toast.LENGTH_LONG).show();
			}
		});

	}
	

}
