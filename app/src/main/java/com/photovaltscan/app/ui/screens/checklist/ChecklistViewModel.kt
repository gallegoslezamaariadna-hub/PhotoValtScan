package com.photovaltscan.app.ui.screens.checklist

import androidx.lifecycle.ViewModel
import com.photovaltscan.app.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChecklistViewModel @Inject constructor() : ViewModel() {

    // Plantilla Mock (Hardcoded para validación de UI como se acordó)
    private val mockTemplate = Template(
        id = "TPL-001",
        name = "Levantamiento Eléctrico FV",
        sections = listOf(
            Section(
                id = "SEC-01",
                order = 1,
                title = "01 Información General",
                subsections = listOf(
                    Subsection(
                        id = "SUB-01",
                        order = 1,
                        title = "Datos del Sistema",
                        questions = listOf(
                            Question("Q1", "¿Existe sistema FV?", QuestionType.BooleanChoice),
                            Question("Q2", "Número de módulos", QuestionType.Number, conditionalOn = "Q1", conditionalValue = "true"),
                            Question("Q3", "Fotografía del recibo CFE", QuestionType.PhotoEvidence)
                        )
                    )
                )
            ),
            Section(
                id = "SEC-02",
                order = 2,
                title = "02 Tableros",
                subsections = listOf(
                    Subsection(
                        id = "SUB-02",
                        order = 1,
                        title = "Tablero Principal",
                        questions = listOf(
                            Question("Q4", "Fotografía legible del interruptor principal", QuestionType.PhotoEvidence),
                            Question("Q5", "Tensión Fase-Fase (V)", QuestionType.Number),
                            Question("Q6", "Estado físico del tablero", QuestionType.MultipleChoice(listOf("Bueno", "Regular", "Malo")))
                        )
                    )
                )
            )
        )
    )

    private val _template = MutableStateFlow(mockTemplate)
    val template: StateFlow<Template> = _template.asStateFlow()

    private val _answers = MutableStateFlow<Map<String, String>>(emptyMap())
    val answers: StateFlow<Map<String, String>> = _answers.asStateFlow()

    private val _currentSectionIndex = MutableStateFlow(0)
    val currentSectionIndex: StateFlow<Int> = _currentSectionIndex.asStateFlow()

    fun updateAnswer(questionId: String, answer: String) {
        val current = _answers.value.toMutableMap()
        current[questionId] = answer
        _answers.value = current
    }

    fun setSection(index: Int) {
        if (index in mockTemplate.sections.indices) {
            _currentSectionIndex.value = index
        }
    }
}
