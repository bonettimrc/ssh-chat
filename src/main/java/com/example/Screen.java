package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.Message.Type;

import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;

public class Screen implements Command, Runnable {
    private InputStream in;
    private OutputStream out;
    private OutputStream err;
    private ExitCallback callback;
    private User user;
    private StringBuilder stringBuilder;

    public void setUser(User user) {
        this.user = user;
    }

    public Screen() {
        super();
    }

    @Override
    public void start(ChannelSession channel, Environment env) throws IOException {
        user.userName = env.getEnv().get(Environment.ENV_USER);
        new Thread(this).start();
    }

    @Override
    public void destroy(ChannelSession channel) throws Exception {
        user.destroy();
    }

    @Override
    public void setInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public void setOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void setErrorStream(OutputStream err) {
        this.err = err;

    }

    @Override
    public void setExitCallback(ExitCallback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        while (true) {
            String line;
            try {
                line = readLine();
            } catch (EndOfTextException e) {
                break;
            } catch (IOException e) {
                callback.onExit(-1);
                break;
            }
            user.onMessageSent(new Message(line, user, Type.PublicMessage));
        }
        callback.onExit(0);
    }

    private String readLine() throws EndOfTextException, IOException {
        stringBuilder = new StringBuilder();
        while (true) {
            char c;
            try {
                c = readChar();
            } catch (BackSpaceException e) {
                onBackSpace();
                continue;
            } catch (CarriageReturnException e) {
                onCarriageReturn();
                break;
            }
            stringBuilder.append(c);
        }
        String line = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return line;

    }

    private char readChar() throws BackSpaceException, EndOfTextException, CarriageReturnException, IOException {
        char c = (char) in.read();
        switch (c) {
            case 127:
                throw new BackSpaceException();
            case 3:
                throw new EndOfTextException();
            case 13:
                throw new CarriageReturnException();
            case 27:
                return readChar();
            default:
                out.write(c);
                out.flush();
                return c;
        }
    }

    private void onBackSpace() throws IOException {
        if (stringBuilder.length() == 0) {
            return;
        }
        out.write('\r');
        for (int i = 0; i < stringBuilder.length(); i++) {
            out.write(' ');
        }
        out.write('\r');
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        out.write(stringBuilder.toString().getBytes());
        out.flush();
    }

    private void onCarriageReturn() throws IOException {
        out.write('\r');
        for (int i = 0; i < stringBuilder.length(); i++) {
            out.write(' ');
        }
        out.write('\r');
        out.flush();
    }

    public void writeLine(String line) throws IOException {
        out.write('\r');
        for (int i = 0; i < stringBuilder.length(); i++) {
            out.write(' ');
        }
        out.write('\r');
        out.write(line.getBytes());
        // newLine
        out.write('\r');
        out.write('\n');
        out.write(stringBuilder.toString().getBytes());
        out.flush();
    }

    private class EndOfTextException extends Exception {
    }

    private class CarriageReturnException extends Exception {

    }

    private class BackSpaceException extends Exception {

    }

}
