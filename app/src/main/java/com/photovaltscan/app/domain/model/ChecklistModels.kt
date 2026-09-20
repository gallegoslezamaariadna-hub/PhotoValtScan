package com.photovaltscan.app.domain.model

data class Template(
    val id: String,
    val name: String,
    val sections: List<Section>
)

data class Section(
    val id: String,
    val order: Int,
    val title: String,
    val subsections: List<Subsection>
)

data class Subsection(
    val id: String,
    val order: Int,
    val title: String,
    val questions: List<Question>
)

data class Question(
    val id: String,
    val text: String,
    val type: QuestionType,
    val isRequired: Boolean = true,
    val conditionalOn: String? = null, // ID de pregunta anterior
    val conditionalValue: String? = null // Valor que activa esta pregunta
)

sealed class QuestionType {
    object Text : QuestionType()
    object Number : QuestionType()
    data class MultipleChoice(val options: List<String>) : QuestionType()
    object BooleanChoice : QuestionType()
    object PhotoEvidence : QuestionType()
}
