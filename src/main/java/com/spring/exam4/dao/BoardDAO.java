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
 * 掲示板関連DAO
 */
@Repository
public class BoardDAO {
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 掲示文保存
	 * @param board 保存する書き込み情報
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
	 * 該当書き込みを読む
	 * @param 検索する文の番号
	 * @return 検索された書き込み情報。なければ null
	 */
	public Board get(int boardnum) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		// 当該番号の文書を読む
		Board board = mapper.getBoard(boardnum);
		// 照会数1増加
		mapper.addHits(boardnum);
		return board;
	}
	
	/**
	 * 検索結果レコード数
	 * @param searchText 検索語
	 * @return 全文の数
	 */
	public int getTotal(String searchText) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int total = mapper.getTotal(searchText);
		return total;
	}
	
	/**
	 * 1ページの書き込みリストを読むこと
	 * @param searchText 検索語
	 * @param startRecord 全体結果の中で読み始める位置(初行は0)
	 * @param countPerPage 読むレコード数 (1ページ当たりの文章数)
	 * @return 目録
	 */
	public ArrayList<Board> listBoard(String searchText, int startRecord, int countPerPage) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		
		// 全体検索結果の中で読み始める位置と本数
		RowBounds rb = new RowBounds(startRecord, countPerPage);
		// 検索語,読み取り範囲を渡す
		ArrayList<Board> boardlist = mapper.listBoard(searchText, rb);
		return boardlist;
	}

	/**
	 * 該当書き込みの削除
	 * @param 削除する書き込み番号とログインIDが含まれた情報
	 * @return 削除された文数
	 */
	public int deleteBoard(Board board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.deleteBoard(board);
		return result;
	}

	/**
	 * 掲示文修正
	 * @param board 修正する文章情報
	 * @return 修正された文数
	 */
	public int updateBoard(Board board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.updateBoard(board);
		return result;
	}

	/**
	 * リプル保存
	 * @param reply 保存するリプル情報
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
	 * 1つの書き込みのリプル一覧
	 * @param boardnum 本文番号
	 * @return リプルリスト
	 */
	public ArrayList<Reply> listReply(int boardnum) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		ArrayList<Reply> replylist = mapper.listReply(boardnum);
		return replylist;
	}

	/**
	 * リプル番号で該当リプル削除
	 * @param reply 削除するリプル番号とログインIDが含まれた情報
	 * @return 削除されたリプル数
	 */
	public int deleteReply(Reply reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.deleteReply(reply);
		return result;
	}

	/**
	 * reply modify
	 * @param reply 修正するリプル情報
	 * @return 修正されたリプル数
	 */
	public int updateReply(Reply reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.updateReply(reply);
		return result;
	}

}
