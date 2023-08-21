/* Copyright 2008 - 2021 Hochschule Offenburg
 * For a list of authors see README.md
 * This software of HSOAutonomy is released under GPL-3 License (see gpl.txt).
 */

package com.miracle3d.coach.base.geometry.orientationFilter;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;

import java.util.LinkedList;

public abstract class OrientationFilterBase implements IOrientationFilter
{
	protected static final int DEFAULT_BUFFER_SIZE = 5;

	protected final LinkedList<Rotation> filterBuffer = new LinkedList<>();

	protected int bufferSize = DEFAULT_BUFFER_SIZE;

	public OrientationFilterBase()
	{
	}

	public OrientationFilterBase(int i)
	{
		bufferSize = i;
	}

	@Override
	public Rotation filterOrientation(Rotation newOrientation)
	{
		if (newOrientation == null)
			return null;

		filterBuffer.push(newOrientation);
		if (filterBuffer.size() > bufferSize)
			filterBuffer.pollLast();

		return calculateNewOrientation();
	}

	protected abstract Rotation calculateNewOrientation();

	@Override
	public void reset()
	{
		filterBuffer.clear();
	}
}
