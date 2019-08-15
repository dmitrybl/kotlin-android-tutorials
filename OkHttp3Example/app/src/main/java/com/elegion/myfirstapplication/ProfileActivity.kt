package com.elegion.myfirstapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private lateinit var email: TextView
    private lateinit var name: TextView
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_profile)

        email = findViewById(R.id.tvEmail)
        name = findViewById(R.id.tvName)

        val bundle = intent.extras
        user = bundle!!.get(USER_KEY) as User
        email.text = user.email
        name.text = user.name
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionLogout -> {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}