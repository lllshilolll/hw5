package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals(25, homeWork.calculate("4 * 5 - 3 + sin ( 90 ) * pow ( 2 , 3 )"), 0.0001);
        assertEquals(21, homeWork.calculate("4 * 5 - 3 + sin ( 90 / 3 ) * pow ( 2 , 3 )"), 0.0001);
    }
}