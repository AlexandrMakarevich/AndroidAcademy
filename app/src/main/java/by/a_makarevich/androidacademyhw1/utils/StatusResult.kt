package by.a_makarevich.androidacademyhw1.utils

sealed class StatusResult {
    object Loading: StatusResult()
    object Success: StatusResult()
    object Error: StatusResult()
}
