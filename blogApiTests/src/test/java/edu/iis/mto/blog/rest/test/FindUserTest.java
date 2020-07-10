package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class FindUserTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";

    @Test
    public void findingUsersShouldReturnCollectionWithoutRemovedUsers() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .param("searchString", "domain.com")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .and()
               .body("size", is(2))
               .when()
               .get(USER_API + "/find");
    }
}
