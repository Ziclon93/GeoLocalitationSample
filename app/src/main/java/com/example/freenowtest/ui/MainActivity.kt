package com.example.freenowtest.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.freenowtest.R
import com.example.freenowtest.databinding.ActivityMainBinding
import com.example.freenowtest.domain.model.Vehicle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        binding.bottomNavigatorView.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_main,
            )
        )
        binding.bottomNavigatorView.setOnItemSelectedListener { item ->
            val navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_main,
            )
            when (item.itemId) {
                R.id.fragment_vehicle_catalog_option -> {
                    if (binding.bottomNavigatorView.selectedItemId != R.id.fragment_vehicle_catalog_option) {
                        navController.navigate(R.id.fragment_vehicle_catalog)
                        true
                    } else false
                }
                R.id.fragment_map_option -> {
                    if (binding.bottomNavigatorView.selectedItemId != R.id.fragment_map_option) {
                        navController.navigate(R.id.fragment_map)
                        true
                    } else false
                }
                else -> false
            }
        }
    }

    fun showApiErrorDialog(onRetryButton: (() -> Unit)) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_dialog_api_error_message))
        builder.apply {
            setCancelable(false)
            this.setPositiveButton(getString(R.string.retry)) { dialog, _ ->
                onRetryButton()
                dialog.dismiss()
            }
        }
        builder.create().show()
    }

    fun showVehicleInMap(vehicle: Vehicle) {
        val bundle = bundleOf(getString(R.string.vehicle) to vehicle)
        Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(
            R.id.fragment_map,
            bundle,
        )
        binding.bottomNavigatorView.menu.findItem(R.id.fragment_map_option).isChecked = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when (Navigation.findNavController(
            this, R.id.nav_host_fragment_content_main
        ).currentDestination?.id!!) {
            R.id.fragment_vehicle_catalog -> binding.bottomNavigatorView.menu.findItem(R.id.fragment_vehicle_catalog_option).isChecked =
                true
            R.id.fragment_map -> binding.bottomNavigatorView.menu.findItem(R.id.fragment_map_option).isChecked =
                true
        }
    }

}