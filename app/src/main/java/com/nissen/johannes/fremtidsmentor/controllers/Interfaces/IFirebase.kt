package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson

interface IFirebase {
    fun getUserFromFirebase(id: String)
    fun setValueListener()
    fun newMentor(m: Mentor): Boolean
    fun newCommunity(user: NormalPerson): Boolean
}