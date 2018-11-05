package com.spring.exam4.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.exam4.dao.BoardDAO;
import com.spring.exam4.util.FileService;
import com.spring.exam4.util.PageNavigator;
import com.spring.exam4.vo.Board;
import com.spring.exam4.vo.Reply;

/**
 * board controller
 * write, read, list, delete, modify
 */
@Controller
@RequestMapping("board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 会員関連データ処理客体
	@Autowired
	BoardDAO dao;		
	

	final int countPerPage = 12;			// ページ当たりの文章数	
	final int pagePerGroup = 5;				//ページ移動グループ当たり表示するページ数
	final String uploadPath = "/boardfile";	// ファイルアップロード経路

	
	/**
	 * write Form
	 */
	@RequestMapping (value="write", method=RequestMethod.GET)
	public String writeForm() {
		return "board/writeForm";
	}

	/**
	 * write insert
	 * @param board 入力した内容保存
	 */
	@RequestMapping (value="write", method=RequestMethod.POST)
	public String write(Board board, MultipartFile upload,
			HttpSession session, Model model) {
		
		// セッションでログインしたユーザーのIDを読んでprod 客体の作成者情報にセッティング
		String id = (String) session.getAttribute("logid");
		board.setId(id);
		
		// 添付ファイルがある場合,指定された経路に保存し,原本ファイル名と保存されたファイル名をprod客体にセット
		if (!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedfile);
		}
		
		dao.insert(board);
		return "redirect:/";
	}
	
	/**
	 * board list
	 * @param page 現在のページ。なければ1
	 * @param searchText 検索語。なければ""
	 */
	@RequestMapping (value="list", method=RequestMethod.GET)
	public String list(Model model,HttpSession session,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="searchText", defaultValue="") String searchText) {
				
		// 掲示板の書き込みの全数
		int total = dao.getTotal(searchText);			
		
		// ページ計算のためのオブジェクト生成
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total); 
		
		ArrayList<Board> boardlist = dao.listBoard(searchText, navi.getStartRecord(), navi.getCountPerPage());	
	
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("navi", navi);
		model.addAttribute("searchText", searchText);
		return "board/list";
	}
	

	/**
	 * write read
	 * @param boardnum 選択した文の番号
	 * @return 該当書き込みの情報
	 */
	@RequestMapping (value="read", method=RequestMethod.GET)
	public String read(int boardnum, Model model,HttpServletResponse response) {
		logger.info("read 1");
		// 受信した文章の番号で該当の文書を読む
		Board board = dao.get(boardnum);
		if (board == null) {
			return "redirect:list";
		}		

		ArrayList<Reply> replylist = dao.listReply(boardnum);
		

		model.addAttribute("board", board);
		model.addAttribute("replylist", replylist);
		
		logger.info("read 2");
		return "board/read";
	}
	
	/**
	 * file download
	 * @param boardnum ファイルが添付された文字番号
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public String fileDownload(int boardnum, Model model, HttpServletResponse response) {
		logger.info("download 1");
		Board board = dao.get(boardnum);
		//原来のファイル名
		String originalfile = new String(board.getOriginalfile());
		try {
			response.setHeader("Content-Disposition", " attachment;filename="+ URLEncoder.encode(originalfile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//保存されたファイル経路
		String fullPath = uploadPath + "/" + board.getSavedfile();
		// サーバーのファイルを読む入力ストリームとクライアントに伝える出力ストリーム
		FileInputStream filein = null;
		ServletOutputStream fileout = null;
		
		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();
			//Springのファイル関連ユーティル
			FileCopyUtils.copy(filein, fileout);
			
			filein.close();
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("download 2");
		return null;
	}

	/**
	 * write delete
	 * @param boardnum 削除する文字番号
	 */
	@RequestMapping (value="delete", method=RequestMethod.GET)
	public String delete(int boardnum, HttpSession session) {
		String id = (String) session.getAttribute("logid");
		//削除する書き込み番号と本人の書き込みか確認するログインID
		Board board = new Board();
		board.setBoardnum(boardnum);
		board.setId(id);
		//添付されたファイルがあるか,まず確認
		String savedfile = dao.get(boardnum).getSavedfile();
		//書き込み削除
		int result = dao.deleteBoard(board);
		// 削除成功, and 添付されたファイルがある場合,ファイルも削除
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + savedfile);
		}
		
		return "redirect:list";
	}
	
	/**
	 * write modify Form
	 * @param boardnum 修正する文字番号
	 * @return 該当番号の情報
	 */
	@RequestMapping (value="edit", method=RequestMethod.GET)
	public String editForm(int boardnum, HttpSession session, Model model) {
		
		Board board = dao.get(boardnum);
		model.addAttribute("board", board);
		return "board/editForm";
	}
	
	/**
	 * write modify
	 * @param board修正する文情報
	 */
	@RequestMapping (value="edit", method=RequestMethod.POST)
	public String edit(Board board, MultipartFile upload, HttpSession session) {
		
		// 修正する内容がログインした本人の文か確認
		String id = (String) session.getAttribute("logid");
		Board oldBoard = dao.get(board.getBoardnum());
		if (oldBoard == null || !oldBoard.getId().equals(id)) {
			return "redirect:list";
		}
		// 修正する情報にログインIDを保存
		board.setId(id);
		// 修正時に新しく添付したファイルがあれば既存ファイルを削除して新しいアップロード
		if (!upload.isEmpty()) {
			// 既存の文に添付されたファイルの実際保存された名前
			String savedfile = oldBoard.getSavedfile();
			// 既存ファイルがあれば削除
			if (savedfile != null) {
				FileService.deleteFile(uploadPath + "/" + savedfile);
			}
			// 新規にアップロードしたファイル保存
			savedfile = FileService.saveFile(upload, uploadPath);
			
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedfile);
		}
			// 文修正処理
			dao.updateBoard(board);
		return "redirect:read?boardnum=" + board.getBoardnum();
	}

	/**
	 * reply insert
	 * @param reply ユーザーが入力した文の内容
	 */
	@RequestMapping (value="replyWrite", method=RequestMethod.POST)
	public String replyWrite(Reply reply,HttpSession session,Model model) {
		
		// セッションでログインしたユーザーのIDを読んでReplyオブジェクトの作成者情報にセット
		String id = (String) session.getAttribute("logid");
		reply.setId(id);
		// リプル情報をDBに保存
		dao.insertReply(reply);

		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
	
	/**
	 * reply delete
	 * @param reply削除するリプル番号と本文番号が配信
	 */
	@RequestMapping (value="replyDelete", method=RequestMethod.GET)
	public String deleteReply(Reply reply, HttpSession session) {
		String id = (String) session.getAttribute("logid");
		// 削除する書き込み番号と本人の書き込みか確認するログインID
		reply.setId(id);
		
		dao.deleteReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
	
	/**
	 * reply modify
	 * @param reply修正するリプル情報
	 */
	@RequestMapping (value="replyEdit", method=RequestMethod.POST)
	public String replyEdit(Reply reply, HttpSession session) {
		logger.info("replyEdit 1");
		
		// 削除するリプル情報と本人の文かを確認するログインID
		String id = (String) session.getAttribute("logid");
		reply.setId(id);
		
		// リプル修正処理
		dao.updateReply(reply);

		
		logger.info("replyEdit 2");
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
	

}
