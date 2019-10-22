package com.nissen.johannes.fremtidsmentor.entities

import android.provider.ContactsContract

class NormalPerson(): User() {

    private var email: String? = null
    private var id: String? = null
    private var username: String? = null
    private var password: String? = null
    private var Interests: ArrayList<String>? = null

    constructor(id: String, username: String, email: String, password: String, interests: ArrayList<String>) : this() {
        this.id = id
        this.email = email
        this.username = username
        this.password = password
        this.Interests = interests
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getInterests(): ArrayList<String>? {
        return Interests
    }

    fun setInterests(Interests: ArrayList<String>) {
        this.Interests = Interests
    }

    fun addToInterests(newInterest: String) {
        if (!Interests!!.contains(newInterest))
            Interests!!.add(newInterest)
    }

    fun RemoveInterests(Interest: String) {
        if(Interests!!.contains(Interest))
            Interests!!.remove(Interest)
    }

}