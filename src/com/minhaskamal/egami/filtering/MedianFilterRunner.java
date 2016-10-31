/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: 30-Sep-2016                                        *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.filtering;

import java.util.Arrays;
import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixEditor;
import com.minhaskamal.egamiLight.MatrixUtilities;

public class MedianFilterRunner {
	/**
	 * 
	 * @param matrix
	 * @param maskLength should be an odd number. best is 3.
	 * @return
	 */
	public static Matrix applyMedianFilter(Matrix matrix, int maskLength){
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), Matrix.BLACK_WHITE);
		
		int border = (maskLength-1)/2;
		matrix = MatrixEditor.createBorder(matrix, border, Matrix.MAX_PIXEL);
		
		int rows = matrix.getRows(),
			cols = matrix.getCols();
		
		for(int i=border; i<rows-border; i++) {
			for(int j=border; j<cols-border; j++) {
				
				int[][] arrayRaw = MatrixUtilities.vectorize(
						matrix.subMatrix(i-border, i+maskLength-border, j-border, j+maskLength-border));
				int[] array = new int[arrayRaw.length];
				for(int k=0; k<array.length; k++){
					array[k] = arrayRaw[k][0];
				}
				
				matrix2.pixels[i-border][j-border][0] = getMedian(array);
			}	
		}
		
		return matrix2;
	}
	
	private static int getMedian(int[] array){
		
		Arrays.sort(array);
		int median = array[array.length/2];
		
		return median;
	}
}
