package com.daftech.eplclubs.data

import com.daftech.eplclubs.model.Club
import com.daftech.eplclubs.model.ClubData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ClubRepository {

    private val clubs = mutableListOf<Club>()

    init {
        if (clubs.isEmpty()) {
            ClubData.clubs.forEach {
                clubs.add(it)
            }
        }
    }

    fun getAllClub(): Flow<List<Club>> {
        return flowOf(clubs)
    }

    fun getClubById(clubId: Int): Club {
        return clubs.first {
            it.id == clubId
        }
    }

    fun getFavoriteClub(): Flow<List<Club>> {
        return flowOf(clubs.filter { it.isFavorite == true })
    }

    fun updateFavoriteClub(clubId: Int, isFavorite: Boolean): Flow<Boolean> {
        val index = clubs.indexOfFirst { it.id == clubId }
        val result = if (index >= 0) {
            val club = clubs[index]
            clubs[index] = club.copy(isFavorite = isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: ClubRepository? = null

        fun getInstance(): ClubRepository =
            instance ?: synchronized(this) {
                ClubRepository().apply {
                    instance = this
                }
            }
    }
}