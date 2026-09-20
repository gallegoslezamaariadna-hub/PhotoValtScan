package com.photovaltscan.app.ui.screens.validation

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.photovaltscan.app.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidationScreen(
    onNavigateBack: () -> Unit,
    onSyncAndClose: () -> Unit
) {
    val scrollState = rememberScrollState()

    var checkFolders by remember { mutableStateOf(true) }
    var checkElectrical by remember { mutableStateOf(true) }
    var checkVideos by remember { mutableStateOf(true) }
    var reportThermal by remember { mutableStateOf(true) }
    var termsAccepted by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Resumen y Validación Final", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Text("PRJ-2023-8841 - Planta Industrial Norte", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onNavigateBack) {
                        Text("Revisar Datos", color = MaterialTheme.colorScheme.onSurface)
                    }
                    Button(
                        onClick = onSyncAndClose,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        enabled = termsAccepted
                    ) {
                        Icon(Icons.Filled.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar y Sincronizar")
                    }
                }
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
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

            // Progress Summary Bento Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Estado General del Levantamiento", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LinearProgressIndicator(
                            progress = 1f,
                            modifier = Modifier.weight(1f).height(8.dp),
                            color = PrimaryBlue,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("100% COMPLETADO", fontSize = 12.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        MetricItem(title = "Checklists", value = "24/24", icon = Icons.Filled.FactCheck, iconColor = Color(0xFF1B6B3E))
                        MetricItem(title = "Equipos", value = "12", icon = Icons.Filled.Inventory2, iconColor = PrimaryBlue)
                        MetricItem(title = "Hallazgos", value = "3", icon = Icons.Filled.Warning, iconColor = MaterialTheme.colorScheme.error, subtitle = "(1 Crítico)")
                    }
                }
            }

            // Warning Attention
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFBE188).copy(alpha = 0.3f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF7E5700).copy(alpha = 0.5f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Filled.Info, contentDescription = null, tint = Color(0xFF7E5700))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("ATENCIÓN REQUERIDA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7E5700))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Existen hallazgos críticos (Corrosión en Chiller) que requieren acción correctiva inmediata. Asegúrese de que el responsable del sitio firme el acuse de recibo de esta información.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            // Thermal Detection
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Filled.LocalFireDepartment, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Detección y Reporte de Puntos Calientes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Se han registrado anomalías térmicas en tableros principales de distribución con un delta de temperatura superior a 15°C sobre la norma.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = reportThermal, onCheckedChange = { reportThermal = it }, colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.error))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reportar anomalía térmica crítica a ingeniería", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Surface(color = MaterialTheme.colorScheme.errorContainer, shape = RoundedCornerShape(12.dp)) {
                                Text("Crítico", fontSize = 10.sp, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }
                    }
                }
            }

            // Checklist Pre-Retiro
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column {
                    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)).fillMaxWidth().padding(16.dp)) {
                        Text("Checklist de Pre-Retiro de Instalación", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Confirmación obligatoria de entregables antes de abandonar el sitio", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ChecklistItem(checked = checkFolders, onCheckedChange = { checkFolders = it }, title = "Carpetas 01 a 06 completas:", desc = "01 Información del Sitio, 02 Documentación Existente, 03 Fotografías, 04 Videos, 05 Reporte de Levantamiento y 06 CAD / Modelos 3D verificados y sincronizados.")
                        ChecklistItem(checked = checkElectrical, onCheckedChange = { checkElectrical = it }, title = "Mediciones eléctricas completas:", desc = "Voltajes, corrientes, factor de potencia y armónicos debidamente registrados.")
                        ChecklistItem(checked = checkVideos, onCheckedChange = { checkVideos = it }, title = "Videos con narración de audio:", desc = "Recorrido narrado y vuelo de dron 360° grabados y validados sin ruido excesivo.")
                    }
                }
            }

            // Signature Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column {
                    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)).fillMaxWidth().padding(16.dp)) {
                        Text("Firma de Conformidad", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("El responsable del sitio certifica la veracidad de los datos recopilados.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        ValidationTextField(label = "NOMBRE DEL RESPONSABLE (SITIO)", value = "Ing. Laura Gómez", icon = Icons.Filled.Person)
                        ValidationTextField(label = "CARGO / PUESTO", value = "Gerente de Planta", icon = Icons.Filled.Badge)

                        // Canvas Placeholder
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                                Text("FIRMA AUTÓGRAFA DIGITAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Limpiar panel", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue, textDecoration = TextDecoration.Underline, modifier = Modifier.clickable {})
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(2.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.Draw, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Firmar aquí", color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f))
                                }
                            }
                        }

                        // Terms
                        Row(verticalAlignment = Alignment.Top) {
                            Checkbox(checked = termsAccepted, onCheckedChange = { termsAccepted = it }, colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Confirmo que he revisado el resumen del levantamiento, incluyendo los hallazgos críticos, y autorizo el cierre de esta inspección.", 
                                fontSize = 14.sp, 
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun MetricItem(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, iconColor: Color, subtitle: String = "") {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
        if (subtitle.isNotEmpty()) {
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ChecklistItem(checked: Boolean, onCheckedChange: (Boolean) -> Unit, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange, colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue))
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.padding(top = 12.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(desc, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun ValidationTextField(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    var text by remember { mutableStateOf(value) }
    Column {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            leadingIcon = { Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.outlineVariant) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}