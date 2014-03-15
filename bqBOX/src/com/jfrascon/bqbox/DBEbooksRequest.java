package com.jfrascon.bqbox;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

public class DBEbooksRequest extends AsyncTaskLoader<Entry>{

	public DBEbooksRequest(Context context) {
		super(context);
	}

	@Override
	public Entry loadInBackground() {
		try {
			return DBApiSingleton.getDBApi().metadata("/", 100, null, true,
					null);
		} catch (DropboxException e) {
			e.printStackTrace();
			return null;
		}
	}

}
