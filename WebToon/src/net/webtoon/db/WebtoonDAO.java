package net.webtoon.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import net.board.db.FanBean;

public class WebtoonDAO {

	private Connection getConnection() throws Exception {
		Context init=new InitialContext();// import javax.naming
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/MysqlDB"); 
		// context 파일의 name값을 넣는다, xml에서 name은 마음대로지어도됨
		// object 타입으로 저장되어있어서 (DataSource)로 형변환 해주어야함
		Connection con = null;
		con=ds.getConnection();
		// getConnection 함수를 통해서 connection 형태로 변환가능 
		return con;
	}
	
	public WebtoonBean getWebtoon(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WebtoonBean wb = new WebtoonBean();
		try {
			con = getConnection();
			// 게시판 글 번호 구하기
			// num 구하기, 게시판 글 중에 가장 큰 번호
			String sql = "select * from webtoon where web_num=?";
			// 4 저장 <= 결과 실행
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			// 5 첫행에 데이터가 있으면 가장큰 번호+1;
			while (rs.next()) {
				wb.setWeb_num(rs.getInt("web_num"));
				wb.setWeb_subject(rs.getString("web_subject"));
				wb.setWeb_author(rs.getString("web_author"));
				wb.setWeb_genre(rs.getString("web_genre"));
				wb.setWeb_start(rs.getString("web_start"));
				wb.setWeb_portal(rs.getString("web_portal"));
				wb.setWeb_info(rs.getString("web_info"));
				wb.setWeb_ing(rs.getString("web_ing"));
				wb.setWeb_link(rs.getString("web_link"));
				wb.setWeb_thumb_link(rs.getString("web_thumb_link"));
			}								
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return wb;
	}
	
	public boolean insertcheck(WebtoonBean webtoon){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag=false;//flase-디비에 있음    true-디비에 없음
		
		try{
			con=getConnection();
			
			String sql="select * from webtoon where web_subject= ? ";
			pstmt =con.prepareStatement(sql);
			
			pstmt.setString(1, webtoon.getWeb_subject());
			rs=pstmt.executeQuery();
			//디비에 있으면  펄스   없으면 트루
			if(rs.next()){
				flag=false;
			}else{
				flag=true;
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null){try{rs.close();}catch(SQLException e){e.printStackTrace();}
			}
		}
		
		
		return flag;
	}
	
	
	public void insertWebtoon(WebtoonBean webtoon) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;
		try {
			con = getConnection();
			

			// 게시판 글 번호 구하기
			// num 구하기, 게시판 글 중에 가장 큰 번호
			String sql = "select max(web_num) from webtoon";
			// 4 저장 <= 결과 실행
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			// 5 첫행에 데이터가 있으면 가장큰 번호+1;
			while (rs.next()) {
				num=rs.getInt("max(web_num)")+1;
			}								
			
			// sql insert num구한값 => re_ref
			// re_lev 0, re_seq 0,
			sql = "insert into webtoon(web_num,web_subject,web_author,web_genre,web_start,web_portal,web_info,web_ing,web_link,web_thumb_link) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";		
			
			pstmt = con.prepareStatement(sql);			
			pstmt.setInt(1, num);
			pstmt.setString(2, webtoon.getWeb_subject());
			pstmt.setString(3, webtoon.getWeb_author());
			pstmt.setString(4, webtoon.getWeb_genre());
			pstmt.setString(5, webtoon.getWeb_start());
			pstmt.setString(6, webtoon.getWeb_portal());
			pstmt.setString(7, webtoon.getWeb_info());
			pstmt.setString(8, webtoon.getWeb_ing());
			pstmt.setString(9, webtoon.getWeb_link());
			pstmt.setString(10, webtoon.getWeb_thumb_link());

			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null){try{rs.close();}catch(SQLException e){e.printStackTrace();}
			}
		}
	}
	public List<WebtoonBean> getWebtoonList(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<WebtoonBean> list = new ArrayList<WebtoonBean>();
		try{
			con = getConnection();
			
			String sql="select * from webtoon";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				WebtoonBean wb = new WebtoonBean();
				wb.setWeb_num(rs.getInt("web_num"));
				wb.setWeb_subject(rs.getString("web_subject"));
				wb.setWeb_author(rs.getString("web_author"));
				wb.setWeb_genre(rs.getString("web_genre"));
				wb.setWeb_start(rs.getString("web_start"));
				wb.setWeb_portal(rs.getString("web_portal"));
				wb.setWeb_info(rs.getString("web_info"));
				wb.setWeb_ing(rs.getString("web_ing"));
				wb.setWeb_link(rs.getString("web_link"));
				wb.setWeb_thumb_link(rs.getString("web_thumb_link"));		
				list.add(wb);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null){try{rs.close();}catch(SQLException e){e.printStackTrace();}}
		}
		return list;
	}
	
	public WebtoonBean updategetwebtoon(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WebtoonBean wb=new WebtoonBean();
		try{
			con=getConnection();
			
			String sql = "select * from webtoon where web_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			if(rs.next()){
				wb.setWeb_num(rs.getInt("web_num"));
				wb.setWeb_subject(rs.getString("web_subject"));
				wb.setWeb_author(rs.getString("web_author"));
				wb.setWeb_genre(rs.getString("web_genre"));
				wb.setWeb_start(rs.getString("web_start"));
				wb.setWeb_portal(rs.getString("web_start"));
				wb.setWeb_info(rs.getString("web_info"));
				wb.setWeb_ing(rs.getString("web_ing"));
				wb.setWeb_link(rs.getString("web_link"));
				wb.setWeb_thumb_link(rs.getString("web_thumb_link"));
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null){try{rs.close();}catch(SQLException e){e.printStackTrace();}}
		}
		
		
		return wb;
	}
	public void updateWebtoon(WebtoonBean webtoon) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			// 게시판 글 번호 구하기
			// num 구하기, 게시판 글 중에 가장 큰 번호
			String sql = "update webtoon set web_subject=?, web_author=?, web_genre=?, web_start=?, web_portal=?, web_info=?, web_ing=?, web_link=?, web_thumb_link=?"
					+ "where web_num=?";
			// 4 저장 <= 결과 실행
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, webtoon.getWeb_subject());
			pstmt.setString(2, webtoon.getWeb_author());
			pstmt.setString(3, webtoon.getWeb_genre());
			pstmt.setString(4, webtoon.getWeb_start());
			pstmt.setString(5, webtoon.getWeb_portal());
			pstmt.setString(6, webtoon.getWeb_info());
			pstmt.setString(7, webtoon.getWeb_ing());
			pstmt.setString(8, webtoon.getWeb_link());
			pstmt.setString(9, webtoon.getWeb_thumb_link());
			pstmt.setInt(10, webtoon.getWeb_num());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			}
		}

	public double getMeanScore(int web_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double result = 0;
		try {
			con = getConnection();
			String sql = "select round(avg(rec_web_grade),1) from recommend where rec_web_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, web_num);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getDouble("round(avg(rec_web_grade),1)");
			}								
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null){try{rs.close();}catch(SQLException e){e.printStackTrace();}
			}
		}
		return result;
	}
	
	public int getCountRec(int web_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			con = getConnection();
			String sql = "select count(rec_mem_num) from recommend where rec_web_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, web_num);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt("count(rec_mem_num)");
			}								
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null){try{rs.close();}catch(SQLException e){e.printStackTrace();}
			}
		}
		return result;
	}
	
	public void writeReview(WebtoonBoardBean wbb){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;
		try {
			con = getConnection();
			// 게시판 글 번호 구하기
			// num 구하기, 게시판 글 중에 가장 큰 번호
			String sql = "select max(wbb_bdnum) from webtoon_board";
			// 4 저장 <= 결과 실행
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			// 5 첫행에 데이터가 있으면 가장큰 번호+1;
			while (rs.next()) {
				num=rs.getInt("max(wbb_bdnum)")+1;
			}								
			// sql insert num구한값 => re_ref
			// re_lev 0, re_seq 0,
			pstmt.close();
			sql = "insert into webtoon_board(wbb_web_num,wbb_bdnum,wbb_mem_num,wbb_mem_nik,wbb_comment,wbb_sumlike,wbb_date) "
					+ "values(?,?,?,?,?,?,now())";		
			
			pstmt = con.prepareStatement(sql);			
			pstmt.setInt(1, wbb.getWbb_web_num());
			pstmt.setInt(2, num);
			pstmt.setString(3, wbb.getWbb_mem_num());
			pstmt.setString(4, wbb.getWbb_mem_nik());
			pstmt.setString(5, wbb.getWbb_comment());
			pstmt.setInt(6, wbb.getWbb_sumlike());

			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null){try{rs.close();}catch(SQLException e){e.printStackTrace();}
			}
		}
	}
	public List<WebtoonBoardBean> getReview(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<WebtoonBoardBean> list = new ArrayList<WebtoonBoardBean>();
		try {
			con = getConnection();
			String sql = "select * from webtoon_board where wbb_web_num=? order by wbb_date desc, wbb_sumlike desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				WebtoonBoardBean wbb = new WebtoonBoardBean();
				wbb.setWbb_web_num(rs.getInt("wbb_web_num"));
				wbb.setWbb_bdnum(rs.getInt("wbb_bdnum"));
				wbb.setWbb_mem_num(rs.getString("wbb_mem_num"));
				wbb.setWbb_mem_nik(rs.getString("wbb_mem_nik"));
				wbb.setWbb_comment(rs.getString("wbb_comment"));
				wbb.setWbb_sumlike(rs.getInt("wbb_sumlike"));
				wbb.setWbb_date(rs.getDate("wbb_date"));
				list.add(wbb);
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return list;
	}
	
	public List<WebtoonBoardBean> getTop2Review(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<WebtoonBoardBean> list = new ArrayList<WebtoonBoardBean>();
		try {
			con = getConnection();
			String sql = "select * from webtoon_board where wbb_web_num=? order by wbb_sumlike desc limit 0,2;";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				WebtoonBoardBean wbb = new WebtoonBoardBean();
				wbb.setWbb_web_num(rs.getInt("wbb_web_num"));
				wbb.setWbb_bdnum(rs.getInt("wbb_bdnum"));
				wbb.setWbb_mem_num(rs.getString("wbb_mem_num"));
				wbb.setWbb_mem_nik(rs.getString("wbb_mem_nik"));
				wbb.setWbb_comment(rs.getString("wbb_comment"));
				wbb.setWbb_sumlike(rs.getInt("wbb_sumlike"));
				wbb.setWbb_date(rs.getDate("wbb_date"));
				list.add(wbb);
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return list;
	}
	
	public int getReviewCount(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
			String sql = "select count(wbb_bdnum) from webtoon_board where wbb_web_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				count = rs.getInt("count(wbb_bdnum)");
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return count;
	}
	
	public List<FanBean> getTop2Fanart(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FanBean> list = new ArrayList<FanBean>();
		try {
			con = getConnection();
			String sql = "select * from webtoon_fanart where fa_web_num=? order by fa_sumlike desc limit 0,2;";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				FanBean fb = new FanBean();
				fb.setFa_num(rs.getInt("fa_num"));
				fb.setFa_web_num(rs.getInt("fa_web_num"));
				fb.setFa_img(rs.getString("fa_img"));
				
				list.add(fb);
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return list;
	}
	
	public boolean isRecommend(String mem_num, int web_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		try {
			con = getConnection();
			String sql = "select * from recommend where rec_web_num=? and rec_mem_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, web_num);
			pstmt.setString(2, mem_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				check = true;
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return check;
	}
	
	public boolean setCommLike(String mem_num, int wbb_bdnum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		try {
			con = getConnection();
			String sql = "select * from webtoon_likecount where wbb_mem_num=? and wbb_bdnum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_num);
			pstmt.setInt(2, wbb_bdnum);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				pstmt.close();
				sql = "delete from webtoon_likecount where wbb_mem_num=? and wbb_bdnum=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mem_num);
				pstmt.setInt(2, wbb_bdnum);
				pstmt.executeUpdate();
				
				pstmt.close();
				sql = "update webtoon_board set wbb_sumlike=wbb_sumlike-1 where wbb_bdnum=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, wbb_bdnum);
				pstmt.executeUpdate();
				
				check = true;
			}else{
				pstmt.close();
				sql = "insert into webtoon_likecount values(?,?,?);";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, wbb_bdnum);
				pstmt.setInt(2, 100);
				pstmt.setString(3, mem_num);
				
				pstmt.executeUpdate();
				
				pstmt.close();
				sql = "update webtoon_board set wbb_sumlike=wbb_sumlike+1 where wbb_bdnum=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, wbb_bdnum);
				pstmt.executeUpdate();
				
				check = false;
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return check;
	}
	
	public int sumLike(int wbb_bdnum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;
		try {
			con = getConnection();
			String sql = "select wbb_sumlike from webtoon_board where wbb_bdnum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, wbb_bdnum);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				num = rs.getInt("wbb_sumlike");
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return num;
	}
	
	public List<Integer> isCommLike(String mem_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> check = new ArrayList<Integer>();
		try {
			con = getConnection();
			String sql = "select * from webtoon_likecount where wbb_mem_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_num);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				check.add(rs.getInt("wbb_bdnum"));
			}
		} catch (Exception e) { e.printStackTrace(); }
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	e.printStackTrace();}
			if (con != null)try {con.close();} catch (SQLException e) {	e.printStackTrace();}
			if(rs!=null)try{rs.close();}catch(SQLException e){e.printStackTrace();}
		}
		return check;
	}
}
