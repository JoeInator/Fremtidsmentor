package com.nissen.johannes.fremtidsmentor.entities

class Mentor(id: String, username: String, password: String,descr: String) : User(id, username, password) {

    private var description: String? = null
    private var id: String? = null
    private var username: String? = null
    private var password: String? = null

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