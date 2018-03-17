package game.model.enums;

public enum SquareCardShapeEnum {
	
	FIFTEEN(1,1,1,1),
	SEVEN(0,1,1,1),
	FIVE(0,1,0,1),
	THREE(0,0,1,1),
	ONE(0,0,0,1);
	
	private Integer[] pathCode = new Integer[4];
	
	SquareCardShapeEnum(int a, int b, int c, int d){
		pathCode[0] = a;
		pathCode[1] = b;
		pathCode[2] = c;
		pathCode[3] = d;
	}
	
	public Integer[] getCardEnum(){
		return pathCode;	
	}
}