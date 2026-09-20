package com.photovaltscan.app.ui.screens.sync

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.photovaltscan.app.ui.theme.PrimaryBlue
import com.photovaltscan.app.ui.theme.SecondaryGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncScreen(
    onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var isSyncing by remember { mutableStateOf(false) }
    var syncProgress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Centro de Sincronización", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
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
            // Main Sync Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (isSyncing) Icons.Filled.Sync else Icons.Filled.CloudSync,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = PrimaryBlue
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        if (isSyncing) "Sincronizando con el servidor..." else "Estado Actual: Sincronizado",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        "Última sincronización: Hoy, 10:45 AM",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if (isSyncing) {
                        LinearProgressIndicator(
                            progress = syncProgress,
                            modifier = Modifier.fillMaxWidth().height(8.dp),
                            color = PrimaryBlue,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("${(syncProgress * 100).toInt()}%", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (!isSyncing) {
                                isSyncing = true
                                syncProgress = 0f
                                scope.launch {
                                    while (syncProgress < 1f) {
                                        delay(100)
                                        syncProgress += 0.05f
                                    }
                                    isSyncing = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        enabled = !isSyncing
                    ) {
                        Icon(Icons.Filled.Sync, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isSyncing) "SINCRONIZANDO..." else "INICIAR SINCRONIZACIÓN MANUAL", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Sync Queue
            Text("Cola de Sincronización", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SyncQueueItem(title = "PRJ-2023-089A - Planta Tratamiento", subtitle = "3 Nuevos Hallazgos, 12 Fotografías", status = "Pendiente", isError = false)
                SyncQueueItem(title = "PRJ-2023-088B - Subestación T-02", subtitle = "Formulario de Levantamiento Completado", status = "Pendiente", isError = false)
                SyncQueueItem(title = "PRJ-2023-075C - Torre Central", subtitle = "Fallo al subir video (Conexión inestable)", status = "Error", isError = true)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sync Settings
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Configuración de Red", fontWeight = FontWeight.Bold, color = PrimaryBlue)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    var syncWifiOnly by remember { mutableStateOf(true) }
                    var syncHighRes by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Sincronizar solo con Wi-Fi", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("Evita el uso de datos móviles (Recomendado)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = syncWifiOnly, onCheckedChange = { syncWifiOnly = it }, colors = SwitchDefaults.colors(checkedThumbColor = PrimaryBlue, checkedTrackColor = PrimaryBlue.copy(alpha = 0.5f)))
                    }
                    
                    Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Subir fotos en Alta Resolución", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("Consumirá más ancho de banda y tiempo", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = syncHighRes, onCheckedChange = { syncHighRes = it }, colors = SwitchDefaults.colors(checkedThumbColor = PrimaryBlue, checkedTrackColor = PrimaryBlue.copy(alpha = 0.5f)))
                    }
                }
            }
        }
    }
}

@Composable
fun SyncQueueItem(title: String, subtitle: String, status: String, isError: Boolean) {
    val statusColor = if (isError) MaterialTheme.colorScheme.error else SecondaryGreen
    val bgColor = if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isError) Icons.Filled.Error else Icons.Filled.UploadFile,
            contentDescription = null,
            tint = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(color = bgColor, shape = RoundedCornerShape(12.dp)) {
            Text(status, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = statusColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
        }
    }
}