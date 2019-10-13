package com.atrariksa.testng.test.simple;

import com.atrariksa.testng.simple.PlainJavaGreeter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlainJavaGreeterTest {
    @Test()
    public void testPlainJavaGreeter() {

        PlainJavaGreeter plainJavaGreeter = new PlainJavaGreeter();
        Assert.assertNotNull(plainJavaGreeter.greet());
        Assert.assertEquals(plainJavaGreeter.greet(), "hello");
        Assert.assertNotNull(plainJavaGreeter.greet("notNull"));
        Assert.assertEquals(plainJavaGreeter.greet("atrariksa"), "hello, atrariksa");
    }
}
