package test;

import com.minhaskamal.egami.contrastEnhancement.HistogramEqualizer;
import com.minhaskamal.egamiLight.Matrix;

public class HistogramEqualizerTest extends EgamiTest{

	public static void main(String[] args) {
		new HistogramEqualizerTest().runTest();
	}
	
	@Override
	public void testMethod() throws Exception {
		Matrix matrix = new Matrix(CITY_IMG, Matrix.BLACK_WHITE);
		matrix.write(DESKTOP+"in.png");		

		Matrix matrix1 = HistogramEqualizer.histogramEqualizer(matrix);
		matrix1.write(DESKTOP+"out1.png");		

		Matrix matrix2 = HistogramEqualizer.biHistogramEqualizer(matrix);		
		matrix2.write(DESKTOP+"out2.png");		
	}

}
