/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;

public class RotateLoadingLayout extends LoadingLayout {

    int[] dropdownResIds = {
            R.drawable.dropdown_anim_00,
            R.drawable.dropdown_anim_01,
            R.drawable.dropdown_anim_02,
            R.drawable.dropdown_anim_03,
            R.drawable.dropdown_anim_04,
            R.drawable.dropdown_anim_05,
            R.drawable.dropdown_anim_06,
            R.drawable.dropdown_anim_07,
            R.drawable.dropdown_anim_08,
            R.drawable.dropdown_anim_09,
            R.drawable.dropdown_anim_10};

    public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {

        }
    }

    //下拉时调用
    protected void onPullImpl(float scaleOfLayout) {
        int idx = (int) (scaleOfLayout * 10);

        if (idx <= 10) {
            //根据第二阶段娃娃宽高 给椭圆形图片进行等比例的缩放
            Bitmap initialBitmap = BitmapFactory.decodeResource(getResources(),dropdownResIds[idx]);
            if (idx == 0)idx = 1;
            int measuredWidth = initialBitmap.getWidth()*idx/10;
            int measuredHeight = initialBitmap.getHeight()*idx/10;
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(initialBitmap, measuredWidth,measuredHeight, true);
            //Bitmap bitmap = Bitmap.createScaledBitmap(src,)
            mHeaderImage.setImageBitmap(scaledBitmap);
        } else {
            mHeaderImage.setImageResource(dropdownResIds[10]);
        }
    }

    @Override
    protected void refreshingImpl() {
        //mHeaderImage.startAnimation();
        mHeaderImage.setImageResource(R.drawable.refresh_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
        animationDrawable.start();
    }

    @Override
    protected void resetImpl() {
        //mHeaderImage.clearAnimation();

    }

    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected void releaseToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.dropdown_anim_00;
    }

}
