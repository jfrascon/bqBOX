package com.jfrascon.bqbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI.Entry;
import com.jfrascon.bqbox.dbrequest.DBDownloadEbookRequest;
import com.jfrascon.bqbox.dbrequest.DBEbooksRequest;

public class EbooksActivity extends Activity implements LoaderCallbacks<Entry> {

	private final int PETICION_LISTA_EBOOKS = 0;
	private final int PETICION_DESCARGA = 1;

	ListView lista_ebooks = null;
	ListaAdapter lista_adapter = null;
	Spinner desplegable = null;

	ArrayList<Entry> ebooks = null;
	String ebook_seleccionado = "";
	String ruta_actual = null;

	ArrayList<String> rutas_anteriores = null;

	LoaderCallbacks<Entry> callback = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ebooks);

		ruta_actual = "/";
		rutas_anteriores = new ArrayList<String>();

		callback = this;

		ebooks = new ArrayList<Entry>(); // Modelo
		lista_adapter = new ListaAdapter(this, R.layout.layout_fila, ebooks); // Controlador
		lista_ebooks = (ListView) findViewById(R.id.lista_ebooks); // Vista
		lista_ebooks.setAdapter(lista_adapter);
		lista_ebooks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Log.i(this.getClass().getName(), ebooks.get(position).path);

				if (!ebooks.get(position).isDir) {

					ebook_seleccionado = ebooks.get(position).path;// ebooks.get(position).fileName();
					getLoaderManager().restartLoader(PETICION_DESCARGA, null,
							callback).forceLoad();
					Toast.makeText((Context) callback,
							"Descarga de ebook a la sdcard", Toast.LENGTH_LONG)
							.show();
				} else {

					if (!rutas_anteriores.isEmpty()
							&& ebooks.get(position).path
									.equalsIgnoreCase(rutas_anteriores
											.get(rutas_anteriores.size() - 1))) {

						rutas_anteriores.remove(rutas_anteriores.size() - 1);
					} else {

						rutas_anteriores.add(ruta_actual);
					}

					ruta_actual = ebooks.get(position).path;
					Log.i(this.getClass().getName(),
							rutas_anteriores.toString());
					Log.i(this.getClass().getName(), ruta_actual);
					getLoaderManager().restartLoader(PETICION_LISTA_EBOOKS,
							null, callback).forceLoad();
				}
			}
		});

		ArrayAdapter<CharSequence> desplegable_adapter = ArrayAdapter
				.createFromResource(this, R.array.criterio_ordenacion,
						android.R.layout.simple_spinner_item); // Controlador
		desplegable_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		desplegable = (Spinner) findViewById(R.id.desplegable); // Vista
		desplegable.setAdapter(desplegable_adapter);
		desplegable.setEnabled(false);
		desplegable.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {

				case 0: // Comparación para ordenar por nombre de fichero.
					Collections.sort(ebooks, new Comparator<Entry>() {
						@Override
						public int compare(Entry e1, Entry e2) {
							return e1.fileName().compareToIgnoreCase(
									e2.fileName());
						}
					});
					break;

				case 1: // Comparación para ordenar por fecha.
					Collections.sort(ebooks, new Comparator<Entry>() {
						@Override
						public int compare(Entry e1, Entry e2) {
							return new Date(e1.modified).compareTo(new Date(
									e2.modified));
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
		getLoaderManager().initLoader(PETICION_DESCARGA, null, callback);
		getLoaderManager().initLoader(PETICION_LISTA_EBOOKS, null, callback)
				.forceLoad();

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public Loader<Entry> onCreateLoader(int arg0, Bundle arg1) {
		switch (arg0) {
		case PETICION_LISTA_EBOOKS:
			return new DBEbooksRequest(this, ruta_actual);
		case PETICION_DESCARGA:
			return new DBDownloadEbookRequest(this, ebook_seleccionado);
		default:
			return null;
		}

	}

	@Override
	public void onLoadFinished(Loader<Entry> arg0, Entry arg1) {

		if (arg1 != null) {
			// Los ficheros son hojas en el sistema de ficheros, no cuelga nada de ellos.
			// Dejar el ListView tal y como está.
			if (!arg1.isDir) 
				return;
			// En caso de seleccionar un directorio, actualizar el ListView.
			ebooks.clear();
			
			if (!rutas_anteriores.isEmpty()) {

				Entry subir_a = new Entry();
				subir_a.isDir = true;
				subir_a.path = rutas_anteriores
						.get(rutas_anteriores.size() - 1);
				subir_a.icon = "subir_a";
				// rutas_anteriores.remove(rutas_anteriores.size() - 1);
				ebooks.add(subir_a);
			}

			if (arg1.contents.size() > 0) {
				
				for (Entry ent : arg1.contents) {

					ebooks.add(ent);
					Log.i(this.getClass().getName(), ">> " + ent.path + " ("
							+ ent.icon + ", " + ent.isDir + ", " + ent.modified
							+ ")");
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