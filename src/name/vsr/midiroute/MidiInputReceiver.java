package name.vsr.midiroute;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * A simple class implementing the Receiver interface, which prints MIDI
 * messages and passes them to the receiver.
 */
public class MidiInputReceiver implements Receiver {
    private Receiver receiver;

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Public constructor.
     *
     * @param receiver The Receiver object.
     */
    public MidiInputReceiver(final Receiver receiver)
    {
        this.receiver = receiver;
    }

    /**
     * Print MIDI message and pass it to the receiver.
     *
     * @param message The MIDI message.
     * @param timeStamp Ignored.
     */
    @Override
    public void send(MidiMessage message, long timeStamp) {
        final byte[] data = message.getMessage();

        String messageStr = "midi received " + bytesToHex(data);
        System.out.println(messageStr);

        receiver.send(message, -1);
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }
}
