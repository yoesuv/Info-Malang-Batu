package com.yoesuv.infomalangbatu.menu.listplace.views

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

class FragmentListPlace : Fragment(), MenuProvider {

    private var binding: FragmentListplaceBinding? = null
    private val viewModel: FragmentListPlaceViewModel by viewModels()
    private var adapter: ListPlaceAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = FragmentListplaceBinding.inflate(inflater, container, false)
            binding?.listplace = viewModel
        }
        binding?.lifecycleOwner = viewLifecycleOwner
        setupRecycler()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupProperties(requireContext())
        loadPlace(PlaceLocation.ALL)
    }

    private fun setupRecycler() {
        adapter = ListPlaceAdapter {
            onItemClick(it)
        }
        binding?.recyclerViewListPlace?.adapter = adapter
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_place, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.listSemua -> loadPlace(PlaceLocation.ALL)
            R.id.listKabMalang -> loadPlace(PlaceLocation.KAB_MALANG)
            R.id.listKotaBatu -> loadPlace(PlaceLocation.KOTA_BATU)
            R.id.listKotaMalang -> loadPlace(PlaceLocation.KOTA_MALANG)
        }
        menuItem.isChecked = true
        return false
    }

    private fun loadPlace(place: PlaceLocation) {
        if (place == PlaceLocation.ALL) {
            viewModel.getListPlaceAll()?.observe(viewLifecycleOwner) {
                onListDataChange(it)
            }
        } else {
            viewModel.getListPlace(place)?.observe(viewLifecycleOwner) {
                onListDataChange(it)
            }
        }
    }

    private fun onListDataChange(listPlace: List<PlaceModel>) {
        if (listPlace.isNotEmpty()) {
            adapter?.submitList(listPlace)
            adapter?.notifyItemRangeChanged(0, listPlace.size)
        }
    }

    private fun onItemClick(placeModel: PlaceModel) {
        val action = FragmentListPlaceDirections.actionToListPlaceDetail()
        action.dataDetailListPlace = placeModel
        findNavController().navigate(action)
    }

}