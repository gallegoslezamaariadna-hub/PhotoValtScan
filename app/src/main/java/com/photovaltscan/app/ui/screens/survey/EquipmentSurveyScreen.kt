package com.photovaltscan.app.ui.screens.survey

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
import com.photovaltscan.app.ui.components.MediaCaptureComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentSurveyScreen(
    onNavigateBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val scrollState = rememberScrollState()
    var isNewEquipment by remember { mutableStateOf(false) }
    var formKey by remember { mutableIntStateOf(0) }

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
                onClick = { 
                    isNewEquipment = true
                    formKey++
                },
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
        key(formKey) {
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
                    SurveyTextField(label = "Número de Medidor CFE", value = if (isNewEquipment) "" else "U264AR", modifier = Modifier.weight(1f))
                    SurveyTextField(label = "Medidores Internos", value = if (isNewEquipment) "" else "3 medidores Schneider PM5300", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                TransformerCard("Transformador 1 (T.F 1)", if (isNewEquipment) "" else "750", if (isNewEquipment) "" else "34,500V", if (isNewEquipment) "" else "480Y/277V", if (isNewEquipment) "" else "4.0")
                Spacer(modifier = Modifier.height(8.dp))
                TransformerCard("Transformador 2 (T.F 2)", if (isNewEquipment) "" else "750", if (isNewEquipment) "" else "13,200V", if (isNewEquipment) "" else "480Y/277V", if (isNewEquipment) "" else "4.0")
            }

            // 2. Tableros Principales
            SectionCard(
                title = "2. Tableros Principales (Switchboard 1 & 2)",
                subtitle = "Especificaciones técnicas de interruptores y tableros principales",
                icon = Icons.Filled.Bolt
            ) {
                SwitchboardCard("Switchboard 1", if (isNewEquipment) "" else "QEDCF126P", if (isNewEquipment) "" else "480V", if (isNewEquipment) "" else "1200", if (isNewEquipment) "" else "PG1200", if (isNewEquipment) "" else "65")
                Spacer(modifier = Modifier.height(8.dp))
                SwitchboardCard("Switchboard 2", if (isNewEquipment) "" else "QEDCF127P", if (isNewEquipment) "" else "480V", if (isNewEquipment) "" else "1200", if (isNewEquipment) "" else "PJ1200", if (isNewEquipment) "" else "65")
            }

            // 3. MEDICIÓN
            SectionCard(
                title = "3 - MEDICIÓN",
                subtitle = "Datos generales de medición actual del cliente (CFE)",
                icon = Icons.Filled.Speed
            ) {
                Text("Medición actual del cliente (CFE)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Column(modifier = Modifier.fillMaxWidth()) {
                    CheckboxWithLabel(label = "Media tensión", initialChecked = if (isNewEquipment) false else true)
                    CheckboxWithLabel(label = "Baja Tensión", initialChecked = false)
                    CheckboxWithLabel(label = "Otro", initialChecked = false)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("¿Cuantos clientes tiene la nave?", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                var clientSliderPos by remember { mutableFloatStateOf(if (isNewEquipment) 0f else 1f) }
                Slider(
                    value = clientSliderPos,
                    onValueChange = { clientSliderPos = it },
                    valueRange = 0f..5f,
                    steps = 4,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("0", fontSize = 12.sp)
                    Text("1", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                    Text("5", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                SurveyDropdown(label = "¿Cuenta con más de una medición el edificio?", options = listOf("Sí", "No"), selected = if (isNewEquipment) "" else "No", modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(16.dp))
                SurveyTextField(label = "Número de medidor", value = if (isNewEquipment) "" else "M586LM", modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(16.dp))
                SurveyTextField(
                    label = "Observaciones adicionales",
                    value = if (isNewEquipment) "" else "• Nombre del cliente 1 y No. De medidor:\n• Nombre del cliente 2 y No. De medidor:\n• Nombre del cliente 3 y No. De medidor:\n• Nombre del cliente 4 y No. De medidor:",
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 4. Cubierta
            SectionCard(
                title = "4 - Informacion general de cubierta",
                subtitle = "Equipamiento de cubierta",
                icon = Icons.Filled.Roofing
            ) {
                Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text("4.1.1 - De manera general marcar en fotos las medidas de los obstáculos y equipos", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(16.dp))
                    MediaCaptureComponent { }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text("4.1.2 - ¿El edificio cuenta con antenas o pararrayos en cubierta? (indicar en imágenes ubicación)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        CheckboxWithLabel(label = "Antenas", initialChecked = if (isNewEquipment) false else true)
                        CheckboxWithLabel(label = "Pararrayos", initialChecked = if (isNewEquipment) false else true)
                        CheckboxWithLabel(label = "Ambos", initialChecked = if (isNewEquipment) false else true)
                        CheckboxWithLabel(label = "N/A", initialChecked = false)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                        Text("4.1.3 - Imagen satelital o croquis de ubicación de las antenas y/o pararrayos", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(16.dp))
                        MediaCaptureComponent { }
                    }
                    Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                        Text("4.1.4 - En caso de existir pretil, indicar ubicación y altura.", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(16.dp))
                        SurveyTextField(
                            label = "",
                            value = if (isNewEquipment) "" else "Altura de pretil: es de más a menos 2.20 A 0.80\nAnexar fotografías de ubicación de pretil:",
                            singleLine = false
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text("4.1.5 - Marcar en fotografías la medida aproximada de la altura del pretil", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MediaCaptureComponent { }
                        MediaCaptureComponent { }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text("4.1.6 - Fotos generales de la cubierta del edificio", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MediaCaptureComponent { }
                        MediaCaptureComponent { }
                        MediaCaptureComponent { }
                        MediaCaptureComponent { }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text("4.1.7 - Observaciones adicionales", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(16.dp))
                    SurveyTextField(label = "", value = if (isNewEquipment) "" else "Existe una pendiente de 2 aguas", singleLine = false, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            // 5. Fotografías y videos
            SectionCard(
                title = "5 - Fotografías y vídeos de espacios en general",
                subtitle = "Carpeta en One Drive",
                icon = Icons.Filled.FolderShared
            ) {
                Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text("5.1.1 Liga De One Drive", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(16.dp))
                    SurveyTextField(label = "", value = if (isNewEquipment) "" else "https://beetmann0-my.sharepoint.com/personal/proyectos_beetmann_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fproyectos%5Fbeetmann%5Fcom%2FDocuments%2FPIRETH%2FINGENIERIAS%20PROLOGIS%202024%2FREY01202&viewid=2ba422a6%2D0718%2D47b3%2Db2c7%2D6b5a4df56b13", singleLine = false, modifier = Modifier.fillMaxWidth())
                }
            }

            // 6. Información de cierre
            SectionCard(
                title = "6 - Información de cierre",
                subtitle = "Cierre de levantamiento",
                icon = Icons.Filled.Verified
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                        Text("6.1.1 Nombre de quien guio el recorrido en sitio por parte del cliente.", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(16.dp))
                        SurveyTextField(label = "", value = if (isNewEquipment) "" else "NO SE SABE", modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(16.dp)) {
                        Text("6.1.2 Se hizo el vuelo de Dron", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(16.dp))
                        SurveyDropdown(label = "", options = listOf("Si", "No"), selected = if (isNewEquipment) "" else "Si", modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(64.dp))
        }
        }
    }
}

@Composable
fun CheckboxWithLabel(label: String, initialChecked: Boolean) {
    var checked by remember { mutableStateOf(initialChecked) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue)
        )
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
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