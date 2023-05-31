package com.young.module.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = "/young/module/login/LoginActivity")
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val textView=findViewById<TextView>(R.id.textView)
        textView.setOnClickListener {
        Log.i("============","==========LoginActivity=======")}
    }
}