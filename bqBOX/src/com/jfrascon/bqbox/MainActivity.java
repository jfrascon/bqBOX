package com.jfrascon.bqbox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jfrascon.bqbox.utils.DBApiSingleton;

public class MainActivity extends Activity {

	final static private String ACCOUNT_PREFS_NAME = "prefs";
	final static private String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";

	Button acceso_dropbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		acceso_dropbox = (Button) findViewById(R.id.acceso_dropbox);
		acceso_dropbox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DBApiSingleton.getDBApi().getSession()
						.startOAuth2Authentication(MainActivity.this);
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();

		if (DBApiSingleton.getDBApi().getSession().authenticationSuccessful()) {
			try {
				// Required to complete auth, sets the access token on the
				// session
				DBApiSingleton.getDBApi().getSession().finishAuthentication();

				String accessToken = DBApiSingleton.getDBApi().getSession()
						.getOAuth2AccessToken();

				if (accessToken != null) {
					SharedPreferences prefs = getSharedPreferences(
							ACCOUNT_PREFS_NAME, 0);
					Editor edit = prefs.edit();
					edit.putString(ACCESS_TOKEN_NAME, accessToken).commit();

					Intent intent = new Intent(this, EbooksActivity.class);
					startActivity(intent);
					finish();

				}

			} catch (IllegalStateException e) {
				Log.e(this.getClass().getName(), "Error authenticating", e);
			}
		}
	}

}
