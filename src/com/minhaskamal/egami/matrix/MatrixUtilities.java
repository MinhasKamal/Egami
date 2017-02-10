/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
* Modification Date: 04-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.matrix;

public class MatrixUtilities {
	
////----pixel value operation----////
	
	
	/**
	 * Returns number of occurrences of all the pixels in a Matrix.
	 * @param matrix histogram of matrix
	 * @return
	 */
	public static int[][] countPixelFreq(Matrix matrix){
		int rows = matrix.getRows(),
			cols = matrix.getCols(),
			type = matrix.getType();
		
		int[][] pixelFreq = new int[matrix.getType()][Matrix.MAX_PIXEL+1];
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				for(int k=0; k<type; k++){
					pixelFreq[k][matrix.pixels[i][j][k]]++;
				}
			}
		}
		
		return pixelFreq;
	}
	
	/**
	 * Returns the average of all pixels of a Matrix.
	 * @param mat
	 * @return
	 */
	public static double getMeanPixelValue(Matrix matrix){
		int rows = matrix.getRows(),
			cols = matrix.getCols(),
			type = matrix.getType();
		
		double sumOfPixelByRow;
		double sumOfPixelByColInRow = 0;
		for(int x=0, y, z; x<rows; x++){
			sumOfPixelByRow=0;
			for(y=0; y<cols; y++){
				for(z=0; z<type; z++){
					sumOfPixelByRow += matrix.pixels[x][y][z];
				}
			}
			
			sumOfPixelByColInRow += sumOfPixelByRow/(type*cols);
		}
		
		return sumOfPixelByColInRow/rows;
	}
	
	/**
	 * The minimum and maximum pixel value of input
	 * <code>Matrix</code> is returned as array.
	 * @param matrix
	 * @return
	 */
	public static int[] getMinMaxPixelValue(Matrix matrix){
		int rows = matrix.getRows(),
			cols = matrix.getCols(),
			type = matrix.getType();
		
		int MinPixel = Integer.MAX_VALUE,
			MaxPixel = Integer.MIN_VALUE;
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				for(int k=0; k<type; k++){
					if(MinPixel>matrix.pixels[i][j][k]){
						MinPixel=matrix.pixels[i][j][k];
					}if(MaxPixel<matrix.pixels[i][j][k]){
						MaxPixel=matrix.pixels[i][j][k];
					}
				}
			}
		}
		
		return new int[]{MinPixel, MaxPixel};
	}
	
	
////----pixel creation----////
	
	
	/**
	 * Returns a pixel of the same type as input Matrix, all carrying the 
	 * same <code>pixelValue</code>.
	 * @param matrix
	 * @param pixelValue
	 * @return
	 */
	public static int[] getCustomPixel(Matrix matrix, int pixelValue){
		int[] customPixel = new int[matrix.getType()];
		
		for(int i=0; i<customPixel.length; i++){
			customPixel[i] = pixelValue;
		}
		
		return customPixel;
	}
	
	public static int[] getBlackPixel(Matrix matrix){
		return getCustomPixel(matrix, Matrix.MIN_PIXEL);
	}
	
	public static int[] getWhitePixel(Matrix matrix){
		return getCustomPixel(matrix, Matrix.MAX_PIXEL);
	}
	

////----matrix creation----////
	
	/**
	 * Creates a Matrix of the same size and type of input Matrix,
	 * and all pixel values are initiated with the <code>initialValue</code>.
	 * @param matrix
	 * @param initialValue
	 * @return
	 */
	public static Matrix createNewMatrix(Matrix matrix, int initialValue){
		int rows = matrix.getRows(),
			cols = matrix.getCols(),
			type = matrix.getType();
		
		Matrix matrix2 = new Matrix(rows, cols, type);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				for(int k=0; k<type; k++){
					matrix2.pixels[i][j][k] = initialValue;
				}
			}
		}
		
		return matrix2;
	}
	
	public static Matrix createEmptyMatrix(Matrix matrix){
		return new Matrix(matrix.getRows(), matrix.getCols(), matrix.getType());
	}
	

////----matrix data transformation----////
	
	/**
	 * Converts Matrix to a vector of pixels.
	 * @param matrix
	 * @return
	 */
	public static int[][] vectorize(Matrix matrix){
		int height = matrix.getRows();
		int width = matrix.getCols();
		int type = matrix.getType();
		
		int[][] vector = new int[height*width][type];
		
		for(int i=0, k=0; i<height; i++){
			for(int j=0; j<width; j++){
				vector[k] = matrix.pixels[i][j].clone();
				k++;
			}
		}
		
		return vector;
	}
	
	/**
	 * Creates Matrix from a vector of pixels.
	 * @param vector
	 * @param row
	 * @param col
	 * @return
	 */
	public static Matrix createMatrix(int[][] vector, int row, int col){

		Matrix matrix = new Matrix(row, col, vector[0].length);
		
		int k=0;
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				matrix.pixels[i][j] = vector[k].clone();
				k++;
			}
		}
		
		return matrix;
	}
	

////----matrix operation----////

	/**
	 * Scales pixel values of input Matrix in <code>Matrix.MIN_PIXEL</code> to 
	 * <code>Matrix.MAX_PIXEL</code>.
	 * @param matrix
	 * @return
	 */
	public static Matrix normalizeMatrix(Matrix matrix){
		int rows = matrix.getRows(),
			cols = matrix.getCols(),
			type = matrix.getType();
		
		int[] minMaxValue = getMinMaxPixelValue(matrix);
		
		double multiple = 0;
		if(minMaxValue[1]-minMaxValue[0] != 0){
			multiple = (double) (Matrix.MAX_PIXEL-Matrix.MIN_PIXEL)/(minMaxValue[1]-minMaxValue[0]);
		}
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				for(int k=0; k<type; k++){
					matrix.pixels[i][j][k] -= minMaxValue[0];
					matrix.pixels[i][j][k] = (int) (matrix.pixels[i][j][k]*multiple);
				}
			}
		}
		
		return matrix;
	}
	
}
