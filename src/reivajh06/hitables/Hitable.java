package reivajh06.hitables;

import reivajh06.Ray;
import reivajh06.Vector3D;

public interface Hitable {

	boolean hit(Ray r, double tMin, double tMax, HitRecord record);

	class HitRecord {

		public double t;
		public Vector3D p;
		public Vector3D normal;

		public HitRecord(double t, Vector3D p, Vector3D normal) {
			this.t = t;
			this.p = p;
			this.normal = normal;
		}

		public HitRecord() {}
	}
}
