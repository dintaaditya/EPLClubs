package com.daftech.eplclubs

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.daftech.eplclubs.model.ClubData
import com.daftech.eplclubs.ui.EPLClubApp
import com.daftech.eplclubs.ui.navigation.Screen
import com.daftech.eplclubs.ui.theme.EPLClubsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class EPLClubAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            EPLClubsTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                EPLClubApp(navController = navController)
            }
        }
    }

    //verify startDestination
    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    //test whether bottom navigation is working as expected
    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_about).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    //search available data, lazy grid must be displayed
    @Test
    fun home_searchAvailableData() {
        val data = "ARSENAL"
        composeTestRule.onNodeWithStringId(R.string.placeholder_search).performTextInput(data)
        composeTestRule.onNodeWithTag("ClubList").assertIsDisplayed()
    }

    //search not available data, empty list must be displayed
    @Test
    fun home_searchNotAvailableData() {
        val data = "REAL MADRID"
        composeTestRule.onNodeWithStringId(R.string.placeholder_search).performTextInput(data)
        composeTestRule.onNodeWithStringId(R.string.mssg_empty_data_home).assertIsDisplayed()
    }

    //test whether back button is working as expected
    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("ClubList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(ClubData.clubs[5].name.uppercase()).performClick()
        navController.assertCurrentRouteName(Screen.DetailClub.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back_button)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    //test whether can navigate to detail page & show correct detail data
    @Test
    fun home_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("ClubList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(ClubData.clubs[5].name.uppercase()).performClick()
        navController.assertCurrentRouteName(Screen.DetailClub.route)
        composeTestRule.onNodeWithText(ClubData.clubs[5].name).assertIsDisplayed()
    }

    //test whether add favorite club and show favorite data are working as expected
    @Test
    fun favorite_AddDataToFavorite_ShowInFavoritePage() {
        composeTestRule.onNodeWithText(ClubData.clubs[0].name.uppercase()).performClick()
        navController.assertCurrentRouteName(Screen.DetailClub.route)
        composeTestRule.onNodeWithTag("favorite_button").performClick()
        composeTestRule.onNodeWithTag("back_button").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(ClubData.clubs[0].name.uppercase()).assertIsDisplayed()
    }

    //test whether add favorite club, show favorite data, then remove favorite from favorite page are working as expected
    @Test
    fun favorite_AddAndRemoveFavoriteData_DataNotShowInFavoritePage() {
        //add data to favorite
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        composeTestRule.onNodeWithText(ClubData.clubs[1].name.uppercase()).performClick()
        composeTestRule.onNodeWithTag("favorite_button").performClick()
        composeTestRule.onNodeWithTag("back_button").performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()

        //remove from favorite
        composeTestRule.onNodeWithText(ClubData.clubs[1].name.uppercase()).performClick()
        composeTestRule.onNodeWithTag("favorite_button").performClick()
        composeTestRule.onNodeWithTag("back_button").performClick()
        composeTestRule.onNodeWithText(ClubData.clubs[1].name.uppercase()).assertIsNotDisplayed()
    }

    //test whether navigate to about page is working as expected & show correct data
    @Test
    fun home_navigateToAboutPage() {
        composeTestRule.onNodeWithStringId(R.string.menu_about).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.author_name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.author_email).assertIsDisplayed()
    }


}