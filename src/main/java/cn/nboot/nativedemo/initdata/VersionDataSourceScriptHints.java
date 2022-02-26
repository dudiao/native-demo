package cn.nboot.nativedemo.initdata;

import org.springframework.aot.context.bootstrap.generator.infrastructure.nativex.NativeConfigurationRegistry;
import org.springframework.aot.context.bootstrap.generator.infrastructure.nativex.NativeResourcesEntry;
import org.springframework.nativex.AotOptions;
import org.springframework.nativex.type.NativeConfiguration;

public class VersionDataSourceScriptHints implements NativeConfiguration {

  @Override
  public boolean isValid(AotOptions aotOptions) {
    return NativeConfiguration.super.isValid(aotOptions);
  }

  @Override
  public void computeHints(NativeConfigurationRegistry registry, AotOptions aotOptions) {
    registry.resources().add(NativeResourcesEntry.ofBundle("db/**/*.sql"));
  }
}
