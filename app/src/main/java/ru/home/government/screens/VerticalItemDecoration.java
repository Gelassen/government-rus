package ru.home.government.screens;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;

    public VerticalItemDecoration(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = dividerHeight;
        }
    }
}
