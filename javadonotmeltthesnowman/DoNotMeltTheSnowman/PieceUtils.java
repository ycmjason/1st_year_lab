public class PieceUtils {

  public static Piece charToPiece(char c) {
    // TODO: Part I, a, 1.
    switch (c){
        case '^' : return Piece.EMITTER_NORTH;
        case '>' : return Piece.EMITTER_EAST;
        case 'v' : return Piece.EMITTER_SOUTH;
        case '<' : return Piece.EMITTER_WEST;

        case '|' : return Piece.LASER_VERTICAL;
        case '-' : return Piece.LASER_HORIZONTAL;
        case '+' : return Piece.LASER_CROSSED;

        case '/' : return Piece.MIRROR_SW_NE;
        case '\\': return Piece.MIRROR_NW_SE;

        case '#' : return Piece.WALL;
        case 'o' : return Piece.TARGET;
        case ' ' : return Piece.EMPTY;
        case '@' : return Piece.SNOWMAN;
    }
    return null;
  }
  public static Piece[][] charsToPieces(char[] description,
                                        int width, int height) {
    // TODO: Part I, a, 2.
    Piece[][] pieces = new Piece[width][height];
    for (int i=0; i<description.length; i++){
        pieces[i % width][(height-1) - (i/width)] = charToPiece(description[i]);
    }
    return pieces;
  }

  public static boolean isEmitter(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
      case EMITTER_EAST:
      case EMITTER_SOUTH:
      case EMITTER_WEST:
        return true;
    }

    return false;
  }

  public static Coordinate findEmitter(Piece[][] pieces) {
    // TODO: Part I, b.
    for (int x=0; x<pieces.length; x++){
        for (int y=0; y<pieces[x].length; y++){
            if (isEmitter(pieces[x][y])){
                return (new Coordinate(x,y));
            }
        }
    }
    return null;
  }

  public static Piece hideLaser(Piece p) {
    switch (p) {
      case LASER_VERTICAL:
      case LASER_HORIZONTAL:
      case LASER_CROSSED:
        return Piece.EMPTY;
    }
    return p;
  }

  public static Piece addLaser(Piece p, boolean isHorizontal) {
    // TODO: Part I, c, 1.
    switch (p){
      case EMPTY:
        return isHorizontal?Piece.LASER_HORIZONTAL:Piece.LASER_VERTICAL;
      case LASER_VERTICAL:
        return isHorizontal?Piece.LASER_CROSSED:p;
      case LASER_HORIZONTAL:
        return !isHorizontal?Piece.LASER_CROSSED:p;
    }
    return p;
  }

  public static Coordinate move(Piece p, Coordinate c, int xo, int yo) {
    // TODO: Part I, c, 2.
    switch (p) {
        case EMITTER_NORTH:
            return new Coordinate(c.getX(),c.getY()+1);
        case EMITTER_EAST:
            return new Coordinate(c.getX()+1,c.getY());
        case EMITTER_SOUTH:
            return new Coordinate(c.getX(),c.getY()-1);
        case EMITTER_WEST:
            return new Coordinate(c.getX()-1,c.getY());

        case EMPTY:
        case LASER_CROSSED:
        case LASER_HORIZONTAL:
        case LASER_VERTICAL:
            return new Coordinate(c.getX()+xo,c.getY()+yo);

        case MIRROR_SW_NE: // "/"
            return new Coordinate(c.getX()+yo,c.getY()+xo);
        case MIRROR_NW_SE: // "\"
            return new Coordinate(c.getX()-yo,c.getY()-xo);
            
        case WALL:
        case SNOWMAN:
        case TARGET:
            return c;
    }
    return null;
  }


  public static Piece rotate(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
        return Piece.EMITTER_EAST;
      case EMITTER_EAST:
        return Piece.EMITTER_SOUTH;
      case EMITTER_SOUTH:
        return Piece.EMITTER_WEST;
      case EMITTER_WEST:
        return Piece.EMITTER_NORTH;
      case MIRROR_SW_NE:
        return Piece.MIRROR_NW_SE;
      case MIRROR_NW_SE:
        return Piece.MIRROR_SW_NE;
    }
    return p;
  }

}
