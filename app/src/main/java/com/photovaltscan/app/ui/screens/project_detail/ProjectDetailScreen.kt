package com.photovaltscan.app.ui.screens.project_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.photovaltscan.app.R
import com.photovaltscan.app.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    onNavigateBack: () -> Unit,
    onStartChecklist: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Detalle del Proyecto", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("PRJ-2023-089A", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onStartChecklist("PRJ-2023-089A") },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(Icons.Filled.PlayCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Iniciar Visita", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            
            // Context header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Proyectos", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                    Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    Text("PRJ-2023-089A", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                        Box(modifier = Modifier.size(6.dp).background(PrimaryBlue, CircleShape))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("En Ejecución", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Header Project Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                    Box(modifier = Modifier.width(6.dp).fillMaxHeight().background(PrimaryBlue))
                    Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                        Text("Renovación Planta Tratamiento Norte", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text("Fase 2: Inspección Estructural y Calibración de Equipos", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            InfoItem(icon = Icons.Filled.LocationOn, label = "DIRECCIÓN FÍSICA", value = "Av. Industrial Sector 4, Lote 12B\nZona Franca, Parque Tecnológico")
                            InfoItem(icon = Icons.Filled.MyLocation, label = "COORDENADAS GPS", value = "Lat: 19.4326° N\nLon: 99.1332° W")
                            InfoItem(icon = Icons.Filled.Person, label = "RESPONSABLE ASIGNADO", value = "Arq. Roberto Méndez")
                        }
                    }
                }
            }

            Text("Métricas de Avance", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            
            // Progress Dashboard Grid
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Avance General", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Consolidado de todos los módulos", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Icon(Icons.Filled.DonutLarge, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text("82", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
                        Text("%", fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 12.dp, start = 4.dp))
                        Spacer(modifier = Modifier.width(24.dp))
                        LinearProgressIndicator(
                            progress = 0.82f,
                            modifier = Modifier.weight(1f).height(8.dp).padding(bottom = 16.dp),
                            color = PrimaryBlue,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    }
                }
            }

            // Sub-metrics
            SubMetricCard(title = "INFORMACIÓN", progress = 1f, progressText = "100%", icon = Icons.Filled.Description)
            SubMetricCard(title = "FOTOS", progress = 0.92f, progressText = "92%", icon = Icons.Filled.PhotoCamera)
            SubMetricCard(title = "MEDICIONES", progress = 0.85f, progressText = "85%", icon = Icons.Filled.Straighten)

            // Carpeta Normativa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Estructura de Carpetas del Sitio", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Verificación de cumplimiento de repositorio", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Icon(Icons.Filled.Folder, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        FolderItem("01 Información del Sitio", "Croquis y Recibo CFE", "Completo", Color(0xFF43A047), Color(0xFFE8F5E9))
                        FolderItem("02 Documentación Existente", "Diagramas Unifilares e Ingeniería", "Completo", Color(0xFF43A047), Color(0xFFE8F5E9))
                        FolderItem("03 Fotografías", "Dron, Cubierta, FV, Acometida", "Completo", Color(0xFF43A047), Color(0xFFE8F5E9))
                        FolderItem("04 Videos", "Recorrido Narrado y Dron 360°", "En Proceso", MaterialTheme.colorScheme.onSecondaryContainer, MaterialTheme.colorScheme.secondaryContainer)
                        FolderItem("05 Reporte de Levantamiento", "Plantilla Oficial", "Pendiente", MaterialTheme.colorScheme.onSecondaryContainer, MaterialTheme.colorScheme.secondaryContainer)
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun SubMetricCard(title: String, progress: Float, progressText: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier.weight(1f).height(6.dp),
                            color = PrimaryBlue,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(progressText, fontSize = 14.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (progress >= 1f) {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = PrimaryBlue)
            }
        }
    }
}

@Composable
fun FolderItem(title: String, desc: String, statusText: String, statusColor: Color, statusBg: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Folder, contentDescription = null, tint = PrimaryBlue)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Surface(color = statusBg, shape = RoundedCornerShape(12.dp)) {
            Text(statusText, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = statusColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
        }
    }
}