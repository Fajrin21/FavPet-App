package com.example.submissioncompose.data

import com.example.submissioncompose.model.PetData
import com.example.submissioncompose.model.Pet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class PetRepository {
    private val dummyPet = mutableListOf<Pet>()

    init {
        if (dummyPet.isEmpty()) {
            PetData.dummyPet.forEach {
                dummyPet.add(it)
            }
        }
    }

    fun getPetById(petId: Int): Pet {
        return dummyPet.first {
            it.id == petId
        }
    }

    fun getFavoritePet(): Flow<List<Pet>> {
        return flowOf(dummyPet.filter { it.isFavorite })
    }

    fun searchPet(query: String) = flow {
        val data = dummyPet.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updatePet(playerId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyPet.indexOfFirst { it.id == playerId }
        val result = if (index >= 0) {
            val pet = dummyPet[index]
            dummyPet[index] = pet.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: PetRepository? = null

        fun getInstance(): PetRepository =
            instance ?: synchronized(this) {
                PetRepository().apply {
                    instance = this
                }
            }
    }
}