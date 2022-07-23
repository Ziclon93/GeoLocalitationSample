package com.example.freenowtest.ui.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.example.freenowtest.databinding.FragmentVehicleCatalogBinding
import com.example.freenowtest.domain.model.Vehicle
import com.example.freenowtest.ui.MainActivity
import com.example.freenowtest.ui.catalog.adapter.OnVehicleCatalogAdapterListener
import com.example.freenowtest.ui.catalog.adapter.VehicleCatalogAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VehicleCatalogFragment : Fragment(), OnVehicleCatalogAdapterListener {

    private var adapter: VehicleCatalogAdapter? = null
    private val vm: VehicleCatalogViewModel by viewModels()
    private var _binding: FragmentVehicleCatalogBinding? = null

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = VehicleCatalogAdapter(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVehicleCatalogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setupAdapter()

        vm.vehicles.observe(viewLifecycleOwner, { vehicleList ->
            if (vehicleList != null) adapter?.addData(vehicleList)
            else (activity as MainActivity).showApiErrorDialog { vm.loadVehicleCatalog() }
        })

        vm.isLoading.observe(viewLifecycleOwner, { isLoading ->
            binding.vehiclesProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        vm.loadVehicleCatalog()
        return binding.root
    }

    private fun setupAdapter() {
        binding.vehiclesRecyclerView.isNestedScrollingEnabled = false
        binding.vehiclesRecyclerView.setHasFixedSize(true)
        binding.vehiclesRecyclerView.adapter = adapter
    }

    override fun showVehicleInMap(vehicle: Vehicle) {
        (activity as MainActivity).showVehicleInMap(vehicle)
    }

    override fun showViewHolder(vehicleImg: ImageView, vehicle: Vehicle) {
        vm.loadVehicleResourcesUseCase.showVehicleImage(vehicleImg, vehicle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}