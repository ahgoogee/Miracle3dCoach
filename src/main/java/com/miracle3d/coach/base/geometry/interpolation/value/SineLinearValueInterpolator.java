/* Copyright 2008 - 2021 Hochschule Offenburg
 * For a list of authors see README.md
 * This software of HSOAutonomy is released under GPL-3 License (see gpl.txt).
 */

package com.miracle3d.coach.base.geometry.interpolation.value;

import com.miracle3d.coach.base.geometry.interpolation.progress.LinearProgress;
import com.miracle3d.coach.base.geometry.interpolation.progress.ProgressFunction;

/**
 * @author Stefan Glaser
 */
public class SineLinearValueInterpolator extends ValueInterpolatorBase
{
	public double amplitude;

	public SineLinearValueInterpolator()
	{
		this(new LinearProgress(), 0);
	}

	public SineLinearValueInterpolator(ProgressFunction progress, double amplitude)
	{
		super(progress);

		this.amplitude = amplitude;
	}

	@Override
	protected double calculateInterpolationValue(double initial, double target, float t)
	{
		double lin = (target - initial) * t;
		double sin = Math.sin(t * Math.PI) * amplitude;

		return initial + lin + sin;
	}
}
