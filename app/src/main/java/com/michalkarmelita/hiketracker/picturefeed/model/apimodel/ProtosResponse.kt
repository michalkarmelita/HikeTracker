package com.michalkarmelita.hiketracker.picturefeed.model.apimodel

data class ProtosResponse(val photos: Photos)

data class Photos(val photo: List<PhotoDetails>)

data class PhotoDetails(val id: String)
