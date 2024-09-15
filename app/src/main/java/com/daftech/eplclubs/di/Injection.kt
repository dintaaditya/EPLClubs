package com.daftech.eplclubs.di

import com.daftech.eplclubs.data.ClubRepository


object Injection {
    fun provideRepository(): ClubRepository {
        return ClubRepository.getInstance()
    }
}