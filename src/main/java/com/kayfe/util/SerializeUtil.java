package com.kayfe.util;

import java.io.*;

public class SerializeUtil {

    public static byte[] serialize(Object obj){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bao = null;
        try {
            bao = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bao);
            oos.writeObject(obj);
            byte[] bytes = bao.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bao != null) {
                    bao.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object deSerialize(byte[] bytes){
        if (bytes == null){
            return null;
        }
        ByteArrayInputStream bai = null;
        ObjectInputStream ois = null;
        try {
            bai = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bai);
            return ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bai != null){
                    bai.close();
                }
                if (ois != null) {
                    ois.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
