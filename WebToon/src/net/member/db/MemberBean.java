package net.member.db;

import java.util.Date;

public class MemberBean {

	private String id;
	private String pass;
	private String email;
	private String hint;
	private String hintans;
	private String nik;
	private String ages;
	private String gender;
	private Date date;
	private String progileimg;
	
	public String getProgileimg() {
		return progileimg;
	}
	public void setProgileimg(String progileimg) {
		this.progileimg = progileimg;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getHintans() {
		return hintans;
	}
	public void setHintans(String hintans) {
		this.hintans = hintans;
	}
	public String getNik() {
		return nik;
	}
	public void setNik(String nik) {
		this.nik = nik;
	}
	public String getAges() {
		return ages;
	}
	public void setAges(String ages) {
		this.ages = ages;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

}
