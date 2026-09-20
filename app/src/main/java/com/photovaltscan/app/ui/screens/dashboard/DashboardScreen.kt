package com.photovaltscan.app.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.photovaltscan.app.R
import com.photovaltscan.app.domain.model.Project
import com.photovaltscan.app.ui.theme.PrimaryBlue
import com.photovaltscan.app.ui.theme.SecondaryGreen
import com.photovaltscan.app.ui.theme.StatusAssigned
import com.photovaltscan.app.ui.theme.StatusReview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onProjectClick: (String) -> Unit
) {
    val projects by viewModel.projects.collectAsState()
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
                    // Header Drawer
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(viewModel.userName, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = PrimaryBlue)
                        Text(viewModel.userRole, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* Iniciar Visita */ },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Iniciar Visita", fontWeight = FontWeight.Bold)
                        }
                    }
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    // Menu Items
                    Column(modifier = Modifier.padding(vertical = 16.dp).weight(1f)) {
                        DrawerMenuItem(icon = Icons.Filled.Dashboard, label = "Dashboard", isSelected = true)
                        DrawerMenuItem(icon = Icons.Filled.Assignment, label = "Proyectos")
                        DrawerMenuItem(icon = Icons.Filled.SyncAlt, label = "Sincronización", modifier = Modifier.clickable { scope.launch { drawerState.close(); onProjectClick("sync") } })
                        DrawerMenuItem(icon = Icons.Filled.Analytics, label = "Reportes")
                        DrawerMenuItem(icon = Icons.Filled.Settings, label = "Configuración / Perfil")
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    // Footer Drawer
                    DrawerMenuItem(icon = Icons.Filled.Logout, label = "Cerrar Sesión", modifier = Modifier.padding(vertical = 16.dp))
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
                        IconButton(onClick = { /* Search */ }) {
                            Icon(Icons.Filled.Search, contentDescription = "Buscar")
                        }
                        IconButton(onClick = { onProjectClick("sync") }) {
                            Icon(Icons.Filled.Sync, contentDescription = "Sincronizar")
                        }
                        IconButton(onClick = { /* Notifications */ }) {
                            Box {
                                Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones")
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(2.dp)
                                        .size(8.dp)
                                        .background(MaterialTheme.colorScheme.error, CircleShape)
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Perfil",
                            modifier = Modifier.padding(end = 16.dp, start = 8.dp).size(32.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomBarItem(icon = Icons.Filled.Save, label = "Guardar")
                        BottomBarItem(icon = Icons.Filled.Dashboard, label = "Dashboard", isSelected = true)
                        BottomBarItem(icon = Icons.Filled.CheckCircle, label = "Finalizar")
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                // Header Content
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("Resumen de Operaciones", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text("Bienvenido. Tienes 3 proyectos asignados para hoy.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    OutlinedButton(
                        onClick = { /* Filter */ },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Icon(Icons.Filled.FilterList, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Filtrar")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Section Title
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Proyectos Asignados", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp)) {
                        Text("${projects.size} TOTAL", fontSize = 12.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }

                // Grid of Projects
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 300.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(projects) { project ->
                        ProjectDashboardCard(project, onProjectClick)
                    }
                }
            }
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

@Composable
fun BottomBarItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, isSelected: Boolean = false) {
    val bgColor = if (isSelected) MaterialTheme.colorScheme.tertiaryContainer else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { }
            .background(bgColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = contentColor)
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = contentColor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDashboardCard(project: Project, onClick: (String) -> Unit) {
    // Definir color de estado de manera arbitraria o según el modelo
    val (statusColor, statusContainer, statusText, statusIcon) = when(project.id.hashCode() % 3) {
        0 -> listOf(Color(0xFF47607E), Color(0xFFC2DCFF), "Programado", Icons.Filled.Schedule)
        1 -> listOf(Color(0xFFBA1A1A), Color(0xFFFFDAD6), "En Campo", Icons.Filled.Engineering)
        else -> listOf(Color(0xFF47607E), Color(0xFFC2DCFF), "Programado", Icons.Filled.Schedule)
    }

    Card(
        onClick = { onClick(project.id) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            Box(modifier = Modifier.width(8.dp).fillMaxHeight().background(statusColor as Color))
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp).weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp)) {
                            Text("ID: ${project.id}", fontSize = 10.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                        Surface(color = statusContainer as Color, shape = RoundedCornerShape(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) {
                                Icon(statusIcon as androidx.compose.ui.graphics.vector.ImageVector, contentDescription = null, tint = statusColor as Color, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(statusText as String, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = statusColor as Color)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(project.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Filled.CorporateFare, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("CLIENTE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(project.client, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("SITIO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(project.siteName, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }

                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                
                Box(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)).padding(16.dp)) {
                    val isDownloaded = (statusText == "En Campo")
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDownloaded) MaterialTheme.colorScheme.surfaceVariant else PrimaryBlue,
                            contentColor = if (isDownloaded) MaterialTheme.colorScheme.onSurface else Color.White
                        ),
                        border = if (isDownloaded) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
                    ) {
                        Icon(
                            if (isDownloaded) Icons.Filled.CheckCircle else Icons.Filled.DownloadForOffline, 
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isDownloaded) "Descargado" else "Descargar Datos (Offline)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}