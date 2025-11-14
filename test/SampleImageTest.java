import reivajh06.Vector3D;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SampleImageTest {

	private static final String RESOURCESDIRECTORYPATH = "resources//";

	public static void main(String[] args) {
		sampleImage();
	}

	private static void sampleImage() {
		StringBuilder data = new StringBuilder();
		int nx = 200;
		int ny = 100;

		data.append("P3\n%s %s\n255\n".formatted(nx, ny));

		for(int j = ny - 1; j >= 0; j--) {
			for(int i = 0; i < nx; i++) {
				Vector3D col = new Vector3D(
						(double) i / (double) nx,
						(double) j / (double) ny,
						0.2);

				int ir =  (int) (255.99 * col.r());
				int ig = (int) (255.99* col.g());
				int ib = (int) (255.99 * col.b());

				data.append("%s %s %s\n".formatted(ir, ig, ib));
			}
		}

		try {
			Files.writeString(Path.of(RESOURCESDIRECTORYPATH + "sample_image.ppm"), data.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
