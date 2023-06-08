package com.young.cn

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class TestKotlinActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), activityResultRegistry
        ) { result ->
            val contactUri = result.data?.data
            contactUri?.let {
                contentResolver.query(contactUri, null, null, null, null).use {
                        cursor ->
                    if (cursor?.moveToFirst() == true) {
                        var number = ""
                        var name = ""
                        val numberIndex =
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                        if (numberIndex != -1) {
                            number = cursor.getString(numberIndex)
                            if (number.isNotEmpty()) {
                                Log.i("TestKotlinActivity===","===number===${number.replace(" ", "")}")
//                                binding.etPhone.setText(number.replace(" ", ""))
                            }
                        }

                        val nameIndex =
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        if (nameIndex != -1) {
                            name = cursor.getString(nameIndex)
                        }
                        Log.i("TestKotlinActivity===", "$name:$number")
                    }
                    cursor?.close()
                }
            }
        }.launch(Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI))
        setContentView(R.layout.activity_test_kotlin)
    }




}