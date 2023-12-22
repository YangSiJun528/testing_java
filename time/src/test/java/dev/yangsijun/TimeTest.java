package dev.yangsijun;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map.Entry;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeTest {

  @Test
  void printDefaultTimeZone() {
    // LocalDateTime의 시간대는 JVM의 기본 시간대 설정이나 환경변수(user.timezone)에 의존한다.
    // [Intellij] Run - Edit Configurations -> Environment - VM options에 `-Duser.timezone={시간대}` 설정해서 변경 가능
    //   참고 : https://howtodoinjava.com/java/date-time/setting-jvm-timezone/
    //         https://velog.io/@ashappyasikonw/Java-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%97%90%EC%84%9C-Default-Time-Zone%EC%9D%80-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%84%A4%EC%A0%95%EB%90%98%EB%8A%94%EA%B0%80
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.printf("DisplayName: %s, ID: %s, Offset: %s%n",
        TimeZone.getDefault().getDisplayName(),
        TimeZone.getDefault().getID(),
        TimeZone.getDefault().getRawOffset());
    System.out.println("------------------------------------------------------------------------------------------");
  }

  @Test
  void localDataTimeHasNoZoneInfo() {
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime kstZone = dateTime.atZone(ZoneId.of("Asia/Seoul"));
    ZonedDateTime pstZone = dateTime.atZone(ZoneId.of("America/Los_Angeles"));
    System.out.println("KtsZone: " + kstZone);
    System.out.println("KtsZoneToLocalDate: " + kstZone.toLocalDateTime());
    System.out.println("----------------------");
    System.out.println("KtsZone: " + pstZone);
    System.out.println("KtsZoneToLocalDate: " + pstZone.toLocalDateTime());
    System.out.println("----------------------");
    System.out.println(kstZone.toLocalDateTime().toString() + " == " + pstZone.toLocalDateTime().toString());

    // LocalDateTime은 시간대 정보를 포함하지 않는다.
    assertEquals(kstZone.toLocalDateTime().toString(), pstZone.toLocalDateTime().toString());
  }
}
