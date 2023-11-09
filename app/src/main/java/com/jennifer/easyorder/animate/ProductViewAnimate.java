package com.jennifer.easyorder.animate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewAnimate extends DefaultItemAnimator {


    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final Animator anim = ObjectAnimator.ofFloat(view, "translationX", 0, -view.getWidth());
        anim.setDuration(getRemoveDuration());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchRemoveFinished(holder);
                view.setTranslationX(0);
            }
        });
        anim.start();
        return true;
    }
}
