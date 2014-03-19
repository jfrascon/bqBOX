package com.jfrascon.bqbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PortadaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portada);

		ImageView imv_portada = (ImageView) findViewById(R.id.imv_portada);
		TextView tv_titulo_libro = (TextView) findViewById(R.id.nombre_libro);
		Bundle b = getIntent().getExtras();
		Book libro = null;

		try {

			FileInputStream fis = new FileInputStream(new File(
					b.getString("ruta_libro")));
			libro = (new EpubReader()).readEpub(fis);
			Resource res = libro.getCoverImage();

			if (res != null) {

				Bitmap bm_portada = BitmapFactory.decodeStream(res
						.getInputStream());
				imv_portada.setImageBitmap(bm_portada);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		String titulo_libro = libro.getTitle();

		if (titulo_libro == null) {

			titulo_libro = "Título no disponible";
		}

		tv_titulo_libro.setText(titulo_libro);
	}
}
