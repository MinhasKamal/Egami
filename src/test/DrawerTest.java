package test;

import com.minhaskamal.egami.drawing.Drawer;
import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixUtilities;

public class DrawerTest extends EgamiTest{

	public static void main(String[] args) {
		new DrawerTest().runTest();
	}
	
	@Override
	public void testMethod() throws Exception {
		Matrix matrix = new Matrix(FRUIT_IMG, Matrix.BLACK_WHITE);
		
		Drawer.drawGrid(matrix, 
				50, 50, 
				100, 100, 
				5, 5, 
				5, 5,
				-0.05, 0.05,
				MatrixUtilities.getBlackPixel(matrix));
		
		Drawer.drawLine(matrix, 50.0, 100, MatrixUtilities.getBlackPixel(matrix));
		
		matrix.write(DESKTOP+"out.png");		
	}

}
