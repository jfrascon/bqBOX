package com.jfrascon.bqbox.utils;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

public class DBApiSingleton {

	static final private String APP_KEY = "gxbiyscpmumo7uh";
	static final private String APP_SECRET = "6qcbw9vp27flg1p";

	static private DropboxAPI<AndroidAuthSession> mDBApi = null;

	private DBApiSingleton() {
	}

	public static DropboxAPI<AndroidAuthSession> getDBApi() {
		if (mDBApi == null) {
			AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
			AndroidAuthSession session = new AndroidAuthSession(appKeys);
			mDBApi = new DropboxAPI<AndroidAuthSession>(session);
		}

		return mDBApi;
	}

}
