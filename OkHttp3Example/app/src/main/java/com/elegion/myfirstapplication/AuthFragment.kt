package com.elegion.myfirstapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(): AuthFragment {
            val args = Bundle()

            val fragment = AuthFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var enter: Button
    private lateinit var register: Button


    private val mOnEnterClickListener = View.OnClickListener {
        if(isEmailValid() && isPasswordValid()) {
            val request = Request.Builder()
                    .url(BuildConfig.SERVER_URL + "/user/")
                    .build()

            val client = ApiUtils.getBasicAuthClient(
                    email.text.toString(),
                    password.text.toString(),
                    true
            )

            client?.newCall(request)?.enqueue(object: Callback {

                val mainHandler  = Handler(activity.mainLooper)

                override fun onResponse(call: Call, response: Response) {
                    mainHandler.post {
                        if (!response.isSuccessful) {
                            showMessage(R.string.auth_error)
                        } else {
                            val gson = Gson()
                            val json = gson.fromJson(response.body?.string(), JsonObject::class.java)
                            val user = gson.fromJson(json.get("data"), User::class.java)
                            val startProfileIntent = Intent(activity, ProfileActivity::class.java)
                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, user)
                            startActivity(startProfileIntent)
                            activity.finish()
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    mainHandler.post {
                        showMessage(R.string.request_error)
                    }
                }

            })
        }
    }

    private val mOnRegisterClickListener = View.OnClickListener {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                .addToBackStack(RegistrationFragment::class.java.name)
                .commit()
    }

    private fun isEmailValid(): Boolean {
        return !TextUtils.isEmpty(email.text)
    }

    private fun isPasswordValid(): Boolean {
        return !TextUtils.isEmpty(password.text)
    }

    private fun showMessage(stringRes: Int) {
        Toast.makeText(activity, stringRes, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fr_auth, container, false)

        email = v.findViewById(R.id.etEmail)
        password = v.findViewById(R.id.etPassword)
        enter = v.findViewById(R.id.buttonEnter)
        register = v.findViewById(R.id.buttonRegister)

        enter.setOnClickListener(mOnEnterClickListener)
        register.setOnClickListener(mOnRegisterClickListener)

        return v
    }
}
