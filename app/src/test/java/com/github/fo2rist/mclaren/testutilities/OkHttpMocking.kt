package com.github.fo2rist.mclaren.testutilities

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.mockito.Mockito


fun OkHttpClient.overrideAnswersToSuccess() {
    val immediateRequestMock = createSuccessfulCallMock()
    whenever(this.newCall(any())).thenReturn(immediateRequestMock)
}

fun createSuccessfulCallMock(): Call {
    val requestMock = mock<Call>(defaultAnswer = Mockito.RETURNS_MOCKS)
    val responseMock = mock<Response>()
    //make it successful
    whenever(responseMock.isSuccessful).thenReturn(true)
    //make it return immediately
    whenever(requestMock.enqueue(any())).thenAnswer {
        val callback: Callback = it.getArgument(0)
        callback.onResponse(requestMock, responseMock)
    }
    return requestMock
}
