package com.nissen.johannes.fremtidsmentor.controllers

import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IFirebase
import com.nissen.johannes.fremtidsmentor.controllers.implementations.FirebaseController

class ControllerRegistry {

//    private var userController: IUserController? = null
//    private var dataController: IDataController? = null
    private var firebaseController: IFirebase? = null

    fun ControllerRegistry() {
        // Needs to be here to prevent instantiation.
    }

//    fun getUserController(): IUserController {
//        if (userController == null) userController = UserController()
//        return userController
//    }
//
//    fun getDataController(): IDataController {
//        if (dataController == null) dataController = DataController()
//        return dataController
//    }

    fun getFirebaseController(): IFirebase {
        if (firebaseController == null) firebaseController = FirebaseController()
        return firebaseController as IFirebase
    }

}