package reivajh06.renderers;

import reivajh06.ImageContainer;

public class BatchRenderer extends BaseRenderer {

	private Pixel[][] rows;
	private int rIndex; //position of current row
	private int pIndex; //position of current pixel within current row

	public BatchRenderer(ImageContainer imageContainer, int batchSize) {
		super(imageContainer);

		rows = new Pixel[batchSize][imageContainer.getImageWidth()];
	}

	@Override
	public void render(int x, int y, int rgb) {
		rows[rIndex][pIndex++] = new Pixel(x, y, rgb);

		if(pIndex >= rows[0].length) {
			rIndex++;
			pIndex = 0;
		}

		if(rIndex >= rows.length) {
			for(Pixel[] row : rows) {
				for(Pixel pixel : row) {
					imageContainer.paintPixel(pixel.x(), pixel.y(), pixel.rgb());
				}
			}

			imageContainer.repaint();
			rIndex = 0;
			pIndex = 0;
		}
	}
}
