/* Copyright 2008 - 2021 Hochschule Offenburg
 * For a list of authors see README.md
 * This software of HSOAutonomy is released under GPL-3 License (see gpl.txt).
 */

package com.miracle3d.coach.base.misc;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class PrioritizedPosition
{
	private final Vector3D position;

	private final float priority;

	public PrioritizedPosition(Vector3D position, float priority)
	{
		this.position = position;
		this.priority = priority;
	}

	public Vector3D getPosition()
	{
		return position;
	}

	public float getPriority()
	{
		return priority;
	}
}
