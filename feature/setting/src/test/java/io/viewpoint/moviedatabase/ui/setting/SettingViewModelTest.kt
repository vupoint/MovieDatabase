package io.viewpoint.moviedatabase.ui.setting

import io.mockk.coEvery
import io.mockk.mockk
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.model.ConfigurationLanguage
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.test.TestBase
import io.viewpoint.moviedatabase.test.mock.TestPreferencesService
import io.viewpoint.moviedatabase.ui.setting.model.Language
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SettingViewModelTest : TestBase() {
    @Test
    fun `restore the saved language from the supported languages`() = runTest {
        val preferences = TestPreferencesService()
        preferences.putValue(PreferencesKeys.SELECTED_LANGUAGE_ISO, "ko")
        val repository = mockk<ConfigurationRepository>()
        coEvery { repository.getSupportedLanguages() } returns listOf(
            ConfigurationLanguage(name = "English", englishName = "English", languageCode = "en"),
            ConfigurationLanguage(name = "Korean", englishName = "Korean", languageCode = "ko"),
        )

        val viewModel = SettingViewModel(preferences, repository, LanguageMapper())

        expectThat(viewModel.uiState.value).isEqualTo(
            SettingUiState(
                languages = listOf(
                    Language(name = "English", englishName = "English", languageCode = "en"),
                    Language(name = "Korean", englishName = "Korean", languageCode = "ko"),
                ),
                selectLanguage = Language(name = "Korean", englishName = "Korean", languageCode = "ko"),
            )
        )
    }
}
