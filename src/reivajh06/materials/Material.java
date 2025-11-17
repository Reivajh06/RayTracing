package reivajh06.materials;

import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;

public interface Material {

	default Vector3D reflect(Vector3D v, Vector3D n) {
		return Vector3D.subtract(
				v,
				Vector3D.scalarProduct(n, Vector3D.dot(v, n) * 2)
		);
	}
	boolean scatter(Ray rIn, Hitable.HitRecord record, Vector3D attenuation, Ray scattered);
}
