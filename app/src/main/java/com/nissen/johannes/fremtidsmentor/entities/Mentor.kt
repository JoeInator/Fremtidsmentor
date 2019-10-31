package com.nissen.johannes.fremtidsmentor.entities

class Mentor() : User() {

    private var teaser: String? = null
    private var description: String? = null
    private var id: String? = null
    private var email: String? = null
    private var username: String? = null
    private var password: String? = null
    private var competencies: ArrayList<String>? = null
    
    constructor(username: String, email: String, password: String, teaser: String, descr: String, competencies: ArrayList<String>) : this() {
        this.email = email
        this.username = username
        this.password = password
        this.teaser = teaser
        this.description = descr
        this.competencies = competencies
    }

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

    fun getTeaser(): String?{
        return teaser
    }

    fun setTeaser(teaser: String) {
        this.teaser = teaser
    }

    fun getComps(): ArrayList<String>? {
        return competencies
    }

    fun setComps(comps: ArrayList<String>) {
        this.competencies = comps
    }

    fun addToComps(newComp: String) {
        if (!competencies!!.contains(newComp))
            competencies!!.add(newComp)
    }

    fun RemoveComps(comp: String) {
        if(competencies!!.contains(comp))
            competencies!!.remove(comp)
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

}
