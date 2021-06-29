package com.github.fo2rist.mclaren.utils

import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


private const val EXISTING_CIRCUIT = "monaco_monte_carlo"
private const val NOT_EXISTING_CIRCUIT = "something that does not exist"

private const val EXISTING_DRIVER = "norris"

@RunWith(RobolectricTestRunner::class)
class ResourcesUtilsTest {
    val context: Context by lazy { ApplicationProvider.getApplicationContext() }

    @Test
    fun `resourceUriToId gives 0 for incorrect URI`() {
        assertEquals(0, ResourcesUtils.resourceUriToId(context.resources,
                Uri.parse("some://incorrect")))
        assertEquals(0, ResourcesUtils.resourceUriToId(context.resources,
                Uri.parse("some://com.appname/drawable")))
        assertEquals(0, ResourcesUtils.resourceUriToId(context.resources,
                Uri.parse("some://com.appname/drawable/name")))
    }

    @Test
    fun `resourceUriToId gives non 0 for correct URI`() {
        assertNotEquals(0, ResourcesUtils.resourceUriToId(context.resources,
                Uri.parse("some://com.github.fo2rist.mclaren/drawable/ic_info")))
    }

    @Test
    fun `getCircuitImageUriById gives CorrectUri for ExistingCircuit`() {
        val imageResourceUri = ResourcesUtils.getCircuitImageUriById(EXISTING_CIRCUIT)

        assertNotEquals(0, ResourcesUtils.resourceUriToId(context.resources, imageResourceUri))
    }

    fun `getCircuitImageUriById gives NotResolvableUri for NotExistingCircuit`() {
        val imageResourceUri = ResourcesUtils.getCircuitImageUriById(NOT_EXISTING_CIRCUIT)

        assertEquals(0, ResourcesUtils.resourceUriToId(context.resources, imageResourceUri))
    }

    @Test
    fun `getCircuitDetailedImageUriById gives CorrectUri for ExistingCircuit`() {
        val imageResourceUri = ResourcesUtils.getCircuitDetailedImageUriById(EXISTING_CIRCUIT)

        assertNotEquals(0, ResourcesUtils.resourceUriToId(context.resources, imageResourceUri))
    }

    @Test
    fun `getCircuitDrsImageUriById gives CorrectUri for ExistingCircuit`() {
        val imageResourceUri = ResourcesUtils.getCircuitDrsImageUriById(EXISTING_CIRCUIT)

        assertNotEquals(0, ResourcesUtils.resourceUriToId(context.resources, imageResourceUri))
    }

    @Test
    fun `getCircuitSectorsImageUriById gives CorrectUri for ExistingCircuit`() {
        val imageResourceUri = ResourcesUtils.getCircuitSectorsImageUriById(EXISTING_CIRCUIT)

        assertNotEquals(0, ResourcesUtils.resourceUriToId(context.resources, imageResourceUri))
    }

    @Test
    fun `getCircuitTurnsImageUriById gives CorrectUri for ExistingCircuit`() {
        val imageResourceUri = ResourcesUtils.getCircuitTurnsImageUriById(EXISTING_CIRCUIT)

        assertNotEquals(0, ResourcesUtils.resourceUriToId(context.resources, imageResourceUri))
    }

    @Test
    fun `getDriverPortraitResUri gives CorrectUri for ExistingDriver`() {
        val imageResourceUri = ResourcesUtils.getDriverPortraitResUri(EXISTING_DRIVER)

        assertNotEquals(0, ResourcesUtils.resourceUriToId(context.resources, imageResourceUri))
    }

    @Test
    fun `getHelmetResId gives CorrectId for ExistingDriver`() {
        val resourceId = ResourcesUtils.getHelmetResId(context.resources, EXISTING_DRIVER)

        assertNotEquals(0, resourceId)
    }

    @Test
    fun `getHelmetResId gives 0 for NotExistingDriver`() {
        val resourceId = ResourcesUtils.getHelmetResId(context.resources, EXISTING_DRIVER)

        assertNotEquals(0, resourceId)
    }
}
