package com.example.projectetp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment(),(WallpapersModel)->Unit {

    private val firebaseRepository=FirebaseRepository()


    private var wallpapersList:List<WallpapersModel> = ArrayList()
    private val wallpapersListAdapter:WallpapersListAdapter= WallpapersListAdapter(wallpapersList,this)

    private var isLoading:Boolean=true

    private val wallpapersViewModel:WallpapersViewModel by viewModels()

  private lateinit var wallpapers_list_view:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

//    wallpapers_list_view=findViewById
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //ACtion bar
//        (activity as AppCompatActivity).setSupportActionBar(main_toolbar)


    if(firebaseRepository.getUser()==null) {
    //user not logged

    }
       wallpapers_list_view.setHasFixedSize(true)
        wallpapers_list_view.layoutManager=GridLayoutManager(context,3)
        wallpapers_list_view.adapter=wallpapersListAdapter

        //bottom of recView
        wallpapers_list_view.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    //reach at bottom
                    if(!isLoading){
                        //load next page
                        wallpapersViewModel.loadWallpapersData()
                        isLoading=true
                    }
                }
            }
        })


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        wallpapersViewModel.getWallpapersList().observe(viewLifecycleOwner, Observer {
            wallpapersList=it
            wallpapersListAdapter.wallpapersList=wallpapersList
            wallpapersListAdapter.notifyDataSetChanged()

        //load complete
            isLoading=false
        })
    }

    override fun invoke(wallpaper:WallpapersModel){
    //upon click of item navigate to details frag
        val action=HomeFragmentDirections.actionHomeFragment3ToDetailFragment2(wallpaper.image)
        findNavController().navigate(action)
}
}