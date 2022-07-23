package com.example.freenowtest.ui.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.freenowtest.R
import com.example.freenowtest.databinding.HolderVehicleBinding
import com.example.freenowtest.domain.model.Vehicle
import com.example.freenowtest.ui.catalog.VehicleViewModel

internal class VehicleCatalogAdapter(val mListener: OnVehicleCatalogAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val vehicles: MutableList<Vehicle> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderVehicleBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_vehicle, parent, false
        )
        return VehicleViewHolder(holderVehicleBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VehicleViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): Vehicle {
        return vehicles[position]
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    fun addData(vehicleList: List<Vehicle>) {
        this.vehicles.clear()
        this.vehicles.addAll(vehicleList)
        notifyDataSetChanged()
    }

    inner class VehicleViewHolder(
        private val dataBinding: ViewDataBinding
    ) : RecyclerView.ViewHolder(dataBinding.root) {

        fun onBind(vehicle: Vehicle) {
            val holderVehicleBinding = dataBinding as HolderVehicleBinding
            holderVehicleBinding.viewmodel = VehicleViewModel(vehicle)
            mListener.showViewHolder(holderVehicleBinding.vehicleImg, vehicle)
            itemView.setOnClickListener {
                mListener.showVehicleInMap(vehicle)
            }
        }
    }
}
