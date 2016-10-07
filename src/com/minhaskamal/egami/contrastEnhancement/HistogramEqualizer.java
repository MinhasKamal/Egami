/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
* Modification Date: 07-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.contrastEnhancement;

import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixTypeConverter;
import com.minhaskamal.egamiLight.MatrixUtilities;

public class HistogramEqualizer {
	
	/**
	 * Apply Histogram Equalization over a grey-scale Matrix.
	 * @param matrix
	 * @return
	 */
	public static Matrix histogramEqualizer(Matrix matrix){
		matrix = MatrixTypeConverter.convert(matrix, Matrix.BLACK_WHITE);
		
		int[] pixelFreq = MatrixUtilities.countPixelFreq(matrix)[0];
		int totalPixelCount = matrix.getRows()*matrix.getCols();
		double[] cumulativeDistributiveValues =  countCumulativeDistributiveValues(pixelFreq, totalPixelCount);
		Matrix matrix2 = createNewMatrix(matrix, cumulativeDistributiveValues);
		
		return matrix2;
	}
	
	/**
	 * Apply Bi-Histogram Equalization over a grey-scale Matrix.
	 * @param matrix
	 * @return
	 */
	public static Matrix biHistogramEqualizer(Matrix matrix) {
		matrix = MatrixTypeConverter.convert(matrix, Matrix.BLACK_WHITE);
		
		int[] pixelFreq = MatrixUtilities.countPixelFreq(matrix)[0];
		int totalPixelCount = matrix.getRows()*matrix.getCols();
		double[] cumulativeDistributiveValues = countBiCumulativeDistributiveValues(pixelFreq, totalPixelCount);
		Matrix matrix2 = createNewMatrix(matrix, cumulativeDistributiveValues);

		return matrix2;
	}
	
	/**
	 * Apply Bi-Histogram Equalization over sections of a grey-scale Matrix.
	 * @param matrix
	 * @return
	 */
	public static Matrix spatialBiHistogramEqualizer(Matrix matrix, int sectionWidth, int sectionHeight) {
		int rows = matrix.getRows(),
			cols = matrix.getCols();
		
		Matrix matrix2 = new Matrix(rows, cols, Matrix.BLACK_WHITE);
		for(int i=0; i<rows-sectionHeight; i+=sectionHeight){
			for(int j=0; j<cols-sectionWidth; j+=sectionWidth){
				Matrix subMatrix = matrix.subMatrix(i, i+sectionHeight, j, j+sectionWidth);
				subMatrix = biHistogramEqualizer(subMatrix);
				
				for(int m=0; m<sectionHeight ; m++){
					for(int n=0; n<sectionWidth ; n++){
						matrix2.pixels[m+i][n+j] = subMatrix.pixels[m][n];
					}
				}
			}
		}
		
		return matrix2;
	}
	
	
	private static double[] countCumulativeDistributiveValues(int[] freq, int totalCount){
		int maxValue = freq.length-1;
		double multiple = (double) maxValue/totalCount;
		
		double[] cumulativeDistributiveValues =  new double[freq.length];
		cumulativeDistributiveValues[0] = freq[0]*multiple;
		for(int i=1; i<cumulativeDistributiveValues.length; i++){
			cumulativeDistributiveValues[i] = cumulativeDistributiveValues[i-1] + freq[i]*multiple;
		}
		
		return cumulativeDistributiveValues;
	}
	
	private static double[] countBiCumulativeDistributiveValues(int[] freq, int totalCount){
		int maxValue = freq.length-1;
		int[] med = calculateMediumPixelAndPixelCount(freq, totalCount);
		int mediumValue = med[0];
		int lowerMediumPixelCount = med[1],
			higherMediumPixelCount = totalCount-lowerMediumPixelCount;
		double lowerMultiple = (double) mediumValue/lowerMediumPixelCount,
				higherMultiple = (double) (maxValue-mediumValue-1)/higherMediumPixelCount;

		double[] cumulativeDistributiveValues =  new double[freq.length];
		cumulativeDistributiveValues[0] = freq[0]*lowerMultiple;
		for (int i=1; i<=mediumValue; i++) {
			cumulativeDistributiveValues[i] = cumulativeDistributiveValues[i-1] + freq[i]*lowerMultiple;
		}for (int i=mediumValue+1; i<cumulativeDistributiveValues.length; i++) {
			cumulativeDistributiveValues[i] = cumulativeDistributiveValues[i-1] + freq[i]*higherMultiple;
		}
		
		return cumulativeDistributiveValues;
	}
	
	private static int[] calculateMediumPixelAndPixelCount(int[] freq, int totalCount){
		int mediumPixel = 0;
		int lowerMediumPixelCount = 0;
		for (int i=0; i<256; i++) {
			lowerMediumPixelCount += freq[i];
			if (lowerMediumPixelCount > totalCount/2) {
				mediumPixel = i-1;
				break;
			}
		}
		if (mediumPixel<0) {
			mediumPixel = 0;
		} else {
			lowerMediumPixelCount -= freq[mediumPixel+1];
		}
		
		return new int[]{mediumPixel, lowerMediumPixelCount};
	}
	
	private static Matrix createNewMatrix(Matrix matrix, double[] cumulativeDistributiveValues){
		int rows = matrix.getRows(),
			cols = matrix.getCols();
		
		Matrix matrix2 = new Matrix(rows, cols, matrix.getType());
		for(int x=0, y; x<rows; x++){
			for(y=0; y<cols; y++){
				matrix2.pixels[x][y][0] = (int) cumulativeDistributiveValues[ matrix.pixels[x][y][0] ];
			}
		}
		
		return matrix2;
	}
	
}
