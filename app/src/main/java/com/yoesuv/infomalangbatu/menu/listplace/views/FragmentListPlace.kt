package com.yoesuv.infomalangbatu.menu.listplace.views

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databinding.FragmentListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.adapters.ListPlaceAdapter
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentListPlaceViewModel

class FragmentListPlace: Fragment(), MenuProvider {

    private lateinit var binding: FragmentListplaceBinding
    private val viewModel: FragmentListPlaceViewModel by viewModels()
    private lateinit var adapter: ListPlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListplaceBinding.inflate(inflater, container, false)
        binding.listplace = viewModel

        setupRecycler()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupProperties(context)
        viewModel.getListPlace(PlaceLocation.ALL)
        viewModel.listPlaceResponse.observe(viewLifecycleOwner) {
            onListDataChange(it)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        activity?.recreate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListPlace(PlaceLocation.ALL)
    }

    private fun setupRecycler(){
        adapter = ListPlaceAdapter {
            onItemClick(it)
        }
        binding.recyclerViewListPlace.adapter = adapter
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_place, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.listSemua -> viewModel.getListPlace(PlaceLocation.ALL)
            R.id.listKabMalang -> viewModel.getListPlace(PlaceLocation.KAB_MALANG)
            R.id.listKotaBatu -> viewModel.getListPlace(PlaceLocation.KOTA_BATU)
            R.id.listKotaMalang -> viewModel.getListPlace(PlaceLocation.KOTA_MALANG)
        }
        menuItem.isChecked = true
        return false
    }

    private fun onListDataChange(listPlace: MutableList<PlaceModel>?){
        listPlace?.isNotEmpty()?.let { isNotEmpty ->
            if (isNotEmpty) {
                adapter.submitList(listPlace)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun onItemClick(placeModel: PlaceModel) {
        val action = FragmentListPlaceDirections.actionToListPlaceDetail()
        action.dataDetailListPlace = placeModel
        findNavController().navigate(action)
    }

}