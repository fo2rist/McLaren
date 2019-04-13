package com.github.fo2rist.mclaren.ui.models

import android.support.annotation.StringRes
import com.github.fo2rist.mclaren.R

/**
 * Single property of a driver such as age, name etc.
 */
enum class DriverProperty(
    /** Should property present for every Driver. */
    open val isMandatory: Boolean,
    /** Property's displayable name. */
    @StringRes
    open val nameResId: Int
) {
    Name(isMandatory = true, nameResId = R.string.driver_name),
    DateOfBirth(isMandatory = true, nameResId = R.string.driver_date_of_birth),
    Nationality(isMandatory = true, nameResId = R.string.driver_nationality),
    Twitter(isMandatory = true, nameResId = R.string.driver_twitter),
    Tag(isMandatory = false, nameResId = R.string.driver_tag),
    WorldChampionships(isMandatory = false, nameResId = R.string.driver_world_championships),
    BestFinish(isMandatory = false, nameResId = R.string.driver_best_finish),
    Podiums(isMandatory = false, nameResId = R.string.driver_podiums),
    PolePositions(isMandatory = false, nameResId = R.string.driver_pole_positions),
    FastestLaps(isMandatory = false, nameResId = R.string.driver_fastest_laps),
    Place(isMandatory = false, nameResId = R.string.driver_place),
    Points(isMandatory = false, nameResId = R.string.driver_points),
    TeamPageLink(isMandatory = false, nameResId = R.string.driver_team_link),
    HeritagePageLink(isMandatory = false, nameResId = R.string.driver_heritage_link),
}
