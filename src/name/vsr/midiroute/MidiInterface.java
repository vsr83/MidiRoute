package name.vsr.midiroute;

import javax.sound.midi.*;

/**
 * A simple interface for interacting with MIDI devices.
 */
public class MidiInterface {
    // Information on each MIDI device.
    private MidiDevice.Info[] midiDeviceInfos;

    // MidiDevice object for each MIDI device used to interact with the device.
    private MidiDevice[] midiDevices;

    // The number of MIDI devices.
    private int numDevices;

    // A Transmitter object for MIDI OUT of each device.
    private Transmitter[] transmitters;

    public MidiInterface() {
    }

    /**
     * Initialize MIDI and collect data on the devices
     *
     * @throws MidiUnavailableException
     *     If device is unavailable.
     */
    public void initialize() throws MidiUnavailableException {
        midiDeviceInfos = MidiSystem.getMidiDeviceInfo();
        numDevices = midiDeviceInfos.length;

        transmitters = new Transmitter[numDevices];
        midiDevices = new MidiDevice[numDevices];

        for (int indDevice = 0; indDevice < numDevices; indDevice++) {
            midiDevices[indDevice] = MidiSystem.getMidiDevice(midiDeviceInfos[indDevice]);
        }
    }

    /**
     * Get name of the MIDI device.
     *
     * @param indDevice
     *     Index of the device.
     * @return
     *     Name of the device.
     */
    public String getName(final int indDevice) {
        return midiDeviceInfos[indDevice].getName();
    }

    /**
     * Check whether MIDI IN is available for a given device.
     *
     * @return
     *     Flag indicating availability.
     */
    public boolean isMidiInAvailable(final int indDevice) {
        return (midiDevices[indDevice].getMaxReceivers() != 0);
    }

    /**
     * Check whether MIDI OUT is available for a given device.
     *
     * @param indDevice
     *     Index of the device.
     * @return
     *     Flag indicating availability.
     */
    public boolean isMidiOutAvailable(final int indDevice)
    {
        return (midiDevices[indDevice].getMaxTransmitters() != 0);
    }

    /**
     * Open a MIDI device
     *
     * @param indDevice
     *     Index of the device.
     * @throws MidiUnavailableException
     *     If device is unavailable.
     */
    public void openDevice(final int indDevice) throws MidiUnavailableException
    {
        midiDevices[indDevice].open();
    }

    /**
     * Get MIDI IN transmitter for a device.
     *
     * @param indDevice
     *     Index of the device.
     * @return
     *     The Receiver object for the device.
     * @throws MidiUnavailableException
     *     If the device or a receiver for the device is unavailable..
     */
    public Receiver getMidiInputReceiver(int indDevice) throws MidiUnavailableException
    {
        return midiDevices[indDevice].getReceiver();
    }

    /**
     * Set MIDI OUT receiver for a device.
     *
     * @param indDevice
     *     Index of the device.
     * @param receiver
     *     The receiver.
     * @throws MidiUnavailableException
     *     If device is unavailable.
     **/
    public void setMidiOutputReceiver(int indDevice, final Receiver receiver) throws MidiUnavailableException {
        if (transmitters[indDevice] != null)
        {
            transmitters[indDevice].close();
            transmitters[indDevice] = null;
        }
        transmitters[indDevice] = midiDevices[indDevice].getTransmitter();
        transmitters[indDevice].setReceiver(receiver);
    }

    /**
     * Get the number of MIDI devices.
     *
     * @return The number of MIDI devices.
     */
    public int getNumDevices() {
        return numDevices;
    }

}
