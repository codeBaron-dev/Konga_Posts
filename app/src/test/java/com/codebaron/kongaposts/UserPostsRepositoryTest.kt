package com.codebaron.kongaposts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.codebaron.kongaposts.model.PostsModelItem
import com.codebaron.kongaposts.model.Token
import com.codebaron.kongaposts.mvvm.UserPostsRepository
import com.codebaron.kongaposts.remote.Endpoints
import com.codebaron.kongaposts.remote.ResponseHandler
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPostsRepositoryTest {

    // This rule ensures that LiveData executes each task synchronously
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mockito rule to enable Mockito annotations
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    // Mock Endpoints
    @Mock
    private lateinit var mockEndpoints: Endpoints

    // Mock Observer
    @Mock
    private lateinit var mockObserver: Observer<ResponseHandler<Token?>>

    // ArgumentCaptor to capture values passed to the observer
    @Captor
    private lateinit var captor: ArgumentCaptor<ResponseHandler<Token?>>

    private lateinit var userPostsRepository: UserPostsRepository

    @Before
    fun setup() {
        // Initialize MockK
        MockKAnnotations.init(this)

        // Initialize the repository with the mocked Endpoints
        userPostsRepository = UserPostsRepository(mockEndpoints)
    }

    @After
    fun tearDown() {
        // Close MockK
        unmockkAll()
    }

    @Test
    fun testAuthenticationSuccess() {
        // Mock successful response
        val mockCall =  mockk<Call<Token>>()
        every { mockEndpoints.authentication() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback: Callback<Token> = it.invocation.args[0] as Callback<Token>
            callback.onResponse(mockCall, Response.success(Token("mock_token")))
        }

        // Observe the authentication response
        userPostsRepository.authentication().observeForever(mockObserver)

        // Verify that the observer is notified with a success response
       /* verify(mockObserver, times(2)).onChanged(capture(captor))
        assert(captor.value is ResponseHandler.Loading)
        assert(captor.value is ResponseHandler.Success)
        assertEquals((captor.value as ResponseHandler.Success).data?.token, "mock_token")*/

        //TODO: Fix errors above in the commented code
    }

    @Test
    fun testAuthenticationError() {
        // Mock error response
        val mockCall: Call<Token> = mockk()
        every { mockEndpoints.authentication() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback: Callback<Token> = it.invocation.args[0] as Callback<Token>
            callback.onResponse(mockCall, Response.error(400, mockk()))
        }

        // Observe the authentication response
        userPostsRepository.authentication().observeForever(mockObserver)

        // Verify that the observer is notified with an error response
       /* verify(mockObserver, times(2)).onChanged(capture(captor))*/
        assert(captor.value is ResponseHandler.Loading)
        assert(captor.value is ResponseHandler.Error)
        assertEquals((captor.value as ResponseHandler.Error).message, "mock_error")

        //TODO: Fix errors above in the commented code
    }

    @Test
    fun testGetAllPostsSuccess() {
        // Mock successful response
        val mockCall: Call<List<PostsModelItem>> = mockk()
        every { mockEndpoints.getAllUsers(any()) } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback: Callback<List<PostsModelItem>> = it.invocation.args[0] as Callback<List<PostsModelItem>>
            //callback.onResponse(mockCall, Response.success(listOf(PostsModelItem(1, "Title", "Body"))))
        }

        // Observe the posts response
            //userPostsRepository.getAllPosts("mock_token").observeForever(mockObserver)

        // Verify that the observer is notified with a success response
        //verify(mockObserver, times(2)).onChanged(capture(captor))
        assert(captor.value is ResponseHandler.Loading)
        assert(captor.value is ResponseHandler.Success)
        //assertEquals((captor.value as ResponseHandler.Success).data?.size, 1)

        //TODO: Fix errors above in the commented code
    }

    @Test
    fun testGetAllPostsError() {
        // Mock error response
        val mockCall: Call<List<PostsModelItem>> = mockk()
        every { mockEndpoints.getAllUsers(any()) } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback: Callback<List<PostsModelItem>> = it.invocation.args[0] as Callback<List<PostsModelItem>>
            callback.onResponse(mockCall, Response.error(400, mockk()))
        }

        // Observe the posts response
        //userPostsRepository.getAllPosts("mock_token").observeForever(mockObserver)

        // Verify that the observer is notified with an error response
        //verify(mockObserver, times(2)).onChanged(capture(captor))
        assert(captor.value is ResponseHandler.Loading)
        assert(captor.value is ResponseHandler.Error)
        assertEquals((captor.value as ResponseHandler.Error).message, "mock_error")

        //TODO: Fix errors above in the commented code
    }

}