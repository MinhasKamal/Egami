package test;

import com.minhaskamal.egami.contrastEnhancement.HistogramEqualizer;
import com.minhaskamal.egami.drawing.Drawer;
import com.minhaskamal.egami.matrix.Matrix;
import com.minhaskamal.egami.matrix.MatrixUtilities;

public class DrawerTest extends EgamiTest{

	public static void main(String[] args) {
		new DrawerTest().runTest();
	}
	
	@Override
	public void testMethod() throws Exception {
		Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\test\\sample2.png", Matrix.BLACK_WHITE);
		matrix = HistogramEqualizer.biHistogramEqualizer(matrix);
		
		Drawer.drawGrid(matrix, 
				57, 146, 
				40, 23, 
				7.35, 5.9, 
				27, 28,
				-0.002, 0.002,
				MatrixUtilities.getBlackPixel(matrix));
		
		//Drawer.drawLine(matrix, 50.0, 100, MatrixUtilities.getBlackPixel(matrix));
		
		matrix.write("C:\\Users\\admin\\Desktop\\test\\sample2out.png");		
	}

}
