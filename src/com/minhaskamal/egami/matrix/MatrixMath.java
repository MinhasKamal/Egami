/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
* Date: 04-Oct-2016                                        *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.matrix;

public class MatrixMath {
	
////----matrix to matrix----////
	
	
	public static Matrix add(Matrix matrix1, Matrix matrix2){
		int[][][] resultPixel = addArrayToArray(matrix1.pixels, matrix2.pixels);
		return new Matrix(resultPixel);
	}
	
	public static Matrix add(Matrix matrix, int number){
		int[][][] resultPixel = addNumberToArray(matrix.pixels, number);
		return new Matrix(resultPixel);
	}
	
	public static Matrix subtract(Matrix matrix1, Matrix matrix2){
		int[][][] resultPixel = subtractArrayToArray(matrix1.pixels, matrix2.pixels);
		return new Matrix(resultPixel);
	}
	
	public static Matrix subtract(Matrix matrix, int number){
		int[][][] resultPixel = subtractNumberToArray(matrix.pixels, number);
		return new Matrix(resultPixel);
	}
	
	public static Matrix multiply(Matrix matrix1, Matrix matrix2){
		int[][][] resultPixel = multiplyArrayToArray(matrix1.pixels, matrix2.pixels);
		return new Matrix(resultPixel);
	}
	
	public static Matrix multiply(Matrix matrix, int number){
		int[][][] resultPixel = multiplyNumberToArray(matrix.pixels, number);
		return new Matrix(resultPixel);
	}
	
	public static Matrix divide(Matrix matrix1, Matrix matrix2){
		int[][][] resultPixel = divideArrayToArray(matrix1.pixels, matrix2.pixels);
		return new Matrix(resultPixel);
	}
	
	public static Matrix divide(Matrix matrix, int number){
		int[][][] resultPixel = divideNumberToArray(matrix.pixels, number);
		return new Matrix(resultPixel);
	}
	
	
////----array to array----////
	
	
	public static int[][][] addArrayToArray(int[][][] array1, int[][][] array2){
		
		int[][][] resultArray = new int[array1.length][array1[0].length][array1[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					resultArray[i][j][k] = array1[i][j][k]+array2[i][j][k];
				}
			}
		}
		
		return resultArray;
	}
	
	public static int[][][] subtractArrayToArray(int[][][] array1, int[][][] array2){
		
		int[][][] resultArray = new int[array1.length][array1[0].length][array1[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					resultArray[i][j][k] = array1[i][j][k]-array2[i][j][k];
				}
			}
		}
		
		return resultArray;
	}

	public static int[][][] multiplyArrayToArray(int[][][] array1, int[][][] array2){
		
		int[][][] resultArray = new int[array1.length][array1[0].length][array1[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					resultArray[i][j][k] = array1[i][j][k]*array2[i][j][k];
				}
			}
		}
		
		return resultArray;
	}
	
	public static int[][][] divideArrayToArray(int[][][] array1, int[][][] array2){
		
		int[][][] resultArray = new int[array1.length][array1[0].length][array1[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					if(array2[i][j][k]==0){
						array2[i][j][k] = 1;
					}
					
					resultArray[i][j][k] = array1[i][j][k]/array2[i][j][k];
				}
			}
		}
		
		return resultArray;
	}
	
	/*public int[] addArrayByArray(int[] array1, int[] array2){
		return addArrayToArray(new int[][][]{{array1}}, new int[][][]{{array2}})[0][0];
	}*/

	
////----number to array----////
	
	
	public static int[][][] addNumberToArray(int[][][] array, int number){
		
		int[][][] resultArray = new int[array.length][array[0].length][array[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					resultArray[i][j][k] = array[i][j][k]+number;
				}
			}
		}
		
		return resultArray;
	}
	
	public static int[][][] subtractNumberToArray(int[][][] array, int number){
		
		int[][][] resultArray = new int[array.length][array[0].length][array[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					resultArray[i][j][k] = array[i][j][k]-number;
				}
			}
		}
		
		return resultArray;
	}

	public static int[][][] multiplyNumberToArray(int[][][] array, int number){
		
		int[][][] resultArray = new int[array.length][array[0].length][array[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					resultArray[i][j][k] = array[i][j][k]*number;
				}
			}
		}
		
		return resultArray;
	}

	public static int[][][] divideNumberToArray(int[][][] array, int number){
		
		int[][][] resultArray = new int[array.length][array[0].length][array[0][0].length];
		for(int i=0, j, k; i<resultArray.length; i++){
			for(j=0; j<resultArray[i].length; j++){
				for(k=0; k<resultArray[i][j].length; k++){
					resultArray[i][j][k] = array[i][j][k]/number;
				}
			}
		}
		
		return resultArray;
	}
}
