package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class FragmentLogin : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        view.loginBtn.setOnClickListener{
            Login()
        }

        view.sign_up_option.setOnClickListener{
            signUp()
        }

        view.forgot_password.setOnClickListener{
            Toast.makeText(this.context, R.string.Not_Implemented, Toast.LENGTH_SHORT).show()
        }

        view.sign_up_option.getPaint().setUnderlineText(true)

        view.forgot_password.getPaint().setUnderlineText(true)

        return view
    }

    private fun Login() {
        val intent = Intent(this.context, ActivityCommunity::class.java).apply({})
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun signUp() {
        val newfragment = FragmentSignUp()
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.login_fragment, newfragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}