package reivajh06;

import reivajh06.multithreading.ThreadPoolMTRayTracer;
import reivajh06.renderers.PixelRenderer;

import javax.swing.*;
import java.awt.*;

public class Main {

	public static final int NUM_SAMPLES = 800;

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	private static ImageContainer imageContainer = new ImageContainer(WIDTH, HEIGHT);

	public static void main(String[] args) {
		JFrame window = new JFrame();
		JPanel root = new JPanel();
		root.setLayout(new BorderLayout());

		root.add(imageContainer);

		window.setContentPane(root);

		RayTracer rayTracer = new ThreadPoolMTRayTracer();

		root.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		root.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		window.pack();

		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setEnabled(true);
		window.setVisible(true);

		rayTracer.run(createScene(), new PixelRenderer(imageContainer), new ImageConfig(imageContainer.getImageWidth(), imageContainer.getImageHeight(), NUM_SAMPLES));
	}

	public static void paintImage() {
		for(int i = 0; i < imageContainer.getImageWidth(); i++) {
			for(int j = 0; j < imageContainer.getImageHeight(); j++) {
				imageContainer.paintPixel(i, j, 0xFF);

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static Scene createScene() {
		return Scene.randomScene(200, 100);
	}
}
