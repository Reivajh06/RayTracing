package reivajh06.renderers;

import reivajh06.ImageContainer;
import reivajh06.Renderer;

public abstract class BaseRenderer implements Renderer {

	ImageContainer imageContainer;

	public BaseRenderer(ImageContainer imageContainer) {
		this.imageContainer = imageContainer;
	}
}
