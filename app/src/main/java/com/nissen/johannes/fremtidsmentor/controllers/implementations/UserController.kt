package com.nissen.johannes.fremtidsmentor.controllers.implementations

import android.util.Log
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IUserController
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import com.nissen.johannes.fremtidsmentor.entities.User

class UserController: IUserController {

    lateinit var currentUser: NormalPerson
    lateinit var currentMentor: Mentor
    var FirebaseController = ControllerRegistry.databaseController.DatabaseController

    override fun CreateUser(newUser: NormalPerson) {
        currentUser = FirebaseController.newCommunity(newUser)
    }

    override fun CreateMentor(newMentor: Mentor) {
        currentMentor = FirebaseController.newMentor(newMentor)
    }

    override fun DeleteUser(User: User) {

    }

    override fun UpdateUser(User: NormalPerson) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setUser(User: NormalPerson) {
        Log.d("HEK", User.getEmail().toString())
        currentUser = User
        Log.d("HEK", currentUser.getEmail().toString())
    }

    override fun getUser(): NormalPerson? {
        return currentUser
    }

}