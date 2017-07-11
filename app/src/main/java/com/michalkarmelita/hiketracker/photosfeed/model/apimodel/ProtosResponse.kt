package com.michalkarmelita.hiketracker.photosfeed.model.apimodel

data class ProtosResponse(val photos: Photos)

data class Photos(val photo: List<PhotoDetails>)

data class PhotoDetails(val id: String, val farm: String, val server: String, val secret: String)
