package cn.nboot.nativex.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Jackson序列化配置
 *
 * @author songyinyin
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {

  @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
  private String NORM_DATETIME_PATTERN;
  public static String NORM_DATE_PATTERN = "yyyy-MM-dd";
  public static String NORM_TIME_PATTERN = "HH:mm:ss";

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer() {
    return builder -> {
      builder.locale(Locale.CHINA);
      builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
      builder.simpleDateFormat(NORM_DATETIME_PATTERN);
      builder.modules(new JavaTimeModule(NORM_DATETIME_PATTERN));
    };
  }

  static class JavaTimeModule extends SimpleModule {

    private String datetimePattern;

    public JavaTimeModule(String datetimePattern) {
      this.datetimePattern = datetimePattern;
    }

    public JavaTimeModule() {
      super(PackageVersion.VERSION);
      this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(datetimePattern)));
      this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
      this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));
      this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(datetimePattern)));
      this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
      this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));
    }
  }
}
