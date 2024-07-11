package com.codingstuff.googlemapandroid

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
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
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19f))

        val markerOption = MarkerOptions()
        markerOption.position(latLng)
        markerOption.title("Location")
        markerOption.snippet("This is my location")
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromDrawable(R.drawable.location)))
        map?.addMarker(markerOption)
    }

    private fun getBitmapFromDrawable(resId: Int): Bitmap? {
        var bitmap : Bitmap? = null
        val drawable = ResourcesCompat.getDrawable(resources , resId , null)
        if (drawable != null){
            bitmap = Bitmap.createBitmap(150 , 150 , Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0,0,canvas.width , canvas.height)
            drawable.draw(canvas)
        }
        return bitmap
    }
}