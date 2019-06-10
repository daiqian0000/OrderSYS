package com.asion.common.qrcode;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

/**
 * 二维码信息参数配置模型
 * 
 * @author chenboyang
 *
 */
public class QRCodeModel {

	/**
	 * 二维码信息内容
	 */
	private String content;
	
	/**
	 * 二维码宽度
	 */
	private int width = 200;
	
	/**
	 * 二维码高度
	 */
	private int height = 200;

	/**
	 * 二维码Logo配置信息
	 */
	private Logo logo;

	/**
	 * 二维码描述配置信息
	 */
	private Desc desc;
	
	public QRCodeModel() {

	}

	public QRCodeModel(String content) {
		this.content = content;
	}

	public QRCodeModel(String content, int width, int height) {
		this.content = content;
		this.width = width;
		this.height = height;
	}

	public QRCodeModel(String content, int width, int height, File logoFile, int logoRatio, String[] descTexts, Font descFont, Color descColor) {
		this.content = content;
		this.width = width;
		this.height = height;
		this.logo = createLogo(logoFile, logoRatio);
		this.desc = createDesc(descTexts, descFont, descColor);
	}

	public Logo createLogo() {
		return new Logo();
	}
	
	public Logo createLogo(File file, int ratio) {
		return new Logo(file, ratio);
	}

	public Desc createDesc() {
		return new Desc();
	}
	
	public Desc createDesc(String[] texts) {
		return new Desc(texts);
	}

	public Desc createDesc(String[] texts, Font font, Color color) {
		return new Desc(texts, font, color);
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}

	public Desc getDesc() {
		return desc;
	}

	public void setDesc(Desc desc) {
		this.desc = desc;
	}
	
	/**
	 * 二维码Logo配置模型
	 * 
	 * @author chenboyang
	 *
	 */
	public class Logo {
		
		public Logo() {

		}
		
		public Logo(File file, int ratio) {
			this.file = file;
			this.ratio = ratio;
		}
		
		/**
		 * Logo图片文件
		 */
		private File file;

		/**
		 * Logo占位比
		 */
		private int ratio;

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public int getRatio() {
			return ratio;
		}

		public void setRatio(int ratio) {
			this.ratio = ratio;
		}

	}

	/**
	 * 二维码描述配置模型
	 * 
	 * @author chenboyang
	 *
	 */
	public class Desc {

		public Desc() {

		}

		public Desc(String[] texts) {
			this.texts = texts;
		}
		
		public Desc(String[] texts, Font font, Color color) {
			this.texts = texts;
			this.font = font;
			this.color = color;
		}
		
		/**
		 * 描述文本组
		 */
		private String[] texts;

		/**
		 * 文本字体
		 */
		private Font font = new Font(null, Font.BOLD, 10);

		/**
		 * 文本颜色
		 */
		private Color color = Color.BLACK;

		public String[] getTexts() {
			return texts;
		}

		public void setTexts(String[] texts) {
			this.texts = texts;
		}

		public Font getFont() {
			return font;
		}

		public void setFont(Font font) {
			this.font = font;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}
		
	}

}
