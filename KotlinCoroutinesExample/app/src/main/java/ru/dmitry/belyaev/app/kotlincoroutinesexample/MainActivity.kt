package ru.dmitry.belyaev.app.kotlincoroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.data.observe(this, Observer {
            showUI(it)
        })

    }

    private fun showUI(status: Status) {
        when(status) {
            is Status.Loading -> {
                text.visibility = View.GONE
                progress.visibility = View.VISIBLE
            }
            is Status.Success -> {
                text.visibility = View.VISIBLE
                progress.visibility = View.GONE
                val user = status.result
                text.text = "username: ${user.username}\n email: ${user.email}\n"
            }
            is Status.Error -> {
                text.visibility = View.VISIBLE
                progress.visibility = View.GONE
                val errorMessage = status.message
                text.text = "Error: $errorMessage"
            }
        }

    }

}
