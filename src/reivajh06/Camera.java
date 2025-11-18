package reivajh06;

import static reivajh06.Main.RANDOM;

public class Camera {

	private static Vector3D randomInUnitDisk() {
		Vector3D p;

		do {
			p = Vector3D.scalarProduct(new Vector3D(RANDOM.nextDouble(0, 1), RANDOM.nextDouble(0, 1), 0), 2);
			p.subtract(new Vector3D(1, 1, 0));

		} while(Vector3D.dot(p, p) >= 1.0);

		return p;
	}

	private final Vector3D origin;
	private final Vector3D lowerLeftCorner;
	private final Vector3D horizontal;
	private final Vector3D vertical;
	private Vector3D u;
	private Vector3D v;
	private Vector3D w;
	double lensRadius;

	public Camera(Vector3D origin, Vector3D lowerLeftCorner, Vector3D horizontal, Vector3D vertical) {
		this.origin = origin;
		this.lowerLeftCorner = lowerLeftCorner;
		this.horizontal = horizontal;
		this.vertical = vertical;
	}

	public Camera(double vfov, double aspect) {
		double theta = vfov * Math.PI / 180;
		double halfHeight = Math.tan(theta / 2);
		double halfWidth = aspect * halfHeight;

		lowerLeftCorner = new Vector3D(-halfWidth, -halfHeight, -1.0);
		horizontal = new Vector3D(2 * halfWidth, 0.0, 0.0);
		vertical = new Vector3D(0.0, 2 * halfHeight, 0.0);
		origin = new Vector3D();
	}

	public Camera(Vector3D lookfrom, Vector3D lookat, Vector3D vup, double vfov, double aspect) {
		Vector3D u, v, w;

		double theta = vfov * Math.PI / 180;
		double halfHeight = Math.tan(theta / 2);
		double halfWidth = aspect * halfHeight;

		origin = lookfrom;
		w = Vector3D.unitVector(Vector3D.subtract(lookfrom, lookat));
		u = Vector3D.unitVector(Vector3D.cross(vup, w));
		v = Vector3D.cross(w, u);

		//replaced by the next operations
		//lowerLeftCorner = new Vector3D(-halfWidth, -halfHeight, 1.0);

		lowerLeftCorner = Vector3D.subtract(origin, Vector3D.scalarProduct(u, halfWidth));
		lowerLeftCorner.subtract(Vector3D.scalarProduct(v, halfHeight));
		lowerLeftCorner.subtract(w);

		horizontal = Vector3D.scalarProduct(u, 2 * halfWidth);
		vertical = Vector3D.scalarProduct(v, 2 * halfHeight);
	}

	public Camera(Vector3D lookfrom, Vector3D lookat, Vector3D vup, double vfov, double aspect, double aperture, double focusDistance) {
		lensRadius = aperture / 2;

		double theta = vfov * Math.PI / 180;
		double halfHeight = Math.tan(theta / 2);
		double halfWidth = aspect * halfHeight;

		origin = lookfrom;

		w = Vector3D.unitVector(Vector3D.subtract(lookfrom, lookat));
		u = Vector3D.unitVector(Vector3D.cross(vup, w));
		v = Vector3D.cross(w, u);

		lowerLeftCorner = Vector3D.subtract(origin, Vector3D.scalarProduct(u, halfWidth * focusDistance));
		lowerLeftCorner.subtract(Vector3D.scalarProduct(v, halfHeight * focusDistance));
		lowerLeftCorner.subtract(Vector3D.scalarProduct(w, focusDistance));

		horizontal = Vector3D.scalarProduct(u, 2 * halfWidth * focusDistance);
		vertical = Vector3D.scalarProduct(v, 2 * halfHeight * focusDistance);
	}

	public Camera() {
		this(
				new Vector3D(),
				new Vector3D(-2.0, -1.0, -1.0),
				new Vector3D(4.0, 0.0, 0.0),
				new Vector3D(0.0, 2.0, 0.0)
		);
	}

	public Ray getRay(double s, double t) {
		Vector3D rd = Vector3D.scalarProduct(randomInUnitDisk(), lensRadius);
		Vector3D offset = Vector3D.add(Vector3D.scalarProduct(u, rd.x()), Vector3D.scalarProduct(v, rd.y()));

		return new Ray(
				Vector3D.add(origin, offset),
				Vector3D.add(
						Vector3D.add(lowerLeftCorner, Vector3D.scalarProduct(horizontal, s)),
						Vector3D.subtract(Vector3D.scalarProduct(vertical, t), Vector3D.add(origin, offset))
				));
	}
}
