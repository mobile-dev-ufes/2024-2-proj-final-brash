package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

/**
 * Enum class representing the different categories or stages of learning.
 * These categories can be used to classify the status of a particular learning material or concept.
 *
 * @property NOVO Represents a new item or concept that is being introduced for the first time.
 * @property RECENTE Represents a recently learned item or concept.
 * @property MADURO Represents an item or concept that has been mastered or is mature.
 * @property APRENDENDO Represents an item or concept that is currently being learned.
 * @property REAPRENDENDO Represents an item or concept that is being relearned after a period of time.
 */
enum class CategoriaDoAprendizado {
    NOVO, RECENTE, MADURO, APRENDENDO, REAPRENDENDO
}

