package com.example.ansicontrolsequences;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ControlSequence {
    private final byte[] introducer = { 0x1B, 0x5B };// ESC [
    private final byte parameterSeparator = 0x3B;// ;

    private byte[] controlSequence;

    public byte[] getControlSequence() {
        return controlSequence;
    }

    @Override
    public String toString() {
        return new String(controlSequence);
    }

    /**
     * @param parameterBytes    0x30–0x3F
     * @param intermediateBytes 0x20–0x2F
     * @param finalByte         0x40–0x7E
     * @throws IOException
     */
    public ControlSequence(byte[] parameterBytes, byte[] intermediateBytes, byte finalByte) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(introducer);
            byteArrayOutputStream.write(join(parameterBytes, parameterSeparator));
            byteArrayOutputStream.write(join(intermediateBytes, parameterSeparator));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byteArrayOutputStream.write(finalByte);
        controlSequence = byteArrayOutputStream.toByteArray();
    }

    public ControlSequence(byte[] parameterBytes, byte finalByte) {
        this(parameterBytes, new byte[] {}, finalByte);
    }

    public ControlSequence(byte parameterByte, byte finalByte) {
        this(new byte[] { parameterByte }, finalByte);
    }

    public ControlSequence(char parameterByte, char finalByte) {
        this((byte) parameterByte, (byte) finalByte);
    }

    private static byte[] join(byte[] bytes, byte separator) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length; i++) {
            byteArrayOutputStream.write(bytes[i]);
            if (i == bytes.length - 1) {
                continue;
            }
            byteArrayOutputStream.write(separator);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] args) {
        System.out.println(new ControlSequence(new byte[] { (byte) '3', (byte) '1' }, (byte) 'm'));
        System.out.println("dio cae");
        System.out.println(new ControlSequence(new byte[] { (byte) '3', (byte) '1' }, (byte) 'm'));
        System.out.println("dio cae");
    }
}
