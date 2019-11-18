package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson

interface IUserController {

    fun CreateUser(newUser: NormalPerson)
    fun CreateMentor(newMentor: Mentor)
    fun DeleteUser(User: NormalPerson)
    fun UpdateUser(User: NormalPerson)

}