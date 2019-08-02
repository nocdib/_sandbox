package hello;

/**
 * Greetings is a model for the greeting representation. A GET request to the API
 * should return something like the following:
 * <code>
 * {
 *     "id": 1,
 *     "content": "Hello, World!"
 * }
 * </code>
 *
 * @author      Greg Jay
 * @author      https://spring.io/guides/gs/rest-service/
 * @version     1.0
 * @since       1.0
 */
public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}