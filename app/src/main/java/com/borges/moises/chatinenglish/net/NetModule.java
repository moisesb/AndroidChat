package com.borges.moises.chatinenglish.net;

import com.borges.moises.chatinenglish.login.LoginService;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Moisés on 02/07/2016.
 */
@Module
public class NetModule {
    private final String mHostName;
    private final String mServiceName;

    public NetModule(String hostName, String serviceName) {
        mHostName = hostName;
        mServiceName = serviceName;
    }

    @Provides
    @Singleton
    public ServerConfig providesServerConfig() {
        return new ServerConfig(mHostName,mServiceName);
    }

    @Provides
    @Singleton
    public XMPPTCPConnectionConfiguration providesConfiguration() {
        return XMPPTCPConnectionConfiguration.builder()
                .setServiceName(mServiceName) // "safic.net"
                .setHost(mHostName) // "chat.safic.net"
                .build();
    }

    @Provides
    @Singleton
    public AbstractXMPPConnection providesConnection(XMPPTCPConnectionConfiguration configuration){
        return new XMPPTCPConnection(configuration);
    }

    @Provides
    @Singleton
    public Roster providesRoster(AbstractXMPPConnection connection) {
        return Roster.getInstanceFor(connection);
    }

    @Provides
    @Singleton
    public ChatManager providesChatMenager(AbstractXMPPConnection connection) {
        return ChatManager.getInstanceFor(connection);
    }
}
