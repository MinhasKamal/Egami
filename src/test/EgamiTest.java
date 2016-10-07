/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Modification Date: 04-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/


package test;

public abstract class EgamiTest {
	public static final String DESKTOP = System.getenv("SystemDrive") + System.getenv("HOMEPATH") + "\\Desktop\\",
			RESOURCE = "src\\res\\imgs\\";
	
	public static final String IMG = RESOURCE+"image.png";
	public static final String TINY_IMG = RESOURCE+"tinyImage.png";
	public static final String ANIMAL_IMG = RESOURCE+"animal.png";
	public static final String DEEPDREAM_IMG = RESOURCE+"deepDream.png";
	public static final String EINSTEIN_IMG = RESOURCE+"einstein.png";
	public static final String FOOD_IMG = RESOURCE+"food.png";
	public static final String FRUIT_IMG = RESOURCE+"fruit.png";
	public static final String PAINTING_IMG = RESOURCE+"painting.png";
	public static final String CITY_IMG = RESOURCE+"city.png";
	
	public static final String START_MESSAGE = "<--Operation Started-->";
	public static final String SUCCESS_MESSAGE = "<--Operation Successful!-->";

	private long startTime;
	
	
	
	public EgamiTest() {
		startTime = 0;
	}
	
	
	
	public void runTest(){
		this.startTime = System.currentTimeMillis();
		
		try {
			System.out.println(START_MESSAGE);
			
			testMethod();
			
			System.out.println(SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("Time Elapsed: " + getElapsedTime() + "s");
		}
	}
	
	public double getElapsedTime(){
		return (double)(System.currentTimeMillis()-startTime)/1000;
	}
	
	public abstract void testMethod() throws Exception;
}
