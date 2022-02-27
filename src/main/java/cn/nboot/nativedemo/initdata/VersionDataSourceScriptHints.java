package cn.nboot.nativedemo.initdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.context.bootstrap.generator.infrastructure.nativex.NativeConfigurationRegistry;
import org.springframework.aot.context.bootstrap.generator.infrastructure.nativex.NativeResourcesEntry;
import org.springframework.nativex.AotOptions;
import org.springframework.nativex.type.NativeConfiguration;

@Slf4j
public class VersionDataSourceScriptHints implements NativeConfiguration {

  @Override
  public boolean isValid(AotOptions aotOptions) {
    return NativeConfiguration.super.isValid(aotOptions);
  }

  @Override
  public void computeHints(NativeConfigurationRegistry registry, AotOptions aotOptions) {
    log.info("start read sql script: db/**/*.sql");
    registry.resources().add(NativeResourcesEntry.of("db/**/*.sql"));
  }
}
