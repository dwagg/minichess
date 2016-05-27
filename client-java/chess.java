import java.util.Vector;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import java.util.Comparator;

public class chess {
        public static  char [][] board = new char[6][5];
	public static int move = 1;
	private static boolean blackMove = false;
//	public static Stack<String> //stack = new Stack<String>();
	public static Stack<Integer> undoStack = new Stack<Integer>();
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
//	stack = new Stack<String>();
	undoStack = new Stack<Integer>();
	boardSet(strOut);
	}
	
	public static String boardGet() {
		// return the state of the game - one example is given below - note that the state has exactly 40 or 41 characters
		
		String strOut = "";
		
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
		int blackSum = 0;
		int whiteSum = 0;
		for(int i =0; i < 6; i++)
		{
			for (int j=0; j < 5; j++)
			{
				char c = board[i][j]; 
				if (c == 'p')
				{
				//	if (i == 1)
				//		blackSum +=5;
				//	else
					blackSum +=1;
				}
				if (c == 'P')
				{
				//	if (i == 4)
				//		blackSum +=5;
				//	else
					whiteSum +=1;
				}
				if (c == 'r')
					blackSum +=8;
				if (c == 'R')
					whiteSum +=8;
				if (c == 'n')
					blackSum +=6;
				if (c == 'N')
					whiteSum +=6;
				if (c == 'b')
					blackSum +=12;
				if (c == 'B')
					whiteSum +=12;
				if (c == 'q')
					blackSum +=20;
				if (c == 'Q')
					whiteSum +=20;
				if (c == 'k')
					blackSum +=200;
				if (c == 'K')
					whiteSum +=200;
				
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
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y) && isOwn(board[y][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x, y+1) && isOwn(board[y+1][x]) == false)
			{
				toAdd = buildMove(x,y,x,y+1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x,y-1) && isOwn(board[y-1][x]) == false)
			{
				toAdd = buildMove(x,y,x,y-1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1, y+1) && isOwn(board[y+1][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y+1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y-1) && isOwn(board[y-1][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y-1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1, y+1) && isOwn(board[y+1][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y+1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y-1) && isOwn(board[y-1][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y-1);
			//	System.out.println(toAdd);
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
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x]))
					i = 11;
				i++;
			}	
			i = 1;
			while (isValid(x,y-i) && isOwn(board[y-i][x]) == false)
			{
				toAdd = buildMove(x,y,x,y-i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y) && isOwn(board[y][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y) && isOwn(board[y][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x-i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y+i) && isOwn(board[y+i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y+i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y-i) && isOwn(board[y-i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y-i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y+i) && isOwn(board[y+i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y+i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x-i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y-i) && isOwn(board[y-i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y-i);
			//	System.out.println(toAdd);
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
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y) && isNothing(board[y][x-1]))
			{
				toAdd = buildMove(x,y,x-1,y);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				
			}
			if (isValid(x, y+1) && isNothing(board[y+1][x]))
			{
				toAdd = buildMove(x,y,x,y+1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x,y-1) && isNothing(board[y-1][x]))
			{
				toAdd = buildMove(x,y,x,y-1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}

			//Diagonal moves 
			int i = 1;
			while (isValid(x+i,y+i) && isOwn(board[y+i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y+i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y+i) && isOwn(board[y+i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y+i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x-i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x+i,y-i) && isOwn(board[y-i][x+i]) == false)
			{
				toAdd = buildMove(x,y,x+i,y-i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y-i][x+i]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x-i,y-i) && isOwn(board[y-i][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y-i);
			//	System.out.println(toAdd);
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
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y+2) && isOwn(board[y+2][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y+2);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+2,y-1) && isOwn(board[y-1][x+2]) == false)
			{
				toAdd = buildMove(x,y,x+2,y-1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y-2) && isOwn(board[y-2][x+1]) == false)
			{
				toAdd = buildMove(x,y,x+1,y-2);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-2,y+1) && isOwn(board[y+1][x-2]) == false)
			{
				toAdd = buildMove(x,y,x-2,y+1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y+2) && isOwn(board[y+2][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y+2);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-2,y-1) && isOwn(board[y-1][x-2]) == false)
			{
				toAdd = buildMove(x,y,x-2,y-1);
			//	System.out.println(toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y-2) && isOwn(board[y-2][x-1]) == false)
			{
				toAdd = buildMove(x,y,x-1,y-2);
			//	System.out.println(toAdd);
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
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x+i]))
					i = 11;
				i++;		
			}
			i = 1;
			while (isValid(x-i,y) && isOwn(board[y][x-i]) == false)
			{
				toAdd = buildMove(x,y,x-i,y);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y][x-i]))
					i = 11;
				i++;
			}
			i = 1; 
			while (isValid(x,y+i) && isOwn(board[y+i][x]) == false)
			{
				toAdd = buildMove(x,y,x,y+i);
			//	System.out.println(toAdd);
				moves.add(toAdd);
				if (isEnemy(board[y+i][x]))
					i = 11;
				i++;
			}
			i = 1;
			while (isValid(x,y-i) && isOwn(board[y-i][x]) == false)
			{
				toAdd = buildMove(x,y,x,y-i);
			//	System.out.println(toAdd);
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
			//	System.out.println( "pawn: " + toAdd);
				moves.add(toAdd);
			}
			if (isValid(x+1,y+1) && isEnemy(board[y+1][x+1]))
			{
				toAdd = buildMove(x,y,x+1,y+1);
			//	System.out.println ("pawn cap: " + toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y+1) && isEnemy(board[y+1][x-1]))
			{
				toAdd = buildMove(x,y,x-1,y+1);
			//	System.out.println ("pawn cap: " + toAdd);
				moves.add(toAdd);
			}
		}
		//white moves
		if (board[y][x] == 'P')
		{
			if (isValid(x,y-1) && isNothing(board[y-1][x]))
			{
				toAdd = buildMove(x,y,x,y-1);
			//	System.out.println("Pawn: " + toAdd);
				moves.add(toAdd);
			}	
			if (isValid(x+1,y-1) && isEnemy(board[y-1][x+1]))
			{
				toAdd = buildMove(x,y,x+1,y-1);
			//	System.out.println ("Pawn cap: " + toAdd);
				moves.add(toAdd);
			}
			if (isValid(x-1,y-1) && isEnemy(board[y-1][x-1]))
			{
				toAdd = buildMove(x,y,x-1,y-1);
			//	System.out.println ("Pawn cap: " + toAdd);
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
	//		System.out.println(strOut.get(i));
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

	public static Vector<String> sortByScore(Vector<String> moves, int [] scores)
	{
		class PairMv
		{
			public int score;
			public String move;
		}
		Vector<PairMv> pairs = new Vector<PairMv>();
		int size = moves.size();
		for (int i = 0; i < size; i++)
		{
			PairMv toAdd = new PairMv();
			toAdd.score = scores[i];
			toAdd.move = moves.get(i);
			pairs.add(toAdd);
		}
		Collections.sort(pairs, new Comparator<PairMv>()
		{
			@Override
			public int compare(PairMv lhs, PairMv rhs)
			{
				return lhs.score < rhs.score? -1: lhs.score > rhs.score? 1:0;
			}
		});
		Vector<String> sorted = new Vector<String>();
		for (int i = 0; i < size; i++)
		{
			String toAdd = pairs.get(i).move;
			sorted.add(toAdd);
		}
		return sorted;
	}
	
	public static Vector<String> movesEvaluated() {
		// with reference to the state of the game, determine the possible moves and sort them in order of an increasing evaluation score before returning them - note that you can call the chess.movesShuffled() function in here
		
		Vector<String> moves = movesShuffled();	
		int size = moves.size();	
		if (size < 2)
			return moves;
		int [] scores = new int[moves.size()];

		for (int i = 0; i < size; i++)
		{
			move(moves.get(i));
			scores[i] = eval();		
			undo();
		}
		moves = sortByScore(moves,scores);
		return moves;
	}

	public static int encodeUndo(int xto, int xfrom, int yto, int yfrom, char pieceFrom, char pieceTo)
	{
		int code = 0;
		int turn = -1;
		int moveNum = -1;
		int xtoCode = xto;
		int xfromCode = xfrom;
		int ytoCode = yto;
		int yfromCode = yfrom;
		int winner = -1;
		int fromCode = -1;
		int toCode = -1;
		char winnerCh = winner();
		char mover = pieceFrom;
		char reciever = pieceTo;
		mover = Character.toUpperCase(mover);
		reciever = Character.toUpperCase(reciever);
		if (winnerCh == '?')
		{
			winner = 0;
		}	
		else if (winnerCh == '=')
		{
			winner = 1;
		}
		else if (winnerCh == 'B')
		{
			winner = 2;
		}
		else
		{
			winner = 3;
		}

		if (blackMove == true)
		{
			turn = 1;
		}
		else
		{
			turn = 0;
		}

		moveNum = move;

		if (mover == 'P')
		{
			fromCode = 0;
		}
		else if (mover == 'R')
		{
			fromCode = 1;
		}
		else if (mover == 'N')
		{
			fromCode = 2;
		}
		else if (mover == 'B')
		{	
			fromCode = 3;
		}
		else if (mover == 'Q')
		{
			fromCode = 4;
		}
		else if (mover == 'K')
		{
			fromCode = 5;
		}
		else
		{
			fromCode = 6;
		}
		
		if (reciever  == 'P')
		{
			toCode = 0;
		}
		else if (reciever == 'R')
		{
			toCode = 1;
		}
		else if (reciever == 'N')
		{
			toCode = 2;
		}
		else if (reciever == 'B')
		{	
			toCode = 3;
		}
		else if (reciever == 'Q')
		{
			toCode = 4;
		}
		else if (reciever == 'K')
		{
			toCode = 5;
		}
		else
		{
			toCode = 6;
		}
		code += toCode;
		code += fromCode *   10;
		code += ytoCode *    100;
		code += yfromCode *  1000;
		code += xtoCode *    10000;
		code += xfromCode *  100000;
		code += moveNum *    1000000;
		code += winner *     100000000;
		code += turn *       1000000000;

		return code; 

	}
	
	public static void move(String charIn) {
		// perform the supplied move (for example "a5-a4\n") and update the state of the game / your internal variables accordingly - note that it advised to do a sanity check of the supplied move
		if (charIn.length() == 0)
			return;
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
		//if (winner() != '?')
		//	return;	
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
		System.out.println(Integer.toString( encodeUndo(xto, xfrom, yto, yfrom, board[yfrom][xfrom], board[yto][xto])));
		//System.out.println(Integer.toString((10101026 % 100) / 10));
		//stack.push(boardGet());
		undoStack.push(encodeUndo(xto, xfrom, yto, yfrom, board[yfrom][xfrom], board[yto][xto]));
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
		Vector<String> moves = movesEvaluated();
		if (moves.size() > 0)
		{
			String s = moves.get(0);
			move(s);
			return s;	
		}
		return "a2-a3\n";
	}

	public static int negamax(int depth, int duration)
	{
		int score = -100000;
		int tmp = 0;
		Vector<String> moves = movesShuffled();
		System.out.println(" " + Integer.toString(depth));
		if (depth == 0 || winner() != '?')
		{
			return eval();
		}
		for (int i = 0; i < moves.size(); i++)
		{
			move(moves.get(i));
			tmp = -negamax(depth - 1, duration);
			score = score > tmp ? score:tmp;
			undo();
		}
		return score;
	}
	
	public static String moveNegamax(int intDepth, int intDuration) {
		// perform a negamax move and return it - one example output is given below - note that you can call the the other functions in here
		/*String bestMove = negamax(intDepth, intDuration);
		move(bestMove);
		return bestMove;*/
		String best = "";
		int score = -100000;
		int tmp = 0;
		Vector<String> moves = movesShuffled();
		for (int i = 0; i < moves.size(); i++)
		{
			move(moves.get(i));
			tmp = -negamax(intDepth - 1, intDuration);
			undo();
			if (tmp > score)
			{
				best = moves.get(i);
				score = tmp;
			}
		}
		move(best);
		return best;
	}

	public static int alphaBeta(int depth, int alpha, int beta)
	{

		if (depth == 0 || winner() != '?')
		{
			return eval();
		}
		int score = -100000;
		int tmp = 0;
		Vector<String> moves = movesEvaluated();
		int size = moves.size();
		
		for (int i = 0; i < size; i++)
		{
			move(moves.get(i));
			tmp = -alphaBeta(depth - 1, -beta, -alpha);
			score = tmp > score?tmp:score;
			undo();
			
			alpha = alpha > score?alpha:score;
	
			if (alpha >= beta)
			{
				break;
			} 
		}
		return score;
	}
	
	public static String moveAlphabeta(int intDepth, int intDuration) {
		// perform a alphabeta move and return it - one example output is given below - note that you can call the the other functions in here
		String best = "";
		int alpha = -100000;
		int beta = 100000;
		int tmp = 0;
		Vector<String> moves = movesEvaluated();
		int size = moves.size();

		for (int i =0; i < size; i++)
		{
			move(moves.get(i));
			tmp = -alphaBeta(intDepth - 1,-beta, -alpha);
			undo();
			if (tmp > alpha)
			{
				best = moves.get(i);
				alpha = tmp;
			}	
		}	
		move(best);
		return best;
	}

	public static void fundo()
	{	
		int code = 0;
		int toCode = -1;
		int fromCode = -1;
		int ytoCode = -1;
		int yfromCode = -1;
		int xtoCode = -1;
		int xfromCode = -1;
		int moveNum = -1;
		int winner = -1;
		int turn = -1;
		char to = 'm';
		char from = 'n';
		boolean bTurn = false;
		char winnerCh = 'n';
		if (undoStack.empty() == false)
		{
			code = undoStack.pop();
			toCode = code %     10;
			fromCode = (code %  100) /        10;
			ytoCode = (code %   1000) /       100;
			yfromCode = (code % 10000) /      1000;
			xtoCode = (code %   100000) /     10000;
			xfromCode = (code % 1000000) /    100000;
			moveNum = (code %   100000000) /  1000000;
			winner = (code %    1000000000) / 100000000;
			if (code > 500000000)
			{
				turn = 1;
			}
			else
			{
				turn = 0;
			}
			if (turn == 1)
			{
				bTurn = true;
			}
			else
			{
				bTurn = false;
			}
			if (toCode == 0)
			{
				to = bTurn ? 'P':'p';
			}
			else if (toCode == 1)
			{
				to = bTurn ? 'R':'r';
			}
			else if (toCode == 2)
			{
				to = bTurn ? 'N':'n';
			}
			else if (toCode == 3)
			{
				to = bTurn ? 'B':'b';
			}
			else if (toCode == 4)
			{
				to = bTurn ? 'Q':'q';
			}
			else if (toCode == 5)
			{
				to = bTurn ? 'K':'k';
			}
			else
			{
				to = '.';
			}
		
			if (fromCode == 0)
			{
				from = bTurn ? 'p':'P';
			}
			else if (fromCode == 1)
			{
				from = bTurn ? 'r':'R';
			}
			else if (fromCode == 2)
			{
				from = bTurn ? 'n':'N';
			}
			else if (fromCode == 3)
			{
				from = bTurn ? 'b':'B';
			}
			else if (fromCode == 4)
			{
				from = bTurn ? 'q':'Q';
			}
			else if (fromCode == 5)
			{
				from = bTurn ? 'k':'K';
			}
				
			if (winner == 0)
			{
				winnerCh = '?';
			}
			else if (winner == 1)
			{
				winnerCh = '=';
			}
			else if (winner == 2)
			{
				winnerCh = 'B';
			}
			else
			{
				winnerCh = 'W';
			}
			System.out.println("to: " + to);
			System.out.println("from: " + from);
			System.out.println("winner: " + winnerCh);
			String t = bTurn == true? "black":"white";
			System.out.println("turn: " + t);
			System.out.println("moveNum: " + moveNum);
			System.out.println("xfrom: " + xfromCode);
			System.out.println("yfrom: " + yfromCode);
			System.out.println("xto: " + xtoCode);
			System.out.println("yto: " + ytoCode);
			board[yfromCode][xfromCode] = from;
			board[ytoCode][xtoCode] = to;
			blackMove = bTurn;
			move = moveNum;	
		}
	}
	
	public static void undo() {
		// undo the last move and update the state of the game / your internal variables accordingly - note that you need to maintain an internal variable that keeps track of the previous history for this
		//if (stack.empty() == false)
			//boardSet(stack.pop());
		fundo();
	}
}
