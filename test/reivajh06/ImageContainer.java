package reivajh06;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ImageContainer extends JComponent {

	private final BufferedImage image;
	private final int[] pixels;
	private final int width;

	public ImageContainer(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		this.width = width;
		setPreferredSize(new Dimension(width, height));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
		super.repaint(tm, x, y, width, height);
	}

	public void paintPixel(int x, int y, int rgb) {
		//image.setRGB(x, y, rgb);
		pixels[x + y * width] = rgb;
	}

	public int getImageWidth() {
		return image.getWidth();
	}

	public int getImageHeight() {
		return image.getHeight();
	}
}
