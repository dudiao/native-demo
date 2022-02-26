package cn.nboot.nativedemo.initdata;

import lombok.Getter;

/**
 * SQL类型
 *
 * @author songyinyin
 */
@Getter
public enum SqlType {

  /**
   * 建表语句
   */
  SCHEMA(0, "schema"),
  /**
   * 初始化数据
   */
  DATA(1, "data")
  ;

  SqlType(int index, String type) {
    this.index = index;
    this.type = type;
  }

  private long index;
  private String type;

  public static SqlType getSqlType(long index) {
    long newIndex = index % 2;
    for (SqlType value : values()) {
      if (value.index == newIndex) {
        return value;
      }
    }
    throw new IllegalStateException("illegal index: " + index);
  }
}
