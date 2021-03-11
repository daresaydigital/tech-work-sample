package com.midnight.weatherforecast;

import android.security.NetworkSecurityPolicy;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(NetworkSecurityPolicy.class)
public class MyNetworkSecurityPolicy {

    public MyNetworkSecurityPolicy() {
    }

    @Implementation
    public static NetworkSecurityPolicy getInstance() {
        try {
            Class<?> shadow = MyNetworkSecurityPolicy.class.forName("android.security.NetworkSecurityPolicy");
            return (NetworkSecurityPolicy) shadow.newInstance();
        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    @Implementation
    public boolean isCleartextTrafficPermitted() {
        return true;
    }
}
