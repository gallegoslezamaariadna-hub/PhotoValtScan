package com.photovaltscan.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.photovaltscan.app.R
import com.photovaltscan.app.ui.theme.PrimaryBlue
import kotlinx.coroutines.launch

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel() {
    val employeeId = prefs.getString("current_user", "TEC-402") ?: "TEC-402"
    val fullName = prefs.getString("name_$employeeId", "Usuario Desconocido") ?: ""
    val email = prefs.getString("email_$employeeId", "correo@empresa.com") ?: ""
    val role = prefs.getString("role_$employeeId", "Técnico de Campo") ?: ""
    val status = prefs.getString("status_$employeeId", "approved") ?: "approved"
    val unit = "Unidad Técnica #402"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.width(280.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(viewModel.unit, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = PrimaryBlue)
                        Text(viewModel.fullName, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    Column(modifier = Modifier.padding(vertical = 16.dp).weight(1f)) {
                        DrawerMenuItem(icon = Icons.Filled.Dashboard, label = "Dashboard")
                        DrawerMenuItem(icon = Icons.Filled.Assignment, label = "Proyectos")
                        DrawerMenuItem(icon = Icons.Filled.SyncAlt, label = "Sincronización")
                        DrawerMenuItem(icon = Icons.Filled.Settings, label = "Configuración / Perfil", isSelected = true)
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 24.dp)
                            .fillMaxWidth()
                            .clickable { onLogout() }
                    ) {
                        Icon(Icons.Filled.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Cerrar Sesión", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                                contentDescription = "Logo",
                                modifier = Modifier.size(32.dp).clip(CircleShape).border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("PhotoValtScan", fontWeight = FontWeight.Bold, color = PrimaryBlue)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Sync */ }) {
                            Icon(Icons.Filled.Sync, contentDescription = "Sincronizar")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header
                Column {
                    Text("Perfil y Configuración de Técnico", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("Revisa tus datos correctos, actualiza tu foto de perfil y modifica tu contraseña de acceso.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                // Profile Details Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Badge, contentDescription = null, tint = PrimaryBlue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Datos de Identificación del Personal", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryBlue)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                                contentDescription = "Foto de Perfil",
                                modifier = Modifier
                                    .size(96.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PrimaryBlue, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("FOTOGRAFÍA DE PERFIL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { /* TODO */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.height(48.dp)
                                ) {
                                    Icon(Icons.Filled.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Tomar Foto / Galería", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = "",
                                        onValueChange = {},
                                        placeholder = { Text("O pega una URL...", fontSize = 12.sp) },
                                        modifier = Modifier.weight(1f).height(48.dp),
                                        shape = RoundedCornerShape(4.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { /* TODO */ },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.height(48.dp)
                                    ) {
                                        Text("Guardar URL", fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                ProfileTextField(label = "ID DE TÉCNICO / USUARIO", value = viewModel.employeeId, modifier = Modifier.weight(1f))
                                ProfileTextField(label = "ESTADO DE LA CUENTA", value = if (viewModel.status == "approved") "Activo / Aprobado" else "Pendiente de Aprobación", modifier = Modifier.weight(1f))
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                ProfileTextField(label = "CORREO ELECTRÓNICO VERIFICADO", value = viewModel.email, modifier = Modifier.weight(1f))
                                ProfileTextField(label = "ROL ASIGNADO", value = viewModel.role, modifier = Modifier.weight(1f))
                            }
                            ProfileTextField(label = "UNIDAD OPERATIVA", value = viewModel.unit, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }

                // Password Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.LockReset, contentDescription = null, tint = PrimaryBlue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Seguridad y Cambio de Contraseña", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryBlue)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            PasswordTextField(label = "CONTRASEÑA ACTUAL")
                            PasswordTextField(label = "NUEVA CONTRASEÑA")
                            PasswordTextField(label = "CONFIRMAR NUEVA CONTRASEÑA")
                            
                            Button(
                                onClick = { /* TODO */ },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                            ) {
                                Text("ACTUALIZAR CONTRASEÑA", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Theme Mode Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Contrast, contentDescription = null, tint = PrimaryBlue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Apariencia y Modo del Dispositivo", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryBlue)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Elige cómo deseas que la aplicación se adapte al modo de tu dispositivo (Claro u Oscuro).", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ThemeButton(icon = Icons.Filled.SettingsSystemDaydream, label = "Automático", modifier = Modifier.weight(1f))
                            ThemeButton(icon = Icons.Filled.LightMode, label = "Claro", modifier = Modifier.weight(1f))
                            ThemeButton(icon = Icons.Filled.DarkMode, label = "Oscuro", modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { },
            readOnly = true,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun PasswordTextField(label: String) {
    var text by remember { mutableStateOf("") }
    Column {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("••••••••", color = MaterialTheme.colorScheme.onSurfaceVariant) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        )
    }
}

@Composable
fun ThemeButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { /* TODO */ },
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(4.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(2.dp))
            Text(label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun DrawerMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, isSelected: Boolean = false, modifier: Modifier = Modifier) {
    val bgColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(bgColor, RoundedCornerShape(50))
            .clickable { /* Select */ }
            .padding(horizontal = 16.dp)
    ) {
        Icon(icon, contentDescription = null, tint = contentColor)
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, color = contentColor, fontWeight = fontWeight)
    }
}