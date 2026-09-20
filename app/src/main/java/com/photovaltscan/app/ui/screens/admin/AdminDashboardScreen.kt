package com.photovaltscan.app.ui.screens.admin

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.photovaltscan.app.ui.theme.PrimaryBlue
import com.photovaltscan.app.ui.theme.SecondaryGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class PendingUser(val id: String, val name: String, val email: String)

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel() {
    private val _pendingUsers = MutableStateFlow<List<PendingUser>>(emptyList())
    val pendingUsers: StateFlow<List<PendingUser>> = _pendingUsers.asStateFlow()

    init {
        loadPendingUsers()
    }

    private fun loadPendingUsers() {
        val users = mutableListOf<PendingUser>()
        prefs.all.keys.filter { it.startsWith("name_") }.forEach { key ->
            val id = key.removePrefix("name_")
            val status = prefs.getString("status_$id", "approved")
            if (status == "pending") {
                val name = prefs.getString("name_$id", "") ?: ""
                val email = prefs.getString("email_$id", "") ?: ""
                users.add(PendingUser(id, name, email))
            }
        }
        _pendingUsers.value = users
    }

    fun assignRole(userId: String, newRole: String) {
        prefs.edit().apply {
            putString("role_$userId", newRole)
            putString("status_$userId", "approved")
            apply()
        }
        loadPendingUsers()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val pendingUsers by viewModel.pendingUsers.collectAsState()
    var selectedUser by remember { mutableStateOf<PendingUser?>(null) }
    var showAssignDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Panel de Administración", fontWeight = FontWeight.Bold, color = PrimaryBlue)
                        Text("Gestión de Usuarios", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil")
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Filled.Logout, contentDescription = "Salir")
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
                .padding(16.dp)
        ) {
            Text(
                "Usuarios Pendientes de Aprobación",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (pendingUsers.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.CheckCircleOutline, contentDescription = null, modifier = Modifier.size(64.dp), tint = SecondaryGreen)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No hay usuarios pendientes.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(pendingUsers) { user ->
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable { 
                                selectedUser = user
                                showAssignDialog = true
                            },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(48.dp).background(PrimaryBlue.copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Filled.Person, contentDescription = null, tint = PrimaryBlue)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(user.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text(user.id, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Surface(
                                    color = MaterialTheme.colorScheme.errorContainer,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Pendiente", fontSize = 10.sp, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAssignDialog && selectedUser != null) {
        AssignRoleDialog(
            user = selectedUser!!,
            onDismiss = { showAssignDialog = false },
            onAssign = { role ->
                viewModel.assignRole(selectedUser!!.id, role)
                showAssignDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignRoleDialog(user: PendingUser, onDismiss: () -> Unit, onAssign: (String) -> Unit) {
    val roles = listOf("Técnico de Campo Junior", "Ing. de Campo Senior", "Supervisor de Zona", "Auditor de Calidad")
    var expanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(roles[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Asignar Rol Operativo") },
        text = {
            Column {
                Text("Asigna un rol operativo definitivo para el usuario:", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(user.name, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                Text(user.id, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedRole,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        roles.forEach { role ->
                            DropdownMenuItem(
                                text = { Text(role) },
                                onClick = {
                                    selectedRole = role
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onAssign(selectedRole) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Asignar y Aprobar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}