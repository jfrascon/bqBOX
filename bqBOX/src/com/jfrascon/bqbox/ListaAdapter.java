package com.jfrascon.bqbox;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI.Entry;

public class ListaAdapter extends ArrayAdapter<Entry> {

	private LayoutInflater inflater;
	private int resource;

	ArrayList<Entry> ebooks;

	public ListaAdapter(Context context, int resource, List<Entry> objects) {
		super(context, resource, objects);
		this.resource = resource;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = inflater.inflate(resource, parent, false);

		Entry e = (Entry) this.getItem(position);

		EbookHolder holder = new EbookHolder();
		holder.imagen_generica = (ImageView) convertView
				.findViewById(R.id.imagen_generica);
		holder.nombre_fichero = (TextView) convertView
				.findViewById(R.id.nombre_fichero);
		holder.fecha_modificacion = (TextView) convertView
				.findViewById(R.id.fecha_modificacion);

		int resId = 0;
		CharSequence nombre_fich = e.fileName();
		CharSequence fecha = e.modified;

		if (e.isDir) {
			resId = R.drawable.ic_action_collection;
			if (e.icon.equalsIgnoreCase("subir_a")) {
				resId = R.drawable.ic_action_collapse;
				nombre_fich = "Subir a " + nombre_fich;
				if (nombre_fich.equals("Subir a ")) {
					nombre_fich = "Subir a bqBOX";  
				}
				fecha = "";
			}
		}
		// holder.imagen_generica.setImageResource(R.drawable.ic_action_collection);
		else {
			resId = R.drawable.ic_action_view_as_list;
		}
		holder.imagen_generica.setImageResource(resId);
		holder.nombre_fichero.setText(nombre_fich);
		holder.fecha_modificacion.setText(fecha);

		return convertView;
	}

	class EbookHolder {
		private ImageView imagen_generica;
		private TextView nombre_fichero;
		private TextView fecha_modificacion;
	}

}
