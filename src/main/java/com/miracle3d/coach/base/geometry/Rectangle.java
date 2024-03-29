/* Copyright 2008 - 2021 Hochschule Offenburg
 * For a list of authors see README.md
 * This software of HSOAutonomy is released under GPL-3 License (see gpl.txt).
 */

package com.miracle3d.coach.base.geometry;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.Serializable;

/**
 * A rectangular Area in 2D space.
 *
 * @author Klaus Dorer
 */
public class Rectangle implements Serializable
{
	private final Vector2D bl;
	private final Vector2D tr;

	public Rectangle(Vector2D bottomLeft, Vector2D topRight)
	{
		this.bl = bottomLeft;
		this.tr = topRight;
	}

	public Rectangle(Area2D.Float area)
	{
		this(area.getBottomLeft(), area.getTopRight());
	}

	public Vector2D getBottomLeft()
	{
		return bl;
	}

	public Vector2D getTopRight()
	{
		return tr;
	}

	@Override
	public String toString()
	{
		return "Rectangle [bl=" + bl + ", tr=" + tr + "]";
	}
}
