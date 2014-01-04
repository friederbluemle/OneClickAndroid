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
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class CompatibilityActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unsupported);

        // Make links clickable
        TextView textView = (TextView) findViewById(R.id.unsupported_text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        if (Utils.isDeviceSupported()) {
            startMainActivity();

            // Finish this activity so it does not show up when the user presses the back button
            finish();
        }

        findViewById(R.id.continue_anyway).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(getBaseContext(), StartActivity.class));
    }
}
