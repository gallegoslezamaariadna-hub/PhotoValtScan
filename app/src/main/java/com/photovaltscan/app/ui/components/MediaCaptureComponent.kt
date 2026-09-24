package com.photovaltscan.app.ui.components

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.photovaltscan.app.utils.MediaUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaCaptureComponent(
    onMediaCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempUri?.let {
                    photoUri = it
                    onMediaCaptured(it)
                }
            }
        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if (success) {
                tempUri?.let {
                    photoUri = it
                    onMediaCaptured(it)
                }
            }
        }
    )
    var isVideoAction by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                photoUri = uri
                onMediaCaptured(uri)
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        if (cameraGranted) {
            if (isVideoAction) {
                tempUri = MediaUtils.createTempVideoUri(context)
                tempUri?.let { videoLauncher.launch(it) }
            } else {
                tempUri = MediaUtils.createTempImageUri(context)
                tempUri?.let { cameraLauncher.launch(it) }
            }
        } else {
            Toast.makeText(context, "Permisos de cámara y almacenamiento requeridos", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(4.dp))
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        if (photoUri != null) {
            val isVideo = photoUri.toString().contains(".mp4") || photoUri.toString().contains("video")
            if (isVideo) {
                Icon(
                    Icons.Filled.Videocam, 
                    contentDescription = "Video capturado", 
                    modifier = Modifier.fillMaxSize().padding(24.dp), 
                    tint = com.photovaltscan.app.ui.theme.PrimaryBlue
                )
            } else {
                AsyncImage(
                    model = photoUri,
                    contentDescription = "Foto capturada",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Icon(Icons.Filled.AddAPhoto, tint = MaterialTheme.colorScheme.outlineVariant, contentDescription = "Capturar foto")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Añadir Fotografía") },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false
                                isVideoAction = false
                                permissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    )
                                )
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.AddAPhoto, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Tomar foto desde la cámara")
                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false
                                isVideoAction = true
                                permissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    )
                                )
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Videocam, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Grabar video")
                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false
                                galleryLauncher.launch("*/*")
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.PhotoLibrary, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Elegir desde la galería")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}