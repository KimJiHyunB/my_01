package com.spring.exam4.vo;

public class Reply {

	int replynum;	
	int boardnum;
	String id;			
	String text;		
	String inputdate;
	
	public Reply() {
	}

	public Reply(int replynum, int boardnum, String id, String text, String inputdate) {
		super();
		this.replynum = replynum;
		this.boardnum = boardnum;
		this.id = id;
		this.text = text;
		this.inputdate = inputdate;
	}

	public int getReplynum() {
		return replynum;
	}

	public void setReplynum(int replynum) {
		this.replynum = replynum;
	}

	public int getBoardnum() {
		return boardnum;
	}

	public void setBoardnum(int boardnum) {
		this.boardnum = boardnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getInputdate() {
		return inputdate;
	}

	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}

	@Override
	public String toString() {
		return "Reply [replynum=" + replynum + ", boardnum=" + boardnum + ", id=" + id + ", text=" + text
				+ ", inputdate=" + inputdate + "]";
	}
	
	
}
