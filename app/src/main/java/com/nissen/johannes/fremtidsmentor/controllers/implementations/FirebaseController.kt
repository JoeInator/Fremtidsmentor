package com.nissen.johannes.fremtidsmentor.controllers.implementations

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IFirebase
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import java.util.ArrayList
import android.util.Log
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry

class FirebaseController : IFirebase {

    var database = FirebaseDatabase.getInstance().getReference("")
    var databaseCats = FirebaseDatabase.getInstance().getReference("categories")
    var userBranch = FirebaseDatabase.getInstance().getReference("users/normalUser")
    var mentorBranch = FirebaseDatabase.getInstance().getReference("users/mentor")
    var UserController = ControllerRegistry.usercontroller.getUseController()

    init {
        databaseCats.keepSynced(true)
        userBranch.keepSynced(true)
        mentorBranch.keepSynced(true)
    }

    override fun getUserFromFirebase(id: String) {
        val user = NormalPerson("hjk", "ghjk", "Ghjk")
        Log.d("HEJ!!", id)
        userBranch.child(id).addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                user.setEmail(p0.child("email").value.toString().trim())
                user.setId(id)
                user.setPassword(p0.child("password").value.toString().trim())
                user.setUsername(p0.child("username").value.toString().trim())
                for (h in p0.child("interests").children) {
                    user.addToInterests(h.value.toString().trim())
                }
                UserController.setUser(user)
            }
        })
    }

    override fun setValueListener() {

    }

    override fun updateUser(user: NormalPerson) {
        userBranch.child(user.getId()!!).setValue(user)
            .addOnCompleteListener {
                Log.d("FIREBASE","User is updated")
            }
            .addOnFailureListener {
                Log.d("FIREBASE","Failed to update user")
            }
            .addOnSuccessListener {
                Log.d("FIREBASE","User is updated")
            }
            .addOnCanceledListener {
                Log.d("FIREBASE","Failed to update user")
            }
    }

    fun getUserInfo(username: String): NormalPerson? {

        return null
    }

    override fun newMentor(m: Mentor): Mentor {
        lateinit var succes: String
        val id = userBranch.push().getKey()
        m.setId(id!!)
        userBranch.child(id!!).setValue(m)
            .addOnCompleteListener {
                Log.d("FIREBASE","Made new user")
            }
            .addOnFailureListener {
                userBranch.child(id!!).removeValue()
                Log.d("FIREBASE","Failed to make a new user")
            }
            .addOnSuccessListener {
                Log.d("FIREBASE","Made new user")
            }
            .addOnCanceledListener {
                userBranch.child(id!!).removeValue()
                Log.d("FIREBASE","Failed to make a new user")
            }
        return m
    }

    override fun newCommunity(user: NormalPerson): NormalPerson {
        lateinit var succes: String
        val id = userBranch.push().getKey()
        user.setId(id!!)
        userBranch.child(id!!).setValue(user)
            .addOnCompleteListener {
                Log.d("FIREBASE","Made new user")
            }
            .addOnFailureListener {
                userBranch.child(id!!).removeValue()
                Log.d("FIREBASE","Failed to make a new user")
            }
            .addOnSuccessListener {
                Log.d("FIREBASE","Made new user")
            }
            .addOnCanceledListener {
                userBranch.child(id!!).removeValue()
                Log.d("FIREBASE","Failed to make a new user")
            }
        return user
    }

    fun loadCategories(con: Context): ArrayList<String> {
        TODO("not implemented")
    }

    override fun deleteuser(path: String, id: String) {
        if (path.contains("normalUser")) {
            userBranch.child(id).removeValue()
        } else if (path.contains("mentor")) {
            mentorBranch.child(id).removeValue()
        }
    }
}