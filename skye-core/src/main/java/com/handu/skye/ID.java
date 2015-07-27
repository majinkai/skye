package com.handu.skye;

import com.handu.skye.common.Base58;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author Jinkai.Ma
 */
public class ID {
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base58.encode(bb.array());
    }
}
