package com.segovia_jorge.a02_crud_vistas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.segovia_jorge.a02_crud_vistas.databinding.ActivityGoogleMapsBinding

class googleMaps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding
    private var estudios: List<EstudioVideojuego> = emptyList()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Obtener los estudios almacenados desde la base de datos
        val dbHelper = SqliteHelper(this)
        estudios = dbHelper.obtenerTodosEstudios()

        binding.btnIrSiguiente.setOnClickListener {
            irALaSiguienteUbicacion()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Mover el mapa a la ubicación del primer estudio si existen estudios
        if (estudios.isNotEmpty()) {
            moverMapaAPais(estudios[currentIndex])
        } else {
            Toast.makeText(this, "No existen estudios almacenados", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moverMapaAPais(estudio: EstudioVideojuego) {
        val coordenadas = LatLng(estudio.latitudEstudio, estudio.longitudEstudio)
        mMap.clear() // Limpiar los marcadores anteriores
        mMap.addMarker(MarkerOptions().position(coordenadas).title("Ubicación: ${estudio.nombreEstudio}"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 10f))
    }

    private fun irALaSiguienteUbicacion() {
        if (estudios.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % estudios.size
            val estudio = estudios[currentIndex]
            moverMapaAPais(estudio)
        } else {
            Toast.makeText(this, "No existen estudios almacenados", Toast.LENGTH_SHORT).show()
        }
    }
}
