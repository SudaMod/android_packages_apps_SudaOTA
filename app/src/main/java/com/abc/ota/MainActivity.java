/*
 * Copyright (C) 2015 Chandra Poerwanto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abc.ota;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.abc.ota.configs.LinkConfig;
import com.abc.ota.dialogs.WaitDialogFragment;
import com.abc.ota.fragments.ABCOTAFragment;

public class MainActivity extends PreferenceActivity implements
        WaitDialogFragment.OTADialogListener, LinkConfig.LinkConfigListener {

    private static final String FRAGMENT_TAG = ABCOTAFragment.class.getName();
    private ABCOTAFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragment = (ABCOTAFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (mFragment == null) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new ABCOTAFragment(), FRAGMENT_TAG)
                    .commit();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return ABCOTAFragment.class.getName().equalsIgnoreCase(fragmentName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressCancelled() {
        Fragment fragment = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment instanceof WaitDialogFragment.OTADialogListener) {
            ((WaitDialogFragment.OTADialogListener) fragment).onProgressCancelled();
        }
    }

    @Override
    public void onConfigChange() {
        Fragment fragment = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment instanceof LinkConfig.LinkConfigListener) {
            ((LinkConfig.LinkConfigListener) fragment).onConfigChange();
        }
    }
}
