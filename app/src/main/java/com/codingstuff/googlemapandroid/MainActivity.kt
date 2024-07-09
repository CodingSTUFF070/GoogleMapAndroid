package com.codingstuff.googlemapandroid

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var radioGroup: RadioGroup
    private var map: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        radioGroup = findViewById(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, itemId: Int ->
            when (itemId) {
                R.id.normalBtn -> {
                    map?.mapType = GoogleMap.MAP_TYPE_NORMAL
                }

                R.id.satelliteBtn -> {
                    map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
                }

                R.id.hybridBtn -> {
                    map?.mapType = GoogleMap.MAP_TYPE_HYBRID
                }

                R.id.terrainBtn -> {
                    map?.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }

            }
        }
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_styles))
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL

        map?.uiSettings?.isZoomControlsEnabled = true
        map?.uiSettings?.isMapToolbarEnabled = true

        val latLng = LatLng(28.7050, 77.1025)
        val latLng2 = LatLng(28.7050, 77.1030)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19f))


        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Location1")
        markerOptions.snippet("This is my home !!")
        //  markerOptions.alpha(3f)
        markerOptions.draggable(true)
        //  markerOptions.rotation(12f)
        markerOptions.flat(true)
        markerOptions.visible(true)
        //   markerOptions.infoWindowAnchor(-3f,2f)
        //    markerOptions.anchor(2f,2f)
        markerOptions.zIndex(0f)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        Log.i("markerValues", "onMapReady: ${markerOptions.position}")
        map?.addMarker(markerOptions)

        val markerOptions2 = MarkerOptions()
        markerOptions2.position(latLng2)
        markerOptions2.title("Location2")
        markerOptions2.zIndex(1f)
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        map?.addMarker(markerOptions2)
    }
}