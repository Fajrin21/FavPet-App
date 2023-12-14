package com.example.submissioncompose.di

import com.example.submissioncompose.data.PetRepository

object Injection {
    fun provideRepository(): PetRepository {
        return PetRepository.getInstance()
    }
}