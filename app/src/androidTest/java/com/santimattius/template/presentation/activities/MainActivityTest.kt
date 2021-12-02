package com.santimattius.template.presentation.activities

import androidx.test.core.app.ActivityScenario
import com.santimattius.template.CoroutinesTestRule
import com.santimattius.template.PictureMother
import com.santimattius.template.ScreenshotTest
import com.santimattius.template.UITest
import com.santimattius.template.domain.usecases.GetPictures
import com.santimattius.template.presentation.models.PictureUiModel
import com.santimattius.template.presentation.models.mapping.asUiModels
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class MainActivityTest : UITest(), ScreenshotTest {

    @ExperimentalCoroutinesApi
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
    fun showsEmptyCaseIfThereAreNoPictures() {
        givenThereAreNoPictures()
        compareScreenshot(name = "showsEmptyCaseIfThereAreNoPictures")
    }

    @Test
    fun showsJustOnePicture() {
        givenThereAreSomeOnePicture()
        compareScreenshot(name = "showsJustOnePicture")
    }

    @Test
    fun showsPicturesIfThereAreSomePictures() {
        givenThereAreSomePictures()
        compareScreenshot(name = "showsPicturesIfThereAreSomePictures")
    }

    private fun compareScreenshot(name: String) {
        val launch = ActivityScenario.launch(MainActivity::class.java)
        launch.onActivity { activity ->
            compareScreenshot(activity, name)
        }
    }

    private fun givenThereAreNoPictures() {
        runBlockingTest { Mockito.`when`(getPictures.invoke()).thenReturn(emptyList()) }
    }

    private fun givenThereAreSomePictures(): List<PictureUiModel> {
        val pictures = PictureMother.createPictures()
        runBlockingTest { Mockito.`when`(getPictures.invoke()).thenReturn(pictures) }
        return pictures.asUiModels()
    }

    private fun givenThereAreSomeOnePicture(): List<PictureUiModel> {
        val picture = PictureMother.picture()
        val pictures = listOf(picture)
        runBlockingTest { Mockito.`when`(getPictures.invoke()).thenReturn(pictures) }
        return pictures.asUiModels()
    }
}