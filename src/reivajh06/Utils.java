package reivajh06;

import java.util.Random;

public class Utils {

	private static final Random RANDOM = new Random();

	public static Vector3D randomInUnitSphere() {
		Vector3D p;

		do {
			p = Vector3D.subtract(
					Vector3D.scalarProduct(
							new Vector3D(RANDOM.nextDouble(0, 1), RANDOM.nextDouble(0, 1), RANDOM.nextDouble(0, 1)),
							2.0),
					new Vector3D(1, 1, 1)

			);

		} while(p.squaredLength() >= 1.0);

		return p;
	}
}
