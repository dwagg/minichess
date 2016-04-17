import java.util.Vector;
import java.util.Collections;
import java.util.Random;

public class chess {
        public static  char [][] board = new char[6][5];
	public static int move = 1;
	private static boolean blackMove = false;
	public static void reset() {
		// reset the state of the game / your internal variables - note that this function is highly dependent on your implementation
	
	String strOut = "";
	
	strOut += "1 W\n";
	strOut += "kqbnr\n";
	strOut += "ppppp\n";
	strOut += ".....\n";
	strOut += ".....\n";
	strOut += "PPPPP\n";
	strOut += "RNBQK\n";

	boardSet(strOut);
	}
	
	public static String boardGet() {
		// return the state of the game - one example is given below - note that the state has exactly 40 or 41 characters
		
		String strOut = "";
		
		/*strOut += "1 W\n";
		strOut += "kqbnr\n";
		strOut += "ppppp\n";
		strOut += ".....\n";
		strOut += ".....\n";
		strOut += "PPPPP\n";
		strOut += "RNBQK\n";*/

		strOut += Integer.toString(move);
                strOut += " ";
		if (blackMove == true)
			strOut += "B\n";
		else
			strOut += "W\n";
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				strOut += board[i][j];
			}
			strOut += "\n";
		} 
		//System.out.print(strOut);		
		return strOut;
	}
	
	public static void boardSet(String strIn) {
		// read the state of the game from the provided argument and set your internal variables accordingly - note that the state has exactly 40 or 41 characters
		int boardNdx =0;
		if (strIn.charAt(1) == ' '){
			//move is char at 0
			String numStr = strIn.substring(0,1);
			move = Integer.parseInt(numStr);
			if (strIn.charAt(2) == 'B')
				blackMove = true;
			else
				blackMove = false;
			boardNdx = 4;
		}
		else 
		{
			//move is 
			String numStr = strIn.substring(0,2);
			move = Integer.parseInt(numStr);
			if (strIn.charAt(3) == 'B')
				blackMove = true;
			else
				blackMove = false;
			boardNdx = 5;
		}
		int rdrPos = boardNdx;
		for (int i = 0; i < 6; i++)
		{
			for (int j =0; j <5; j++)
			{
				board[i][j] = strIn.charAt(rdrPos++);
			}
			rdrPos++; //to disregard the \n
		}
	}
	
	public static char winner() {
		// determine the winner of the current state of the game and return '?' or '=' or 'W' or 'B' - note that we are returning a character and not a string
		boolean mvMax = false;
		if (move > 40)
			mvMax = true;
		boolean blackKing = false;
		boolean whiteKing = false;
		for (int i =0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (board[i][j] == 'k')
					blackKing = true;
				if (board[i][j] == 'K')
					whiteKing = true;
			}
		}
		if (mvMax && blackKing && whiteKing){
		//	System.out.println("=\n" + boardGet());
			return '=';
		}
		if (blackKing != true){
		//	System.out.println("W\n" + boardGet());
			return 'W';
		}
		if (whiteKing != true){
		//	System.out.println("B\n" + boardGet());
			return 'B';
		}
		//System.out.println("?\n" + boardGet());
		return '?';
	}
	
	public static boolean isValid(int intX, int intY) {
		if (intX < 0) {
			return false;
			
		} else if (intX > 4) {
			return false;
			
		}
		
		if (intY < 0) {
			return false;
			
		} else if (intY > 5) {
			return false;
			
		}
		
		return true;
	}
	
	public static boolean isEnemy(char charPiece) {
		// with reference to the state of the game, return whether the provided argument is a piece from the side not on move - note that we could but should not use the other is() functions in here but probably
		char tmp = charPiece;
		if (tmp == '.')
			return false;
		if (blackMove == false)
		{
			//White move. if charPiece is upper enemy is false
			if (charPiece == Character.toUpperCase(tmp))
				return false;
			else 
				return true;
		}
		else
		{
			//Black move. if charPiece is upper enemy is true
			if (charPiece == Character.toUpperCase(tmp))
				return true;	
			else
				return false;
		}
		
	}
	
	public static boolean isOwn(char charPiece) {
		// with reference to the state of the game, return whether the provided argument is a piece from the side on move - note that we could but should not use the other is() functions in here but probably
		char tmp = charPiece;
		if (tmp == '.')
			return false;
		if (blackMove == false)
		{
			//White move. If charPiece is upper own is true
			if (charPiece == Character.toUpperCase(tmp))
				return true;
			else 
				return false;
		}
		else
		{
			//Black move. if charPiece is upper own is false
			if (charPiece == Character.toUpperCase(tmp))
				return false;	
			else
				return true;
		}
	}
	
	public static boolean isNothing(char charPiece) {
		// return whether the provided argument is not a piece / is an empty field - note that we could but should not use the other is() functions in here but probably
		if (charPiece == '.')
			return true;
		
		return false;
	}
	
	public static int eval() {
		// with reference to the state of the game, return the the evaluation score of the side on move - note that positive means an advantage while negative means a disadvantage
		int multiplier = 1;
		if (blackMove == true)
		{
			multiplier = -1;
		}	
		// p=1 r=2 n=3 b=4 q=5 k=16
		int blackSum = 0;
		int whiteSum = 0;
		for(int i =0; i < 6; i++)
		{
			for (int j=0; j < 5; j++)
			{
				char c = board[i][j]; 
				if (c == 'p')
					blackSum +=1;
				if (c == 'P')
					whiteSum +=1;
				if (c == 'r')
					blackSum +=2;
				if (c == 'R')
					whiteSum +=2;
				if (c == 'n')
					blackSum +=3;
				if (c == 'N')
					whiteSum +=3;
				if (c == 'b')
					blackSum +=4;
				if (c == 'B')
					whiteSum +=4;
				if (c == 'q')
					blackSum +=5;
				if (c == 'Q')
					whiteSum +=5;
				if (c == 'k')
					blackSum +=16;
				if (c == 'K')
					whiteSum +=16;
				
			}
		}
		if (blackMove == true)
			return blackSum - whiteSum;
		else
			return whiteSum - blackSum;
	}
	
	public static String buildMove(int fromX, int fromY, int toX, int toY)
	{
		char xCh [] = new char [5];
		xCh[0] = 'a';
		xCh[1] = 'b';
		xCh[2] = 'c';
		xCh[3] = 'd';
		xCh[4] = 'e';
		
		String move = String.valueOf(xCh[fromX]) + Integer.toString(6-fromY) + "-" + xCh[toX] + (6-toY) + '\n';
		return move;
	}

	public static Vector<String> kingMoves(int x, int y)
	{
		Vector<String> moves = new Vector<String>();
		String toAdd ="";
		if (isValid(x,y))
		{
			if ( isValid(x+1,y) && isOwn(board[y][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y) && isOwn(board[y][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x, y+1) && isOwn(board[y+1][x]) == false)
			{
				toAdd = buildMove(x,y,x,y+1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x,y-1) && isOwn(board[y-1][x]) == false)
			{
				toAdd = buildMove(x,y,x,y-1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1, y+1) && isOwn(board[y+1][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y+1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y-1) && isOwn(board[y-1][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y-1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1, y+1) && isOwn(board[y+1][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y+1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y-1) && isOwn(board[y-1][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y-1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
		}
		return moves;
	}
	public static Vector<String> queenMoves(int x, int y)
	{
		Vector<String> moves = new Vector<String>();
		String toAdd = "";

		if (isValid(x,y))
		{
			int i = 1;
			while (isValid(x,y+i) && isOwn(board[y+i][x]) == false)
			{
				toAdd = buildMove(x,y,x,y+i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x]))
					i = 11;
				i++;
			}	
			i = 1;
			while (isValid(x,y-i) && isOwn(board[y-i][x]) == false)
			{
				toAdd = buildMove(x,y,x,y-i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y) && isOwn(board[y][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y) && isOwn(board[y][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x-i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y+i) && isOwn(board[y+i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y+i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y-i) && isOwn(board[y-i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y-i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y+i) && isOwn(board[y+i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y+i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x-i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y-i) && isOwn(board[y-i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y-i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x-i]))
					i = 11;
				i++;
			}
		}
		return moves;
	}

	public static Vector<String> bishopMoves(int x, int y)
	{
		Vector<String> moves = new Vector<String>();
		String toAdd ="";
		if (isValid(x,y))
		{
			//first 4 ifs are the bishop special moves
			if ( isValid(x+1,y) && isNothing(board[y][x+1]))
			{
				toAdd = buildMove(x,y,x+1,y);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y) && isNothing(board[y][x-1]))
			{
				toAdd = buildMove(x,y,x-1,y);
				System.out.println(toAdd);
				moves.add(toAdd);
				
			}
			if (isValid(x, y+1) && isNothing(board[y+1][x]))
			{
				toAdd = buildMove(x,y,x,y+1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x,y-1) && isNothing(board[y-1][x]))
			{
				toAdd = buildMove(x,y,x,y-1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}

			//Diagonal moves 
			int i = 1;
			while (isValid(x+i,y+i) && isOwn(board[y+i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y+i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y+i) && isOwn(board[y+i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y+i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x-i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y-i) && isOwn(board[y-i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y-i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y-i) && isOwn(board[y-i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y-i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x-i]))
					i = 11;
				i++;
			}
		}	
		return moves;
	}

	public static Vector<String> knightMoves(int x, int y)
	{
		Vector<String> moves = new Vector<String>();
		String toAdd ="";
		if (isValid(x,y))
		{
			if (isValid(x+2, y+1) && isOwn(board[y+1][x+2]) == false)
			{	
				toAdd = buildMove(x,y,x+2,y+1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y+2) && isOwn(board[y+2][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y+2);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+2,y-1) && isOwn(board[y-1][x+2]) == false)
			{
				toAdd = buildMove(x,y,x+2,y-1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y-2) && isOwn(board[y-2][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y-2);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-2,y+1) && isOwn(board[y+1][x-2]) == false)
			{
				toAdd = buildMove(x,y,x-2,y+1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y+2) && isOwn(board[y+2][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y+2);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-2,y-1) && isOwn(board[y-1][x-2]) == false)
			{
				toAdd = buildMove(x,y,x-2,y-1);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y-2) && isOwn(board[y-2][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y-2);
				System.out.println(toAdd);
				moves.add(toAdd);
			}
		}
		return moves;
	}
	public static Vector<String> rookMoves(int x, int y)
	{
		Vector<String> moves = new Vector<String>();
		int i =1;
		String toAdd ="";
		if (isValid(x,y))
		{
			while (isValid(x+i,y) && isOwn(board[y][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x+i]))
					i = 11;
				i++;		
			}
			i = 1;
			while (isValid(x-i,y) && isOwn(board[y][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x-i]))
					i = 11;
				i++;
			}
			i = 1; 
			while (isValid(x,y+i) && isOwn(board[y+i][x]) == false)
			{
				toAdd = buildMove(x,y,x,y+i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x,y-i) && isOwn(board[y-i][x]) == false)
			{
				toAdd = buildMove(x,y,x,y-i);
				System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x]))
					i = 11;
				i++;
			}
		}
		return moves;
	}
	public static Vector<String> pawnMoves(int x, int y)
	{
		Vector<String> moves = new Vector<String>();

		String toAdd ="";
		if (isValid(x,y) == false)
			return moves;
		//black moves
		if (board[y][x] == 'p')
		{
			if (isValid(x,y+1) && isNothing(board[y+1][x]))
			{
				toAdd = buildMove(x,y,x,y+1);
				System.out.println( "pawn: " + toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y+1) && isEnemy(board[y+1][x+1]))
			{
				toAdd = buildMove(x,y,x+1,y+1);
				System.out.println ("pawn cap: " + toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y+1) && isEnemy(board[y+1][x-1]))
			{
				toAdd = buildMove(x,y,x-1,y+1);
				System.out.println ("pawn cap: " + toAdd);
				moves.add(toAdd);
			}
		}
		//white moves
		if (board[y][x] == 'P')
		{
			if (isValid(x,y-1) && isNothing(board[y-1][x]))
			{
				toAdd = buildMove(x,y,x,y-1);
				System.out.println("Pawn: " + toAdd);
				moves.add(toAdd);
			}	
			if (isValid(x+1,y-1) && isEnemy(board[y-1][x+1]))
			{
				toAdd = buildMove(x,y,x+1,y-1);
				System.out.println ("Pawn cap: " + toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y-1) && isEnemy(board[y-1][x-1]))
			{
				toAdd = buildMove(x,y,x-1,y-1);
				System.out.println ("Pawn cap: " + toAdd);
				moves.add(toAdd);
			}
		}
		return moves;
	}

	public static Vector<String> generateMoves(char piece, int x, int y)
	{
		Vector<String> moves = new Vector<String>();
		char c = piece;
		c = Character.toUpperCase(c);
		if (c == 'K')
		{
			moves.addAll(kingMoves(x,y));
		}
		if (c == 'Q')
		{
			moves.addAll(queenMoves(x,y));
		}
		if (c == 'B')
		{
			moves.addAll(bishopMoves(x,y));
		}
		if (c == 'N')
		{
			moves.addAll(knightMoves(x,y));
		}
		if (c == 'R')
		{
			moves.addAll(rookMoves(x,y));
		}
		if (c == 'P')
		{
			moves.addAll(pawnMoves(x,y));
		}
		return moves;
	}

	public static Vector<String> findMoves()
	{	
		Vector<String> moves = new Vector<String>();
		for (int i =0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{	
				if (isOwn(board[i][j]) == true)
				{
					moves.addAll(generateMoves(board[i][j],j,i));	
				}
			}
		}
		return moves;
	}
	
	public static Vector<String> moves() {
		// with reference to the state of the game and return the possible moves - one example is given below - note that a move has exactly 6 characters
		
		Vector<String> strOut = new Vector<String>();
		strOut = findMoves();
		for (int i = 0; i < strOut.size(); i++)
		{
			System.out.println(strOut.get(i));
		}
		return strOut;
	}
	
	public static Vector<String> movesShuffled() {
		// with reference to the state of the game, determine the possible moves and shuffle them before returning them - note that you can call the chess.moves() function in here
			
		Vector<String> shuf =  new Vector<String>();
		shuf = moves();
		Collections.shuffle(shuf);
		return shuf;
	}
	
	public static Vector<String> movesEvaluated() {
		// with reference to the state of the game, determine the possible moves and sort them in order of an increasing evaluation score before returning them - note that you can call the chess.moves() function in here
		
		return new Vector<String>();
	}
	
	public static void move(String charIn) {
		// perform the supplied move (for example "a5-a4\n") and update the state of the game / your internal variables accordingly - note that it advised to do a sanity check of the supplied move

		char xCh [] = new char [5];
		xCh[0] = 'a';
		xCh[1] = 'b';
		xCh[2] = 'c';
		xCh[3] = 'd';
		xCh[4] = 'e';
		
		int xfrom = -1;
		int xto = -1;
		int yfrom = -1;
		int yto = -1;
		
		for (int i = 0; i < 5; i++)
		{
			if (charIn.charAt(0) == xCh[i])
				xfrom = i;
			if (charIn.charAt(3) == xCh[i])
				xto = i;
		}
		if (charIn.charAt(1) == '1')
			yfrom = 5;
		
		if (charIn.charAt(1) == '2')
			yfrom = 4;

		if (charIn.charAt(1) == '3')
			yfrom = 3;
		
		if (charIn.charAt(1) == '4')
			yfrom = 2;

		if (charIn.charAt(1) == '5')
			yfrom = 1;
		
		if (charIn.charAt(1) == '6')
			yfrom = 0;

		//===========
		if (charIn.charAt(4) == '1')
			yto = 5;
		
		if (charIn.charAt(4) == '2')
			yto = 4;

		if (charIn.charAt(4) == '3')
			yto = 3;
		
		if (charIn.charAt(4) == '4')
			yto = 2;

		if (charIn.charAt(4) == '5')
			yto = 1;
		
		if (charIn.charAt(4) == '6')
			yto = 0;

		board[yto][xto] = board[yfrom][xfrom];
		board[yfrom][xfrom] = '.';
		if (yto == 0 && board[yto][xto] == 'P')
			board[yto][xto] = 'Q';
		if (yto == 5 && board[yto][xto] == 'p')
			board[yto][xto] = 'q';
		if (blackMove)
			move++;

		blackMove = !blackMove;
	}
	
	public static String moveRandom() {
		// perform a random move and return it - one example output is given below - note that you can call the chess.movesShuffled() function as well as the chess.move() function in here
		Vector<String> moves = movesShuffled();
		Random rand = new Random();
		if (moves.size() == 0)
			return "";
		int random = rand.nextInt(moves.size());
		move(moves.get(random));	
		return moves.get(random);
	}
	
	public static String moveGreedy() {
		// perform a greedy move and return it - one example output is given below - note that you can call the chess.movesEvaluated() function as well as the chess.move() function in here
		
		return "a2-a3\n";
	}
	
	public static String moveNegamax(int intDepth, int intDuration) {
		// perform a negamax move and return it - one example output is given below - note that you can call the the other functions in here
		
		return "a2-a3\n";
	}
	
	public static String moveAlphabeta(int intDepth, int intDuration) {
		// perform a alphabeta move and return it - one example output is given below - note that you can call the the other functions in here
		
		return "a2-a3\n";
	}
	
	public static void undo() {
		// undo the last move and update the state of the game / your internal variables accordingly - note that you need to maintain an internal variable that keeps track of the previous history for this
	}
}
