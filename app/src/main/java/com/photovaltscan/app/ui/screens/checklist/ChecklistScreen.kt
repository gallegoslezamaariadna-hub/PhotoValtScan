package com.photovaltscan.app.ui.screens.checklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.photovaltscan.app.domain.model.Question
import com.photovaltscan.app.domain.model.QuestionType
import com.photovaltscan.app.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen(
    onNavigateBack: () -> Unit,
    viewModel: ChecklistViewModel = hiltViewModel()
) {
    val template by viewModel.template.collectAsState()
    val currentSectionIndex by viewModel.currentSectionIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(template.name) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ScrollableTabRow(
                selectedTabIndex = currentSectionIndex,
                edgePadding = 8.dp,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[currentSectionIndex]),
                        color = PrimaryBlue
                    )
                }
            ) {
                template.sections.forEachIndexed { index, section ->
                    Tab(
                        selected = currentSectionIndex == index,
                        onClick = { viewModel.setSection(index) },
                        text = {
                            Text(
                                section.title,
                                color = if (currentSectionIndex == index) PrimaryBlue else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    )
                }
            }

            val currentSection = template.sections[currentSectionIndex]
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                currentSection.subsections.forEach { subsection ->
                    item {
                        Text(
                            text = subsection.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryBlue,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(subsection.questions) { question ->
                        // Lógica para campos condicionales
                        val showQuestion = if (question.conditionalOn != null) {
                            answers[question.conditionalOn] == question.conditionalValue
                        } else true

                        if (showQuestion) {
                            QuestionItem(
                                question = question,
                                answer = answers[question.id],
                                onAnswerChanged = { viewModel.updateAnswer(question.id, it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionItem(
    question: Question,
    answer: String?,
    onAnswerChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.text, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            when (val type = question.type) {
                is QuestionType.Text, is QuestionType.Number -> {
                    OutlinedTextField(
                        value = answer ?: "",
                        onValueChange = onAnswerChanged,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is QuestionType.BooleanChoice -> {
                    Row {
                        FilterChip(
                            selected = answer == "true",
                            onClick = { onAnswerChanged("true") },
                            label = { Text("Sí") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = answer == "false",
                            onClick = { onAnswerChanged("false") },
                            label = { Text("No") }
                        )
                    }
                }
                is QuestionType.MultipleChoice -> {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        type.options.forEach { option ->
                            FilterChip(
                                selected = answer == option,
                                onClick = { onAnswerChanged(option) },
                                label = { Text(option) }
                            )
                        }
                    }
                }
                is QuestionType.PhotoEvidence -> {
                    Button(
                        onClick = { /* TODO: Abrir cámara */ },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Filled.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tomar Fotografía")
                    }
                }
            }
        }
    }
}
