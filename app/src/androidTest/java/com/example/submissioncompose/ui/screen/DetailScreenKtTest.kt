package com.example.submissioncompose.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.submissioncompose.R
import com.example.submissioncompose.model.Pet
import com.example.submissioncompose.ui.theme.SubmissionComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val FakePetData = Pet(
        id = 0,
        name = "cating",
        breed = "persian",
        image = R.drawable.persiancat,
        description = "tessssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss",
        isFavorite = false
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionComposeTheme {
                DetailInformation(
                    id = FakePetData.id,
                    name = FakePetData.name,
                    breed = FakePetData.breed,
                    image = FakePetData.image,
                    description = FakePetData.description,
                    isFavorite = FakePetData.isFavorite,
                    navigateBack = {},
                    onFavoriteButtonClicked = {_, _ ->}
                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(FakePetData.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(FakePetData.description).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertHasClickAction()
    }

    @Test
    fun printComposeTree() {
        composeTestRule.onRoot().printToLog("Compose Tree:")
    }

    @Test
    fun detailInformation_isScrollable() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsDisplayed()

        val isFavorite = FakePetData.isFavorite
        val expectedContentDescription = if (isFavorite) {
            "Remove from Favorite"
        } else {
            "Add to Favorite"
        }

        composeTestRule.onNodeWithTag("favorite_detail_button")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}