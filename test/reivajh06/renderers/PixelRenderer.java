package reivajh06.renderers;

import reivajh06.ImageContainer;

public class PixelRenderer extends BaseRenderer {

	public PixelRenderer(ImageContainer imageContainer) {
		super(imageContainer);
	}

	@Override
	public void render(int x, int y, int rgb) {
		imageContainer.paintPixel(x, y, rgb);
		imageContainer.repaint();
	}
}
