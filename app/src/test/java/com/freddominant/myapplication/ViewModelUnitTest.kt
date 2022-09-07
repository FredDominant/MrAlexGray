package com.freddominant.myapplication

import com.freddominant.myapplication.data.entities.RepositoryImpl
import com.freddominant.myapplication.ui.viewmodel.MrAlexGrayViewModel
import com.freddominant.myapplication.ui.views.GitHubReposViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import org.mockito.stubbing.Answer

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelUnitTest {
    private val mockRepository: RepositoryImpl = mock()
    private val mockCoroutineContextProvider: CoroutineContextProvider = mock()

    @Test
    fun `viewModel should have correct initial view state`() {
        val viewModel = MrAlexGrayViewModel(
            mockRepository,
            mockCoroutineContextProvider
        )
        val initialViewState = GitHubReposViewState(
            isLoading = false,
            hasError = false,
            repos = emptyList(),
            isFetchingMore = false
        )

        val viewModelInitialState = viewModel.viewState.value

        Assert.assertEquals(viewModelInitialState.errorMessage, initialViewState.errorMessage)
        Assert.assertEquals(viewModelInitialState.isLoading, initialViewState.isLoading)
        Assert.assertEquals(viewModelInitialState.isFetchingMore, initialViewState.isFetchingMore)
        Assert.assertTrue(viewModelInitialState.repos.isEmpty())
        Assert.assertEquals(viewModel.nextPageToFetch, 0)

        runTest{
            verify(mockCoroutineContextProvider, never()).io
            verify(mockRepository, never()).fetchRepos(10, 0)
        }
    }

    @Test
    fun `viewModel initialise method should update view state properly`() {
        runTest {
            whenever(mockCoroutineContextProvider.io).thenReturn(Dispatchers.Unconfined)
            whenever(mockRepository.fetchRepos(10, 0)).thenReturn(RepositoryUnitTest.dummyGitHubRepo)

            val viewModel = MrAlexGrayViewModel(
                mockRepository,
                mockCoroutineContextProvider
            )

            Assert.assertEquals(viewModel.nextPageToFetch, 0)

            viewModel.initialise()

            val state = viewModel.viewState.value
            val expectedViewState = GitHubReposViewState(
                isLoading = false,
                hasError = false,
                repos = RepositoryUnitTest.dummyGitHubRepo,
                isFetchingMore = false
            )
            Assert.assertTrue(state.isLoading == expectedViewState.isLoading)
            Assert.assertTrue(state.hasError == expectedViewState.hasError)
            Assert.assertTrue(state.isFetchingMore == expectedViewState.isFetchingMore)
            Assert.assertEquals(state.repos, expectedViewState.repos)
            Assert.assertEquals(viewModel.currentGitHubRepos, RepositoryUnitTest.dummyGitHubRepo)
            Assert.assertEquals(viewModel.nextPageToFetch, 1)

            verify(mockCoroutineContextProvider, times(1)).io
            verify(mockRepository, times(1)).fetchRepos(10, 0)
        }
    }

    @Test
    fun `viewModel loadMoreRepositories method should fetch more data and update view state properly`() {
        runTest {
            whenever(mockCoroutineContextProvider.io).thenReturn(Dispatchers.Unconfined)
            whenever(mockRepository.fetchRepos(10, 0)).thenReturn(RepositoryUnitTest.dummyGitHubRepo)

            val viewModel = MrAlexGrayViewModel(
                mockRepository,
                mockCoroutineContextProvider
            )

            Assert.assertEquals(viewModel.nextPageToFetch, 0)

            viewModel.initialise()

            Assert.assertEquals(viewModel.currentGitHubRepos, RepositoryUnitTest.dummyGitHubRepo)
            Assert.assertEquals(viewModel.nextPageToFetch, 1)

            verify(mockCoroutineContextProvider, times(1)).io
            verify(mockRepository, times(1)).fetchRepos(10, 0)

            whenever(mockRepository.fetchRepos(10, 1)).thenReturn(RepositoryUnitTest.extraDummyGitHubRepo)

            viewModel.loadMoreRepositories()

            Assert.assertEquals(viewModel.nextPageToFetch, 2)
            Assert.assertEquals(
                viewModel.currentGitHubRepos,
                RepositoryUnitTest.dummyGitHubRepo + RepositoryUnitTest.extraDummyGitHubRepo
            )
            Assert.assertEquals(
                viewModel.currentGitHubRepos.size,
                (RepositoryUnitTest.dummyGitHubRepo + RepositoryUnitTest.extraDummyGitHubRepo).size
            )

            verify(mockCoroutineContextProvider, times(2)).io
            verify(mockRepository, times(1)).fetchRepos(10, 1       )
        }
    }

    @Test
    fun `viewModel should handle error properly`() {
        runTest {

            val mockException = Exception("Mock Exception")
            whenever(mockCoroutineContextProvider.io).thenReturn(Dispatchers.Unconfined)
            `when`(mockRepository.fetchRepos(10, 0)).thenAnswer { mockException }

            val viewModel = MrAlexGrayViewModel(
                mockRepository,
                mockCoroutineContextProvider
            )

            Assert.assertEquals(viewModel.nextPageToFetch, 0)

            viewModel.initialise()

            val state = viewModel.viewState.value
            val expectedViewState = GitHubReposViewState(
                isLoading = false,
                hasError = true,
                repos = emptyList(),
                isFetchingMore = false,
                errorMessage = mockException.message
            )
            Assert.assertTrue(state.isLoading == expectedViewState.isLoading)
            Assert.assertTrue(state.hasError == expectedViewState.hasError)
            Assert.assertTrue(state.isFetchingMore == expectedViewState.isFetchingMore)
            Assert.assertTrue(state.repos.isEmpty())
            Assert.assertTrue(viewModel.currentGitHubRepos.isEmpty())
            Assert.assertEquals(state.repos, expectedViewState.repos)
            Assert.assertEquals(viewModel.nextPageToFetch, 0)

            verify(mockCoroutineContextProvider, times(1)).io
            verify(mockRepository, times(1)).fetchRepos(10, 0)
        }
    }
}