package com.afflyas.fwcwallpapers.ui.listimages

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment

import com.afflyas.fwcwallpapers.R
import com.afflyas.fwcwallpapers.core.MainActivity
import com.afflyas.fwcwallpapers.databinding.FragmentListImagesBinding
import com.afflyas.fwcwallpapers.repository.PixabayImage
import com.afflyas.fwcwallpapers.ui.common.ItemClickCallback
import com.afflyas.fwcwallpapers.ui.common.RetryCallback
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager
import com.afflyas.fwcwallpapers.R.id.recyclerView



class ListImagesFragment : Fragment(), RetryCallback, ItemClickCallback {

    private lateinit var fragmentBinding: FragmentListImagesBinding

    @Inject
    lateinit var mainActivity: MainActivity

    /**
     * Custom factory to enable injecting into ViewModel
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewModel: ListImagesViewModel

    private var listAdapter: ListImagesAdapter? = null

    /**
     * Enable injections
     */
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_images, container, false)
        fragmentBinding.callback = this

        //mainActivity.setSupportActionBar(fragmentBinding.toolbar)
        //setHasOptionsMenu(true)

        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListImagesViewModel::class.java)
        subscribeUI()
    }

    private fun subscribeUI() {




        fragmentBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {
                mViewModel.loadImages(p0)
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean { return false }
        })

        listAdapter = ListImagesAdapter(this)
        fragmentBinding.recyclerView.layoutManager = GridLayoutManager(mainActivity, 2)
        fragmentBinding.recyclerView.adapter = listAdapter


        mViewModel.searchResult.observe(this, Observer {
            fragmentBinding.response = it
            listAdapter?.setImagesList(it.getData())
        })

        fragmentBinding.swipeRefresh.setOnRefreshListener {
            mViewModel.refresh()
        }

        if(mViewModel.term.value == null){
            mViewModel.term.value = "Russia World Cup"
        }

        if (mViewModel.searchResult.value == null) {
            mViewModel.loadImages(mViewModel.term.value)
        }
    }

    override fun retry() {
        mViewModel.refresh()
    }

    override fun onItemClick(pixabayImage: PixabayImage) {
        val action = ListImagesFragmentDirections.actionListImagesFragmentToImageFragment(pixabayImage)
        NavHostFragment.findNavController(this).navigate(action)
    }

}
