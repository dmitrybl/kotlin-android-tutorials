package com.elegion.myfirstapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.voice.VoiceInteractionSession
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RegistrationFragment : Fragment() {

    companion object {
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }

    }

    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var passwordAgain: EditText
    private lateinit var registration: Button

    private val mOnRegistrationClickListener = View.OnClickListener {
        if(isInputValid()) {
            val user = User(email.text.toString(), name.text.toString(), password.text.toString())

            val request = Request.Builder()
                    .url(BuildConfig.SERVER_URL + "/registration/")
                    .post(RequestBody.create(JSON, Gson().toJson(user)))
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                val handler = Handler(activity.mainLooper)

                override fun onFailure(call: Call, e: IOException) {
                    handler.post {
                        showMessage(R.string.request_error)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    handler.post {
                        if(response.isSuccessful) {
                            showMessage(R.string.response_code_204)
                            fragmentManager.popBackStack()
                        } else {

                        }
                    }
                }

            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fr_registration, container, false)

        email = view.findViewById(R.id.etEmail)
        name = view.findViewById(R.id.etName)
        password = view.findViewById(R.id.etPassword)
        passwordAgain = view.findViewById(R.id.tvPasswordAgain)
        registration = view.findViewById(R.id.btnRegistration)

        registration.setOnClickListener(mOnRegistrationClickListener)

        return view
    }

    private fun isInputValid(): Boolean {
        return isEmailValid(email.text.toString())
                && !TextUtils.isEmpty(name.text)
                && isPasswordsValid()
    }

    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun isPasswordsValid(): Boolean {
        val password = password.text.toString()
        val passwordAgain = passwordAgain.text.toString()

        return password == passwordAgain
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain)
    }

    private fun showMessage(stringRes: Int) {
        Toast.makeText(activity, stringRes, Toast.LENGTH_LONG).show()
    }
}
