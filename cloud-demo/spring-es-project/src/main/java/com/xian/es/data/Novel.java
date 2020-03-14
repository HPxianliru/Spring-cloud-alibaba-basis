package com.xian.es.data;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.Date;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/02/02 2:12 下午
 */
@Document(indexName = "book",type="novel")
@Data
public class Novel {

    @Id
    private Long id;

    private String title;

    private String author;

    private Integer word_count;

    private Date publish_data;

    public Novel(){
        super();
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", word_count=" + word_count +
                ", publish_data=" + publish_data +
                '}';
    }
}
