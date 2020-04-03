package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import com.nissen.johannes.fremtidsmentor.entities.User

interface IUserController {

    fun CreateUser(newUser: NormalPerson)
    fun CreateMentor(newMentor: Mentor)
    fun DeleteUser(User: User)
    fun UpdateUser(User: NormalPerson)
    fun setUser(User: NormalPerson)
    fun getUser(): NormalPerson
    fun setMentor(mentor: Mentor)
    fun getMentor(): Mentor?

}