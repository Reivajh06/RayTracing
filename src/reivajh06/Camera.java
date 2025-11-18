package reivajh06;

public class Camera {

	private final Vector3D origin;
	private final Vector3D lowerLeftCorner;
	private final Vector3D horizontal;
	private final Vector3D vertical;

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

	public Camera() {
		this(
				new Vector3D(),
				new Vector3D(-2.0, -1.0, -1.0),
				new Vector3D(4.0, 0.0, 0.0),
				new Vector3D(0.0, 2.0, 0.0)
		);
	}

	public Ray getRay(double u, double v) {
		return new Ray(
				origin,
				Vector3D.subtract(
						Vector3D.add(
								Vector3D.add(lowerLeftCorner, Vector3D.scalarProduct(horizontal, u)),
								Vector3D.scalarProduct(vertical, v)
						),
						origin
				));
	}
}
