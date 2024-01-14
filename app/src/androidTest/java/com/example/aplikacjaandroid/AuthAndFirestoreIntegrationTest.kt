
import android.content.Context
import com.example.aplikacjaandroid.viewmodels.CreateAccountViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AuthAndFirestoreIntegrationTest
{

    @Mock
    lateinit var mockAuth: FirebaseAuth

    @Mock
    lateinit var mockFirestore: FirebaseFirestore

    @InjectMocks
    lateinit var createAccountViewModel: CreateAccountViewModel


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        createAccountViewModel.updateUserEmail("dummy@user.com")
        createAccountViewModel.updateUserPassword("dummyPassword")
        createAccountViewModel.updateRepeatedPassword("dummyPassword")
        createAccountViewModel.updateUserName("dummyName")
        createAccountViewModel.updateUserName("dummyLastName")
        createAccountViewModel.validateUserInput()

    }

    private fun mockAuthResultTask(success: Boolean): Task<AuthResult> {
        val authResultTask = mock(Task::class.java) as Task<AuthResult>
        `when`(authResultTask.isSuccessful).thenReturn(success)
        return authResultTask
    }

    @Test
    fun testCreateAccount_Success() {

        val mockContext: Context = mock(Context::class.java)
        val onSuccessCallback: () -> Unit = {}

        val email = "dummy@user.com"
        val password = "dummyPassword"
        val userData = hashMapOf(
            "name" to "dummyName",
            "lastname" to "dummyLastName",
            "status" to "Active",
            "accountCreationDate" to "dummyToday"
        )

        `when`(mockAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(mockAuthResultTask(true))

        `when`(mockAuth.currentUser).thenReturn(mock(FirebaseUser::class.java))
        `when`(mockAuth.currentUser!!.uid).thenReturn("dummyUserId")

        val documentReference = mock(DocumentReference::class.java)

        `when`(mockFirestore.collection("users").document("dummyUserId")).thenReturn(documentReference)
        `when`(documentReference.set(userData)).thenReturn(mock(Task::class.java) as Task<Void>?)


        createAccountViewModel.createAccount(context = mockContext, onSuccessCallback = onSuccessCallback)


        verify(mockAuth, times(1)).createUserWithEmailAndPassword(email, password)
        verify(mockAuth.currentUser, times(1))?.uid

        verify(mockFirestore, times(1)).collection("users")
        verify(documentReference, times(1)).set(userData)
    }





}