package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Blog;
import utility.ConnectionManager;

public class BlogDao implements BlogDaoInterface {
	
	private static final String insert_blog = "INSERT INTO BLOG(blogid, blogtitle, blogdesc, postedon) VALUES(id_seq.nextval,?,?,?)";
	private static final String select_all_blog = "SELECT * FROM BLOG";
	private static final String delete_blog = "DELETE FROM BLOG WHERE blogid=?";
	
	@Override
	public void insertBlog(Blog blog) throws SQLException {
		try {
			Connection con = ConnectionManager.getconnection();
			PreparedStatement ps = con.prepareStatement(insert_blog);
			ps.setString(1, blog.getBlogtitle());
			ps.setString(2, blog.getBlogDesc());
			ps.setDate(3, java.sql.Date.valueOf(blog.getPostedOn()));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Blog selectBlog(int blogid) throws Exception {
		Blog blog=new Blog();
		Connection con = ConnectionManager.getconnection();
		PreparedStatement ps=con.prepareStatement("SELECT * FROM blog WHERE blogid=?");
		ps.setInt(1, blogid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			blog.setBlogid(rs.getInt(1));
			blog.setBlogtitle(rs.getString(2));
			blog.setBlogDesc(rs.getString(3));
			blog.setPostedOn(rs.getDate(4).toLocalDate());
		}
		return blog;
	}

	@Override
	public List<Blog> selectAllBlog() {
		Blog blog = null;
		List<Blog> blogList = new ArrayList<>();
		try {
			Connection con = ConnectionManager.getconnection();
			PreparedStatement ps = con.prepareStatement(select_all_blog);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				String blogtitle = rs.getString(2);
				String blogdescription = rs.getString(3);
				LocalDate date = rs.getDate(4).toLocalDate();
				
				blog = new Blog();
				blog.setBlogid(id);
				blog.setBlogtitle(blogtitle);
				blog.setBlogDesc(blogdescription);
				blog.setPostedOn(date);
				blogList.add(blog);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return blogList;
	}

	@Override
	public boolean deleteBlog(int blogid) throws SQLException {
		System.out.println(blogid);
		Connection con;
		boolean rowDeleted = false;
		try {
			con = ConnectionManager.getconnection();
			PreparedStatement ps = con.prepareStatement(delete_blog);
			ps.setInt(1, blogid);
			rowDeleted = ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDeleted;
	}

	@Override
	public boolean updateBlog(Blog blog) throws SQLException, Exception {
		Connection con = ConnectionManager.getconnection();
		PreparedStatement ps = con.prepareStatement("UPDATE blog SET blogtitle=?,blogdesc=?,postedon=? WHERE blogid=?");
		ps.setString(1, blog.getBlogtitle());
		ps.setString(2, blog.getBlogDesc());
		ps.setDate(3, java.sql.Date.valueOf(blog.getPostedOn()));
		ps.setInt(4, blog.getBlogid());
		ps.execute();
		return true;
	}

}