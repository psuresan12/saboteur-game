package game.model.enums;

public enum TriangleCardShapeEnum {

	SEVEN(1,1,1),
	THREE(0,1,1),
	ONE(0,0,1);
	
	private Integer[] pathCode = new Integer[3];
	
	TriangleCardShapeEnum(int a, int b, int c){
		pathCode[0] = a;
		pathCode[1] = b;
		pathCode[2] = c;
	}
	
	public Integer[] getCardEnum(){
		return pathCode;	
	}

}