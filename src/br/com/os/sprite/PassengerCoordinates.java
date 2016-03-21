package br.com.os.sprite;

public class PassengerCoordinates {

	private SpriteSheetCooordinate[] coordinatesRightwards = new SpriteSheetCooordinate[3];
	private SpriteSheetCooordinate[] coordinatesLeftwards = new SpriteSheetCooordinate[3];;
	private SpriteSheetCooordinate[] coordinatesUpwards = new SpriteSheetCooordinate[3];;

	public PassengerCoordinates(){

		coordinatesRightwards[0] = new SpriteSheetCooordinate(2, 64, 25, 32);
		coordinatesRightwards[1] = new SpriteSheetCooordinate(36, 64, 25, 32);
		coordinatesRightwards[2] = new SpriteSheetCooordinate(64, 64, 25, 32);

		coordinatesLeftwards[0] = new SpriteSheetCooordinate(2, 32, 27, 32);
		coordinatesLeftwards[1] = new SpriteSheetCooordinate(34, 32, 27, 32);
		coordinatesLeftwards[2] = new SpriteSheetCooordinate(64, 32, 27, 32);

		coordinatesUpwards[0] = new SpriteSheetCooordinate(194, 224, 26, 32);
		coordinatesUpwards[1] = new SpriteSheetCooordinate(228, 224, 26, 32);
		coordinatesUpwards[2] = new SpriteSheetCooordinate(260, 224, 26,32);

	}


	public PassengerCoordinates(int codigoPassageiro){

		switch (codigoPassageiro) {
		case 1:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(2, 64, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(36, 64, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(64, 64, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(2, 32, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(34, 32, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(64, 32, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(2, 96, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(34, 96, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(64, 96, 26,32);

			break;
		case 2:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(2, 192, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(36, 192, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(64, 192, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(2, 160, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(34, 160, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(64, 160, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(2, 224, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(34, 224, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(64, 224, 26,32);



			break;			
		case 3:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(98, 64, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(132, 64, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(164, 64, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(98, 32, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(132, 32, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(164, 32, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(98, 96, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(132, 96, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(164, 96, 26,32);


			break;	
		case 4:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(98, 192, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(132, 192, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(164, 192, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(98, 160, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(132, 160, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(164, 160, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(98, 224, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(132, 224, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(164, 224, 26,32);

			break;	
		case 5:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(194, 64, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(228, 64, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(260, 64, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(194, 32, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(228, 32, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(260, 32, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(194, 96, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(228, 96, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(260, 96, 26,32);


			break;	
		case 6:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(194, 192, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(228, 192, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(260, 192, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(194, 160, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(228, 160, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(260, 160, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(194, 224, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(228, 224, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(260, 224, 26,32);


			break;	
		case 7:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(290, 64, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(324, 64, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(356, 64, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(290, 32, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(324, 32, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(356, 32, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(290, 96, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(324, 96, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(356, 96, 26,32);		
			break;				
		default:

			coordinatesRightwards[0] = new SpriteSheetCooordinate(2, 64, 25, 32);
			coordinatesRightwards[1] = new SpriteSheetCooordinate(36, 64, 25, 32);
			coordinatesRightwards[2] = new SpriteSheetCooordinate(64, 64, 25, 32);

			coordinatesLeftwards[0] = new SpriteSheetCooordinate(2, 32, 27, 32);
			coordinatesLeftwards[1] = new SpriteSheetCooordinate(34, 32, 27, 32);
			coordinatesLeftwards[2] = new SpriteSheetCooordinate(64, 32, 27, 32);

			coordinatesUpwards[0] = new SpriteSheetCooordinate(194, 224, 26, 32);
			coordinatesUpwards[1] = new SpriteSheetCooordinate(228, 224, 26, 32);
			coordinatesUpwards[2] = new SpriteSheetCooordinate(260, 224, 26,32);			
			break;
		}

	}


	public SpriteSheetCooordinate[] getCoordinatesRightwards() {
		return coordinatesRightwards;
	}

	public SpriteSheetCooordinate[] getCoordinatesLeftwards() {
		return coordinatesLeftwards;
	}

	public SpriteSheetCooordinate[] getCoordinatesUpwards() {
		return coordinatesUpwards;
	}

}
