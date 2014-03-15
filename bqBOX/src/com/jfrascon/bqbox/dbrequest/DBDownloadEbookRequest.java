package com.jfrascon.bqbox.dbrequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.jfrascon.bqbox.utils.DBApiSingleton;

public class DBDownloadEbookRequest extends AsyncTaskLoader<Entry> {

	String name_file;

	public DBDownloadEbookRequest(Context context, String name_file) {
		super(context);
		this.name_file = name_file;
	}

	@Override
	public Entry loadInBackground() {
		try {
			
			File file = new File("/sdcard", name_file);
			FileOutputStream outputStream = new FileOutputStream(file);

			return DBApiSingleton.getDBApi()
					.getFile(name_file, null, outputStream, null).getMetadata();

		} catch (DropboxException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
