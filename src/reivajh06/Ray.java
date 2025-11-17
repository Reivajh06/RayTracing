package reivajh06;

public class Ray {

	private Vector3D origin;
	private Vector3D direction;

	public Ray(Vector3D origin, Vector3D direction) {
		this.origin = origin;
		this.direction = direction;
	}

	public Ray() {}

	public Vector3D origin() {
		return origin;
	}

	public Vector3D direction() {
		return direction;
	}

	public Vector3D pointAtParameter(double t) {
		return Vector3D.add(origin, Vector3D.scalarProduct(direction, t));
	}

	public Ray origin(Vector3D origin) {
		this.origin = origin;
		return this;
	}

	public Ray direction(Vector3D direction) {
		this.direction = direction;
		return this;
	}
}
