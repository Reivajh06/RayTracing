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
