/* Copyright 2008 - 2021 Hochschule Offenburg
 * For a list of authors see README.md
 * This software of HSOAutonomy is released under GPL-3 License (see gpl.txt).
 */

package com.miracle3d.coach.utils.scene;

public interface ISceneGraph {
	IBaseNode getRootNode();

	ISceneGraphHeader getHeader();
}
