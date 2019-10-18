package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.nissen.johannes.fremtidsmentor.entities.Mentor

interface IFirebase {
    fun getPatientFirebase(id: String)
    fun setValueListener()
    fun newMentor(m: Mentor)
    fun newCommunity()
}