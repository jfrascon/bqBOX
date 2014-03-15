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
import android.widget.ListView;
import android.widget.Spinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class EbooksActivity extends Activity implements LoaderCallbacks<Entry> {

	
	Spinner desplegable;
	
	ArrayList<Entry> ebooks;

	ListView lista_ebooks;
	ListaAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ebooks);

		ebooks = new ArrayList<Entry>();
		lista_ebooks = (ListView) findViewById(R.id.lista_ebooks);
		adapter = new ListaAdapter(this, R.layout.layout_fila, ebooks);
		lista_ebooks.setAdapter(adapter);
		
		desplegable = (Spinner) findViewById(R.id.desplegable);

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

		if (arg1 != null) {

			ebooks.clear();

			for (Entry ent : arg1.contents) {
				ebooks.add(ent);
				Log.i(this.getClass().getName(), ">>" + ent.fileName() + ".\n"
						+ ent.icon + ".\n" + ent.isDir + ".\n" + ent.path
						+ ".\n" + ent.modified);
			}
		} else {
			Toast.makeText(this, "No hay ebooks en tu dropbox",
					Toast.LENGTH_LONG).show();
		}

		adapter.notifyDataSetChanged();

	}

	@Override
	public void onLoaderReset(Loader<Entry> arg0) {
		// TODO Auto-generated method stub

	}

}
