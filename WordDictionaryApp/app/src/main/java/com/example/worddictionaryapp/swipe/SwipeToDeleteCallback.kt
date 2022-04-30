package com.example.worddictionaryapp.swipe

import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.worddictionaryapp.R
import com.example.worddictionaryapp.mainscreen.MainFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class SwipeToDeleteCallback(var context: MainFragment): ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or
            ItemTouchHelper.RIGHT
) {
    val deleteColor = ContextCompat.getColor(context.requireContext(), R.color.deletecolor)
    val deleteIcon = R.drawable.ic_baseline_delete_forever_24
    val addColor = ContextCompat.getColor(context.requireContext(), R.color.addcolor)
    val addIcon  = R.drawable.ic_baseline_add_24


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }



    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    )
    {
        RecyclerViewSwipeDecorator.Builder(context.requireContext(),c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
            .addSwipeLeftActionIcon(deleteIcon)
            .addSwipeRightActionIcon(addIcon)
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeRightBackgroundColor(addColor)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX/4, dY, actionState, isCurrentlyActive)
    }
}