package com.borges.moises.chatinenglish.net;

/**
 * Created by Moisés on 02/07/2016.
 */

public class ServerConfig {
    private final String mHostName;
    private final String mServiceName;

    public ServerConfig(String hostName, String serviceName) {
        mHostName = hostName;
        mServiceName = serviceName;
    }

    public String hostName(){
        return mHostName;
    }

    public String serverName(){
        return mServiceName;
    }
}
