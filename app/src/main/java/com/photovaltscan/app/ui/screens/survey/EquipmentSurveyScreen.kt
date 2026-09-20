package com.photovaltscan.app.ui.screens.survey

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentSurveyScreen(
    onNavigateBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Registro de Equipos", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("PRJ-2023-8841 - REY00607", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Scanner */ }) {
                        Icon(Icons.Filled.QrCodeScanner, contentDescription = "Escanear")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* TODO: Nuevo Equipo */ },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nuevo Equipo")
            }
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
                    TextButton(onClick = onNavigateBack) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.onSurface)
                    }
                    Button(
                        onClick = onSaveSuccess,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar REY00607")
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
            // Header
            Card(
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp, topStart = 4.dp, bottomStart = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Box(modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(PrimaryBlue))
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text("Levantamiento de Ingeniería Industrial", fontWeight = FontWeight.Bold, color = PrimaryBlue)
                        Text(
                            "Formulario técnico avanzado para subestaciones, tableros principales, cubierta e interconexión fotovoltaica.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // 1. Subestación
            SectionCard(
                title = "1. Subestación (Transformadores T.F 1 & T.F 2 y CFE)",
                subtitle = "Registro dual de transformadores, medidor CFE e internos",
                icon = Icons.Filled.ElectricalServices
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SurveyTextField(label = "Número de Medidor CFE", value = "U264AR", modifier = Modifier.weight(1f))
                    SurveyTextField(label = "Medidores Internos", value = "3 medidores Schneider PM5300", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                TransformerCard("Transformador 1 (T.F 1)", "750", "34,500V", "480Y/277V", "4.0")
                Spacer(modifier = Modifier.height(8.dp))
                TransformerCard("Transformador 2 (T.F 2)", "750", "13,200V", "480Y/277V", "4.0")
            }

            // 2. Tableros Principales
            SectionCard(
                title = "2. Tableros Principales (Switchboard 1 & 2)",
                subtitle = "Especificaciones técnicas de interruptores y tableros principales",
                icon = Icons.Filled.Bolt
            ) {
                SwitchboardCard("Switchboard 1", "QEDCF126P", "480V", "1200", "PG1200", "65")
                Spacer(modifier = Modifier.height(8.dp))
                SwitchboardCard("Switchboard 2", "QEDCF127P", "480V", "1200", "PJ1200", "65")
            }

            // 3. Cubierta
            SectionCard(
                title = "3. Información de Cubierta, Parapetos y Pararrayos",
                subtitle = "Características estructurales del techo, parapetos y protección contra rayos",
                icon = Icons.Filled.Roofing
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SurveyDropdown(label = "Tipo de Recubrimiento", options = listOf("TPO (Termoplástico)", "PVC", "Lámina Galvanizada", "Concreto"), selected = "TPO (Termoplástico)", modifier = Modifier.weight(1f))
                    SurveyTextField(label = "Rango de Altura", value = "2.20 a 0.80 m", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SurveyTextField(label = "Pendiente de Techo", value = "Pendiente de 2 aguas", modifier = Modifier.weight(1f))
                    SurveyTextField(label = "Pararrayos / Antenas", value = "Sí (4 pararrayos)", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                SurveyTextField(
                    label = "Observaciones de Equipos HVAC y Obstáculos",
                    value = "Se identificaron 4 unidades paquete HVAC en el sector norte. Distancia mínima requerida de libranza para sistemas fotovoltaicos: 3.0 m.",
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 4. Punto de Interconexión
            SectionCard(
                title = "4. Punto de Interconexión y Cierre",
                subtitle = "Punto óptimo de interconexión fotovoltaica y validación con cliente",
                icon = Icons.Filled.Hub
            ) {
                SurveyDropdown(
                    label = "Posible Punto de Interconexión",
                    options = listOf("Tablero Principal", "Transformador", "Celda de Media Tensión"),
                    selected = "Tablero Principal",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                SurveyTextField(label = "Nombre del Guía", value = "Ing. Roberto Mendoza", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                SurveyTextField(
                    label = "Notas de Cierre y Verificación",
                    value = "Recorrido completo realizado sin novedades. Se entregó copia preliminar de bitácora al cliente.",
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun SectionCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(PrimaryBlue.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = PrimaryBlue)
                }
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Divider(modifier = Modifier.padding(bottom = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            content()
        }
    }
}

@Composable
fun TransformerCard(title: String, power: String, vPrim: String, vSec: String, imp: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .background(PrimaryBlue.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SurveyTextField(label = "Potencia (kVA)", value = power, modifier = Modifier.weight(1f))
                SurveyTextField(label = "Tensión Primaria", value = vPrim, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SurveyTextField(label = "Tensión Secundaria", value = vSec, modifier = Modifier.weight(1f))
                SurveyTextField(label = "Impedancia (Z%)", value = imp, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SwitchboardCard(title: String, cat: String, vWork: String, current: String, itm: String, cap: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .background(PrimaryBlue.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SurveyTextField(label = "Núm. Catálogo", value = cat, modifier = Modifier.weight(1f))
                SurveyTextField(label = "Tensión (V)", value = vWork, modifier = Modifier.weight(1f))
                SurveyTextField(label = "Corriente (A)", value = current, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SurveyTextField(label = "Modelo ITM", value = itm, modifier = Modifier.weight(1f))
                SurveyTextField(label = "Cap. Interr. (kA)", value = cap, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SurveyTextField(label: String, value: String, modifier: Modifier = Modifier, singleLine: Boolean = true) {
    var text by remember { mutableStateOf(value) }
    Column(modifier = modifier) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth().then(if(!singleLine) Modifier.heightIn(min = 80.dp) else Modifier),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyDropdown(label: String, options: List<String>, selected: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(selected) }

    Column(modifier = modifier) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                textStyle = MaterialTheme.typography.bodySmall,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
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