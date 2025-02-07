package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import org.testng.annotations.Test;

import java.util.Objects;

public class ExampleTest1 extends BaseClass {

    @Test
    public void exampleTestOpening() {
        String title = driver.getTitle();
        assert Objects.equals(title, "OrangeHRM") : "Test Failed - Title is not matching";
        System.out.println("Test Passed - Title is matching");
    }
}
