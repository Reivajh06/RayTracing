package reivajh06;

public class Ray {

	private final Vector3D origin;
	private final Vector3D direction;

	public Ray(Vector3D origin, Vector3D direction) {
		this.origin = origin;
		this.direction = direction;
	}

	public Vector3D origin() {
		return origin;
	}

	public Vector3D direction() {
		return direction;
	}

	public Vector3D pointAtParameter(double t) {
		return Vector3D.add(origin, Vector3D.scalarProduct(direction, t));
	}
}
