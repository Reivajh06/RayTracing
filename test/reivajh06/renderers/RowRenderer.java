package reivajh06.renderers;

import reivajh06.ImageContainer;

public class RowRenderer extends BaseRenderer {

	private Pixel[] row;
	int index;

	public RowRenderer(ImageContainer imageContainer) {
		super(imageContainer);

		row = new Pixel[imageContainer.getImageWidth()];
	}

	@Override
	public void render(int x, int y, int rgb) {
		row[index++] = new Pixel(x, y, rgb);

		if(index >= row.length) {
			for(Pixel pixel : row) {
				imageContainer.paintPixel(pixel.x(), pixel.y(), pixel.rgb());
			}

			imageContainer.repaint();

			index = 0;
		}
	}
}
