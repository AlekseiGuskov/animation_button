package com.simbirsoft.ag.animationexample;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ProgressButton extends View {

    private enum STATE {
        DEFAULT,
        FIRST_CREATE,
        FIRST_FILL,
        SECOND_STARTED
    }

    private static final int MAX_ANGLE = 360;

    private STATE animationState = STATE.DEFAULT;

    private int size, thicknessExternal, thicknessInner;
    private Paint paintDefaultExternal, paintDefaultInner, paintStartAnimationCreateStateExternal,
            paintStartAnimationCreateStateInner, paintStartAnimationFillStateExternal,
            paintStartAnimationFillStateInner, paintSecondAnimationStateExternal, paintSecondAnimationStateInner;
    private RectF rectExternalArc, rectInnerArc;
    private float indeterminateSweepCreateStateExternal, indeterminateSweepCreateStateInner,
            indeterminateSweepFillStateExternal, indeterminateSweepFillStateInner, startAngle,
            secondStateAnimatedStartAngle;

    private boolean isFirstAnimationCompleted = false;

    public ProgressButton(Context context) {
        super(context);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        thicknessExternal = 40;
        thicknessInner = 20;

        paintDefaultExternal = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintDefaultExternal.setStyle(Paint.Style.STROKE);
        paintDefaultExternal.setStrokeWidth(thicknessExternal);
        paintDefaultExternal.setColor(Color.GRAY);

        paintDefaultInner = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintDefaultInner.setStyle(Paint.Style.STROKE);
        paintDefaultInner.setStrokeWidth(thicknessInner);
        paintDefaultInner.setColor(Color.GRAY);

        paintStartAnimationCreateStateExternal = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStartAnimationCreateStateExternal.setStyle(Paint.Style.STROKE);
        paintStartAnimationCreateStateExternal.setStrokeWidth(thicknessExternal);

        paintStartAnimationCreateStateInner = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStartAnimationCreateStateInner.setStyle(Paint.Style.STROKE);
        paintStartAnimationCreateStateInner.setStrokeWidth(thicknessInner);

        paintStartAnimationFillStateExternal = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStartAnimationFillStateExternal.setStyle(Paint.Style.STROKE);
        paintStartAnimationFillStateExternal.setStrokeWidth(thicknessExternal);
        paintStartAnimationFillStateExternal.setColor(Color.BLUE);

        paintStartAnimationFillStateInner = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStartAnimationFillStateInner.setStyle(Paint.Style.STROKE);
        paintStartAnimationFillStateInner.setStrokeWidth(thicknessInner);
        paintStartAnimationFillStateInner.setColor(Color.BLUE);

        paintSecondAnimationStateExternal = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSecondAnimationStateExternal.setStyle(Paint.Style.STROKE);
        paintSecondAnimationStateExternal.setStrokeWidth(thicknessExternal);

        paintSecondAnimationStateInner = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSecondAnimationStateInner.setStyle(Paint.Style.STROKE);
        paintSecondAnimationStateInner.setStrokeWidth(thicknessInner);

        rectExternalArc = new RectF();
        rectInnerArc = new RectF();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();
        int width = getMeasuredWidth() - xPad;
        int height = getMeasuredHeight() - yPad;
        size = (width < height) ? width : height;
        setMeasuredDimension(size + xPad, size + yPad);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (animationState) {
            case DEFAULT:
            default: {
                canvas.drawArc(rectExternalArc, startAngle, MAX_ANGLE, false, paintDefaultExternal);
                canvas.drawArc(rectInnerArc, startAngle, MAX_ANGLE, false, paintDefaultInner);
                break;
            }
            case FIRST_FILL: {
                if (!isFirstAnimationCompleted) {
                    canvas.drawArc(rectExternalArc, startAngle, indeterminateSweepCreateStateExternal, false,
                            paintStartAnimationCreateStateExternal);
                    canvas.drawArc(rectInnerArc, startAngle, indeterminateSweepCreateStateInner, false,
                            paintStartAnimationCreateStateInner);
                }
                canvas.drawArc(rectExternalArc, startAngle, indeterminateSweepFillStateExternal, false,
                        paintStartAnimationFillStateExternal);
                canvas.drawArc(rectInnerArc, startAngle, indeterminateSweepFillStateInner, false,
                        paintStartAnimationFillStateInner);
                break;
            }
            case FIRST_CREATE: {
                canvas.drawArc(rectExternalArc, startAngle, indeterminateSweepCreateStateExternal, false,
                        paintStartAnimationCreateStateExternal);
                canvas.drawArc(rectInnerArc, startAngle, indeterminateSweepCreateStateInner, false,
                        paintStartAnimationCreateStateInner);
                break;
            }
            case SECOND_STARTED: {
                canvas.drawArc(rectExternalArc, startAngle, MAX_ANGLE, false, paintSecondAnimationStateExternal);
                canvas.drawArc(rectInnerArc, startAngle, MAX_ANGLE, false, paintSecondAnimationStateInner);
                break;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = (w < h) ? w : h;
        updateRect();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            return performClick();
        }
        return true;
    }

    @Override
    public boolean performClick() {
        switch (animationState) {
            case FIRST_CREATE:
            case FIRST_FILL: {
                animateSecondState();
                break;
            }
            case SECOND_STARTED: {
                animationState = STATE.DEFAULT;
                invalidate();
                break;
            }
            case DEFAULT:
            default: {
                animateFirstState();
                break;
            }
        }

        return super.performClick();
    }

    private void animateSecondState() {
        animationState = STATE.SECOND_STARTED;

        final float[] from = new float[3], to = new float[3];
        Color.colorToHSV(Color.WHITE, to);
        Color.colorToHSV(Color.RED, from);
        ValueAnimator colorAnimator = ValueAnimator.ofFloat(0, 1);
        colorAnimator.setDuration(2000);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        final float[] hsv = new float[3];
        final boolean[] isFirstPhase = {true};
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isFirstPhase[0]) {
                    hsv[0] = from[0] + (to[0] - from[0]) * animation.getAnimatedFraction();
                    hsv[1] = from[1] + (to[1] - from[1]) * animation.getAnimatedFraction();
                    hsv[2] = from[2] + (to[2] - from[2]) * animation.getAnimatedFraction();

                    int[] colorsExternal = {Color.HSVToColor(hsv), Color.RED};
                    int[] colorsInner = {Color.RED, Color.HSVToColor(hsv)};
                    paintSecondAnimationStateExternal.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2,
                            colorsExternal, null));
                    paintSecondAnimationStateInner.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2,
                            colorsInner, null));

                    if (Color.HSVToColor(hsv) == Color.WHITE) {
                        isFirstPhase[0] = false;
                    }
                } else {
                    hsv[0] = from[0] + (to[0] - from[0]) * animation.getAnimatedFraction();
                    hsv[1] = from[1] + (to[1] - from[1]) * animation.getAnimatedFraction();
                    hsv[2] = from[2] + (to[2] - from[2]) * animation.getAnimatedFraction();

                    int[] colorsExternal = {Color.HSVToColor(hsv), Color.RED};
                    int[] colorsInner = {Color.RED, Color.HSVToColor(hsv)};
                    paintSecondAnimationStateExternal.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2,
                            colorsExternal, null));
                    paintSecondAnimationStateInner.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2,
                            colorsInner, null));

                    if (Color.HSVToColor(hsv) == Color.RED) {
                        isFirstPhase[0] = true;
                    }
                }

                invalidate();
            }
        });
        colorAnimator.start();

        final ValueAnimator roundAnimator = ValueAnimator.ofFloat(0, MAX_ANGLE);
        roundAnimator.setRepeatCount(ValueAnimator.INFINITE);
        roundAnimator.setDuration(1500);
        roundAnimator.setInterpolator(new LinearInterpolator());
        roundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                boolean isChanged = false;
                secondStateAnimatedStartAngle = (Float) animation.getAnimatedValue();
                Shader shaderExternal = paintSecondAnimationStateExternal.getShader();
                if (shaderExternal != null) {
                    isChanged = true;
                    Matrix gradientMatrix = new Matrix();
                    gradientMatrix.preRotate(secondStateAnimatedStartAngle, rectExternalArc.centerX(), rectExternalArc.centerY());
                    shaderExternal.setLocalMatrix(gradientMatrix);
                    paintSecondAnimationStateExternal.setShader(shaderExternal);
                }
                Shader shaderInner = paintSecondAnimationStateInner.getShader();
                if (shaderInner != null) {
                    isChanged = true;
                    Matrix gradientMatrix = new Matrix();
                    gradientMatrix.preRotate(-secondStateAnimatedStartAngle, rectExternalArc.centerX(), rectExternalArc.centerY());
                    shaderInner.setLocalMatrix(gradientMatrix);
                    paintSecondAnimationStateInner.setShader(shaderInner);
                }
                if (isChanged) {
                    invalidate();
                }
            }
        });
        roundAnimator.start();
    }

    private void animateFirstState() {
        animationState = STATE.FIRST_CREATE;
        indeterminateSweepCreateStateExternal = 0;
        indeterminateSweepCreateStateInner = 0;
        indeterminateSweepFillStateExternal = 0;
        indeterminateSweepFillStateInner = 0;
        final int animationStep = 5;
        final ValueAnimator frontEndExtend = ValueAnimator.ofFloat(0, 1);
        frontEndExtend.setRepeatCount(ValueAnimator.INFINITE);
        frontEndExtend.setInterpolator(new LinearInterpolator());
        frontEndExtend.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animationState == STATE.FIRST_CREATE) {

                    indeterminateSweepCreateStateExternal += animationStep;
                    int[] colorsExternal = {Color.WHITE, Color.BLUE};
                    SweepGradient externalGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, colorsExternal, null);
                    paintStartAnimationCreateStateExternal.setShader(externalGradient);

                    indeterminateSweepCreateStateInner -= animationStep;
                    int[] colorsInner = {Color.BLUE, Color.WHITE};
                    SweepGradient innerGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, colorsInner, null);
                    paintStartAnimationCreateStateInner.setShader(innerGradient);

                    if (animationState == STATE.FIRST_CREATE && indeterminateSweepCreateStateExternal >= MAX_ANGLE) {
                        animationState = STATE.FIRST_FILL;
                    }
                } else if (animationState == STATE.FIRST_FILL) {
                    indeterminateSweepFillStateExternal += animationStep;
                    indeterminateSweepFillStateInner -= animationStep;
                    if (indeterminateSweepFillStateExternal >= MAX_ANGLE) {
                        isFirstAnimationCompleted = true;
                        frontEndExtend.cancel();
                    }
                }
                invalidate();
            }
        });
        frontEndExtend.start();
    }

    private void updateRect() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        rectExternalArc.set(paddingLeft + thicknessExternal, paddingTop + thicknessExternal,
                size - paddingLeft - thicknessExternal, size - paddingTop - thicknessExternal);
        int arcDiff = size / 15;
        rectInnerArc.set(paddingLeft + thicknessExternal + arcDiff, paddingTop + thicknessExternal + arcDiff,
                size - paddingLeft - thicknessExternal - arcDiff, size - paddingTop - thicknessExternal - arcDiff);
    }
}
