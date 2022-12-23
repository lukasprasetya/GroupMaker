package com.binar.groupmaker.ui.member

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.groupmaker.constant.CommonConstant
import com.binar.groupmaker.data.repository.LocalRepository
import com.binar.groupmaker.data.room.entity.Member
import com.binar.groupmaker.wrapper.Resource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


typealias InitialDataResultType = Resource<List<Member>>


class MemberViewModel(private val repository: LocalRepository) : ViewModel() {

    var memberId: Int = CommonConstant.UNSET_ID

    val initialDataResult = MutableLiveData<InitialDataResultType>()

    val getDataResult = MutableLiveData<Resource<List<Member>>>()
    val insertResult = MutableLiveData<Resource<Number>>()
    val deleteResult = MutableLiveData<Resource<Number>>()
    val updateResult = MutableLiveData<Resource<Number>>()



    fun getAllGroupByGroup(id : String){
        viewModelScope.launch(Dispatchers.IO){
            initialDataResult.postValue(Resource.Loading())
            delay(1000)
            initialDataResult.postValue(repository.getAllGroupByGroup(id))
        }
    }





    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO){
            initialDataResult.postValue(Resource.Loading())
            delay(1000)
            initialDataResult.postValue(repository.getAllMember())
        }
    }

    fun deleteMember(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteResult.postValue(Resource.Loading())
            delay(1000)
            deleteResult.postValue(repository.deleteMember(member))
        }
    }

    fun updateMember(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            updateResult.postValue(Resource.Loading())
            delay(1000)
            updateResult.postValue(repository.updateMember(member))
        }
    }

    fun insertMember(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            insertResult.postValue(Resource.Loading())
            delay(1000)
            insertResult.postValue(repository.insertMember(member))
        }
    }

    fun setIntentData(intent: Intent) {
        memberId = intent.getIntExtra(CommonConstant.EXTRAS_ID_NOTE, CommonConstant.UNSET_ID)
    }

}


