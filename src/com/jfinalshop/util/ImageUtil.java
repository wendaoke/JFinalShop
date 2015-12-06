package com.jfinalshop.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import com.jfinalshop.bean.SystemConfig.WatermarkPosition;

/**
 * 工具类 - 图片处理
 * 
 */

public class ImageUtil {

	/**
	 * 图片缩放(图片等比例缩放为指定大小，空白部分以白色填充)
	 * 
	 * @param srcBufferedImage
	 *            源图片
	 * @param destFile
	 *            缩放后的图片文件
	 */
	public static void zoom(BufferedImage srcBufferedImage, File destFile, int destHeight, int destWidth) {
		try {
			Thumbnails.of(srcBufferedImage).size(destWidth, destHeight).outputFormat("JPEG") .toFile(destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加图片水印
	 * 
	 * @param srcBufferedImage
	 *            需要处理的源图片
	 * @param destFile
	 *            处理后的图片文件
	 * @param watermarkFile
	 *            水印图片文件
	 * 
	 */
	public static void imageWatermark(BufferedImage srcBufferedImage, File destFile, File watermarkFile, WatermarkPosition watermarkPosition, int alpha) {
		try {
			if (watermarkFile != null && watermarkFile.exists() && watermarkPosition != null && watermarkPosition != WatermarkPosition.no) {
				if (watermarkPosition == WatermarkPosition.topLeft) {
					Thumbnails.of(srcBufferedImage).scale(1f).watermark(Positions.TOP_LEFT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG").toFile(destFile) ;
				} else if (watermarkPosition == WatermarkPosition.topRight) {
					Thumbnails.of(srcBufferedImage).scale(1f).watermark(Positions.TOP_RIGHT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG") .toFile(destFile);
				} else if (watermarkPosition == WatermarkPosition.center) {
					Thumbnails.of(srcBufferedImage).scale(1f).watermark(Positions.CENTER,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG").toFile(destFile) ;
				} else if (watermarkPosition == WatermarkPosition.bottomLeft) {
					Thumbnails.of(srcBufferedImage).scale(1f).watermark(Positions.BOTTOM_LEFT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG") .toFile(destFile);
				} else if (watermarkPosition == WatermarkPosition.bottomRight) {
					Thumbnails.of(srcBufferedImage).scale(1f).watermark(Positions.BOTTOM_RIGHT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG") .toFile(destFile) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片缩放并添加图片水印(图片等比例缩放为指定大小，空白部分以白色填充)
	 * 
	 * @param srcBufferedImage
	 *            需要处理的图片
	 * @param destFile
	 *            处理后的图片文件
	 * @param watermarkFile
	 *            水印图片文件
	 * 
	 */
	public static void zoomAndWatermark(BufferedImage srcBufferedImage, File destFile, int destHeight, int destWidth, File watermarkFile, WatermarkPosition watermarkPosition, int alpha) {
		try {
			if (watermarkFile != null && watermarkFile.exists() && watermarkPosition != null && watermarkPosition != WatermarkPosition.no) {
				if (watermarkPosition == WatermarkPosition.topLeft) {
					Thumbnails.of(srcBufferedImage).size(destWidth, destHeight).watermark(Positions.TOP_LEFT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG").toFile(destFile) ;
				} else if (watermarkPosition == WatermarkPosition.topRight) {
					Thumbnails.of(srcBufferedImage).size(destWidth, destHeight).watermark(Positions.TOP_RIGHT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG") .toFile(destFile);
				} else if (watermarkPosition == WatermarkPosition.center) {
					Thumbnails.of(srcBufferedImage).size(destWidth, destHeight).watermark(Positions.CENTER,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG").toFile(destFile) ;
				} else if (watermarkPosition == WatermarkPosition.bottomLeft) {
					Thumbnails.of(srcBufferedImage).size(destWidth, destHeight).watermark(Positions.BOTTOM_LEFT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG") .toFile(destFile);
				} else if (watermarkPosition == WatermarkPosition.bottomRight) {
					Thumbnails.of(srcBufferedImage).size(destWidth, destHeight).watermark(Positions.BOTTOM_RIGHT,ImageIO.read(watermarkFile),alpha / 100.0F).outputFormat("JPEG") .toFile(destFile) ;
				}
			}else{
				Thumbnails.of(srcBufferedImage).size(destWidth, destHeight).outputFormat("JPEG") .toFile(destFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取图片文件的类型.
	 * 
	 * @param uploadFile
	 *            图片文件对象.
	 * @return 图片文件类型
	 */
	public static String getImageFormatName(File uploadFile) {
		try {
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(uploadFile);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
			if (!iterator.hasNext()) {
				return null;
			}
			ImageReader imageReader = (ImageReader) iterator.next();
			imageInputStream.close();
			return imageReader.getFormatName().toLowerCase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}