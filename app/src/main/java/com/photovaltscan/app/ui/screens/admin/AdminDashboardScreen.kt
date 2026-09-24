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
data class Technician(val id: String, val name: String, val role: String)

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel() {
    private val _pendingUsers = MutableStateFlow<List<PendingUser>>(emptyList())
    val pendingUsers: StateFlow<List<PendingUser>> = _pendingUsers.asStateFlow()

    private val _technicians = MutableStateFlow<List<Technician>>(emptyList())
    val technicians: StateFlow<List<Technician>> = _technicians.asStateFlow()

    init {
        loadPendingUsers()
        loadTechnicians()
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

    private fun loadTechnicians() {
        val techs = mutableListOf<Technician>()
        prefs.all.keys.filter { it.startsWith("name_") }.forEach { key ->
            val id = key.removePrefix("name_")
            val status = prefs.getString("status_$id", "approved")
            if (status == "approved" && id != "admin" && id != "support") {
                val role = prefs.getString("role_$id", "Técnico") ?: "Técnico"
                val name = prefs.getString("name_$id", "") ?: ""
                techs.add(Technician(id, name, role))
            }
        }
        _technicians.value = techs
    }

    fun assignRole(userId: String, newRole: String) {
        prefs.edit().apply {
            putString("role_$userId", newRole)
            putString("status_$userId", "approved")
            apply()
        }
        loadPendingUsers()
        loadTechnicians()
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
    
    var selectedTechnician by remember { mutableStateOf<Technician?>(null) }
    var showAssignTaskDialog by remember { mutableStateOf(false) }
    
    var selectedTab by remember { mutableStateOf(1) } // 1 for Usuarios

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Panel de Administración", fontWeight = FontWeight.Bold, color = PrimaryBlue)
                        Text(when(selectedTab) {
                            0 -> "Inicio"
                            1 -> "Gestión de Usuarios"
                            2 -> "Plantillas"
                            3 -> "Tareas"
                            else -> "Estadísticas"
                        }, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Filled.People, contentDescription = "Usuarios") },
                    label = { Text("Usuarios") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Filled.Article, contentDescription = "Plantillas") },
                    label = { Text("Plantillas") }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Filled.Assignment, contentDescription = "Tareas") },
                    label = { Text("Tareas") }
                )
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Filled.BarChart, contentDescription = "Estadísticas") },
                    label = { Text("Métricas") }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (selectedTab == 1) {
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
            } else if (selectedTab == 3) {
                val technicians by viewModel.technicians.collectAsState()
                
                Text(
                    "Asignación de Tareas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                Text(
                    "Técnicos registrados: ${technicians.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (technicians.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.GroupOff, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("No hay técnicos registrados.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(technicians) { tech ->
                            Card(
                                modifier = Modifier.fillMaxWidth().clickable { 
                                    selectedTechnician = tech
                                    showAssignTaskDialog = true
                                },
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.size(48.dp).background(SecondaryGreen.copy(alpha = 0.1f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Filled.Engineering, contentDescription = null, tint = SecondaryGreen)
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(tech.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Text(tech.role, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Icon(Icons.Filled.ChevronRight, contentDescription = "Asignar Tarea", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Módulo en desarrollo", color = MaterialTheme.colorScheme.onSurfaceVariant)
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

    if (showAssignTaskDialog && selectedTechnician != null) {
        AssignTaskDialog(
            tech = selectedTechnician!!,
            onDismiss = { showAssignTaskDialog = false },
            onAssign = { task ->
                showAssignTaskDialog = false
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignTaskDialog(tech: Technician, onDismiss: () -> Unit, onAssign: (String) -> Unit) {
    val tasks = listOf("Levantamiento Planta Tratamiento", "Inspección Subestación T-02", "Mantenimiento Torre Central", "Revisión Equipos Periféricos")
    var expanded by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf(tasks[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Asignar Tarea") },
        text = {
            Column {
                Text("Selecciona una tarea para asignar al técnico:", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(tech.name, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                Text(tech.role, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedTask,
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
                        tasks.forEach { task ->
                            DropdownMenuItem(
                                text = { Text(task) },
                                onClick = {
                                    selectedTask = task
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
                onClick = { onAssign(selectedTask) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Asignar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}