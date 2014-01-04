/*
 * Copyright 2013 Koushik Dutta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cyanogenmod.oneclick;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class PtpActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ptpIsEnabled()) {
            startActivity(new Intent(getBaseContext(), HTCFastBootActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.ptp);

        ImageView instructionView = (ImageView) findViewById(R.id.ptp_instructions);

        DecelerateInterpolator interpolator = new DecelerateInterpolator(2.0f);

        AnimationSet instructionAnimations = new AnimationSet(true);
        instructionAnimations.setInterpolator(interpolator);
        TranslateAnimation instructionMoveAnimation = new TranslateAnimation(0, 0, 250, 0);
        instructionMoveAnimation.setDuration(1000);
        instructionMoveAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
        instructionAnimations.addAnimation(instructionMoveAnimation);

        // we want them to read the instructions first! so we give them a few seconds
        AlphaAnimation instructionFadeAnimation = new AlphaAnimation(0.0f, 1.0f);
        instructionFadeAnimation.setDuration(1000);
        instructionFadeAnimation.setStartOffset(500);
        instructionFadeAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
        instructionAnimations.addAnimation(instructionFadeAnimation);

        instructionView.setAnimation(instructionAnimations);

        // continue button should take even longer
        AlphaAnimation buttonAnimation = new AlphaAnimation(0.0f, 1.0f);
        buttonAnimation.setDuration(750);
        buttonAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
        buttonAnimation.setStartOffset(1000);
        findViewById(R.id.next).setAnimation(buttonAnimation);

        OnClickListener openPtpListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.android.settings", "com.android.settings.UsbSettings");
                try {
                    startActivity(intent);
                    startService(new Intent(getBaseContext(), PtpMonitorService.class));
                } catch (ActivityNotFoundException e) {
                    // we want to know if this happens, right?
                }
            }
        };

        findViewById(R.id.next).setOnClickListener(openPtpListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ptpIsEnabled()) {
            startActivity(new Intent(getBaseContext(), UnplugDeviceActivity.class));
            finish();
        }
    }

    private boolean ptpIsEnabled() {
        String usbConfig = SystemProperties.get("persist.sys.usb.config");
        if (usbConfig != null && usbConfig.contains("mtp")) {
            return false;
        }
        return true;
    }
}
