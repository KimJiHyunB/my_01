package com.spring.exam4.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.exam4.vo.Board;
import com.spring.exam4.vo.Reply;


/**
 * board DAO
 */
@Repository
public class BoardDAO {
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * write insert
	 */
	public int insert(Board board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		
		int result = 0;
		try {
			result = mapper.insertBoard(board);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * write read
	 */
	public Board get(int boardnum) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		Board board = mapper.getBoard(boardnum);

		mapper.addHits(boardnum);
		return board;
	}
	
	/**
	 * list account
	 */
	public int getTotal(String searchText) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int total = mapper.getTotal(searchText);
		return total;
	}
	
	/**
	 * list
	 */
	public ArrayList<Board> listBoard(String searchText, int startRecord, int countPerPage) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		RowBounds rb = new RowBounds(startRecord, countPerPage);

		ArrayList<Board> boardlist = mapper.listBoard(searchText, rb);
		return boardlist;
	}

	/**
	 * delete
	 */
	public int deleteBoard(Board board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.deleteBoard(board);
		return result;
	}

	/**
	 * write modify
	 */
	public int updateBoard(Board board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.updateBoard(board);
		return result;
	}

	/**
	 * reply isnert
	 */
	public int insertReply(Reply reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		
		int result = 0;
		try {
			result = mapper.insertReply(reply);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * reply list
	 */
	public ArrayList<Reply> listReply(int boardnum) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		ArrayList<Reply> replylist = mapper.listReply(boardnum);
		return replylist;
	}

	/**
	 * reply delete
	 */
	public int deleteReply(Reply reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.deleteReply(reply);
		return result;
	}

	/**
	 * reply modify
	 */
	public int updateReply(Reply reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.updateReply(reply);
		return result;
	}

}
