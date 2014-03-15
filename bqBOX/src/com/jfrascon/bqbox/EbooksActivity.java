package com.jfrascon.bqbox;

import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class EbooksActivity extends Activity implements
LoaderCallbacks<Entry>{

	ArrayList<Entry> ebooks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ebooks);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this).forceLoad();

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public Loader<Entry> onCreateLoader(int arg0, Bundle arg1) {
		return new DBEbooksRequest(this);
	}

	@Override
	public void onLoadFinished(Loader<Entry> arg0, Entry arg1) {

		if (arg1 != null){
			ebooks = (ArrayList<Entry>) arg1.contents;

		for (Entry ent : ebooks) {
			Log.i(this.getClass().getName(), ">>" + ent.fileName() + ".\n"
					+ ent.icon + ".\n" + ent.isDir + ".\n" + ent.path + ".\n"
					+ ent.modified);
		}
		}else{
			Toast.makeText(this, "No hay ebooks en tu dropbox", Toast.LENGTH_LONG).show();
		}

		
	}

	@Override
	public void onLoaderReset(Loader<Entry> arg0) {
		// TODO Auto-generated method stub
		
	}

}
