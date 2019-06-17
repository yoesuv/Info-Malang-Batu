package com.yoesuv.infomalangbatu.menu.listplace.views

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.AppConstants
import com.yoesuv.infomalangbatu.databinding.FragmentListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.adapters.ListPlaceAdapter
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentListPlaceViewModel

class FragmentListPlace: Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return FragmentListPlace()
        }
    }

    private lateinit var viewModel: FragmentListPlaceViewModel
    private lateinit var binding: FragmentListplaceBinding
    private lateinit var adapter: ListPlaceAdapter
    private var listPlace: MutableList<PlaceModel> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_listplace, container, false)
        viewModel = ViewModelProviders.of(this).get(FragmentListPlaceViewModel::class.java)
        binding.listplace = viewModel

        setupRecycler()
        setupSwipeRefresh()

        viewModel.getListPlace()
        viewModel.listPlaceResponse.observe(this, Observer {
            onListDataChange(it)
        })
        viewModel.error.observe(this, Observer {
            Log.e(AppConstants.TAG_ERROR,"FragmentListPlace # error ${it?.message}")
            it?.printStackTrace()
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_list_place, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.listSemua -> viewModel.getListPlace()
            R.id.listKabMalang -> viewModel.getListPlaceKabMalang()
            R.id.listKotaBatu -> viewModel.getListPlaceKotaBatu()
            R.id.listKotaMalang -> viewModel.getListPlaceKotaMalang()
        }
        item?.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecycler(){
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewListPlace.layoutManager = layoutManager
        adapter = ListPlaceAdapter(activity!!, listPlace)
        binding.recyclerViewListPlace.adapter = adapter
    }

    private fun setupSwipeRefresh(){
        binding.swipeRefreshLayoutListPlace.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        binding.swipeRefreshLayoutListPlace.setOnRefreshListener {
            binding.swipeRefreshLayoutListPlace.isRefreshing = false
            activity?.invalidateOptionsMenu()
            viewModel.getListPlace()
        }
    }

    private fun onListDataChange(listPlace: MutableList<PlaceModel>?){
        if(listPlace?.isNotEmpty()!!){
            this.listPlace.clear()
            this.listPlace.addAll(listPlace)
            binding.recyclerViewListPlace.post{
                adapter.notifyDataSetChanged()
                binding.recyclerViewListPlace.scrollToPosition(0)
            }
        }
    }

}