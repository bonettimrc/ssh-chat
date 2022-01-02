package com.example.ansicontrolsequences;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Function;

public class ControlSequence {
    private final byte[] introducer = { 0x1B, 0x5B };// ESC [

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
            byteArrayOutputStream.write(parameterBytes);
            byteArrayOutputStream.write(intermediateBytes);
        } catch (IOException e) {
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

    public ControlSequence(String parameterBytes, char finalByte) {
        this(parameterBytes.getBytes(), (byte) finalByte);
    }

    public static Function<String, ControlSequence> getCurriedControlSequence(final char finalByte) {
        return new Function<String, ControlSequence>() {
            @Override
            public ControlSequence apply(String t) {
                return new ControlSequence(t, finalByte);
            }

        };
    }

    public static final Function<String, ControlSequence> cursorUp = getCurriedControlSequence('A');
    public static final Function<String, ControlSequence> cursorDown = getCurriedControlSequence('B');
    public static final Function<String, ControlSequence> cursorForward = getCurriedControlSequence('C');
    public static final Function<String, ControlSequence> cursorBack = getCurriedControlSequence('D');
    public static final Function<String, ControlSequence> cursorNextLine = getCurriedControlSequence('E');
    public static final Function<String, ControlSequence> cursorPreviousLine = getCurriedControlSequence('F');
    public static final Function<String, ControlSequence> cursorHorizontalAbsolute = getCurriedControlSequence('G');
    public static final Function<String, ControlSequence> cursorPosition = getCurriedControlSequence('H');
    public static final Function<String, ControlSequence> eraseInDisplay = getCurriedControlSequence('J');
    public static final Function<String, ControlSequence> eraseInLine = getCurriedControlSequence('K');
    public static final Function<String, ControlSequence> scrollUp = getCurriedControlSequence('S');
    public static final Function<String, ControlSequence> scrollDown = getCurriedControlSequence('T');
    public static final Function<String, ControlSequence> horizontalVerticalPosition = getCurriedControlSequence('f');
    public static final Function<String, ControlSequence> selectGraphicRendition = getCurriedControlSequence('m');

}
