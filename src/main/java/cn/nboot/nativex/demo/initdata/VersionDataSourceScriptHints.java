package cn.nboot.nativex.demo.initdata;

import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.type.NativeConfiguration;

@NativeHint(trigger = VersionDataSourceScriptDatabaseInitializer.class,
    resources = @ResourceHint(patterns = {"cn/nboot/nativex/demo/initdata/data/*.sql", "cn/nboot/nativex/demo/initdata/schema/*.sql"}))
public class VersionDataSourceScriptHints implements NativeConfiguration {

}
