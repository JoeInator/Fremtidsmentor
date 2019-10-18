package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.google.firebase.database.FirebaseDatabase

class FirebaseController {

    fun writeSomething(){
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }

}