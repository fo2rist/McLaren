package com.github.fo2rist.mclaren.tests.utils

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.utils.ResourcesUtils
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.FileNotFoundException

private const val EXISTING_CIRCUIT = "australia"
private const val NOT_EXISTING_CIRCUIT = "something that does not exist"

@RunWith(AndroidJUnit4::class)
class ResourcesUtilsTest {

    private val context by lazy { InstrumentationRegistry.getTargetContext() }

    @Test
    fun test_getCircuitImageUriById_gives_correctUri_for_ExistingCircuit() {
        val imageResourceUri = ResourcesUtils.getCircuitImageUriById(EXISTING_CIRCUIT)

        assertNotEquals(0, context.contentResolver.openInputStream(imageResourceUri)!!.available())
    }

    @Test(expected = FileNotFoundException::class)
    fun test_getCircuitImageUriById_gives_notResolvableUri_for_NotExistingCircuit() {
        val imageResourceUri = ResourcesUtils.getCircuitImageUriById(NOT_EXISTING_CIRCUIT)

        context.contentResolver.openInputStream(imageResourceUri)
    }
}
