package reivajh06;

import reivajh06.hitables.Hitable;
import reivajh06.hitables.HitableList;
import reivajh06.hitables.Sphere;
import reivajh06.materials.Dielectric;
import reivajh06.materials.Lambertian;
import reivajh06.materials.Metal;

import java.util.Random;

public class Scene {

	private Camera camera;
	private HitableList world;

	public Scene(Camera camera, HitableList world) {
		this.camera = camera;
		this.world = world;
	}

	public Scene setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}

	public Scene setCamera(double width, double height) {
		return setCamera(new Camera.Builder()
				.aspect(width / height)
				.build());
	}

	public Scene addHitable(Hitable hitable) {
		world.add(hitable);

		return this;
	}

	public Scene world(HitableList world) {
		this.world = world;

		return this;
	}

	public Camera camera() {
		return camera;
	}

	public HitableList world() {
		return world;
	}

	public static Scene randomScene(int width, int height) {
		Random random = new Random();
		HitableList list = new HitableList();

		list.add(new Sphere(new Vector3D(0, -1000, 0), 1000, new Lambertian(new Vector3D(0.5, 0.5, 0.5))));

		for(int a = -11; a < 11; a++) {
			for(int b = -11; b < 11; b++) {
				double chooseMat = random.nextDouble(0, 1);

				Vector3D center = new Vector3D(a + 0.9 * random.nextDouble(0, 1), 0.2, b + 0.9 * random.nextDouble(0, 1));

				if(Vector3D.subtract(center, new Vector3D(4, 0.2, 0)).length() > 0.9) {
					if(chooseMat < 0.8) { //diffuse
						Lambertian lambertian = new Lambertian(new Vector3D(
								random.nextDouble(0, 1) * random.nextDouble(0, 1),
								random.nextDouble(0, 1) * random.nextDouble(0, 1),
								random.nextDouble(0, 1) * random.nextDouble(0, 1)));

						list.add(new Sphere(center, 0.2, lambertian));

					} else if(chooseMat < 0.95) { //metal
						Metal metal = new Metal(new Vector3D(
								0.5 * (1 + random.nextDouble(0, 1)),
								0.5 * (1 + random.nextDouble(0, 1)),
								0.5 * (1 + random.nextDouble(0, 1))),
								0.5 * random.nextDouble(0, 1));

						list.add(new Sphere(center, 0.2, metal));

					} else { // glass
						list.add(new Sphere(center, 0.2, new Dielectric(1.5)));
					}
				}
			}
		}

		list.add(new Sphere(new Vector3D(0, 1, 0), 1.0, new Dielectric(1.5)));
		list.add(new Sphere(new Vector3D(-4, 1, 0), 1.0, new Lambertian(new Vector3D(0.4, 0.2, 0.1))));
		list.add(new Sphere(new Vector3D(4, 1, 0), 1.0, new Metal(new Vector3D(0.7, 0.6, 0.5), 0.0)));

		return new Scene(new Camera.Builder().aspect(width, height).build(), list);
	}
}
