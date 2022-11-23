package com.example.projectetp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

class WallpapersViewModel:ViewModel() {

    private val firebaseRepository:FirebaseRepository= FirebaseRepository()

    private val wallpapersList:MutableLiveData<List<WallpapersModel>>by lazy {
        MutableLiveData<List<WallpapersModel>>().also {
            loadWallpapersData()
        }
    }

    fun getWallpapersList():LiveData<List<WallpapersModel>>{
        return wallpapersList
    }


    fun loadWallpapersData(){
    //load the data
        firebaseRepository.queryWallpapers().addOnCompleteListener{
            if(it.isSuccessful){
                val result =it.result
                if(result!!.isEmpty){
                    //data ended
                }
                else{
                    //ready to load the results
                    if(wallpapersList.value==null){
                        wallpapersList.value=result.toObjects(WallpapersModel::class.java)
                    }
                    else{
                     wallpapersList.value= wallpapersList.value!!.plus(result.toObjects(WallpapersModel::class.java))
                    }

                    //get the last image
                    val lastItem:DocumentSnapshot=result.documents[result.size()-1]
                    firebaseRepository.lastVisible=lastItem
                }
            }
            else{

            }
        }
}
}