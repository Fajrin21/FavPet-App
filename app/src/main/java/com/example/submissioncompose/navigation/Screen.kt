package com.example.submissioncompose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailPet : Screen("home/{playerId}") {
        fun createRoute(petId: Int) = "home/$petId"
    }
}