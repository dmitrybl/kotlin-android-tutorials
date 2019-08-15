package com.elegion.myfirstapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_single_fragment)
        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, AuthFragment.newInstance())
                    .commit()
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }
}