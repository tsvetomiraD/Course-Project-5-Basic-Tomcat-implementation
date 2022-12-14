import org.apache.ibatis.session.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class Dao {
    SqlSessionFactory factory;

    public Dao()  {
        File f = new File("C:\\Users\\TD\\MyBatis\\config.xml");
        try (Reader reader = new FileReader(f)){
            factory = new SqlSessionFactoryBuilder().build(reader);
            factory.getConfiguration().addMapper(Mapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Post getPostById(int id) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.getPostById(id);
        }
    }

    public List<Post> getAllPosts() {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return Arrays.asList(postMapper.getAllPosts());
        }
    }

    public int insertPost(Post post) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.insertPost(post);
        }
    }

    public int updatePost(Post post, int id) {

        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.updatePost(post, id);
        }
    }

    public int deletePost(int id) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.deletePost(id);
        }
    }
}

