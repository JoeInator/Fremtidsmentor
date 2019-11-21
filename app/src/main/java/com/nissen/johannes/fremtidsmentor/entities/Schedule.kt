package com.nissen.johannes.fremtidsmentor.entities

class Schedule() {

    var ScheduleMentor: String? = null
    var ScheduleMentorID: String? = null
    var ScheduleMentee: String? = null
    var ScheduleMenteeID: String? = null
    var ScheduleID: String? = null
    var ScheduleDate: String? = null

    constructor(scheduleID: String, mentor: String, mentorID: String, mentee: String, menteeID: String, date: String): this() {
        this.ScheduleID = scheduleID
        this.ScheduleMentor = mentor
        this.ScheduleMentorID = mentorID
        this.ScheduleMenteeID = menteeID
        this.ScheduleMentee = mentee
        this.ScheduleDate = date
    }


}