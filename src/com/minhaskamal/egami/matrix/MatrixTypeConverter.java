/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
* Modification Date: 04-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.matrix;

public class MatrixTypeConverter {
	
	/**
	 * Converts any of the 4 types of Matrixes to the other.If the input Matrix's type matches
	 * the <code>convertionType</code>, then the same object is returned.
	 * @param convertionType type of output Matrix
	 * @return
	 */
	public static Matrix convert(Matrix matrix, int convertionType){
		int type = matrix.getType();
		Matrix matrix2;
		
		if(type==Matrix.BLACK_WHITE){
			if(convertionType==Matrix.BLACK_WHITE){
				matrix2 = matrix;
			}else if(convertionType==Matrix.BLACK_WHITE_ALPHA){
				matrix2 = convertType1to2(matrix);
			}else if(convertionType==Matrix.RED_GREEN_BLUE){
				matrix2 = convertType1to3(matrix);
			}else{
				matrix2 = convertType1to4(matrix);
			}
			
		}else if(type==Matrix.BLACK_WHITE_ALPHA){
			if(convertionType==Matrix.BLACK_WHITE){
				matrix2 = convertType2to1(matrix);
			}else if(convertionType==Matrix.BLACK_WHITE_ALPHA){
				matrix2 = matrix;
			}else if(convertionType==Matrix.RED_GREEN_BLUE){
				matrix2 = convertType2to3(matrix);
			}else{
				matrix2 = convertType2to4(matrix);
			}
			
		}else if(type==Matrix.RED_GREEN_BLUE){
			if(convertionType==Matrix.BLACK_WHITE){
				matrix2 = convertType3to1(matrix);
			}else if(convertionType==Matrix.BLACK_WHITE_ALPHA){
				matrix2 = convertType3to2(matrix);
			}else if(convertionType==Matrix.RED_GREEN_BLUE){
				matrix2 = matrix;
			}else{
				matrix2 = convertType3to4(matrix);
			}
			
		}else{
			if(convertionType==Matrix.BLACK_WHITE){
				matrix2 = convertType4to1(matrix);
			}else if(convertionType==Matrix.BLACK_WHITE_ALPHA){
				matrix2 = convertType4to2(matrix);
			}else if(convertionType==Matrix.RED_GREEN_BLUE){
				matrix2 = convertType4to3(matrix);
			}else{
				matrix2 = matrix;
			}
			
		}
		
		return matrix2;
	}
	
	public static Matrix convertType1to2(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.BLACK_WHITE_ALPHA);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][1] = Matrix.MAX_PIXEL;
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType1to3(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.RED_GREEN_BLUE);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][1] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][2] = matrix.pixels[i][j][0];
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType1to4(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.RED_GREEN_BLUE_ALPHA);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][1] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][2] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][3] = Matrix.MAX_PIXEL;
			}
		}
		
		return matrix2;
	}

	public static Matrix convertType2to1(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.BLACK_WHITE);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType2to3(Matrix matrix){
		return convertType1to3(matrix);
	}
	
	public static Matrix convertType2to4(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.RED_GREEN_BLUE_ALPHA);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][1] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][2] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][3] = matrix.pixels[i][j][1];
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType3to1(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.BLACK_WHITE);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0] * 0.3 +
						matrix.pixels[i][j][1] * 0.59 +
						matrix.pixels[i][j][2] * 0.11);
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType3to2(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.BLACK_WHITE_ALPHA);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0] * 0.3 +
						matrix.pixels[i][j][1] * 0.59 +
						matrix.pixels[i][j][2] * 0.11);
				matrix2.pixels[i][j][1] = Matrix.MAX_PIXEL;
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType3to4(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.RED_GREEN_BLUE_ALPHA);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][1] = matrix.pixels[i][j][1];
				matrix2.pixels[i][j][2] = matrix.pixels[i][j][2];
				matrix2.pixels[i][j][3] = Matrix.MAX_PIXEL;
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType4to1(Matrix matrix){
		return convertType3to1(matrix);
	}
	
	public static Matrix convertType4to2(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.BLACK_WHITE_ALPHA);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0] * 0.3 +
						matrix.pixels[i][j][1] * 0.59 +
						matrix.pixels[i][j][2] * 0.11);
				matrix2.pixels[i][j][1] = matrix.pixels[i][j][3];
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertType4to3(Matrix matrix){
		int row = matrix.getRows(),
			col = matrix.getCols();
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.RED_GREEN_BLUE);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
				matrix2.pixels[i][j][1] = matrix.pixels[i][j][1];
				matrix2.pixels[i][j][2] = matrix.pixels[i][j][2];
			}
		}
		
		return matrix2;
	}
}
