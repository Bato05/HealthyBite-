package com.example.healthybite.view

/**
 *
 * It is used as a wrapper for data that is exposed
 * through a LiveData and that represents a single event (such as navigation or Toasts).
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allows external read but not external write

    /**
     * Return the contents and avoid using them again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}