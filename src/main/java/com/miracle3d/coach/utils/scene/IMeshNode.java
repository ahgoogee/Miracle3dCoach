/* Copyright 2008 - 2021 Hochschule Offenburg
 * For a list of authors see README.md
 * This software of HSOAutonomy is released under GPL-3 License (see gpl.txt).
 */

package com.miracle3d.coach.utils.scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface IMeshNode extends IBaseNode {
	Boolean isVisible();

	Boolean isTransparent();

	String getObjName();

	Vector3D getScale();

	String[] getMaterials();
}
