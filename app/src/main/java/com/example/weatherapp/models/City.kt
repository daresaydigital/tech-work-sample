package com.example.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(@PrimaryKey val id: Long, val name: String, val country: String)