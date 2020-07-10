package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreateLikePostTest extends FunctionalTests {

    private static final String USER_API = "/blog/user/";

    @Test
    public void likingPostByConfirmedUserShouldReturnOkStatus() {
        String confirmedUserId = "1";
        String postId = "1";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .post(USER_API + confirmedUserId + "/like/" + postId);
    }

    @Test
    public void likingPostByNotConfirmedUserReturnsBadRequestStatus() {
        String nonConfirmedUserId = "2";
        String postId = "2";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(USER_API + nonConfirmedUserId + "/like/" + postId);
    }

    @Test
    public void likingPostByAuthorShouldReturnBadRequestStatus() {
        String authorId = "1";
        String postId = "2";

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(USER_API + authorId + "/like/" + postId);
    }

    @Test
    public void likingOnePostTwoTimeByTheSameUserShouldReturnOkStatus() {
        String userId = "1";
        String postId = "1";

        for (int i = 0; i < 2; i++) {
            given().accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .post(USER_API + userId + "/like/" + postId);
        }
    }
}
