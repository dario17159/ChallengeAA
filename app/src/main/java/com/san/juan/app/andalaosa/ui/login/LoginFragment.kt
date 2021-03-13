package com.san.juan.app.andalaosa.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.san.juan.app.andalaosa.R
import com.san.juan.app.andalaosa.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {


    companion object{
        private const val RC_GI = 123
    }

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnGoogleLogin.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnGoogleLogin.isEnabled = false
            loginGoogle()
        }

        viewModel.authStatus.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            binding.btnGoogleLogin.isEnabled = true
            launchHomFragment(it)
        })
    }

    private fun loginGoogle(){
        val googleIntent = viewModel.client.signInIntent
        startActivityForResult(googleIntent, RC_GI)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(RC_GI == requestCode && resultCode == -1){
                viewModel.loginWithGogle(data)
        }else {
            binding.progressBar.visibility = View.GONE
            binding.btnGoogleLogin.isEnabled = true
        }
    }


    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        launchHomFragment(currentUser)
    }


    private fun launchHomFragment(currentUser: FirebaseUser?){
        currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

}