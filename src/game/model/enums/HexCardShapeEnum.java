package game.model.enums;

public enum HexCardShapeEnum {
	
	SIXTYTHREE(1,1,1,1,1,1),
	THIRTYONE(0,1,1,1,1,1),
	TWENTYNINE(0,1,1,1,0,1),
	TWENTYSEVEN(0,1,1,0,1,1),
	TWENTYONE(0,1,0,1,0,1),
	FIFTEEN(0,0,1,1,1,1),
	THIRTEEN(0,0,1,1,0,1),
	ELEVEN(0,0,1,0,1,1),
	NINE(0,0,1,0,0,1),
	SEVEN(0,0,0,1,1,1),
	FIVE(0,0,0,1,0,1),
	THREE(0,0,0,0,1,1),
	ONE(0,0,0,0,0,1);
	
	private Integer[] pathCode = new Integer[6];
	
	HexCardShapeEnum(int a, int b, int c, int d, int e, int f){
		pathCode[0] = a;
		pathCode[1] = b;
		pathCode[2] = c;
		pathCode[3] = d;
		pathCode[4] = e;
		pathCode[5] = f;
	}
	
	public Integer[] getCardEnum(){
		return pathCode;	
	}

}
