package com.nissen.johannes.fremtidsmentor.controllers.implementations

import android.app.Activity
import android.content.Context
import com.google.firebase.FirebaseApp
import org.junit.Test

import org.junit.Assert.*

import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IFirebase
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.ISchedule
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.IUserController
import com.nissen.johannes.fremtidsmentor.controllers.implementations.FirebaseController
import com.nissen.johannes.fremtidsmentor.controllers.implementations.UserController
import com.nissen.johannes.fremtidsmentor.controllers.implementations.ScheduleController
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import com.nissen.johannes.fremtidsmentor.screenshots.ActivityMain
import org.junit.After
import org.junit.Before


class FirebaseControllerTest {

//    var firebase: IFirebase? = null
//    var userController: IUserController? = null
//    var scheduleController: ISchedule? = null
//
//    @Before
//    fun setUp() {
//        firebase = FirebaseController()
//        userController = UserController()
//        scheduleController = ScheduleController()
//    }
//
//    @After
//    fun tearDown() {
//        firebase = null
//        userController = null
//        scheduleController = null
//    }

    @Test
    fun getUserFromFirebase() {
        val firebase: IFirebase = ControllerRegistry.databaseController.DatabaseController
        val userController: IUserController = ControllerRegistry.usercontroller.UserController
        firebase!!.getUserFromFirebase("-LvFAqpo0BvRhG5jiwYl")
        Thread.sleep(500)
        var user = userController!!.getUser()

        assertEquals("Test123", user.getUsername())
        assertEquals("Test321", user.getPassword())
        assertEquals("test123@hotmail.dk", user.getEmail())
    }

    @Test
    fun updateUser() {

    }

    @Test
    fun newCommunity() {
        val firebase: IFirebase = ControllerRegistry.databaseController.DatabaseController
        val userController: IUserController = ControllerRegistry.usercontroller.UserController
        val nut = firebase.newCommunity(NormalPerson("Bøf", "123@gmail.com", "159"))
        assertTrue(nut.getId() != null)
    }

    @Test
    fun deleteuser() {
        val firebase: IFirebase = ControllerRegistry.databaseController.DatabaseController
        val userController: IUserController = ControllerRegistry.usercontroller.UserController
        firebase.deleteuser("users/normalUser", "-LvNHqA2oNvR3BipQYpS")
        Thread.sleep(500)
        firebase!!.getUserFromFirebase("-LvNHqA2oNvR3BipQYpS")
        assertTrue(userController!!.getUser() == null)
    }

    @Test
    fun loadSchedules() {
        val firebase: IFirebase = ControllerRegistry.databaseController.DatabaseController
        val userController: IUserController = ControllerRegistry.usercontroller.UserController
        val scheduleController: ISchedule = ControllerRegistry.schedulecontroller.getscheduleController()
        firebase.loadSchedules("Test123", "scheduleMentee")
        assertEquals("Mads Faurholt", scheduleController.loadSchedules()[0].ScheduleMentor)
    }
}