package dev.yangsijun;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static dev.yangsijun.Week.*;
import static org.junit.jupiter.api.Assertions.*;

/*
Java 8 이후의 버전에서 제공하는 기능 일부 정리
 */
public class MainTest {

    // Project Amber를 주로 참고 - https://www.baeldung.com/java-project-amber, https://luvstudy.tistory.com/263

    /*
    * 요약
    * ~ Java 11
    *   - var: Local-Variable Type Interface - 로컬 변수 타입 추론
    * ~ Java 17
    *   - record: get/setter, equals, hashCode, toString 을 기본 제공하는 final 클래스
    *   - switch 향상: yield 키워드, pattern matching, arrow(->) 등
    *   - sealed: 자식 객체를 허용해야만 구현/상속 가능한 클래스
    *   - instanceof 향상: pattern matching
    * ~ Java 21
    *   - record pattern : instanceof, switch 에서 사용, 레코드 클래스의 인스턴스를 분해하여 데이터 쿼리를 수행하는 기능(설명하기 어렵다)
     */

    // ~ Java 11

    // Local-Variable Type Interface - 로컬 변수 타입 추론
    // 로컬 변수 타입을 'var'로 설정할 수 있다. 컴파일 타임에 컴파일러가 타입을 추론해서 정해줌
    @Test
    void addition() {
        // 변수 선언
        var list = new ArrayList<String>();	//ArrayList<String> 으로 추론
        var numbers = List.of(1, 2, 3, 4, 5);	//List<Integer> 으로 추론

        // 클래스 출력
        for (var number : numbers){
            System.out.println(number.getClass());
            break;
        }
        System.out.println(numbers.getClass());

        // 테스트
        for (var number : numbers){
            assertEquals(number.getClass(), Integer.class);
            break;
        }
        assertEquals(list.getClass(), ArrayList.class);

        //추가 : 람다 매개변수에 대한 지역 변수 구문
        // Local Variable Syntax for Lambda Parameters

        // var을 사용하면 간결함을 잃지 않고, 지역 변수와 람다 형식에 어노테이션을 사용할 수 있다.
        numbers.stream()
                .reduce(0, (@NotNull var a, @Nullable var b) -> a + b);
    }


    // ~ Java 17

    //switch 문 향상된 기능 제공
    @Test
    void testEnhancementSwitch() {
        assertEquals(getNumLetters(FRIDAY), 6);
        assertEquals(getNumLetters(TUESDAY), 7);
        assertEquals(getNumLetters(WEDNESDAY), 9);
        assertEquals(getNumLetters(null), 0);
    }

    // 화살표(->)를 사용해 간략하게 switch 문 표현 가능, 코드블록을 사용하여 여러 줄 표시 가능
    // switch 문에서 명시적으로 값을 리턴하기 위해 키워드 yield 추가
    private static int getNumLetters(Week day) {
        return switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY -> 7;
            // ~ Java 21, 이제 null도 case 절에 포함 가능
            case null, default -> {
                if (day==null) {
                    yield 0;
                }
                String s = day.name();
                yield s.length();
            }
        };
    }

    //record 객체
    // 객체간의 값 이동을 위한 객체인 DTO를 쉽게 사용할 수 있음, 불변 객체(모든 변수 수정 불가능, 단 Collection 요소 수정은 가능)이다.
    // getter/setter, equals/hashcode, toString 등을 알아서 구현헤준다.
    // final 클래스라서 상속 불가.
    record Point(int x, int y) { }

    // 동일한 역할의 일반적인 class와 비교
    /*
    static final class Point {
        private final int x;
        private final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Point) obj;
            return this.x == that.x &&
                    this.y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point[" +
                    "x=" + x + ", " +
                    "y=" + y + ']';
        }
    }
     */

    @Test
    void testHashCode() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(3, 4);

        assertEquals(point1.hashCode(), point2.hashCode());
        assertNotEquals(point1.hashCode(), point3.hashCode());
    }

    @Test
    void testEquals() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(3, 4);

        assertEquals(point1, point2);
        assertNotEquals(point1, point3);
    }

    @Test
    void testToString() {
        Point point = new Point(1, 2);
        assertEquals("Point[x=1, y=2]", point.toString());
    }

    //Text Block
    // 이스케이프 시퀀스 없이 멀티라인 문자열 작성 가능
    @Test
    void testTextBlock() {
        String str = """
                This
                is
                text block""";

        String expected = "This\nis\ntext block";

        assertEquals(expected, str);
    }

    // (instanceof) pattern matching
    @Test
    void testInstanceOfPatternMatching() {
        Object obj = "Hello, World!";

        if (obj instanceof String s) {
            assertEquals("Hello, World!", s);
        } else {
            fail("Expected an instance of String, but found: " + obj.getClass());
        }

        Object point = new Point(10,20);

        // ~ Java 21
        if (point instanceof Point(int x, int y)) {
            assertEquals(30, x+y);
        } else {
            fail("Expected an instance of Point, but found: " + obj.getClass());
        }
    }


    // sealed class - ~ Java 17
    // sealed(봉인된) 클래스, 자식 객체를 명시적으로 제한
    public abstract sealed class Vehicle permits Car, Truck, Aircraft { }

    public final class Truck extends Vehicle { }
    public non-sealed class Car extends Vehicle { }
    public sealed class Aircraft extends Vehicle permits Airplane, Helicopter { }
    public final class Airplane extends Aircraft { }
    public final class Helicopter extends Aircraft { }

    @Test
    void testShapeSwitchStatement() {
        Vehicle truck = new Truck();
        Vehicle car = new Car();
        Vehicle aircraft  = new Airplane();

        assertEquals("truck", getShapeType(truck));
        assertEquals("car", getShapeType(car));
        assertEquals("aircraft", getShapeType(aircraft));
    }

    private static String getShapeType(Vehicle vehicle) {
        // case 문의 조건으로 여러 타입(자식 클래스) 사용 가능 - Enhanced type checking - ~ Java 17
        return switch (vehicle) {
            case Truck t -> "truck";
            case Car c -> "car";
            case Aircraft a -> "aircraft";
        };
    }

    //record pattern
/*    static void recordInference(MyPair<String, Integer> pair){
        switch (pair) {
            case MyPair(var f, var s) -> null
             // Inferred record pattern MyPair<String,Integer>(var f, var s)
        }
    }*/
}
