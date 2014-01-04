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
import android.provider.Settings;

public class UsbDebuggingMonitorService extends MonitorService {
    @SuppressWarnings("deprecation")
    @Override
    protected boolean canContinue() {
        return (Settings.Secure.getInt(getContentResolver(), Settings.Secure.ADB_ENABLED, 0) == 1);
    }

    @Override
    protected Class<? extends Activity> getNextActivityClass() {
        return PtpActivity.class;
    }
}
