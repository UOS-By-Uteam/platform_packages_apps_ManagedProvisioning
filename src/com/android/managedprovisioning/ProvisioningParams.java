/*
 * Copyright 2014, The Android Open Source Project
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

package com.android.managedprovisioning;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;

/**
 * Provisioning Parameters for DeviceOwner Provisioning
 */
public class ProvisioningParams implements Parcelable {
    public static final long DEFAULT_LOCAL_TIME = -1;
    public static final boolean DEFAULT_WIFI_HIDDEN = false;
    public static final int DEFAULT_WIFI_PROXY_PORT = 0;

    public static String mTimeZone;
    public static long mLocalTime = DEFAULT_LOCAL_TIME;
    public static Locale mLocale;

    public static String mWifiSsid;
    public static boolean mWifiHidden = DEFAULT_WIFI_HIDDEN;
    public static String mWifiSecurityType;
    public static String mWifiPassword;
    public static String mWifiProxyHost;
    public static int mWifiProxyPort = DEFAULT_WIFI_PROXY_PORT;
    public static String mWifiProxyBypassHosts;
    public static String mWifiPacUrl;

    public static String mDeviceAdminPackageName; // Package name of the device admin package.

    public static String mDeviceAdminPackageDownloadLocation; // Url of the device admin .apk
    public static byte[] mDeviceAdminPackageChecksum = new byte[0]; // SHA-1 sum of the .apk file.

    public String getLocaleAsString() {
        if (mLocale != null) {
            return mLocale.getLanguage() + "_" + mLocale.getCountry();
        } else {
            return null;
        }
    }

    public String getDeviceAdminPackageChecksumAsString() {
        StringBuilder sb = new StringBuilder(mDeviceAdminPackageChecksum.length * 2);
        for(byte b: mDeviceAdminPackageChecksum)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTimeZone);
        out.writeLong(mLocalTime);
        out.writeSerializable(mLocale);
        out.writeString(mWifiSsid);
        out.writeInt(mWifiHidden ? 1 : 0);
        out.writeString(mWifiSecurityType);
        out.writeString(mWifiPassword);
        out.writeString(mWifiProxyHost);
        out.writeInt(mWifiProxyPort);
        out.writeString(mWifiProxyBypassHosts);
        out.writeString(mDeviceAdminPackageName);
        out.writeString(mDeviceAdminPackageDownloadLocation);
        out.writeByteArray(mDeviceAdminPackageChecksum);
    }

    public static final Parcelable.Creator<ProvisioningParams> CREATOR
        = new Parcelable.Creator<ProvisioningParams>() {
        @Override
        public ProvisioningParams createFromParcel(Parcel in) {
            ProvisioningParams params = new ProvisioningParams();
            params.mTimeZone = in.readString();
            params.mLocalTime = in.readLong();
            params.mLocale = (Locale) in.readSerializable();
            params.mWifiSsid = in.readString();
            params.mWifiHidden = in.readInt()==1;
            params.mWifiSecurityType = in.readString();
            params.mWifiPassword = in.readString();
            params.mWifiProxyHost = in.readString();
            params.mWifiProxyPort = in.readInt();
            params.mWifiProxyBypassHosts = in.readString();
            params.mDeviceAdminPackageName = in.readString();
            params.mDeviceAdminPackageDownloadLocation = in.readString();
            in.readByteArray(params.mDeviceAdminPackageChecksum);
            return params;
        }

        @Override
        public ProvisioningParams[] newArray(int size) {
            return new ProvisioningParams[size];
        }
    };
}
