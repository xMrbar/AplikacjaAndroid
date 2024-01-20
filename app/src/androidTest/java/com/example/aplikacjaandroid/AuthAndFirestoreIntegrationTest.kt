
import com.example.aplikacjaandroid.viewmodels.CreateAccountViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
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
    }

    private fun mockAuthResultTask(success: Boolean): Task<AuthResult> {
        val authResultTask = mock(Task::class.java) as Task<AuthResult>
        `when`(authResultTask.isSuccessful).thenReturn(success)
        return authResultTask
    }

    @Test
    fun testCreateAccount_Success() {

        createAccountViewModel.updateUserEmail("dummy@user.com")
        createAccountViewModel.updateUserPassword("dummyPassword")
        createAccountViewModel.updateRepeatedPassword("dummyPassword")
        createAccountViewModel.updateUserName("dummyName")
        createAccountViewModel.updateUserLastName("dummyLastName")
        createAccountViewModel.validateUserInput()


        val onSuccessCallback: () -> Unit = {}

        val email = "dummy@user.com"
        val password = "dummyPassword"
        val userData = hashMapOf(
            "name" to "dummyName",
            "lastname" to "dummyLastName",
            "status" to "Active",
            "accountCreationDate" to "dummyToday"
        )

        val authResultTaskCompletion = TaskCompletionSource<AuthResult>()
        val authResultTask: Task<AuthResult> = authResultTaskCompletion.task

        val captor = ArgumentCaptor.forClass(OnCompleteListener::class.java) as ArgumentCaptor<OnCompleteListener<AuthResult>>

        `when`(mockAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(authResultTask)

        `when`(mockAuth.currentUser).thenReturn(mock(FirebaseUser::class.java))
        `when`(mockAuth.currentUser!!.uid).thenReturn("dummyUserId")


        val documentReference = mock(DocumentReference::class.java)

        val mockCollectionReference = mock(CollectionReference::class.java)
        `when`(mockFirestore.collection("users")).thenReturn(mockCollectionReference)

        `when`(mockFirestore.collection("users").document("dummyUserId")).thenReturn(documentReference)
        `when`(documentReference.set(userData)).thenReturn(Tasks.forResult(null))

        createAccountViewModel.createAccount( onSuccessCallback = onSuccessCallback)

    //    val authResult = mock(AuthResult::class.java)
    //    val taskResult = Tasks.forResult(authResult)
    //    captor.value.onComplete(taskResult)

        verify(mockAuth, times(1))?.currentUser

        verify(mockFirestore, times(1)).collection("users")
    }





}