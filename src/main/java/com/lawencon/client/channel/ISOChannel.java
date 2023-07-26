package com.lawencon.client.channel;

import org.jpos.iso.BaseChannel;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class ISOChannel {

    public static String header(String mti,Map<Integer, String> log){

        Optional<Integer> max = log.keySet().stream().max(Comparator.naturalOrder());

        int sizeBit = (max.get() > 63)? 127: 63;

        BigInteger bitmap = BigInteger.ZERO.setBit(sizeBit);

        for (Integer bit: log.keySet()) {
            bitmap = bitmap.setBit(sizeBit - (bit - 1));
        }

        StringBuilder output = new StringBuilder();
        output.append(mti);

        output.append(bitmap.toString(16));

        for(Integer key: log.keySet()){
            output.append(log.get(key));
        }

        String message = output.toString();
        int lengthOfMessage = message.length();

        String header = "0000"+lengthOfMessage;

        int digitLength = String.valueOf(lengthOfMessage).length();

        return header.substring(digitLength);
    }


}
