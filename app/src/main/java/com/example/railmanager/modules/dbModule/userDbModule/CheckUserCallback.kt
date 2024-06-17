package com.example.railmanager.modules.dbModule.userDbModule

interface CheckUserCallback {
    fun onResult(exists: Int)
}