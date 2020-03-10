package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.testdata.DUMMY_URL
import com.github.fo2rist.mclaren.testdata.FakeRemoteConfigService
import com.github.fo2rist.mclaren.ui.models.Driver
import com.github.fo2rist.mclaren.ui.models.DriverId.ALONSO
import com.github.fo2rist.mclaren.ui.models.DriverId.BUTTON
import com.github.fo2rist.mclaren.ui.models.DriverProperty
import com.github.fo2rist.mclaren.ui.models.DriverProperty.DateOfBirth
import com.github.fo2rist.mclaren.ui.models.DriverProperty.Name
import com.github.fo2rist.mclaren.ui.models.DriverProperty.Place
import com.github.fo2rist.mclaren.ui.models.DriverProperty.Points
import com.github.fo2rist.mclaren.ui.models.DriverProperty.TeamPageLink
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private val DRIVER_A = ALONSO
private val DRIVER_B = BUTTON

private const val NAME = "Some Name"
private const val DOB = "29.07.1981"
private const val NATIONALITY = "Some Country"
private const val TWITTER = """@twitter"""
private const val TAG = """#AB123"""
private const val CHAMPIONSHIPS = "1"
private const val POLES = "2"
private const val FASTEST_LAPS = "3"
private const val PLACE = "4"
private const val POINTS = "5"
private const val FINISHES = """1st x 23"""
private const val PODIUMS = "100"

private const val DRIVER_PROPERTIES = """{
        "Name": "$NAME",
        "DateOfBirth": "$DOB",
        "Nationality": "$NATIONALITY",
        "Twitter": "$TWITTER",
        "Tag": "$TAG",
        "HeritagePageLink": "$DUMMY_URL",
        "TeamPageLink": "$DUMMY_URL",
        "WorldChampionships": "$CHAMPIONSHIPS",
        "PolePositions": "$POLES",
        "FastestLaps": "$FASTEST_LAPS",
        "Place": "$PLACE",
        "Points": "$POINTS",
        "BestFinish": "$FINISHES",
        "Podiums": "$PODIUMS"
    }"""

private val ONE_DRIVER_INFO_MODEL = """{
    "$DRIVER_A": $DRIVER_PROPERTIES
}"""

private val TWO_DRIVERS_INFO_MODEL = """{
    "$DRIVER_A": $DRIVER_PROPERTIES,
    "$DRIVER_B": $DRIVER_PROPERTIES
}"""

private val SINGLE_DRIVER_LIST = """[
    $DRIVER_A
]"""

private val TWO_DRIVERS_LIST_REVERSED = """[
    $DRIVER_B,
    $DRIVER_A
]"""


@RunWith(JUnit4::class)
class DriversRepositoryImplTest {

    @Test
    fun `empty config produces empty model`() {
        val driversRepository = DriversRepositoryImpl(FakeRemoteConfigService())

        assertEquals(0, driversRepository.driversList.size)
    }

    @Test
    fun `unknown drivers aren't added to the list`() {
        val driversRepository = DriversRepositoryImpl(
                FakeRemoteConfigService(drivers = ONE_DRIVER_INFO_MODEL, driversOrderList = TWO_DRIVERS_LIST_REVERSED))

        assertEquals(1, driversRepository.driversList.size)
    }

    @Test
    fun `driversList preserves drivers order`() {
        val driversRepository = DriversRepositoryImpl(
                FakeRemoteConfigService(drivers = TWO_DRIVERS_INFO_MODEL, driversOrderList = TWO_DRIVERS_LIST_REVERSED))

        assertEquals(2, driversRepository.driversList.size)
        assertEquals(DRIVER_B, driversRepository.driversList.first())
        assertEquals(DRIVER_A, driversRepository.driversList.last())
    }

    @Test
    fun `getDriver result contains all mandatory and optional fields`() {
        val driversRepository = DriversRepositoryImpl(
                FakeRemoteConfigService(drivers = ONE_DRIVER_INFO_MODEL, driversOrderList = SINGLE_DRIVER_LIST))

        assertEquals(1, driversRepository.drivers.size)
        driversRepository.getDriver(DRIVER_A).let { alonsoModel ->
            assertEquals(NAME, alonsoModel[Name])
            assertEquals(DOB, alonsoModel[DateOfBirth])
            assertEquals(NATIONALITY, alonsoModel[DriverProperty.Nationality])
            assertEquals(TWITTER, alonsoModel[DriverProperty.Twitter])
            assertEquals(TAG, alonsoModel[DriverProperty.Tag])
            assertEquals(DUMMY_URL, alonsoModel[DriverProperty.HeritagePageLink])
            assertEquals(DUMMY_URL, alonsoModel[TeamPageLink])
            assertEquals(CHAMPIONSHIPS, alonsoModel[DriverProperty.WorldChampionships])
            assertEquals(POLES, alonsoModel[DriverProperty.PolePositions])
            assertEquals(FASTEST_LAPS, alonsoModel[DriverProperty.FastestLaps])
            assertEquals(PLACE, alonsoModel[Place])
            assertEquals(POINTS, alonsoModel[Points])
            assertEquals(FINISHES, alonsoModel[DriverProperty.BestFinish])
            assertEquals(PODIUMS, alonsoModel[DriverProperty.Podiums])

            assertModelIsExhaustive(alonsoModel)
        }
    }

    private fun assertModelIsExhaustive(driverModel: Driver) {
        for (property in DriverProperty.values()) {
            assertNotNull(driverModel[property])
        }
    }
}
