package com.jfrascon.bqbox;

import java.util.ArrayList;
import java.util.List;

import com.dropbox.client2.DropboxAPI.Entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		holder.imagen_generica = (ImageView) convertView.findViewById(R.id.imagen_generica);
		holder.nombre_fichero = (TextView) convertView.findViewById(R.id.nombre_fichero);
		holder.fecha_modificacion = (TextView) convertView.findViewById(R.id.fecha_modificacion);
		
		if(e.isDir)
			holder.imagen_generica.setImageResource(R.drawable.ic_action_collection);
		else
			holder.imagen_generica.setImageResource(R.drawable.ic_action_view_as_list);
		
		holder.nombre_fichero.setText(e.fileName());
		holder.fecha_modificacion.setText(e.modified);
		
		return convertView;
	}
	
	
	class EbookHolder{
		private ImageView imagen_generica;
		private TextView nombre_fichero;
		private TextView fecha_modificacion;
	} 
	

}
