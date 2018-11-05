package com.spring.exam4.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import com.spring.exam4.vo.Board;
import com.spring.exam4.vo.Reply;



/**
 * 掲示板関連Mybatis使用メソッド
 */
public interface BoardMapper {
	// ゲシグル保存
	public int insertBoard(Board board);
	// 番号で該当書き込みを検索
	public Board getBoard(int boardnum);
	// 再会数1増加
	public int addHits(int boardnum);
	// 検索後の総文数
	public int getTotal(String searchText);
	// 検索後の在のページの一覧
	public ArrayList<Board> listBoard(String searchText, RowBounds rb);
	//番号とIDで該当書き込みを削除
	public int deleteBoard(Board board);
	// 掲示文修正
	public int updateBoard(Board board);
	// リプル保存
	public int insertReply(Reply reply);
	// 掲示物のリプルリスト
	public ArrayList<Reply> listReply(int boardnum);
	// リプル番号とIDから当該リプル削除
	public int deleteReply(Reply reply);
	// リプル修正
	public int updateReply(Reply reply);

}
