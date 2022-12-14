import org.apache.ibatis.annotations.*;

public interface Mapper {
    @Select("SELECT * FROM posts")
    public Post[] getAllPosts();

    @Select("SELECT * FROM posts WHERE id=#{id}")
    public Post getPostById(int id);

    @Delete("DELETE FROM posts WHERE id=#{id}")
    public int deletePost(int id);

    @Insert("INSERT INTO posts(userId, body, title) VALUES (#{userId},#{body},#{title}")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insertPost(Post post);

    @Update("UPDATE posts SET body = {body} WHERE id = #{id}")
    int updatePost(Post post, int id);

}
