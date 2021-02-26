import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiTests extends BaseTest {

    private static String resourcePath = "/v1/post";
    private static String resourcePath1 = "/v1/comment";
    private static String resourcePath2 = "/v1/comments";


    private static Integer CreatePost = 0;
    private static Integer CreateComment = 0;
    private static Integer CreatePost1 = 0;
    private static Integer CreateComment1 = 0;

    @Test
    public void Test_Creat_Post_Success() {

        String jsonBody = "{\n" +
                " \"title\": \"titulo\" ,\n" +
                "\"content\": \"123456\" \n" +
                "}";

        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(jsonBody)
                .when().post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        CreatePost = jsonPathEvaluator.get("id");


    }

    @Test
    public void Test_Get_Post_Success() {
        given()
                .spec(RequestSpecs.generateToken())
                .when().get(resourcePath + "s")
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());


    }



    @Test
    public void Test_Get_Post_Id() {


        given()
                .spec(RequestSpecs.generateToken())
                .when().get(resourcePath + "/" + CreatePost.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());



    }
    @Test
    public void Test_Put_Success() {
        String jsonBody = "{\n" +
                " \"title\": \"titulo\" ,\n" +
                "\"content\": \"123456\" \n" +
                "}";

        given()
                .spec(RequestSpecs.generateToken())
                .body(jsonBody)
                .when().put(resourcePath + "/" + CreatePost.toString())
                .then()
                .body("message", equalTo("Post updated"))
                .and()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
}
    @Test
    public void Test_Success_Delete_Post() {
        given()
                .spec(RequestSpecs.generateToken())
                .when().delete(resourcePath + "/" + CreatePost.toString())
                .then()
                .body("message", equalTo("Post deleted"))
                .and()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }
    @Test
    public void Test_Create_Comment_Success() {

        String jsonBody = "{\n" +
                " \"name\": \"pepito\" ,\n" +
                "\"comment\": \"comment1\" \n" +
                "}";

        Response response = given().auth()
                .basic("testuser", "testpass")
                .body(jsonBody)
                .when().post(resourcePath1 + "/" + CreatePost.toString());

        JsonPath jsonPathEvaluator = response.jsonPath();
        CreateComment = jsonPathEvaluator.get("id");


    }
    @Test
    public void Test_Create_Post_Success1() {

        String jsonBody = "{\n" +
                " \"title\": \"titulo1\" ,\n" +
                "\"content\": \"1234567\" \n" +
                "}";

        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(jsonBody)
                .when().post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        CreatePost1 = jsonPathEvaluator.get("id");


    }


    @Test
    public void Test_Success_Comment() {

        Response response = given().auth()
                .basic("testuser", "testpass")
                .when().get(resourcePath2  + "/" + CreateComment.toString());

       // JsonPath jsonPathEvaluator = response.jsonPath();
        //CreateComment = jsonPathEvaluator.get("id");

    }
    @Test
    public void Test_Success_CommentId() {

                 given().auth()
                 .basic("testuser", "testpass")
                 .when().get(resourcePath1 + "/" + CreatePost.toString() + "/" + CreateComment.toString())
                 .then().statusCode(200)
                .spec(ResponseSpecs.defaultSpec());


    }

    @Test
    public void Test_Create_Comment_Success1() {

        String jsonBody = "{\n" +
                " \"name\": \"test\" ,\n" +
                "\"comment\": \"testcomment\" \n" +
                "}";

        Response response = given().auth()
                .basic("testuser", "testpass")
                .body(jsonBody)
                .when().post(resourcePath1 + "/" + CreatePost1.toString());

        JsonPath jsonPathEvaluator = response.jsonPath();
        CreateComment1 = jsonPathEvaluator.get("id");


    }


    @Test
    public void Test_Success_Comment_Put() {

        String jsonBody = "{\n" +
                " \"name\": \"pepito2\" ,\n" +
                "\"comment\": \"comment2\" \n" +
                "}";

        given().auth()
                .basic("testuser", "testpass")
                .body(jsonBody)
                .when()
                .put(resourcePath1 + "/" + CreatePost.toString() + "/" + CreateComment.toString())
                .then()
                .body("message", equalTo("Comment updated"))
                .and().statusCode(200)
                .spec(ResponseSpecs.defaultSpec());



    }
    @Test
    public void Test_Success_Delete_Comment() {


        given().auth()
                .basic("testuser", "testpass")
                .when().delete(resourcePath1 + "/" + CreatePost.toString() + "/" + CreateComment.toString())
                .then()
                .body("message", equalTo("Comment deleted"))
                .and().statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
}
}


