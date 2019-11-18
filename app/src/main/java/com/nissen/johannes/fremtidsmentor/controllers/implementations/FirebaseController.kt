package com.nissen.johannes.fremtidsmentor.controllers.implementations

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IFirebase
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import java.util.ArrayList

class FirebaseController : IFirebase {

    var databaseCats = FirebaseDatabase.getInstance().getReference("categories")
    var userBranch = FirebaseDatabase.getInstance().getReference("users/normalPerson")
    var mentorBranch = FirebaseDatabase.getInstance().getReference("users/mentor")

    override fun getUserFromFirebase(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setValueListener() {

    }

    override fun newMentor(m: Mentor): Boolean {
        var succes = true
        val id = userBranch.push().getKey()
        m.setId(id!!)
        userBranch.child(id!!).setValue(m)
            .addOnCompleteListener {
                succes = true
            }
            .addOnCanceledListener {
                succes = false
            }

        return succes
    }

    override fun newCommunity(user: NormalPerson): Boolean {
        var succes = true
        val id = userBranch.push().getKey()
        user.setId(id!!)
        userBranch.child(id!!).setValue(user)
            .addOnCompleteListener {
                succes = true
            }
            .addOnCanceledListener {
                succes = false
            }

        return succes
    }

    fun loadCategories(con: Context): ArrayList<String> {
        TODO("not implemented")
    }


}