package experiment;

import java.util.List;

import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;
import cse332.exceptions.NotYetImplementedException;

public class AlphaBetaSearcher<M extends Move<M>, B extends Board<M, B>> extends AbstractSearcher<M, B> {
    public M getBestMove(B board, int myTime, int opTime) {
    	 BestMove<M> best = alphaBeta(this.evaluator, board, ply, Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
         return best.move;
        
        
        // i think this is super similar to our simpleSearcher but we just need to be checking for 
        // if alpha is >= beta then we break. 
    }
    
    static <M extends Move<M>, B extends Board<M, B>> BestMove<M> alphaBeta(Evaluator<B> evaluator, B board, int depth, int alpha, int beta) {
    	if (depth == 0) {
    		return new BestMove<M>(evaluator.eval(board));
    	}
    	
    	BestMove<M> bestMove = new BestMove<M>(alpha);
    	List<M> moves = board.generateMoves();
    	if (moves.isEmpty()) {
    		if (board.inCheck()) {
    			bestMove.value = -evaluator.mate() - depth;
    		} else {
    			bestMove.value = -evaluator.stalemate();
    		}
    		return bestMove; 
    	} 
    	
    	for (M move : moves) {
    		 board.applyMove(move);
    		 //countNode.count++;
    	     //countNode.countPara.increment();
    		 BestMove<M> currMove = alphaBeta(evaluator, board, depth - 1, -beta, -alpha).negate();
    		 currMove.move = move;
    		 board.undoMove();
    		 
    		 if (currMove.value > bestMove.value) {
    			 bestMove = currMove;
    			 alpha = currMove.value;
    		 }
    		 

    		 //If the value is bigger than beta, we won't actually be able to get this move
    		 if (alpha >= beta) {
    			 //break; ???? 
    			 bestMove.value = alpha;
    			 return bestMove; 
    		 }
    		 
    	}
    	return bestMove;

    }
}