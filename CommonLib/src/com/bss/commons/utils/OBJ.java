package com.bss.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class OBJ {
	private static final Encoder encoder = Base64.getEncoder();
	private static final Decoder decoder = Base64.getDecoder();

	public static byte[] serialize(Serializable obj) throws Exception {
		if (obj != null) {
			try (ByteArrayOutputStream bOut = new ByteArrayOutputStream()) {
				try (ObjectOutputStream oOut = new ObjectOutputStream(bOut)) {
					oOut.writeObject(obj);
				}
				return bOut.toByteArray();
			}
		}
		return null;
	}

	public static Serializable deserialize(byte[] data) throws Exception {
		if (data != null) {
			try (ByteArrayInputStream bIn = new ByteArrayInputStream(data)) {
				try (ObjectInputStream oIn = new ObjectInputStream(bIn)) {
					return (Serializable) oIn.readObject();
				}
			}
		}
		return null;
	}

	public static String zip(Serializable obj) throws Exception {
		if (obj != null) {
			return encoder.encodeToString(ZIP.zip(serialize(obj)));
		}
		return null;
	}

	public static Serializable unZip(String src) throws Exception {
		if (src != null) {
			return deserialize(ZIP.unZip(decoder.decode(src)));
		}
		return null;
	}
}
