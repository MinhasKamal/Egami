/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Modification Date: 05-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.matrix;

public abstract class MatrixTransformer {
	
	protected int constant;
	protected double constant2;
	
	public MatrixTransformer() {
		this(0, 0);
	}
	
	public MatrixTransformer(int constant) {
		this(constant, 0);
	}
	
	public MatrixTransformer(int constant, double constant2) {
		this.constant = constant;
		this.constant2 = constant2;
	}
	
	public Matrix transform(Matrix matrix){
		int rows = matrix.getRows(),
			cols = matrix.getCols(),
			type = matrix.getType();
		
		Matrix matrix2 = new Matrix(rows, cols, type);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				for(int k=0; k<type; k++){
					matrix2.pixels[i][j][k] = transformFunction(matrix.pixels[i][j][k], i, j, k);
				}
			}
		}
		
		return matrix2;
	}
	
	public abstract int transformFunction(int pixel, int rowNo, int colNo, int colorSpaceNo);
}
