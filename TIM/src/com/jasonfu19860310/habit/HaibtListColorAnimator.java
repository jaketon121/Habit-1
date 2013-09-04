package com.jasonfu19860310.habit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jasonfu19860310.habit.model.Habit;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;

public class HaibtListColorAnimator {
	private Map<Long, Animator> animatorMap = new HashMap<Long, Animator>();
	private static int startColorA = Color.rgb(64, 116, 52);
	private static int startColorB = Color.rgb(0, 0, 0);
	private static int pauseColorA = Color.rgb(184, 133, 10);
	private static int pauseColorB = Color.rgb(0, 0, 0);
	
	public void startAnimate(View itemView, long  id) {
		ValueAnimator animator = getColorAnimator(itemView, startColorA, startColorB);
	    addIntoMap(id, animator);
	}

	private void addIntoMap(long id, ValueAnimator animator) {
		Animator oldAnimator = animatorMap.get(id);
		if (oldAnimator != null) {
			oldAnimator.end();
			animatorMap.remove(id);
		}
		animatorMap.put(id, animator);
	}

	private ValueAnimator getColorAnimator(View view, int colorA, int colorB) {
	    ValueAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", colorA, colorB);
	    animator.setDuration(750);
	    animator.setEvaluator(new ArgbEvaluator());
	    animator.setRepeatCount(ValueAnimator.INFINITE);
	    animator.setRepeatMode(ValueAnimator.REVERSE);
	    animator.start();
		return animator;
	}
	
	public void pauseAnimate(View itemView, long id) {
		ValueAnimator animator = getColorAnimator(itemView, pauseColorA, pauseColorB);
	    addIntoMap(id, animator);
	}
	
	public void removeOutdatedAnimator(List<Habit> habits) {
		for (Habit habit : habits) {
			if (habit.isTimer_paused() || habit.isTimer_started()) continue;
			Animator animator = animatorMap.get(habit.getId());
			if (animator != null) {
				animator.cancel();
				animatorMap.remove(habit.getId());
			}
		}
	}
}
