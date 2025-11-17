package reivajh06.materials;

import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;

public interface Material {

	boolean scatter(Ray rIn, Hitable.HitRecord record, Vector3D attenuation, Ray scattered);
}
