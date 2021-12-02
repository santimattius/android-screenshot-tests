package com.santimattius.template.presentation.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.santimattius.template.*
import com.santimattius.template.domain.usecases.GetPictures
import com.santimattius.template.presentation.models.PictureUiModel
import com.santimattius.template.presentation.models.mapping.asUiModels
import com.santimattius.template.recyclerview.RecyclerViewInteraction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest : UITest() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPictures: GetPictures

    override fun defineModules(): Module {
        return module {
            single<GetPictures> { getPictures }
        }
    }

    @Test
    fun showsEmptyCaseIfThereAreNoPictures() = runBlockingTest {
        givenThereAreNoPictures()
        launchFragment()
        onView(withText("No hay resultados")).check(matches(isDisplayed()))
    }

    @Test
    fun showsAuthorNameOfImageIfThereAreImages() {
        val pictures = givenThereAreSomePictures()

        launchFragment()

        RecyclerViewInteraction.onRecyclerView<PictureUiModel>(withId(R.id.home_pictures))
            .withItems(pictures)
            .check { picture, view, exception ->
                matches(hasDescendant(withText("Autor: ${picture.author}"))).check(
                    view,
                    exception
                )
            }
    }

    @Test
    fun doesNotShowEmptyCaseIfThereArePictures() = runBlockingTest {
        givenThereAreSomePictures()
        launchFragment()
        onView(withId(R.id.text_empty_result)).check(matches(not(isDisplayed())))
    }

    @Test
    fun doesNotShowLoadingViewOnceSuperHeroesAreShown() = runBlockingTest {
        givenThereAreSomePictures()
        launchFragment()
        onView(withId(R.id.home_progress_bar)).check(matches(not(isDisplayed())))
    }

    private fun launchFragment() {
        launchFragment<HomeFragment>()
    }

    private fun givenThereAreNoPictures() {
        runBlockingTest { Mockito.`when`(getPictures.invoke()).thenReturn(emptyList()) }
    }

    private fun givenThereAreSomePictures(): List<PictureUiModel> {
        val pictures = PictureMother.createPictures()
        runBlockingTest { Mockito.`when`(getPictures.invoke()).thenReturn(pictures) }
        return pictures.asUiModels()
    }
}