/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.android.contacts.calllog;

import com.android.contacts.R;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.provider.CallLog.Calls;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Helper class to perform operations related to call types.
 */
public class CallTypeHelper {
    /** Used to create the views for the call types. */
    private final LayoutInflater mLayoutInflater;
    /** Name used to identify incoming calls. */
    private final CharSequence mIncomingName;
    /** Name used to identify outgoing calls. */
    private final CharSequence mOutgoingName;
    /** Name used to identify missed calls. */
    private final CharSequence mMissedName;
    /** Name used to identify voicemail calls. */
    private final CharSequence mVoicemailName;
    /** Name used to identify new missed calls. */
    private final CharSequence mNewMissedName;
    /** Name used to identify new voicemail calls. */
    private final CharSequence mNewVoicemailName;

    public CallTypeHelper(Resources resources, LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
        // Cache these values so that we do not need to look them up each time.
        mIncomingName = resources.getString(R.string.type_incoming);
        mOutgoingName = resources.getString(R.string.type_outgoing);
        mMissedName = resources.getString(R.string.type_missed);
        mVoicemailName = resources.getString(R.string.type_voicemail);
        mNewMissedName = addBoldAndColor(mMissedName,
                resources.getColor(R.color.call_log_missed_call_highlight_color));
        mNewVoicemailName = addBoldAndColor(mVoicemailName,
                resources.getColor(R.color.call_log_voicemail_highlight_color));
    }

    /** Returns the text used to represent the given call type. */
    public CharSequence getCallTypeText(int callType) {
        switch (callType) {
            case Calls.INCOMING_TYPE:
                return mIncomingName;

            case Calls.OUTGOING_TYPE:
                return mOutgoingName;

            case Calls.MISSED_TYPE:
                return mMissedName;

            case Calls.VOICEMAIL_TYPE:
                return mVoicemailName;

            default:
                throw new IllegalArgumentException("invalid call type: " + callType);
        }
    }

    /** Returns the text used to represent the given call type. */
    public CharSequence getHighlightedCallTypeText(int callType) {
        switch (callType) {
            case Calls.INCOMING_TYPE:
                // New incoming calls are not highlighted.
                return mIncomingName;

            case Calls.OUTGOING_TYPE:
                // New outgoing calls are not highlighted.
                return mOutgoingName;

            case Calls.MISSED_TYPE:
                return mNewMissedName;

            case Calls.VOICEMAIL_TYPE:
                return mNewVoicemailName;

            default:
                throw new IllegalArgumentException("invalid call type: " + callType);
        }
    }

    /** Returns a new view for the icon to be used to represent a given call type. */
    public View inflateCallTypeIcon(int callType, ViewGroup root) {
        switch (callType) {
            case Calls.INCOMING_TYPE:
                return mLayoutInflater.inflate(R.layout.call_log_incoming_call_icon, root);

            case Calls.OUTGOING_TYPE:
                return mLayoutInflater.inflate(R.layout.call_log_outgoing_call_icon, root);

            case Calls.MISSED_TYPE:
                return mLayoutInflater.inflate(R.layout.call_log_missed_call_icon, root);

            case Calls.VOICEMAIL_TYPE:
                return mLayoutInflater.inflate(R.layout.call_log_voicemail_icon, root);

            default:
                throw new IllegalArgumentException("invalid call type: " + callType);
        }
    }

    /** Creates a SpannableString for the given text which is bold and in the given color. */
    private CharSequence addBoldAndColor(CharSequence text, int color) {
        int flags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;
        SpannableString result = new SpannableString(text);
        result.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(), flags);
        result.setSpan(new ForegroundColorSpan(color), 0, text.length(), flags);
        return result;
    }
}