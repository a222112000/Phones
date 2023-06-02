package com.example.mvi.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mvi.R
import com.example.mvi.presentation.theme.MVITheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVITheme {
                 val state by viewModel.collectAsState()
                 val currentContext = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(6.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){
                        if(state.products != null) {
                            items(state.products!!.products) {
                                Column {
                                    // LazyColumn{
                                    //  items(it){
                                    AsyncImage(
                                        model = it.thumbnail,
                                        contentDescription = it.description,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    AsyncImage(
                                        model = it.images,
                                        contentDescription = it.description
                                    )
                                    Text(
                                        text = it.title,
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = it.description,
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp, fontWeight = FontWeight.Light)
                                    )
                                    Text(
                                        text = "Price: ${it.price.toString()}",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp)
                                    )
                                    Text(
                                        text = "Discount: ${it.discountPercentage.toString()} %",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                                        color = Color.Red
                                    )
                                    Text(
                                        text = "Rate: ${it.rating.toString()}",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp)
                                    )
                                }
                                //   }
                                // }
                            }
                        }
                    }
                    if(state.progressBar){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            CircularProgressIndicator()
                        }
                    }
                    viewModel.collectSideEffect{
                        when(it){
                            is UiComponent.Toast ->{
                                Toast.makeText(currentContext,it.text,Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                }
            }
        }
    }
}