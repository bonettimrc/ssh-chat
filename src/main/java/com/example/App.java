package com.example;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(6666);
        sshd.setHost("0.0.0.0");
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("./key")));
        sshd.setShellFactory(new ChatShellFactory());
        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {

            @Override
            public boolean authenticate(String username, String password, ServerSession session)
                    throws PasswordChangeRequiredException, AsyncAuthException {
                // TODO Auto-generated method stub
                return true;
            }

        });
        sshd.start();

        Thread.sleep(Long.MAX_VALUE);
    }
}
