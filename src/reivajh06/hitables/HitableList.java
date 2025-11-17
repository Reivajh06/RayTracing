package reivajh06.hitables;

import reivajh06.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HitableList implements Hitable {

	private List<Hitable> hitablesList;

	public HitableList() {
		hitablesList = new ArrayList<>();
	}

	public HitableList(Hitable... hitables) {
		this();

		hitablesList.addAll(Arrays.asList(hitables));
	}

	@Override
	public boolean hit(Ray r, double tMin, double tMax, Hitable.HitRecord record) {
		Hitable.HitRecord tempRecord = new Hitable.HitRecord();

		boolean hitAnything = false;
		double closestSoFar = tMax;

		for(Hitable hitable : hitablesList) {
			if(hitable.hit(r, tMin, closestSoFar, tempRecord)) {
				hitAnything = true;
				closestSoFar = tempRecord.t;

				record.t = tempRecord.t;
				record.p = tempRecord.p;
				record.normal = tempRecord.normal;
				record.material = tempRecord.material;
			}
		}

		return hitAnything;
	}

	public void add(Hitable hitable) {
		hitablesList.add(hitable);
	}

	public Hitable remove(int index) {
		return hitablesList.remove(index);
	}

	public boolean remove(Hitable hitable) {
		return hitablesList.remove(hitable);
	}
}
