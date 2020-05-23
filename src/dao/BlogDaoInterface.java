package dao;

import java.sql.SQLException;
import java.util.List;

import model.Blog;

public interface BlogDaoInterface {
	void insertBlog(Blog blog) throws SQLException;
	Blog selectBlog(int blogid) throws Exception;
	List<Blog> selectAllBlog();
	boolean deleteBlog(int blogid) throws SQLException;
	boolean updateBlog(Blog blog) throws SQLException, Exception;
}
