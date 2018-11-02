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
	
	@Autowired
	BoardDAO dao;		
	

	final int countPerPage = 12;			
	final int pagePerGroup = 5;				
	final String uploadPath = "/boardfile";	

	
	/**
	 * write Form
	 */
	@RequestMapping (value="write", method=RequestMethod.GET)
	public String writeForm() {
		return "board/writeForm";
	}

	/**
	 * write insert
	 */
	@RequestMapping (value="write", method=RequestMethod.POST)
	public String write(Board board, MultipartFile upload,
			HttpSession session, Model model) {
		
		String id = (String) session.getAttribute("logid");
		board.setId(id);
		
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
	 */
	@RequestMapping (value="list", method=RequestMethod.GET)
	public String list(
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="searchText", defaultValue="") String searchText,
			Model model,HttpSession session) {
		logger.debug("page: {}, searchText: {}", page, searchText);
		
		int total = dao.getTotal(searchText);			
		
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total); 
		
		ArrayList<Board> boardlist = dao.listBoard(searchText, navi.getStartRecord(), navi.getCountPerPage());	
	
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("navi", navi);
		model.addAttribute("searchText", searchText);
		return "board/list";
	}
	

	/**
	 * write read
	 */
	@RequestMapping (value="read", method=RequestMethod.GET)
	public String read(int boardnum, Model model,HttpServletResponse response) {
		logger.info("read 1");
		//전달된 글 번호로 해당 글정보 읽기
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
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public String fileDownload(int boardnum, Model model, HttpServletResponse response) {
		logger.info("download 1");
		Board board = dao.get(boardnum);
		
		String originalfile = new String(board.getOriginalfile());
		try {
			response.setHeader("Content-Disposition", " attachment;filename="+ URLEncoder.encode(originalfile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String fullPath = uploadPath + "/" + board.getSavedfile();
		
		FileInputStream filein = null;
		ServletOutputStream fileout = null;
		
		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();
			
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
	 */
	@RequestMapping (value="delete", method=RequestMethod.GET)
	public String delete(int boardnum, HttpSession session) {
		String id = (String) session.getAttribute("logid");
		
		Board board = new Board();
		board.setBoardnum(boardnum);
		board.setId(id);
		
		String savedfile = dao.get(boardnum).getSavedfile();

		int result = dao.deleteBoard(board);
		
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + savedfile);
		}
		
		return "redirect:list";
	}
	
	/**
	 * write modify Form
	 */
	@RequestMapping (value="edit", method=RequestMethod.GET)
	public String editForm(int boardnum, HttpSession session, Model model) {
		
		Board board = dao.get(boardnum);
		model.addAttribute("board", board);
		return "board/editForm";
	}
	
	/**
	 * write modify
	 */
	@RequestMapping (value="edit", method=RequestMethod.POST)
	public String edit(Board board, MultipartFile upload, HttpSession session) {
		
		String id = (String) session.getAttribute("logid");
		Board oldBoard = dao.get(board.getBoardnum());
		if (oldBoard == null || !oldBoard.getId().equals(id)) {
			return "redirect:list";
		}
		
		board.setId(id);
		
		if (!upload.isEmpty()) {
			String savedfile = oldBoard.getSavedfile();
			if (savedfile != null) {
				FileService.deleteFile(uploadPath + "/" + savedfile);
			}

			savedfile = FileService.saveFile(upload, uploadPath);
			
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedfile);
		}

		dao.updateBoard(board);
		return "redirect:read?boardnum=" + board.getBoardnum();
	}

	/**
	 * reply insert
	 */
	@RequestMapping (value="replyWrite", method=RequestMethod.POST)
	public String replyWrite(Reply reply,HttpSession session,Model model) {
		
		String id = (String) session.getAttribute("logid");
		reply.setId(id);
		
		dao.insertReply(reply);

		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
	
	/**
	 * reply delete
	 */
	@RequestMapping (value="replyDelete", method=RequestMethod.GET)
	public String deleteReply(Reply reply, HttpSession session) {
		String id = (String) session.getAttribute("logid");
		
		reply.setId(id);
		
		dao.deleteReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
	
	/**
	 * reply modify
	 */
	@RequestMapping (value="replyEdit", method=RequestMethod.POST)
	public String replyEdit(Reply reply, HttpSession session) {
		logger.info("replyEdit 1");
		String id = (String) session.getAttribute("logid");
		reply.setId(id);
		
		dao.updateReply(reply);

		
		logger.info("replyEdit 2");
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
	

}
