package me.bug.destroyedactivitiesnotgceddemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by FrankChan on 2017/8/28.
 * 在项目中，统一用此类，方便管理和追踪
 * 支持各种动画的View，继承{@link com.airbnb.lottie.LottieAnimationView}
 */

public class KiwiAnimationView extends LottieAnimationView {

    private static final String TAG = "KiwiAnimationView";

    private boolean mResumeWhenVisible = false;

    private boolean mAutoPlay = false;

    private boolean mNeedLoopWhenResume = false;

    public KiwiAnimationView(Context context) {
        super(context);
        initView(context, null);
    }

    public KiwiAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public KiwiAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    protected void initView(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs,
                com.airbnb.lottie.R.styleable.LottieAnimationView);
        if (ta.getBoolean(com.airbnb.lottie.
                R.styleable.LottieAnimationView_lottie_autoPlay, false)) {
            mAutoPlay = true;
        }
        mNeedLoopWhenResume = ta.getBoolean(com.airbnb.lottie.
                R.styleable.LottieAnimationView_lottie_loop, false);
        ta.recycle();
    }

    @Override
    public void loop(boolean loop) {
        mNeedLoopWhenResume = loop;
        super.loop(loop);
    }

    public void setNeedLoopWhenResume(boolean needLoopWhenResume) {
        mNeedLoopWhenResume = needLoopWhenResume;
    }

    @Override
    public void playAnimation() {
        super.loop(mNeedLoopWhenResume);
        super.playAnimation();
    }

    @Override
    public void resumeAnimation() {
        super.loop(mNeedLoopWhenResume);
        super.resumeAnimation();
    }

    @Override
    public void pauseAnimation() {
        super.loop(false);
        super.pauseAnimation();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 设置自动播放，触发时机在此处
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 会自动清除资源，回收Bitmap
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        boolean visible = visibility == View.VISIBLE;
        // 只需要处理动画和可见度两者不一致的情况
        Boolean isAnimating = isSaveAnimating();
        if (isAnimating == null) {
            return;
        }
        if (visible == isAnimating) {
            mResumeWhenVisible = false;
        } else if (visible) {
            // 变成可见但是没有执行动画
            if (mResumeWhenVisible) {
                mResumeWhenVisible = false;
                resumeAnimation();
            } else if (mAutoPlay) {
                mResumeWhenVisible = false;
                playAnimation();
            }
        } else {
            // 变成不可见但是动画正在执行
            mResumeWhenVisible = true;
            pauseAnimation();
        }
    }

    /**
     * 在某些版本View的实现中，父类View的构造方法会触发{@link #onVisibilityChanged(View, int)}
     * 但是子类{@link LottieAnimationView}里面的动画对象{@link com.airbnb.lottie.LottieDrawable}还未被初始化出来
     * 导致空指针，由于权限原因无法获取动画对象(private)，必须提供安全的获取isAnimating的方法
     */
    private Boolean isSaveAnimating() {
        try {
            return isAnimating();
        } catch (Exception ex) {
            Log.e(TAG, "Exception");
        }
        return null;
    }

}

