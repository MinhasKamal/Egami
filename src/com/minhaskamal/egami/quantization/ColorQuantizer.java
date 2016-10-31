/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
***********************************************************/

package com.minhaskamal.egami.quantization;

import java.util.LinkedList;
import java.util.List;
import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixTypeConverter;


public class ColorQuantizer {
	
	/**
	 * 
	 * @param matrix only grey-scale
	 * @param classRangeAndValues <code> List < int[] {lower-bound, upper-bound, symbol-value} > </code>
	 * @param outOfRangeValue
	 * @return
	 */
	public static Matrix symbolize(Matrix matrix, List<int[]> classRangeAndValues, int outOfRangeValue){
		matrix = MatrixTypeConverter.convert(matrix, Matrix.BLACK_WHITE);
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, Matrix.BLACK_WHITE);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				
				int newPixel = -999;
				
				int oldPixel = matrix.pixels[i][j][0];
				for(int[] classRangeAndValue: classRangeAndValues){
					if(oldPixel>=classRangeAndValue[0] && oldPixel<=classRangeAndValue[1]){
						newPixel = classRangeAndValue[2];
						break;
					}
				}
				
				if(newPixel<-990){
					newPixel = outOfRangeValue;
				}
				
				matrix2.pixels[i][j][0] = newPixel;
			}
		}
		
		return matrix2;
	}
	
	public static Matrix convertToBinary(Matrix matrix, int threshold){
		List<int[]> classRangeAndValues = new LinkedList<int[]>();
		classRangeAndValues.add(new int[]{0, threshold, Matrix.MIN_PIXEL});
		classRangeAndValues.add(new int[]{threshold, 256, Matrix.MAX_PIXEL});
		
		return symbolize(matrix, classRangeAndValues, 150);
	}
	
}
