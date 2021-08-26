package com.mwg.caches;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;


public class CUtils {

	private static final Encoder encoder = Base64.getEncoder();

	private static final Decoder decoder = Base64.getDecoder();

	public static String toStringData(Serializable obj) throws Exception {
		if (obj != null) {
			return encoder.encodeToString(toBytes(obj));
		}
		return null;
	}

	public static Object fromStringData(String src) throws Exception {
		if (src != null) {
			return fromBytes(decoder.decode(src));
		}
		return null;
	}

	private static byte[] toBytes(Serializable obj) throws Exception {
		if (obj != null) {
			try (ByteArrayOutputStream out = new ByteArrayOutputStream(); ObjectOutputStream objOut = new ObjectOutputStream(out)) {
				objOut.writeObject(obj);
				objOut.flush();
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				return out.toByteArray();
			}
		}
		return null;
	}

	private static Object fromBytes(byte[] data) throws Exception {
		if (data != null) {
			try (ByteArrayInputStream in = new ByteArrayInputStream(data); ObjectInputStream objIn = new ObjectInputStream(in)) {
				return objIn.readObject();
			}
		}
		return null;
	}

}
