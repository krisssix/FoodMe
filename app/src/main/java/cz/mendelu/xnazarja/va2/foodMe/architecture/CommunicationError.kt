package cz.mendelu.xnazarja.va2.foodMe.architecture

data class CommunicationError(
    val code: Int,
    var message: String?,
    var secondaryMessage: String? = null)