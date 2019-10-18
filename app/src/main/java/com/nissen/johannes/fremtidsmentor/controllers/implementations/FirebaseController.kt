package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import java.util.ArrayList

class FirebaseController : IFirebase {

    override fun getPatientFirebase(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setValueListener() {
//        list = ArrayList<>()
//        uc = ControllerRegistry.getUserController()
//
//
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val p = snapshot.getValue<Patient>(Patient::class.java!!)
//
//                    for (snapshotSensor in dataSnapshot.child(snapshot.key!!).child("sensorer").children) {
//                        val sensorList = ArrayList<Sensor>()
//                        val s = snapshotSensor.getValue<Sensor>(Sensor::class.java!!)
//                        sensorList.add(s)
//                        p!!.setSensors(sensorList)
//                    }
//                    list.add(p)
//
//                }
//                println("hello: $list")
//                uc.setPatientList(list)
//            }
//
//            override fun onCancelled(@NonNull databaseError: DatabaseError) {}
//        })
    }

    override fun newMentor(m: Mentor) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newCommunity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun writeSomething(){

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")



        myRef.setValue("Hello, World!")
    }

}