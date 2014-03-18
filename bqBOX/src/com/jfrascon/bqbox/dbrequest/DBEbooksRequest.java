package com.jfrascon.bqbox.dbrequest;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.jfrascon.bqbox.utils.DBApiSingleton;

public class DBEbooksRequest extends AsyncTaskLoader<Entry> {
	
	String ruta_actual = null;

	public DBEbooksRequest(Context context, String ruta_actual) {
		super(context);
		this.ruta_actual = ruta_actual;
	}

	@Override
	public Entry loadInBackground() {
		try {
			return DBApiSingleton.getDBApi().metadata(ruta_actual, 100, null, true,
					null);
		} catch (DropboxException e) {
			e.printStackTrace();
			return null;
		}
	}

}
