package com.nissen.johannes.fremtidsmentor.entities

class Mentor() : User() {

    private var description: String? = null
    private var id: String? = null
    private var username: String? = null
    private var password: String? = null

    constructor(id: String, username: String, password: String, descr: String) : this() {
        this.id = id
        this.username = username
        this.password = password
        this.description = descr
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(descr: String) {
        this.description = descr
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

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

}