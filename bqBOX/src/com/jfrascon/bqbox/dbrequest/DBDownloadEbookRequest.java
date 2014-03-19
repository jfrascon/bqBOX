package com.jfrascon.bqbox.dbrequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.jfrascon.bqbox.utils.DBApiSingleton;

public class DBDownloadEbookRequest extends AsyncTaskLoader<Entry> {

	String nombre_fichero_remoto = null;

	public DBDownloadEbookRequest(Context context, String nombre_fichero_remoto) {
		super(context);
		this.nombre_fichero_remoto = nombre_fichero_remoto;
	}

	@Override
	public Entry loadInBackground() {
		try {
			// El nombre del fichero descargado coincide con el nombre del
			// fichero almancenado en Dropbox.
			String nombre_fichero_local = nombre_fichero_remoto
					.substring(nombre_fichero_remoto.lastIndexOf("/") + 1);
			File file = new File(Environment.getExternalStorageDirectory(),
					nombre_fichero_local);
			FileOutputStream outputStream = new FileOutputStream(file);

			DropboxFileInfo dbfi = DBApiSingleton.getDBApi().

			getFile(nombre_fichero_remoto, null, outputStream, null);

			Log.i(this.getClass().getName(), "Pasando por aquí");

			return dbfi.getMetadata();

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
