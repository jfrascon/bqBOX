package com.jfrascon.bqbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
	ListaAdapter lista_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ebooks);

		ebooks = new ArrayList<Entry>();
		lista_ebooks = (ListView) findViewById(R.id.lista_ebooks);
		lista_adapter = new ListaAdapter(this, R.layout.layout_fila, ebooks);
		lista_ebooks.setAdapter(lista_adapter);

		desplegable = (Spinner) findViewById(R.id.desplegable);
		ArrayAdapter<CharSequence> desplegable_adapter = ArrayAdapter
				.createFromResource(this, R.array.criterio_ordenacion,
						android.R.layout.simple_spinner_item);
		desplegable_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		desplegable.setAdapter(desplegable_adapter);
		desplegable.setEnabled(false);

		desplegable.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				Log.i(this.getClass().getName(), "ITEMSELECTED"+position);
				switch(position){
				
				case 0:
				Collections.sort(ebooks, new Comparator<Entry>() {
			        @Override
			        public int compare(Entry e1, Entry e2) {
			        	return e1.fileName().compareToIgnoreCase(e2.fileName());
			        }
			    });
				break;
				case 1:
					Collections.sort(ebooks, new Comparator<Entry>() {
				        @Override
				        public int compare(Entry e1, Entry e2) {
				        	return new Date(e1.modified).compareTo(new Date(e2.modified));
				        }
				    });
					
				break;
				
				}
				lista_adapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

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

			if (arg1.contents.size() > 0) {

				for (Entry ent : arg1.contents) {
					ebooks.add(ent);
					Log.i(this.getClass().getName(), ">>" + ent.fileName()
							+ ".\n" + ent.icon + ".\n" + ent.isDir + ".\n"
							+ ent.path + ".\n" + ent.modified);
				}
				desplegable.setEnabled(true);
				
			}
		} else {
			Toast.makeText(this, "No hay ebooks en tu dropbox",
					Toast.LENGTH_LONG).show();
		}

		lista_adapter.notifyDataSetChanged();

	}

	@Override
	public void onLoaderReset(Loader<Entry> arg0) {
		// TODO Auto-generated method stub

	}

}
