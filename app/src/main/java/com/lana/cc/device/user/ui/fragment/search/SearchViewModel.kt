package com.lana.cc.device.user.ui.fragment.search

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lana.cc.device.user.manager.api.RubbishService
import com.lana.cc.device.user.model.api.ResultModel
import com.lana.cc.device.user.model.api.search.SearchKeyConclusion
import com.lana.cc.device.user.ui.base.BaseViewModel
import com.lana.cc.device.user.util.switchThread
import io.reactivex.Single
import io.reactivex.SingleTransformer
import org.kodein.di.generic.instance


class SearchViewModel(application: Application) : BaseViewModel(application) {

    private val rubbishService by instance<RubbishService>()

    val searchList = MutableLiveData(emptyList<SearchKeyConclusion>())

    val searchKey = MutableLiveData("")

    //位置
    val city = MutableLiveData("")

    //获取城市定位
     fun getCity() {
        city.value = "成都"
    }

    fun searchKey(key: String) {
        rubbishService.searchClassification(key)
            .switchThread()
            .autoProgressDialog()
            .doOnSuccess {
                searchList.postValue(it.data)
            }.catchApiError()
            .bindLife()
    }


}