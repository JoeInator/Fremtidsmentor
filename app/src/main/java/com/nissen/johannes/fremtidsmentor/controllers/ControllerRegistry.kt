package com.nissen.johannes.fremtidsmentor.controllers

import android.provider.ContactsContract
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IFirebase
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.ISchedule
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IUserController
import com.nissen.johannes.fremtidsmentor.controllers.implementations.FirebaseController
import com.nissen.johannes.fremtidsmentor.controllers.implementations.ScheduleController
import com.nissen.johannes.fremtidsmentor.controllers.implementations.UserController

class ControllerRegistry {

    object databaseController {

        var DatabaseController: IFirebase = FirebaseController()

        fun getFirebaaseController(): IFirebase {
            if (DatabaseController == null) { DatabaseController = FirebaseController() }
            return DatabaseController as IFirebase
        }

    }

    object usercontroller {

        var UserController: IUserController = UserController()

        fun getUseController(): IUserController {
            if (UserController == null) { UserController = UserController() }
            return UserController as IUserController
        }


    }

    object schedulecontroller {
        var ScheduleController: ISchedule = ScheduleController()

        fun getscheduleController(): ISchedule {
            if (ScheduleController == null) { ScheduleController = ScheduleController() }
            return ScheduleController as ISchedule
        }
    }

    private var userController: IUserController? = null
//    private var dataController: IDataController? = null
    private var firebaseController: IFirebase? = null

    fun ControllerRegistry() {
        // Needs to be here to prevent instantiation.
    }

    fun getUserController(): IUserController {
        if (userController == null) userController = UserController()
        return userController as IUserController
    }

//    fun getDataController(): IDataController {
//        if (dataController == null) dataController = DataController()
//        return dataController
//    }

    fun getFirebaseController(): IFirebase {
        if (firebaseController == null) firebaseController = FirebaseController()
        return firebaseController as IFirebase
    }

}