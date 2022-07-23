package com.example.freenowtest.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.freenowtest.R
import com.example.freenowtest.databinding.FragmentMapBinding
import com.example.freenowtest.domain.model.Vehicle
import com.example.freenowtest.util.Constants.HAMBURG_LAT_P1
import com.example.freenowtest.util.Constants.HAMBURG_LAT_P2
import com.example.freenowtest.util.Constants.HAMBURG_LON_P1
import com.example.freenowtest.util.Constants.HAMBURG_LON_P2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.content.ContextCompat
import com.example.freenowtest.ui.MainActivity
import com.example.freenowtest.util.toBitMap
import com.example.freenowtest.util.withDelay
import com.google.android.gms.maps.model.Marker


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private val vm: MapViewModel by viewModels()
    private var _binding: FragmentMapBinding? = null
    private var mMap: GoogleMap? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        setupMap()
        binding.lifecycleOwner = viewLifecycleOwner

        vm.vehicleMap.observe(
            viewLifecycleOwner,
            {
                if (it != null) setMapMarkers(it)
                else (activity as MainActivity).showApiErrorDialog { vm.loadVehicleCatalog() }
            },
        )
        return binding.root
    }


    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val hamburgBounds = LatLngBounds(
            LatLng(HAMBURG_LAT_P2, HAMBURG_LON_P1),
            LatLng(HAMBURG_LAT_P1, HAMBURG_LON_P2),
        )
        mMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(hamburgBounds, 100))

        vm.loadVehicleCatalog()
    }

    private fun setMapMarkers(vehicleMap: Map<Vehicle, Marker?>) {
        vehicleMap.forEach { (vehicle, _) ->
            mMap?.let {
                val marker = it.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            vehicle.coordinate.latitude.toDouble(),
                            vehicle.coordinate.longitude.toDouble()
                        )
                    ).title(vehicle.fleetType.toString())
                        .infoWindowAnchor(0.5f, 0.5f)
                        .anchor(0.5f, 0.5f)
                        .rotation(vehicle.heading)
                        .title(vehicle.fleetType.name)
                )
                marker?.let { m -> vm.addMarker(vehicle, m) }
                marker?.changeState(false)
            }
        }

        mMap?.setOnMarkerClickListener { marker ->
            vm.selectedMarker?.changeState(false)
            marker.changeState(true)
            marker.showInfoWindow()
            val loc = LatLng(
                marker.position.latitude,
                marker.position.longitude,
            )
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 12f), 500, null)
            vm.selectedMarker = marker
            true
        }

        mMap?.let {
            arguments?.getParcelable<Vehicle>(getString(R.string.vehicle))?.let {
                { zoomToVehicle(it) }.withDelay(500)
            }
        }
    }

    private fun Marker.changeState(selected: Boolean) {
        this.setIcon(context?.let { ctx ->
            ContextCompat.getDrawable(
                ctx,
                if (selected) R.drawable.ic_map_marker_selected else R.drawable.ic_map_marker_not_selected

            )?.toBitMap()
        })
    }

    private fun zoomToVehicle(vehicle: Vehicle) {
        val vehicleLocation = LatLng(
            vehicle.coordinate.latitude.toDouble(),
            vehicle.coordinate.longitude.toDouble(),
        )
        val marker = vm.vehicleMap.value?.get(vehicle)
        marker?.changeState(true)
        marker?.showInfoWindow()
        marker?.let { vm.selectedMarker = marker }
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(vehicleLocation, 14f), 1000, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}