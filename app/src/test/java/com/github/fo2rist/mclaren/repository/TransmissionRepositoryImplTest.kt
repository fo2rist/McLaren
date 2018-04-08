package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.utils.custommatchers.anyKotlinObject
import com.github.fo2rist.mclaren.web.TransmissionWebService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class TransmissionRepositoryImplTest {
    private lateinit var repository: TransmissionRepository
    private lateinit var mockWebService: TransmissionWebService

    @Before
    fun setUp() {
        mockWebService = mock(TransmissionWebService::class.java)
        repository = TransmissionRepositoryImpl(mockWebService)
    }

    @Test
    fun test_loadRepository_callsWebService() {
        repository.loadTransmission()

        verify(mockWebService).requestTransmission(anyKotlinObject())
    }
}
