/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
* Modification Date: 07-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.colorSpaceTransformation;

import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixTypeConverter;

public class RGBConverter {
	
	/**
	 * Usually this condition should be satisfied- <br/> <code>multiplierOfRed + multiplierOfGreen + 
	 * multiplierOfBlue + (adjustment / 255) <= 1.00 </code>
	 * @param multiplierOfRed
	 * @param multiplierOfGreen
	 * @param multiplierOfBlue
	 * @param adjustment
	 * @return
	 */
	public static Matrix transform(Matrix matrix, double multiplierOfRed, double multiplierOfGreen,
			double multiplierOfBlue, int adjustment){
		
		if(matrix.getType()<Matrix.RED_GREEN_BLUE){
			matrix = MatrixTypeConverter.convert(matrix, Matrix.RED_GREEN_BLUE);
		}
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, Matrix.BLACK_WHITE);
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0]*multiplierOfRed +
						matrix.pixels[i][j][1]*multiplierOfGreen +
						matrix.pixels[i][j][2]*multiplierOfBlue) + adjustment;
			}
		}
		
		return matrix2;
	}
	
	/**
	 * RGB to YCbCr conversion- returns Y
	 * @return
	 */
	public static Matrix transformToY(Matrix matrix){
		double multiplierOfRed = 0.299;
		double multiplierOfGreen = 0.587;
		double multiplierOfBlue = 0.114;
		int adjustment = 0;
		
		return transform(matrix, multiplierOfRed, multiplierOfGreen, multiplierOfBlue, adjustment);
	}
	/**
	 * RGB to YCbCr conversion- returns Cb
	 * @return
	 */
	public static Matrix transformToCb(Matrix matrix){
		double multiplierOfRed = -0.16874;
		double multiplierOfGreen = -0.33126;
		double multiplierOfBlue = 0.50000;
		int adjustment = 128;
		
		return transform(matrix, multiplierOfRed, multiplierOfGreen, multiplierOfBlue, adjustment);
	}
	/**
	 * RGB to YCbCr conversion- returns Cr
	 * @return
	 */
	public static Matrix transformToCr(Matrix matrix){
		double multiplierOfRed = 0.50000;
		double multiplierOfGreen = -0.41869;
		double multiplierOfBlue = -0.08131;
		int adjustment = 128;
		
		return transform(matrix, multiplierOfRed, multiplierOfGreen, multiplierOfBlue, adjustment);
	}
	
	/**
	 * RGB to HSV conversion- returns Hue
	 * @return
	 */
	public static Matrix transformToHue(Matrix matrix){
		if(matrix.getType()<Matrix.RED_GREEN_BLUE){
			matrix = MatrixTypeConverter.convert(matrix, Matrix.RED_GREEN_BLUE);
		}
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		int type = Matrix.RED_GREEN_BLUE;
		
		Matrix matrix2 = new Matrix(row, col, Matrix.BLACK_WHITE);
		
		int max, mid, min, delta;
		double temp;
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				max=mid=min=matrix.pixels[i][j][0];

				for(int k=1; k<type; k++){
					if(max < matrix.pixels[i][j][k]){
						max = matrix.pixels[i][j][k];
					}else if(min > matrix.pixels[i][j][k]){
						min = matrix.pixels[i][j][k];
					}
					mid += matrix.pixels[i][j][k];
				}
				delta = max-min;
				mid = mid-(max+min);
				
				if(delta>0){
					temp = (mid-min+0.00)/delta;

					if(max==matrix.pixels[i][j][0]){		//red
						temp %= 6;
					}else if(max==matrix.pixels[i][j][1]){	//green
						temp += 2;
					}else{									//blue
						temp += 4;
					}
					
				}else{
					temp = 0;
				}
				
				matrix2.pixels[i][j][0] = (int)(temp*42.5);
			}
		}
		
		return matrix2;
	}
	
	/**
	 * RGB to HSV conversion- returns Saturation
	 * @return
	 */
	public static Matrix transformToSaturation(Matrix matrix){
		if(matrix.getType()<Matrix.RED_GREEN_BLUE){
			matrix = MatrixTypeConverter.convert(matrix, Matrix.RED_GREEN_BLUE);
		}
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		int type = Matrix.RED_GREEN_BLUE;
		
		Matrix matrix2 = new Matrix(row, col, Matrix.BLACK_WHITE);
		
		int max, min, delta;
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				max=min=matrix.pixels[i][j][0];
				for(int k=1; k<type; k++){
					if(max < matrix.pixels[i][j][k]){
						max = matrix.pixels[i][j][k];
					}else if(min > matrix.pixels[i][j][k]){
						min = matrix.pixels[i][j][k];
					}
				}
				delta = max-min;
				
				matrix2.pixels[i][j][0] = ((delta*255)/max);
			}
		}
		
		return matrix2;
	}
	
	/**
	 * RGB to HSV conversion- returns Value
	 * @return
	 */
	public static Matrix transformToValue(Matrix matrix){
		if(matrix.getType()<Matrix.RED_GREEN_BLUE){
			matrix = MatrixTypeConverter.convert(matrix, Matrix.RED_GREEN_BLUE);
		}
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		int type = Matrix.RED_GREEN_BLUE;
		
		Matrix matrix2 = new Matrix(row, col, Matrix.BLACK_WHITE);
		
		int max;
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				max=matrix.pixels[i][j][0];
				for(int k=1; k<type; k++){
					if(max < matrix.pixels[i][j][k]){
						max = matrix.pixels[i][j][k];
					}
				}
				
				matrix2.pixels[i][j][0] = max;
			}
		}
		
		return matrix2;
	}
	
	/**
	 * RGB to HSL conversion- returns Lightness
	 * @return
	 */
	public static Matrix transformToLightness(Matrix matrix){
		if(matrix.getType()<Matrix.RED_GREEN_BLUE){
			matrix = MatrixTypeConverter.convert(matrix, Matrix.RED_GREEN_BLUE);
		}
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		int type = Matrix.RED_GREEN_BLUE;
		
		Matrix matrix2 = new Matrix(row, col, Matrix.BLACK_WHITE);
		
		int max, min;
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				max=min=matrix.pixels[i][j][0];
				for(int k=1; k<type; k++){
					if(max < matrix.pixels[i][j][k]){
						max = matrix.pixels[i][j][k];
					}else if(min > matrix.pixels[i][j][k]){
						min = matrix.pixels[i][j][k];
					}
				}
				
				matrix2.pixels[i][j][0] = (max+min)/2;
			}
		}
		
		return matrix2;
	}
	
}
