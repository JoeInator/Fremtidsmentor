package com.nissen.johannes.fremtidsmentor.controllers.Interfaces

import com.nissen.johannes.fremtidsmentor.entities.Schedule

interface ISchedule {

    fun addSchedule(schedule: Schedule)
    fun loadSchedules(): ArrayList<Schedule>
    fun deleteSchedule(schedule: Schedule)
    fun setschedules(Schedules: ArrayList<Schedule>)


}