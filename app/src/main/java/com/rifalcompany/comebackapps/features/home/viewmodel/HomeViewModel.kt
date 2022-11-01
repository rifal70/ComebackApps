package com.rifalcompany.comebackapps.features.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.rifalcompany.comebackapps.di.DaggerAppComponent
import com.rifalcompany.comebackapps.features.home.data.repository.HomeRepository
import com.rifalcompany.comebackapps.features.home.model.PokemonModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val pokemonModel = MutableLiveData<PokemonModel>()

    private val compositeDisposable = CompositeDisposable()
    private val isConnectionTimeout = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean?>? = MutableLiveData<Boolean?>()

    //buat dapetin contextnya
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext


    @Inject
    lateinit var repository: HomeRepository

    init {
        DaggerAppComponent.create().injectRepository(this)
    }

    fun getPokemonVM(id: Int) {
        compositeDisposable.add(
            repository.getPokemonRepository(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PokemonModel>() {
                    override fun onSuccess(t: PokemonModel) {
                            pokemonModel.value = t
                            isLoading?.value = false
                    }

                    override fun onError(e: Throwable) {
                        when (e) {
                            is HttpException -> {
                                isLoading?.value = false
                                val errorBody = e.response()?.errorBody()
                                Toast.makeText(context, ""+e.message()+" " +e.response(), Toast.LENGTH_SHORT).show()
                                val gson = Gson()
                                val error = gson.fromJson(
                                    errorBody?.string(),
                                    PokemonModel::class.java
                                )
                                pokemonModel.value = error
                                Log.d("Pokemon", "onError: $error + ${e.response()!!.code()}")
                            }
                            is SocketException, is UnknownHostException, is SocketTimeoutException -> {
                                isConnectionTimeout.postValue(true)
                                isLoading?.value = false
                            }
                            else -> {

                            }
                        }
                    }

                })
        )
    }


    fun getPokemonModel(): MutableLiveData<PokemonModel> {
        return pokemonModel
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}