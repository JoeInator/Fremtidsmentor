package com.nissen.johannes.fremtidsmentor.controllers.implementations

import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.ISchedule
import com.nissen.johannes.fremtidsmentor.entities.Schedule

class ScheduleController: ISchedule {

    var schedules = arrayListOf<Schedule>()
    var schedule: Schedule? = null

    override fun addSchedule(schedule: Schedule) {
        schedules.add(schedule)
    }

    override fun loadSchedules(): ArrayList<Schedule> {
        return schedules
    }

    override fun deleteSchedule(schedule: Schedule) {
        schedules.remove(schedule)
    }

    override fun setschedules(Schedules: ArrayList<Schedule>) {
        schedules = Schedules
    }
}