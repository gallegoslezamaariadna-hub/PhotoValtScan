package com.photovaltscan.app.ui.screens.findings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.photovaltscan.app.ui.theme.PrimaryBlue

val StatusHigh = Color(0xFFBA1A1A)
val StatusHighContainer = Color(0xFFFFDAD6)
val StatusMedium = Color(0xFF984061)
val StatusMediumContainer = Color(0xFFFFD9E2)
val StatusLow = Color(0xFF6A5F00)
val StatusLowContainer = Color(0xFFF6E469)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindingsScreen(
    onNavigateBack: () -> Unit,
    onContinue: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Registro de Hallazgos", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.errorContainer,
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Box(modifier = Modifier.size(6.dp).background(MaterialTheme.colorScheme.error, RoundedCornerShape(50)))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("3 Activos", fontSize = 10.sp, color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Text("PRJ-2023-8841 - Planta Industrial Norte", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    Button(
                        onClick = { /* TODO: Nuevo */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.padding(end = 8.dp).height(36.dp)
                    ) {
                        Icon(Icons.Filled.AddCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Nuevo", fontSize = 12.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
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
                    OutlinedButton(onClick = { /* Opciones */ }) {
                        Text("Opciones", color = MaterialTheme.colorScheme.onSurface)
                    }
                    Button(
                        onClick = onContinue,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer, contentColor = MaterialTheme.colorScheme.onTertiaryContainer)
                    ) {
                        Text("Continuar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            
            // List of Findings
            Text("Hallazgos Activos", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            
            FindingCard(
                id = "HLZ-001",
                level = "CRÍTICO",
                title = "Corrosión severa en base de Chiller Principal",
                desc = "Se detectó oxidación avanzada en soportes antivibratorios que compromete la estabilidad estructural del equipo.",
                statusColor = StatusHigh,
                statusContainer = StatusHighContainer,
                isActive = true
            )
            FindingCard(
                id = "HLZ-002",
                level = "MAYOR",
                title = "Fuga de refrigerante en válvula de expansión",
                desc = "Presencia de aceite y escarcha alrededor de la válvula del circuito B.",
                statusColor = StatusMedium,
                statusContainer = StatusMediumContainer,
                isActive = false
            )
            FindingCard(
                id = "HLZ-003",
                level = "MENOR",
                title = "Etiqueta de identificación ilegible",
                desc = "La placa de datos del Tablero TDA-1 está desgastada e ilegible.",
                statusColor = StatusLow,
                statusContainer = StatusLowContainer,
                isActive = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Detail Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Detalles del Hallazgo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Row {
                            IconButton(onClick = {}) { Icon(Icons.Filled.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.onSurfaceVariant) }
                            IconButton(onClick = {}) { Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error) }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    FindingTextField(label = "Título de la Anomalía", value = "Corrosión severa en base de Chiller Principal")
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    FindingDropdown(label = "Equipo Asociado", options = listOf("CHL-01A (Chiller Principal)", "TDA-01", "General"), selected = "CHL-01A (Chiller Principal)")
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    FindingDropdown(
                        label = "Severidad", 
                        options = listOf("Crítico (Peligro Inminente)", "Mayor", "Menor"), 
                        selected = "Crítico (Peligro Inminente)",
                        customColor = StatusHigh,
                        customContainer = StatusHighContainer.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    FindingTextField(
                        label = "Descripción Detallada", 
                        value = "Se detectó oxidación avanzada en soportes antivibratorios de la pata sureste. La pérdida de material compromete la estabilidad estructural del equipo. Se recomienda reemplazo urgente para evitar daño mayor por vibración excesiva durante carga máxima.",
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Evidencia Visual", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .border(2.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
                                    Text("Añadir", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                }
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                    .clip(RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Image, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Box(modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp).background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 2.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.Draw, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text("Anotada", fontSize = 8.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("Guardar Cambios", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FindingCard(id: String, level: String, title: String, desc: String, statusColor: Color, statusContainer: Color, isActive: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (!isActive) Modifier.background(Color.Transparent) else Modifier),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = if (isActive) androidx.compose.foundation.BorderStroke(2.dp, PrimaryBlue) 
                 else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        elevation = if (isActive) CardDefaults.cardElevation(defaultElevation = 2.dp) else CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            Box(modifier = Modifier.width(6.dp).fillMaxHeight().background(statusColor))
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp)) {
                        Text(id, fontSize = 10.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                    Surface(color = statusContainer, shape = RoundedCornerShape(4.dp)) {
                        Text(level, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = statusColor, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun FindingTextField(label: String, value: String, singleLine: Boolean = true) {
    var text by remember { mutableStateOf(value) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth().then(if(!singleLine) Modifier.heightIn(min = 100.dp) else Modifier),
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindingDropdown(
    label: String, 
    options: List<String>, 
    selected: String, 
    customColor: Color? = null,
    customContainer: Color? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(selected) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            val colors = if (customColor != null && customContainer != null) {
                OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = customContainer,
                    unfocusedContainerColor = customContainer,
                    focusedTextColor = customColor,
                    unfocusedTextColor = customColor,
                    focusedBorderColor = customColor,
                    unfocusedBorderColor = customColor
                )
            } else {
                OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            }
            
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = if(customColor != null) FontWeight.Bold else FontWeight.Normal),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = colors
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = MaterialTheme.typography.bodySmall) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}