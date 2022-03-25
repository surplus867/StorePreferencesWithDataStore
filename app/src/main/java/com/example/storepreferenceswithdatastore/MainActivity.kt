package com.example.storepreferenceswithdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.UserManager
import com.example.storepreferenceswithdatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager(applicationContext)

        //save and get the user input
        binding.btnSave.setOnClickListener {
            storeUser()
        }

        /**
         *
         * This function  retrieves saved data
         * as soon as they are stored and even
         * after the app is closed and restored again
         */

        observeData()
    }

    private fun observeData() {

        /**getting and update the age
         * every time user age change it will be observed by userAgeFlow
         */

        userManager.userAgeFlow.asLiveData().observe(this) { age ->
            age?.let {
                binding.tvAge.text = "Age: $age"
            }
        }

        /**getting and update the age
         * every time user age change it will be observed by userNameFlow
         */

        userManager.usernameFlow.asLiveData().observe(this) { name->
            name?.let {
                binding.tvName.text = "Name: $name"
            }
        }

    }


    /**
     * this function save the data to
     * preferences , when we click on save button
     */

    private fun storeUser() {

        val name = binding.etName.text.toString()
        val age = binding.etAge.text.toString().toInt()

        /**
         * Store the values
         * Our class is a suspend function it's run only inside of coroutine scope
         */
        lifecycleScope.launch {
            userManager.storeUserData(age, name)
            Toast.makeText(this@MainActivity,
                "User Saved",
            Toast.LENGTH_SHORT
            ).show()

            //after save our user
            //we need to clear the input data in our edit text
            binding.etName.text.clear()
            binding.etAge.text.clear()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}