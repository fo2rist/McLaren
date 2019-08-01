package com.github.fo2rist.mclaren.pages

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.github.fo2rist.mclaren.R
import org.hamcrest.Matcher

class CircuitsPage : Screen<CircuitsPage>() {

    class CircuitItem(parent: Matcher<View>) : KRecyclerItem<CircuitItem>(parent)

    val onCircuitsList = KRecyclerView(
            builder = { withId(R.id.list_circuits) },
            itemTypeBuilder = { itemType(::CircuitItem) })
}
