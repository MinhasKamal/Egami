package test;

import com.minhaskamal.egami.filtering.ConvolutionRunner;
import com.minhaskamal.egami.matrix.Matrix;
import com.minhaskamal.egami.matrix.MatrixUtilities;

public class ConvolutionRunnerTest extends EgamiTest{

	public static void main(String[] args) {
		new ConvolutionRunnerTest().runTest();
	}
	
	@Override
	public void testMethod() throws Exception {
		Matrix matrix = new Matrix(TINY_IMG, Matrix.BLACK_WHITE);
		System.out.println(matrix.toString());

		Matrix matrix1 = ConvolutionRunner.applyMask(matrix, ConvolutionRunner.getEmbossFilter(), true);
		System.out.println(matrix1.toString());
		matrix1 = MatrixUtilities.normalizeMatrix(matrix1);
		System.out.println(matrix1.toString());
	}

}
