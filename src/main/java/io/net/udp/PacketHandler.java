package io.net.udp;

import java.net.DatagramPacket;

public abstract class PacketHandler {

    public abstract void process(DatagramPacket packet);
}
