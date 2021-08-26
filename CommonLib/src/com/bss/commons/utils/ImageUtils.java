package com.bss.commons.utils;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class ImageUtils {
	public static BufferedImage ImgScale(BufferedImage before, int maxWidth) throws Exception {
		if (before.getWidth() <= maxWidth) {
			return before;
		}
		return Scalr.resize(before, maxWidth, (BufferedImageOp) null);
	}

	public static byte[] ImageToRaw(BufferedImage img, int maxWidth, String imgType) throws Exception {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			ImageIO.write(ImgScale(img, maxWidth), imgType, out);
			return out.toByteArray();
		}
	}
}
