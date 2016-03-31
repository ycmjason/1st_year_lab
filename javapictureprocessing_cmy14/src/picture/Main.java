package picture;

public class Main {

  public static void main(String[] args) {
    /*
     * invert <input> <output>
     * grayscale <input> <output>
     * rotate [90|180|270] <input> <output>
     * flip [H|V] <input> <output>
     * blend <input_1> <input_2> <input_...> <input_n> <output>
     * blur <input> <output>
     */
    assert (args.length>=3) : "Wrong usage.";
    
    Picture oriPic,newPic;
    Picture[] oriPics;
    String cmd = args[0];
    String oriPicPath, newPicPath;

    switch (cmd){
      case "invert":
      case "grayscale":
      	oriPicPath = args[1];
      	newPicPath = args[2];
        oriPic = Utils.loadPicture(oriPicPath);
        newPic = (cmd.equals("invert")) ?
        		invert(oriPic) : grayscale(oriPic);
        assert (Utils.savePicture(newPic, newPicPath)) :
        	"Can't save to the desired destination.";
        break;
      case "rotate":
      case "flip":
        assert (args.length >= 4) : "Wrong usage.";
        String arg = args[1];
      	oriPicPath = args[2];
      	newPicPath = args[3];
      	switch(cmd){
      		case "rotate":
            assert (arg.equals("90") || arg.equals("180") || arg.equals("270")) :
              "You can only choose angles [90|180|270]."; 
            break;
      		case "flip":
            assert (arg.equals("H") || arg.equals("V")) :
              "You can only choose [H|V]."; 
        }
        oriPic = Utils.loadPicture(oriPicPath);
        newPic = (cmd.equals("rotate")) ?
        		rotate(oriPic, arg) : flip(oriPic, arg);
        assert (Utils.savePicture(newPic, newPicPath)) :
        	"Can't save to the desired destination.";
        break;
      case "blend":
        assert (args.length >= 3) : "Wrong usage.";
        newPicPath = args[args.length-1];
        oriPics = new Picture[args.length-2];
        for (int i=0; i<args.length-2; i++){
          oriPics[i] = Utils.loadPicture(args[i+1]);
      	}
        assert (Utils.savePicture(blend(oriPics), newPicPath)) :
        	"Can't save to the desired destination.";
        break;
      case "blur":
        assert (args.length >= 3) : "Wrong usage.";
        oriPic = Utils.loadPicture(args[1]);
        assert (Utils.savePicture(blur(oriPic), args[2])) :
        	"Can't save to the desired destination.";
        break;
    }
  }
  
  private static Picture invert(Picture oriPic){
    Picture newPic = Utils.createPicture(oriPic.getWidth(), oriPic.getHeight());
    for (int j = 0; j<oriPic.getHeight(); j++){
      for (int i = 0; i<oriPic.getWidth(); i++){
          newPic.setPixel(i, j, invertCol(oriPic.getPixel(i, j)));
      }
    }
    return newPic;
  }
  private static Color invertCol(Color col){
    return new Color(255-col.getRed(),255-col.getGreen(),255-col.getBlue());
  }

  private static Picture grayscale(Picture oriPic){
    Picture newPic = Utils.createPicture(oriPic.getWidth(), oriPic.getHeight());
    for (int j = 0; j<oriPic.getHeight(); j++){
      for (int i = 0; i<oriPic.getWidth(); i++){
          newPic.setPixel(i, j, avgRGB(oriPic.getPixel(i, j)));
      }
    }
    return newPic;
  }
  private static Color avgRGB(Color col){
    int avg = (col.getRed()+col.getGreen()+col.getBlue())/3;
    return new Color(avg,avg,avg);
  }

  private static Picture rotate(Picture oriPic, String angle){
    //Pre: angle == 90 | 180 | 270
    Picture newPic;
    if (angle.equals("180")){
      newPic = Utils.createPicture(oriPic.getWidth(), oriPic.getHeight());
    }else{
      newPic = Utils.createPicture(oriPic.getHeight(), oriPic.getWidth());
    }
    for (int j = 0; j<oriPic.getHeight(); j++){
      for (int i = 0; i<oriPic.getWidth(); i++){
        if (angle.equals("90")){
          newPic.setPixel(oriPic.getHeight()-1-j, i, oriPic.getPixel(i, j));
        }else if(angle.equals("180")){
          newPic.setPixel(oriPic.getWidth()-1-i,
              oriPic.getHeight()-1-j, oriPic.getPixel(i, j));
        }else{ //270
          newPic.setPixel(j, oriPic.getWidth()-1-i, oriPic.getPixel(i, j));
        }
      }
    }
    return newPic;
  }

  private static Picture flip(Picture oriPic, String orientation){
    //Pre: orientation == H | V
    Picture newPic = Utils.createPicture(oriPic.getWidth(), oriPic.getHeight());
    for (int j = 0; j<oriPic.getHeight(); j++){
      for (int i = 0; i<oriPic.getWidth(); i++){
        if (orientation.equals("H")){
          newPic.setPixel(oriPic.getWidth()-1-i, j, oriPic.getPixel(i, j));
        }else{ //V
          newPic.setPixel(i, oriPic.getHeight()-1-j, oriPic.getPixel(i, j));
        }
      }
    }
    return newPic;
  }

  private static Picture blend(Picture[] oriPics){
  	Picture newPic;
    int leastW = leastWidth(oriPics);
    int leastH = leastHeight(oriPics);
    newPic = Utils.createPicture(leastW, leastH);
    for (int j = 0; j < leastH; j++){
      for (int i = 0; i < leastW; i++){
      	Color[] cols = new Color[oriPics.length];
        for (int k = 0; k < oriPics.length; k++){
          cols[k] = oriPics[k].getPixel(i, j);
        }
        newPic.setPixel(i, j, avgCol(cols));
      }
    }
    return newPic;
  }
  private static int leastWidth(Picture[] oriPics){
    int leastW = oriPics[0].getWidth();
    for (int i=1; i<oriPics.length; i++){
      leastW = (oriPics[i].getWidth()<leastW)?oriPics[i].getWidth():leastW;
    }
    return leastW;
  }
  private static int leastHeight(Picture[] oriPics){
    int leastH = oriPics[0].getHeight();
    for (int i=1; i<oriPics.length; i++){
      leastH = (oriPics[i].getHeight()<leastH)?oriPics[i].getHeight():leastH;
    }
  	return leastH;
  }
  private static Color avgCol(Color[] cols){
  	int totalR=0, totalG=0, totalB=0, i=0;
  	for( Color col : cols ){
  		totalR += col.getRed();
  		totalG += col.getGreen();
  		totalB += col.getBlue();
  		i++;
  	}
  	return new Color(totalR/i, totalG/i, totalB/i);
  }
  
  private static Picture blur(Picture oriPic){
  	Picture newPic = Utils.createPicture(oriPic.getWidth(), oriPic.getHeight());
    for (int j = 0; j<oriPic.getHeight(); j++){
      for (int i = 0; i<oriPic.getWidth(); i++){
      	if(i == 0 || i == oriPic.getWidth()-1 || 
      		 j == 0 || j == oriPic.getHeight()-1){
      		newPic.setPixel(i, j, oriPic.getPixel(i, j));
      	}else{
          Color[] neighbours = new Color[9];
          for (int y = 0; y<3; y++){
            for (int x = 0; x<3; x++){
              neighbours[y*3+x]=oriPic.getPixel(i-1+x, j-1+y);
            }
          }
          newPic.setPixel(i, j, avgCol(neighbours));
      	}
      }
    }
    return newPic;
  }
}
