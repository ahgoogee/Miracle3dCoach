/* Copyright 2008 - 2021 Hochschule Offenburg
 * For a list of authors see README.md
 * This software of HSOAutonomy is released under GPL-3 License (see gpl.txt).
 */

package base.geometry.orientationFilter;

import base.geometry.Geometry;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;

import java.util.ArrayList;

public class LinearWeightedOrientationFilter extends OrientationFilterBase
{
	public LinearWeightedOrientationFilter()
	{
	}

	public LinearWeightedOrientationFilter(int i)
	{
		super(i);
	}

	@Override
	protected Rotation calculateNewOrientation()
	{
		ArrayList<Rotation> rotations = new ArrayList<>();
		int weight = filterBuffer.size();
		for (Rotation r : filterBuffer) {
			for (int i = 0; i < weight; i++) {
				rotations.add(r);
			}
			weight--;
		}

		return Geometry.getAverageRotation(rotations);
	}
}
