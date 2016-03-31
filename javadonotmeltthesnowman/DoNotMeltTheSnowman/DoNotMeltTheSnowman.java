public class DoNotMeltTheSnowman {

  public static void main(String[] args) {
    int selected_level = Integer.parseInt(args[0]);
    Level level = Levels.getLevels()[selected_level];
    char[] piece_chars = level.getCharArray();
    Piece[][] pieces = new Piece[level.getWidth()][level.getHeight()];
    int k=0;
    for(int i=0; i<level.getWidth(); i++){
      for(int j=0; j<level.getHeight(); j++){
        pieces[i][j]=PieceUtils.charToPiece(piece_chars[k++]);
      }
    }
    Board board = new Board(pieces);
  
    System.out.println("Do not melt the snowman");
    System.out.println("=======================");
    System.out.println();
    Result r;
    do{
      r = board.fireLaser();
      board.renderBoard();
      System.out.print("Enter a row and column: ");
      int row = IOUtil.readInt();
      int col = IOUtil.readInt();
      board.clearLasers();
      board.rotatePiece(new Coordinate(row,col));
    }while(r==Result.MISS);
  }

}
