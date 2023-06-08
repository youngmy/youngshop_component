package com.young.module.demo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alibaba.android.arouter.facade.annotation.Route
import com.young.base.activity.BaseActivity
import com.young.commonconfig.helper.dataStoreActivity
import com.young.helper.DataStoreHelper
import com.young.module.demo.databinding.ActivityDataStoreBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@Route(path = dataStoreActivity)
class DataStoreActivity :
    BaseActivity<ActivityDataStoreBinding>(R.layout.activity_data_store) {

    //定义dataStore
    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = "Study")


    override fun initData() {
        //存数据
        mBinding.btnPut.setOnClickListener {
//            lifecycleScope.launch {
//                put()
//            }
            DataStoreHelper.putData("name","居家")
        }
        //取数据
        mBinding.btnGet.setOnClickListener {
//            val data = get()
            val data =DataStoreHelper.getData("name","办公")
            mBinding.textView.text=data
        }
        mBinding.btnClear.setOnClickListener {
//            lifecycleScope.launch {
//                dataStore.edit {
//                    it.clear()
//                }
//            }
            DataStoreHelper.clearData()
        }
    }

    //定义要操作的key
    private val key = stringPreferencesKey("name1")

    private suspend fun put() = dataStore.edit {
        it[key] = "疫情"
    }

    private fun get() = runBlocking {
        return@runBlocking dataStore.data.map { it[key] ?: "新冠" }.first()
    }


}