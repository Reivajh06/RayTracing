package reivajh06.renderers;

import reivajh06.ImageContainer;

public class RowRenderer extends BaseRenderer {

	private final int width;

	private final ThreadLocal<Pixel[]> rowTL;
	private final ThreadLocal<Integer> indexTL;

	public RowRenderer(ImageContainer imageContainer) {
		super(imageContainer);
		this.width = imageContainer.getImageWidth();

		rowTL = ThreadLocal.withInitial(() -> new Pixel[width]);
		indexTL = ThreadLocal.withInitial(() -> 0);
	}

	@Override
	public void render(int x, int y, int rgb) {
		Pixel[] row = rowTL.get();
		int index = indexTL.get();

		row[index] = new Pixel(x, y, rgb);
		index++;

		if (index >= row.length) {
			for (Pixel p : row) {
				imageContainer.paintPixel(p.x(), p.y(), p.rgb());
			}
			imageContainer.repaint();
			index = 0;
		}

		indexTL.set(index);
	}
}