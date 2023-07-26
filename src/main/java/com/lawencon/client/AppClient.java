package com.lawencon.client;

import com.lawencon.client.channel.ISOChannel;
import org.jpos.iso.ISOMsg;
import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppClient {
    public static void main(String[] args) throws Exception {
        Q2 q2 = new Q2();
        q2.start();

        Thread.sleep(5 * 1000);

        QMUX sender = (QMUX) NameRegistrar.get("mux.jpos");

        // membuat header manual(masih belum mengerti membuat channel)
        DateFormat formatBit7 = new SimpleDateFormat("MMddHHmmss");
//        String mti = "0800";
        String date = formatBit7.format(new Date());
//        Map<Integer, String> logRequestMap = new LinkedHashMap<>();
//
//        logRequestMap.put(7, date.toString());
//        logRequestMap.put(11, "834624");
//        logRequestMap.put(70, "001");
//
//        String header = ISOChannel.header(mti, logRequestMap);
        //

        ISOMsg logRequest = new ISOMsg();

//        logRequest.setHeader(header.getBytes());
        logRequest.set(0,"0800");
        logRequest.set(7, date.toString());
        logRequest.set(11, "834624");
        logRequest.set(70, "001");

        ISOMsg response = sender.request(logRequest, 20 * 1000);

        System.out.println(new String(logRequest.pack()));
//        System.out.println(new String(logRequest.getHeader()));

        if(response == null){
            System.out.println("No Response");
            return;
        }

        System.out.println("Response: "+ new String(response.pack()));
    }
}
