package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson

interface IFirebase {
    fun getUserFromFirebase(id: String)
    fun setValueListener()
    fun newMentor(m: Mentor): Mentor
    fun newCommunity(user: NormalPerson): NormalPerson
    fun deleteuser(path: String, id: String)
}