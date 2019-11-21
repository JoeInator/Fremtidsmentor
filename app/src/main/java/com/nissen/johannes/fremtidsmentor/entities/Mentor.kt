package com.nissen.johannes.fremtidsmentor.entities

class Mentor() : User() {

    private var teaser: String? = null
    private var description: String? = null
    private var id: String? = null
    private var email: String? = null
    private var username: String? = null
    private var password: String? = null
    private var competencies: HashMap<String, ArrayList<String>>? = null
    
    constructor(username: String, email: String, password: String, teaser: String, descr: String, competencies: HashMap<String, ArrayList<String>>) : this() {
        this.email = email
        this.username = username
        this.password = password
        this.teaser = teaser
        this.description = descr
        this.competencies = competencies
    }

    constructor(id: String, username: String, email: String, password: String) : this() {
        this.id = id
        this.username = username
        this.email = email
        this.password = password
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

    fun getComps(): HashMap<String, ArrayList<String>>? {
        return competencies
    }

    fun setComps(comps: HashMap<String, ArrayList<String>>) {
        this.competencies = comps
    }

    fun addToComps(newComp: String) {
        for (h in competencies!!) {
            if ((!h.value.contains(newComp))) {
                h.value.add(newComp)
            }
        }
    }

    fun RemoveComps(comp: String) {
        for (h in competencies!!) {
            if (h.value.contains(comp)) {
                h.value.remove(comp)
            }
        }
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

}
