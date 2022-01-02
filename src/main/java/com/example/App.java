package com.example;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;

public class App {

    private static final class EmptyPasswordAuthenticator implements PasswordAuthenticator {
        @Override
        public boolean authenticate(String username, String password, ServerSession session)
                throws PasswordChangeRequiredException, AsyncAuthException {
            return true;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(22);
        sshd.setHost("0.0.0.0");
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("./key")));
        sshd.setShellFactory(new ChatShellFactory(new Room()));
        sshd.setPasswordAuthenticator(new EmptyPasswordAuthenticator());
        sshd.start();
        Thread.sleep(Long.MAX_VALUE);
    }
}
