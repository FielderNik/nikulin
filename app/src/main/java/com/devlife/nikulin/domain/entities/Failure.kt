package com.devlife.nikulin.domain.entities

sealed class Failure(open val exception: Throwable? = null) {
    class RemoteFailure(exception: Throwable): Failure(exception)
    class DataFailure(exception: Throwable? = null): Failure(exception)
}