package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;

public class ChatCommand implements Command, Runnable {
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ExitCallback callback;
    private OutputStream err;
    private ChatShellFactory chatShellFactory;
    public String userName;
    private StringBuilder stringBuilder;

    public ChatCommand(ChatShellFactory chatShellFactory) {
        super();
        this.chatShellFactory = chatShellFactory;
    }

    @Override
    public void start(ChannelSession channel, Environment env) throws IOException {
        userName = env.getEnv().get("USER");
        new Thread(this).start();
    }

    @Override
    public void destroy(ChannelSession channel) throws Exception {
        chatShellFactory.chatCommands.remove(this);

    }

    @Override
    public void setInputStream(InputStream in) {
        bufferedReader = new BufferedReader(new InputStreamReader(in));

    }

    @Override
    public void setOutputStream(OutputStream out) {
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));

    }

    @Override
    public void setErrorStream(OutputStream err) {
        this.err = err;

    }

    private String repeatChar(int length, char c) {
        return new String(new char[length]).replace('\0', c);
    }

    private String readLine() throws IOException {
        stringBuilder = new StringBuilder();
        label: while (true) {
            int c = bufferedReader.read();
            switch (c) {
                case 13:
                    carriageReturn();
                    break label;
                case 127:
                    if (stringBuilder.length() > 0) {
                        backSpace();
                    }
                    break;
                default:
                    if (c >= 32 && c <= 126) {
                        bufferedWriter.write(c);
                        bufferedWriter.flush();
                        stringBuilder.append((char) c);
                    }
                    if (c == 3) {// endOfText
                        stringBuilder.append((char) c);
                        break label;
                    }
                    break;
            }
        }
        String string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        return string;
    }

    private void carriageReturn() throws IOException {
        bufferedWriter.write('\r');
        bufferedWriter.write(repeatChar(stringBuilder.length(), ' '));
        bufferedWriter.write('\r');
        bufferedWriter.flush();
    }

    private void backSpace() throws IOException {
        bufferedWriter.write('\r');
        bufferedWriter.write(repeatChar(stringBuilder.length(), ' '));
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        bufferedWriter.write('\r');
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    public void writeLine(String string) throws IOException {
        bufferedWriter.write('\r');
        bufferedWriter.write(repeatChar(stringBuilder.length(), ' '));
        bufferedWriter.write('\r');
        bufferedWriter.write(string);
        bufferedWriter.newLine();
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String cmd = readLine();
                if (cmd.endsWith("\u0003")) {
                    break;
                }
                chatShellFactory.onChatCommadReadLine(cmd, this);
            } catch (Exception e) {
                callback.onExit(-1, e.getMessage());
                return;
            }
        }
        callback.onExit(0);

    }

    @Override
    public void setExitCallback(ExitCallback callback) {
        // TODO Auto-generated method stub
        this.callback = callback;
    }

}
