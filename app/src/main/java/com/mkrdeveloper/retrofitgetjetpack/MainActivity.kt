package com.mkrdeveloper.retrofitgetjetpack

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.mkrdeveloper.retrofitgetjetpack.ui.theme.RetrofitGetJetpackTheme
import com.mkrdeveloper.retrofitgetjetpack.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitGetJetpackTheme {
                var body by remember {
                    mutableStateOf("")
                }
                var id by remember {
                    mutableStateOf(0)
                }
                var title by remember {
                    mutableStateOf("")
                }
                var userId by remember {
                mutableStateOf(0)
            }

                val scope = rememberCoroutineScope()

                LaunchedEffect(key1 = true) {
                    scope.launch(Dispatchers.IO) {
                        val response = try{
                            RetrofitInstance.api.getUserByNumber(4)
                        }catch (e:IOException){
                            Toast.makeText(
                                this@MainActivity,
                                "IO error: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }catch (e: HttpException){
                            Toast.makeText(
                                this@MainActivity,
                                "Http error: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }
                        if (response.isSuccessful && response.body() != null){
                            withContext(Dispatchers.Main){
                                body = response.body()!!.body
                                id = response.body()!!.id
                                title = response.body()!!.title
                                userId = response.body()!!.userId

                            }
                        }
                    }
                }

                MyUi(
                    body = body,
                    id = id,
                    title = title,
                    userId = userId
                )
            }
        }
    }
}

@Composable
fun MyUi(body: String, id: Int, title: String, userId: Int) {
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround) {
        Text(text = "body: $body",fontSize = 20.sp)
        Text(text = "id: $id",fontSize = 20.sp)
        Text(text = "title: $title",fontSize = 20.sp)
        Text(text = "userId: $userId",fontSize = 20.sp)
    }
}
