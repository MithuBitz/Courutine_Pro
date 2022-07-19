package com.example.courutineexe

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    // Create a var for the progress bar dialog as nullable
    var customProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute: Button = findViewById(R.id.backgroundExecutionBtn)
        btnExecute.setOnClickListener {
            // run the custom dialog method to show the dialog
            showTheDialog()
            //call the suspend function so it will run on background
            //launch that background process or function with lifecycle Scope
            lifecycleScope.launch {
                executionBcg()
            }
        }
    }

    //Create a suspend function which we want to execute in background
    private suspend fun executionBcg() {
        //call withContext method with Dispatcher.IO thread
        withContext(Dispatchers.IO) {
            //withContext method run a process in background and back with the result in UI thread when it completed its process
            for(i in 1..3000000) {
             Log.e("Delay: ", "" + i)
            }

            //runOnUiThread is used to execute any UI related task in Dispatcher.IO or Input/Output thread
            runOnUiThread {
                //call the cancel progress dialog to know the user that the background process is complete
                cancelDialog()
                Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
            }
        }
    }

    // create a funtion to cancel the progress Dialog
    fun cancelDialog(){
        //if the custom progress dialog not null then
        if (customProgressDialog != null) {
            //dismiss the dialog
            customProgressDialog?.dismiss()
            // set the custom dialog to null
            customProgressDialog = null
        }
    }


    // create a funtion to show the progress Dialog
    fun showTheDialog(){
        // declare the dialog variable with Dialog() method
        customProgressDialog = Dialog(this@MainActivity)
        //set the setContent view with proper layout
        customProgressDialog?.setContentView(R.layout.progress_bar_dialog)
        //show the dialog
        customProgressDialog?.show()
    }

}