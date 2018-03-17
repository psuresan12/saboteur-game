package game.model.enums;

public enum RectangleCardShapeEnum {
	
	FIFTEEN(1,1,1,1),
	ELEVEN(1,0,1,1),
	TEN(1,1,0,1),
	NINE(1,0,0,1),
	SEVEN(0,1,1,1),
	SIX(0,1,1,0),
	FIVE(0,1,0,1),
	THREE(0,0,1,1),
	TWO(0,0,1,0),
	ONE(0,0,0,1);
	
	private Integer[] pathCode = new Integer[4];
	
	RectangleCardShapeEnum(int a, int b, int c, int d){
		pathCode[0] = a;
		pathCode[1] = b;
		pathCode[2] = c;
		pathCode[3] = d;
	}
	
	public Integer[] getCardEnum(){
		return pathCode;	
	}

}
