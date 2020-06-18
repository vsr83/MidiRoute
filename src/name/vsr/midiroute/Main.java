package name.vsr.midiroute;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

public class Main {

    private static String USAGE_STRING = "Usage: java ... [MIDI OUT device number] [MIDI IN device number]";

    /**
     * Print the available devices.
     *
     * @param midiInterface MidiInterface object that has been initialized.
     */
    private static void listDevices(final MidiInterface midiInterface) {
        for (int indDevice = 0; indDevice < midiInterface.getNumDevices(); indDevice++) {
            final String ioAvailable = (midiInterface.isMidiInAvailable(indDevice) ? " [IN]" : "     ") +
                    (midiInterface.isMidiOutAvailable(indDevice) ? " [OUT]" : "      ");

            System.out.printf("%2d: %60s %s\n", indDevice, midiInterface.getName(indDevice), ioAvailable);
        }
    }

    public static void main(String[] args) {

        final MidiInterface midiInterface = new MidiInterface();
        try {
            midiInterface.initialize();

            // write your code here
            if (args.length == 2) {
                final int midiOutDevice = Integer.parseInt(args[0]);
                final int midiInDevice = Integer.parseInt(args[1]);

                // Get MIDI IN receiver interface for the MIDI IN device and open the device.
                Receiver midiInReceiver = midiInterface.getMidiInputReceiver(midiInDevice);
                midiInterface.openDevice(midiInDevice);

                // Create an object, which implements the send method of the Receiver interface
                // by printing the input event and passing the event to the Receiver interface.
                MidiInputReceiver inputReceiver = new MidiInputReceiver(midiInReceiver);

                // Connect MIDI OUT messages to the MIDI IN receiver and open the MIDI OUT device:
                midiInterface.setMidiOutputReceiver(midiOutDevice, inputReceiver);
                midiInterface.openDevice(midiOutDevice);
            } else {
                System.out.println(USAGE_STRING);
                listDevices(midiInterface);
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
